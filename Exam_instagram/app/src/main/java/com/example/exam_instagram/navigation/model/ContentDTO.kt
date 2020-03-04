package com.example.exam_instagram.navigation.model

data class ContentDTO(
    var explain: String? = null,
    var imageUrl: String? = null,
    var uid: String? = null,
    var userID: String? = null,
    var timestamp: Long? = null,
    var favortiteCount: Int? = 0,
    var chkfavorites: Map<String, Boolean> = HashMap()
) {
    data class Comment(
        var uid: String? = null,
        var userID: String? = null,
        var comment: String? = null,
        var timestamp: Long? = null
    )

}