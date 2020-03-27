package com.example.dimoproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.shape.ShapeAppearanceModel.builder
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.takisoft.datetimepicker.DatePickerDialog
import com.takisoft.datetimepicker.TimePickerDialog
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import java.io.File
import java.util.*
import java.util.stream.DoubleStream.builder


class DetailActivity : AppCompatActivity() {

    private var viewModel: DetailViewModel? = null
    //날짜와 시간 다이얼로그에서 설정중인 값을 임시로 저장해 두기 위함
    private val dialogCalendar = Calendar.getInstance()
    private val REQUEST_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val intent =Intent(Intent.ACTION_GET_CONTENT)
            intent.type="image/*"
            startActivityForResult(intent,REQUEST_IMAGE)
        }

        //viewModel 의 생성
        viewModel = application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(DetailViewModel::class.java)
        }

        // 제목과 내용에 Observer를 걸어 화면을 갱신시킨다.
        viewModel!!.let {
            //기존에  viewModel의 변수별로 observe하던 코드를 삭제
//            it.title.observe(this, Observer { supportActionBar?.title = it })
//            it.content.observe(this, Observer { contentEdit.setText(it) })
//            it.alarmTime.observe(this, Observer { alarmInfoView.setAlarmDate(it) })

            //viewModel의 memoLiveData를 observe 하도록 수정함함
            viewModel!!.memoLiveData.observe(this, Observer {
                supportActionBar?.title = it.title
                contentEdit.setText(it.content)
                alarmInfoView.setAlarmDate(it.alarmTime)
                locationInfoView.setLocation(it.latitude, it.longitude)
                weatherInfoView.setWeather(it.weather)

                val imageFile= File(
                    getDir("image",Context.MODE_PRIVATE),
                    it.id+".jpg")
                bgImage.setImageURI(imageFile.toUri())
            })

        }

        //ListActivity에서 아이템을 선택했을때 보내주는 메모 id로 데이터를 로드함
        // 새로 작성하는 메모의 경우  메모 id가 없어 이 루틴은 동작하지 않는다.
        val memoId = intent.getStringExtra("MEMO_ID")
        if (memoId != null) viewModel!!.loadMemo(memoId)

        // 툴바 레이아웃을 눌렀을때 제목을 수정하는 다이얼로그를 띄우는 루틴
        toolbarLayout.setOnClickListener {
            //LayoutInflater로 레이아웃 xml을 view로 변환
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_title, null)
            val titleEdit = view.findViewById<EditText>(R.id.titleEdit)
            // View의 findViewById 함수를 이용하여 view에 포함된 titleedit을 변수에 담음

            //AlertDialog.Builder로 다이얼로그 창을 제작
            AlertDialog.Builder(this)
                .setTitle("제목을 입력하세요")
                .setView(view)      //다이얼 로그의 내용이 되는 view 설정
                .setNegativeButton("취소", null)
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    supportActionBar?.title = titleEdit.text.toString()
                    viewModel!!.memoData.title = titleEdit.text.toString()
                }).show()
        }

        // 내용이 변경될 때마다 Listeneer 내에서 viewModel의 memoData의 내용도 같이 변경해 줌
        contentEdit.addTextChangedListener {
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    viewModel!!.memoData.content = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }
        }
        locationInfoView.setOnClickListener {
            //좌표가 유효한지 확인
            val latitude = viewModel!!.memoData.latitude
            val longitude = viewModel!!.memoData.longitude

            if (!(latitude == 0.0 && longitude == 0.0)) {
                // sdk에서 제공하는 MapView객체 생성 (지도를 출력하는 View)
                val mapView = MapView(this)

                //지도 옵션 변경 위해서는 NaverMap 객체를 받은 후에만 옵션의 변경이 가능하다.
                mapView.getMapAsync {
                    val latitude = viewModel!!.memoData.latitude
                    val longitude = viewModel!!.memoData.longitude
                    val cameraUpdate = CameraUpdate.scrollTo(LatLng(latitude, longitude))
                    it.moveCamera(cameraUpdate)
                }

                AlertDialog.Builder(this)
                    .setView(mapView)
                    .show()
            }

        }
    }

    private fun openDateDialog() {
        val datePickerDialog = DatePickerDialog(this)
        datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            dialogCalendar.set(year, month, dayOfMonth)
            openTimeDialong()
        }
        datePickerDialog.show()
    }

    private fun openTimeDialong() {
        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                dialogCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                dialogCalendar.set(Calendar.MINUTE, minute)

                viewModel?.setAlarm(dialogCalendar.time)
                // 시간 다이얼로그는 생성자 안에서 Listner 를 구현
                // 사용자가 입력한 시간을 임시 캘린더 변수에 설정하고
                // 캘린더 변수의 time값(date객체)를 viewModel에 새 알람값으로 설정해 줌
            },
            0, 0, false
        ) // 다디얼로그 초기시간 0시 0분 설정 24시간제 ->false
        timePickerDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_acitivity, menu)
        return true
    }

    // IntroActivity에서 이미 체크한 위치 권한 허용 여부를
    // 다시 체크하지 않기 위해서 함수에 annotation을 추가함
    @SuppressLint("MissingPermission")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            //공유기능 아이템 선택 시
            R.id.menu_share -> {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, supportActionBar?.title)
                intent.putExtra(Intent.EXTRA_TEXT, contentEdit.text.toString())

                startActivity(intent)
            }

            R.id.menu_alarm -> {
                if (viewModel?.memoData?.alarmTime!!.after(Date())) {
                    AlertDialog.Builder(this)
                        .setTitle("안내")
                        .setMessage("기존에 알람이 설정되어 있습니다. 삭제 또는 재설정할 수 있습니다.")
                        .setPositiveButton("재설정", DialogInterface.OnClickListener { dialog, which ->
                            openDateDialog()
                        })
                        .setNegativeButton("삭제", DialogInterface.OnClickListener { dialog, which ->
                            viewModel?.deleteAlarm()
                        })
                        .show()
                } else {
                    openDateDialog()
                }

            }

            R.id.menu_location -> {
                AlertDialog.Builder(this)
                    .setTitle("안내")
                    .setMessage("현재 위치를 메모에 저장하거나 삭제할 수 있습니다.")
                    .setPositiveButton("위치지정", DialogInterface.OnClickListener { dialog, which ->
                        // LocationManager를 가져와서 위치기능이 켜져있는지 확인
                        // (GPS 및 네트워크 위치 기능을 둘다 확인해야함
                        val locationManager =
                            getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        val isGPSEnabled =
                            locationManager.isProviderEnabled((LocationManager.GPS_PROVIDER))
                        val isNetworkEnabled =
                            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                        //위치기능이 둘다 꺼진경우 SnackBar를 띄워 시스템의 위치 옵션화면을 안내해 줌
                        if (!isGPSEnabled && !isNetworkEnabled) {
                            Snackbar.make(
                                    toolbarLayout, "위치기능을 켜야 기능을 사용할 수 있습니다.",
                                    Snackbar.LENGTH_LONG
                                )
                                .setAction("설정", View.OnClickListener {
                                    val goToSettings =
                                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                    startActivity(goToSettings)
                                }).show()

                        }//이미 켜져있다면
                        else {   // 위치의 정확도와 배터리 소모량을 설정
                            val criteria = Criteria()
                            criteria.accuracy = Criteria.ACCURACY_MEDIUM
                            criteria.powerRequirement = Criteria.POWER_MEDIUM

                            //locationManager에 이 기준값을 넘겨 위치값을 받아올 수 있다.
                            // 위치값은 LocationListener로 넘어오기때문에 object를 구현하여 넘겨줘야 함
                            locationManager.requestSingleUpdate(
                                criteria,
                                object : LocationListener {
                                    //위치정보가 갱신될때 실행됨
                                    override fun onLocationChanged(location: Location?) {
                                        //ViewModel로 넘겨준다.
                                        location?.run {
                                            viewModel!!.setLocation(latitude, longitude)
                                        }
                                    }
                                    override fun onStatusChanged(
                                        provider: String?,
                                        status: Int,
                                        extras: Bundle?
                                    ) { }

                                    override fun onProviderEnabled(provider: String?) {}

                                    override fun onProviderDisabled(provider: String?) {}
                                },
                                null
                            )
                        }
                    })
                    .setNegativeButton("삭제", DialogInterface.OnClickListener { dialog, which ->
                        viewModel!!.setLocation(0.0, 0.0)
                    })
                    .show()
            }

            R.id.menu_weather -> {
                AlertDialog.Builder(this)
                    .setTitle("안내")
                    .setMessage("현재 날씨를 메모에 저장하거나 삭제할 수 있습니다.")
                    .setPositiveButton("날씨 지정하기", DialogInterface.OnClickListener { dialog, which ->
                        // LocationManager를 가져와서 위치기능이 켜져있는지 확인
                        // (GPS 및 네트워크 위치 기능을 둘다 확인해야함
                        val locationManager =
                            getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        val isGPSEnabled =
                            locationManager.isProviderEnabled((LocationManager.GPS_PROVIDER))
                        val isNetworkEnabled =
                            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                        //위치기능이 둘다 꺼진경우 SnackBar를 띄워 시스템의 위치 옵션화면을 안내해 줌
                        if (!isGPSEnabled && !isNetworkEnabled) {
                            Snackbar.make(
                                    toolbarLayout, "위치기능을 켜야 기능을 사용할 수 있습니다.",
                                    Snackbar.LENGTH_LONG
                                )
                                .setAction("설정", View.OnClickListener {
                                    val goToSettings =
                                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                    startActivity(goToSettings)
                                }).show()

                        } else {   // 위치의 정확도와 배터리 소모량을 설정
                            val criteria = Criteria()
                            criteria.accuracy = Criteria.ACCURACY_MEDIUM
                            criteria.powerRequirement = Criteria.POWER_MEDIUM

                            //locationManager에 이 기준값을 넘겨 위치값을 받아올 수 있다.
                            // 위치값은 LocationListener로 넘어오기때문에 object를 구현하여 넘겨줘야 함
                            locationManager.requestSingleUpdate(
                                criteria,
                                object : LocationListener {
                                    //위치정보가 갱신될때 실행됨
                                    override fun onLocationChanged(location: Location?) {
                                        //ViewModel로 넘겨준다.
                                        location?.run {
                                            viewModel!!.setWeather(latitude, longitude)
                                        }
                                    }

                                    override fun onStatusChanged(
                                        provider: String?,
                                        status: Int,
                                        extras: Bundle?
                                    ) {}

                                    override fun onProviderEnabled(provider: String?) {}

                                    override fun onProviderDisabled(provider: String?) {}
                                },
                                null
                            )
                        }
                    })
                    .setNegativeButton("삭제", DialogInterface.OnClickListener { dialog, which ->
                        viewModel!!.deleteWeather()
                    })
                    .show()

            }

        }
        return super.onOptionsItemSelected(item)
    }

    //Activity의 결과를 받음
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //요청했던 코드가 동일한지, 결과값이 OK인지 확인함
        if(requestCode==REQUEST_IMAGE&&resultCode== Activity.RESULT_OK){
            try {
                //결과값으로 들어온 데이터를 비트맨으로 변환한다.
                val inputStream=data?.data?.let{contentResolver.openInputStream(it)}
                inputStream?.let {
                    val image= BitmapFactory.decodeStream(it)
                    //bgImage에 표시되는이미지를 null로 초기화하고 새 이미지를 viewModel에 설정
                    bgImage.setImageURI(null)
                    image?.let { viewModel?.setImageFile(this,it) }
                    it.close()  //모두 완료되면 inputStream 종료해줌
                }
            }
            catch (e : Exception){
                println(e)
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        // 화면에서 뒤로가기를 누를때
        //viewModel의 addOrUpdateMemo()를 호출하여 메모를 DB에 갱신
        viewModel?.addOrUpdateMemo(this)
    }
}