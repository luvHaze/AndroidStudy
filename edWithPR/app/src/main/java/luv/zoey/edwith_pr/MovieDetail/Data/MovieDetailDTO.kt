package luv.zoey.edwith_pr.MovieDetail.Data

import io.realm.Realm
import io.realm.RealmObject

open class MovieDetailDTO(
    var title: String? = null,                  // 제목
    var id: Int? = null,                        // ID
    var date: String? = null,                  // 날짜
    var user_rating: Float? = null,            // 유저 평점
    var audience_rating: Float? = null,        // ㄱ
    var reviewer_rating: Float? = null,
    var reservation_rate: Float? = null,
    var reservation_grade: Int? = null,
    var grade: Int? = null,
    var thumb: String? = null,                 // 썸네일 URL
    var image: String? = null,                 // 이미지 URL
    var photos: String? = null,
    var videos: String? = null,
    var outlinks: String? = null,
    var genre: String? = null,
    var duration: Int? = null,
    var audience: Int? = null,
    var synopsis: String? = null,
    var director: String? = null,
    var actor: String? = null,
    var like: Int? = null,
    var dislike: Int? = null
)
