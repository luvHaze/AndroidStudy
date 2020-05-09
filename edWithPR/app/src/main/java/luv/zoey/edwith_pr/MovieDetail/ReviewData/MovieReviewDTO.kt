package luv.zoey.edwith_pr.MovieDetail.ReviewData

import android.os.Parcelable
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize

@Parcelize
open class MovieReviewDTO(
    var id: Int? = null,
    var writer: String? = null,
    var movieId: Int? = null,
    var writer_image: String? = null,
    var time: String? = null,
    var timestamp: Int? = null,
    var rating: Float? = null,
    var contents: String? = null,
    var recommend: Int? = null
) :Parcelable,RealmObject()



