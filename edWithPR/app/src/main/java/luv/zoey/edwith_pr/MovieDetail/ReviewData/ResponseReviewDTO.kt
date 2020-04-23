package luv.zoey.edwith_pr.MovieDetail.ReviewData


data class ResponseReviewDTO(
    var message: String,
    var code: Int,
    var resultType: String,
    var totalCount: Int,
    var result: ArrayList<MovieReviewDTO>
)
