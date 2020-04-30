package luv.zoey.edwith_pr.MainMenu.Data

import luv.zoey.edwith_pr.MainMenu.Data.Movie


data class MovieList(
    var message: String,
    var code: Int,
    var resultType: String,
    var result : ArrayList<Movie>
)
