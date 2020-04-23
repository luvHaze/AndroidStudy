package luv.zoey.edwith_pr.MainMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_menu.*
import luv.zoey.edwith_pr.AppHelper
import luv.zoey.edwith_pr.MainMenu.Data.Movie
import luv.zoey.edwith_pr.MainMenu.Data.MovieList
import luv.zoey.edwith_pr.R

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // API 로 부터 받아온 MovieList들을 담기 위한 배열
    private var movieList: ArrayList<Movie> = arrayListOf()

    //뷰페이저 어뎁터 객체를 만들고 addItem으로 프래그먼트를 추가해준다
    private val viewPagerAdapter = MenuViewPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(findViewById(R.id.menu_toolbar)) // 액션바 등록

        // volley 0. request들을 보관해둘 RequestQueue 객체를 먼저 등록해준다.
        AppHelper.requestQueue = Volley.newRequestQueue(applicationContext)
        requestData()

/*      activity_menu 레이아웃은 드로어 레이아웃이 메인레이아웃이며
        child 로는 툴바가 들어갈 LinearLayout과
        액션바를 눌렀을때 띄워질 네비게이션 뷰가 있다.
        Drawer 레이아웃은 같은 그래비티 끼리 열어줄 수 있다*/

        //네비게이션 리스너 등록해준다
        navigation_menu.setNavigationItemSelectedListener(this)

        openNavigationView_Button.setOnClickListener {
            // 그래비티가 LEFT에 해당하는 뷰들을 '서랍 열듯이 열겠다' 라는 의미
            drawer.openDrawer(Gravity.LEFT)

        }

    }

    // volley로 API 데이터 요청하는 메소드
    private fun requestData() {

        val url = "http://boostcourse-appapi.connect.or.kr:10000/movie/readMovieList?type=1"

        // volley 1. 하나의 리퀘스트를 만들고
        var request = StringRequest(
            Request.Method.GET, // 어떤 메소드를 사용할지
            url,
            Response.Listener {
                // it -> respose 응답받은 DATA 를 뜻함

                processResponse(it)  // 응답받은 데이터를 Gson으로 가공
                Log.d("success", it)
//
            },
            Response.ErrorListener {
                Log.d("Error_RequestMainMenu", it.toString())
            }

        )
        // 이전 결과 있더라도 새로 추가해준다.
        request.setShouldCache(false)
        // volley 2. 만든 리퀘스트를 리퀘스트 큐에 넣어준다.
        AppHelper.requestQueue.add(request)

    }

    // volley 에서 응답을 받은경우 Gson으로 가공해주는 메소드
    private fun processResponse(responseData: String?) {

        val gson = Gson()
        var receiveData: MovieList = gson.fromJson(responseData, MovieList::class.java)
        // response 받은 데이타를 MoiveListDTO 형식으로 추출 및 저장

        for (movie in receiveData.result) {
            // receiveData 의 영화들의 정보가 담긴 result 리스트에서 
            // 영화를 하나씩 빼서 Movie타입의  movieList에 넣어준다.
            movieList.add(movie)
            Log.d("data", movie.toString())

            // 어뎁터에 하나씩 등록을 해준다.
            viewPagerAdapter.addItem(MovieFragment(movie))
        }

        //만든 어뎁터를 위젯에 등록해주면 어뎁터에 넣어주었던 프레그먼트들이 나오기 시작한다.
        mainmenu_ViewPager.adapter = viewPagerAdapter

    }


    //네비게이션 메뉴 아이템 선택 시 발생하는 콜백메서드 (미구현)
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.movieList_item -> {
                Toast.makeText(this, "sdf", Toast.LENGTH_LONG).show()
            }

            R.id.movieAPI_item -> {

            }
            R.id.movieReserve_item -> {

            }

            R.id.settings_item -> {

            }

        }
        return false
    }

}
