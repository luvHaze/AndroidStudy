package luv.zoey.edwith_pr.MainMenu

data class MovieListDTO (
    var id: Int,                        // ID
    var title :String,                  // 제목
    var title_eng : String,
    var date : String,                  // 날짜
    var user_rating : Float,            // 유저 평점
    var audience_rating : Float,        // ㄱ
    var reviewer_rating : Float,
    var reservation_rate : Float,
    var reservation_grade : Int,
    var grade : Int,
    var thumb : String,
    var image : String
)

