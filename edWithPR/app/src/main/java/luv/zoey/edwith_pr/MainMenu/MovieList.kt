package luv.zoey.edwith_pr.MainMenu

data class MovieList(
    var message: String,
    var code: Int,
    var resultType: String,
    var result : ArrayList<Movie>
)
