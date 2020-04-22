package luv.zoey.edwith_pr.Review

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//액티비티간의 객체 전달을 위해선
//Parcelize를 해서 Parcelable 하게 만들어 주어야
//get/put ParcelableArrayListExtra 함수를 사용할 수 있게 된다.
@Parcelize
data class ReviewItem(
    var content : String,
    var rating : Float
) : Parcelable

// * ParcelableArrayListExtra 의 ReviewItem 타입으로 만들기 위함이다.
