package global.msnthrp.feeed.imageviewer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import global.msnthrp.feeed.App
import global.msnthrp.feeed.R
import global.msnthrp.feeed.dialogs.activities.DialogsActivity
import global.msnthrp.feeed.feed.activities.GroupActivity
import global.msnthrp.feeed.feed.model.PostPhoto
import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.imageviewer.adapters.FullScreenImageAdapter
import global.msnthrp.feeed.imageviewer.viewmodels.ImageViewerViewModel
import global.msnthrp.feeed.models.owner.Group
import global.msnthrp.feeed.services.DownloadFileService
import global.msnthrp.feeed.utils.*
import kotlinx.android.synthetic.main.image_viewer_bottom.*
import kotlinx.android.synthetic.main.image_viewer_content.*
import java.io.File
import javax.inject.Inject

class ImageViewerActivity : AppCompatActivity() {

    companion object {
        const val ARG_PHOTOS = "photos"
        const val ARG_WALL_POSTS = "wallPosts"
        const val ARG_POSITION = "position"
        const val REQUEST_STORAGE = 1836

        fun launch(context: Context?, photos: List<PostPhoto>, wallPosts: List<WallPost>, position: Int = 0) {
            context?.startActivity(Intent(context, ImageViewerActivity::class.java).apply {
                putExtra(ARG_PHOTOS, Array(photos.size) { photos[it] })
                putExtra(ARG_WALL_POSTS, Array(wallPosts.size) { wallPosts[it] })
                putExtra(ARG_POSITION, position)
            })
        }
    }

    @Inject
    lateinit var viewModelFactory: ImageViewerViewModel.Factory
    private lateinit var viewModel: ImageViewerViewModel

    private var presetPosition = 0
    private val photos = arrayListOf<PostPhoto>()
    private val wallPosts = arrayListOf<WallPost>()

    private val permissionHelper by lazy { PermissionHelper(this) }
    private val bottomSheet by lazy { BottomSheetBehavior.from(llBottomSheet) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_image_viewer)
        initData()
        initViewModel()
        initViewPager()
        initButtons()
        invalidatePhoto()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[ImageViewerViewModel::class.java]
    }

    private fun initViewPager() {
        val urls = photos.map { it.photo.getMaxPhoto().url }.toMutableList()
        with(viewPager) {
            adapter = FullScreenImageAdapter(
                this@ImageViewerActivity,
                urls,
                { finish() },
                { rlContent.toggle() }
            )
            addOnPageChangeListener(SwipeListener())
            currentItem = presetPosition
        }
    }

    private fun initButtons() {
        ivShare.setOnClickListener { bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED }
        ivDownload.setOnClickListener { downloadPhoto() }
        ivLike.setOnClickListener { likeOrNot() }
        rlHeader.setOnClickListener { openGroup() }
        tvText.setOnClickListener { showAlert(this, getWallPost()?.text) }
        ivCopy.setOnClickListener { copyPhoto() }

        llShareImageInMessage.setOnClickListener {
            bottomAction { DialogsActivity.launch(this, getPhoto().photo) }
        }
        llShareImageViaOther.setOnClickListener {
            bottomAction { shareImage(this, getUrl()) }
        }

        llSharePostInMessage.setOnClickListener {
            bottomAction { DialogsActivity.launch(this, getWallPost() ?: return@setOnClickListener) }
        }
        llSharePostViaOther.setOnClickListener {
            bottomAction { shareText(this, getWallPost()?.link ?: return@setOnClickListener) }
        }
    }

    private fun invalidatePhoto() {
        getWallPost()?.apply {
            ivPhoto.load(owner?.getPhoto())
            tvOwner.text = owner?.getTitle()?.toLowerCase()
            tvDate.text = getTime(date, full = true).toLowerCase()
            ivLike.setImageResource(
                if (isLiked) R.drawable.ic_like_red else R.drawable.ic_like
            )
            tvText.setVisible(text.isNotEmpty())
            tvText.text = text
        }
    }

    private fun initData() {
        val extras = intent.extras ?: return

        extras.getParcelableArray(ARG_PHOTOS)?.let { array ->
            photos.addAll(array.mapNotNull { it as? PostPhoto })
        }
        extras.getParcelableArray(ARG_WALL_POSTS)?.let { array ->
            wallPosts.addAll(array.mapNotNull { it as? WallPost })
        }
        presetPosition = extras.getInt(ARG_POSITION, 0)
    }

    private fun likeOrNot() {
        val wallPost = getWallPost() ?: return

        viewModel.likeOrNot(wallPost) { isLiked ->
            if (isLiked == null) {
                showToast(this, R.string.post_not_liked)
            } else {
                wallPost.isLiked = isLiked
                ivLike.setImageResource(
                    if (isLiked) R.drawable.ic_like_red else R.drawable.ic_like
                )
            }
        }
    }

    private fun copyPhoto() {
        viewModel.copyImage(getPhoto().photo) { copied ->
            showToast(
                this,
                if (copied) {
                    R.string.image_copied
                } else {
                    R.string.image_not_copied
                }
            )

        }
    }

    private fun openGroup() {
        val group = getWallPost()?.owner as? Group ?: return
        GroupActivity.launch(this, group)
    }

    private fun downloadPhoto() {
        permissionHelper.doOrRequest(
            PermissionHelper.STORAGE,
            getString(R.string.need_access_to_storage)
        ) {
            startService()
        }
    }

    private inline fun bottomAction(action: () -> Unit) {
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        action()
    }

    private fun startService() {
        val url = getUrl()
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            getNameFromUrl(url)
        )
        DownloadFileService.startService(this, url, file.absolutePath, false) {
            showToast(this, if (it != null) R.string.file_downloaded else R.string.file_not_downloaded)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * current photo
     */
    private fun getPhoto() = photos[viewPager.currentItem]

    /**
     * wall post that owns current photo
     */
    private fun getWallPost() = wallPosts.firstOrNull { it.toString() == getPhoto().wallPostId }

    /**
     * url of current photo in the highest resolution
     */
    private fun getUrl() = getPhoto().photo.getMaxPhoto().url

    private inner class SwipeListener : ViewPager.OnPageChangeListener {

        override fun onPageSelected(p0: Int) {
            invalidatePhoto()
        }

        override fun onPageScrollStateChanged(p0: Int) {}

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
    }
}