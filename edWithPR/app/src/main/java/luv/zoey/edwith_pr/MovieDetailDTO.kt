package luv.zoey.edwith_pr

class MovieDetailDTO(
    var title :String,                  // 제목
    var id: Int,                        // ID
    var date : String,                  // 날짜
    var user_rating : Float,            // 유저 평점
    var audience_rating : Float,        // ㄱ
    var reviewer_rating : Float,
    var reservation_rate : Float,
    var reservation_grade : Int,
    var grade : Int,
    var thumb : String,                 // 썸네일 URL
    var image : String,                 // 이미지 URL
    var photos : String,
    var videos : String,
    var outlinks : String,
    var genre : String,
    var duration : Int,
    var audience : Int,
    var synopsis : String
    )
