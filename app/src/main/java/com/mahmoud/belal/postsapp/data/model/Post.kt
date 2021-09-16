package com.mahmoud.belal.postsapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Post(
    @SerializedName("name")
    val title: String = "",

    @SerializedName("image")
    val url: String = ""

) : Parcelable

data class PostsList(
    @SerializedName("data")
    val postList: List<Post> = listOf(),
    @SerializedName("code")
    val code: Int = 0
)

@Entity(tableName = "postTable" , indices = [Index(value = ["title"], unique = true)])
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val postId: Long,


    @ColumnInfo(name = "title")
    val postTitle: String = "",

    @ColumnInfo(name = "url")
    val postImage: String = ""


)


fun List<PostEntity>.asPostsList(): List<Post> = this.map {
    Post(it.postTitle, it.postImage)
}

fun PostEntity.asPostEntity(): Post =
    Post(this.postTitle, this.postImage)


fun Post.asPostEntity(): PostEntity =
    PostEntity(0, this.title, this.url)

