package luv.zoey.edwith_pr.MovieDetail.Data

data class ResponseDTO (
    var message: String,
    var code: Int,
    var resultType: String,
    var result : ArrayList<MovieDetailDTO>
)
