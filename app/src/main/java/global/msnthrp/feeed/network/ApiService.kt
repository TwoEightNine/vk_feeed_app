package global.msnthrp.feeed.network

import global.msnthrp.feeed.dialogs.model.DialogsResponse
import global.msnthrp.feeed.feed.model.FeedResponse
import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.models.attachments.Attachment
import global.msnthrp.feeed.models.attachments.Photo
import global.msnthrp.feeed.models.owner.Group
import global.msnthrp.feeed.models.owner.User
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*


interface ApiService {

    companion object {
        const val NO_TOKEN_HEADER_KEY = "No-Token"
        const val NO_TOKEN_HEADER = "$NO_TOKEN_HEADER_KEY: 1"
    }

    @GET("users.get")
    fun getUsers(
        @Query("user_ids") userIds: String,
        @Query("fields") fields: String = User.FIELDS
    ): Single<BaseResponse<List<User>>>

    @GET("photos.copy")
    fun copyPhoto(
        @Query("owner_id") ownerId: Int,
        @Query("photo_id") photoId: Int,
        @Query("access_key") accessKey: String = ""
    ): Single<BaseResponse<Int>>

    @GET("stats.trackVisitor")
    fun trackVisitor(): Single<BaseResponse<Int>>

    @GET("newsfeed.get?filter=post&count=100")
    fun getNewsfeed(@Query("start_from") nextFrom: String?): Single<BaseResponse<FeedResponse>>

    @GET("newsfeed.getRecommended?count=100")
    fun getSuggestions(@Query("start_from") nextFrom: String?): Single<BaseResponse<FeedResponse>>

    @GET("wall.get?extended=1")
    fun getWall(
        @Query("owner_id") ownerId: Int,
        @Query("count") count: Int,
        @Query("offset") offset: Int
    ): Single<BaseResponse<FeedResponse>>

    @GET("fave.getPosts?extended=1")
    fun getFavorites(
        @Query("count") count: Int,
        @Query("offset") offset: Int
    ): Single<BaseResponse<FeedResponse>>

    @GET("likes.add?type=post")
    fun like(
        @Query("owner_id") ownerId: Int,
        @Query("item_id") itemId: Int
    ): Single<BaseResponse<Any>>

    @GET("likes.delete?type=post")
    fun unlike(
        @Query("owner_id") ownerId: Int,
        @Query("item_id") itemId: Int
    ): Single<BaseResponse<Any>>

    @GET("groups.search?type=group&count=100")
    fun searchGroups(
        @Query("q") query: String
    ): Single<BaseResponse<ListResponse<Group>>>

    @GET("groups.get?extended=1&count=100")
    fun getGroups(
        @Query("user_id") userId: Int
    ): Single<BaseResponse<ListResponse<Group>>>

    @GET("groups.join")
    fun joinGroup(
        @Query("group_id") groupId: Int
    ): Single<BaseResponse<Int>>

    @GET("groups.leave")
    fun leaveGroup(
        @Query("group_id") groupId: Int
    ): Single<BaseResponse<Int>>

    @GET("photos.get?album_id=profile&rev=1")
    fun getAvatar(
        @Query("owner_id") ownerId: Int
    ): Single<BaseResponse<ListResponse<Photo>>>

    @GET("messages.getConversations?filter=all&extended=1&count=200")
    fun getDialogs(): Single<BaseResponse<DialogsResponse>>

    @GET("messages.send")
    fun send(
        @Query("peer_id") peerId: Int,
        @Query("message") message: String,
        @Query("attachment") attachment: String,
        @Query("random_id") randomId: Int
    ): Single<BaseResponse<Int>>

    @GET
    fun downloadFile(@Url fileUrl: String): Single<ResponseBody>

}