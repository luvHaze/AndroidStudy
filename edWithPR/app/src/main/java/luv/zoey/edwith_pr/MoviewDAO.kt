package luv.zoey.edwith_pr

import androidx.lifecycle.LiveData

data class MoviewDAO(
    var movieName : String,
    var movieGrage : Int,
    var movieDate : String,
    var movieStory : String,
    var movieDirector : String,
    var movieActors : String,
    var moviePositive : Int,
    var movieNegative : Int,
    var moviePecentage : Float
): LiveData<MoviewDAO>()