package luv.zoey.edwith_pr.MainMenu.Data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Movie (
    var id: Int? = null,                        // ID
    var title: String?= null,                  // 제목
    var title_eng: String?= null,
    var date: String?= null,                  // 날짜
    var user_rating: Float?= null,            // 유저 평점
    var audience_rating: Float?= null,
    var reviewer_rating: Float?= null,
    var reservation_rate: Float?= null,
    var reservation_grade: Int?= null,
    var grade: Int?= null,
    var thumb: String?= null,
    var image: String?= null
): RealmObject()