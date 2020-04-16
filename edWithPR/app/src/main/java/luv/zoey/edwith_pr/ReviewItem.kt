package luv.zoey.edwith_pr

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewItem(
    var content : String,
    var rating : Float
) : Parcelable