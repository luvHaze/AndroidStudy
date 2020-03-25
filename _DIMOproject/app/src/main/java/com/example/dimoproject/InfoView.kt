package com.example.dimoproject

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

// 안드로이드 시스템에서 View를 생성할 때
// Java 생성자 형태로 호출하기 때문에 default arguments를 호환되도록 만들어 줌
open class InfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0)
    :LinearLayout(context,attrs,defStyleAttr){
    init {
        //view_info.xml을 내부에 포함 시킨다.
        View.inflate(context,R.layout.view_info,this)
    }
}