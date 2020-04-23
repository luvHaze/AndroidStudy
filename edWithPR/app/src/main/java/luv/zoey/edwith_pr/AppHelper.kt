package luv.zoey.edwith_pr

import com.android.volley.RequestQueue

// Volley 의 request들을 받아주는 RequestQueue는 하나만 만들어 주는것이 좋다.
// 싱글턴 클래스

//RequestQueue는 작업이 이루어지는 스레드를 관리한다. 이 작업자 스레드에서는
//네트워크 작업 수행, 캐시 읽기/쓰기, 그리고 응답(Response)을 분석(Parsing)이 이루어진다.
class AppHelper {

    companion object {

        lateinit var requestQueue: RequestQueue
    }

}