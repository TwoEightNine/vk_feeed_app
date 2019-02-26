package global.msnthrp.feeed.imageviewer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.imageviewer.repo.ImageViewerRepository
import global.msnthrp.feeed.models.attachments.Photo
import javax.inject.Inject

class ImageViewerViewModel(private val repo: ImageViewerRepository) : ViewModel() {

    /**
     * @param callback true if now liked, false if not, null if error occurred
     */
    fun likeOrNot(wallPost: WallPost, callback: (Boolean?) -> Unit) {
        if (!wallPost.isLiked) {
            repo.like(wallPost) {
                callback(if (it) true else null)
            }
        } else {
            repo.unlike(wallPost) {
                callback(if (it) false else null)
            }
        }
    }

    fun copyImage(photo: Photo, callback: (Boolean) -> Unit) {
        repo.copyImage(photo, callback)
    }

    class Factory @Inject constructor(private val repo: ImageViewerRepository) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = ImageViewerViewModel(repo) as T
    }
}