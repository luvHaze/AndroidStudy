package luv.zoey.edwith_pr.MovieDetail.ReviewData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieReviewDTO(
    var id: Int,
    var writer: String,
    var movieId: String,
    var writer_image: String?,
    var time: String,
    var timestamp: Int?,
    var rating: Float,
    var contents: String,
    var recommend: Int
) : Parcelable



