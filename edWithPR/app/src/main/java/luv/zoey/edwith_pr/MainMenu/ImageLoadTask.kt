package luv.zoey.edwith_pr.MainMenu

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.lang.Exception
import java.net.URL

class ImageLoadTask(var urlStr: String, var imgView: ImageView)
    : AsyncTask<Unit, Unit, Bitmap>() {
    //AsyncTask<[1],[2],[3]>
    // [1]. 메인 스레드에서 생성된 스레드에게 보내는 인자의 자료형을 정의한다.
    // [2]. 생성된 스레드에서 갱신 작업을 할때, 메인 스레드에게 보내는 인자의 자료형을 정의한다.
    // [3]. 파란색은 생성된 스레드에서 작업 종료 후의 처리를 할때, 메인 스레드에게 보내는 인자의 자료형을 정의한다.


    // 스레드가 시작하기 전에 수행할 작업(메인 스레드)
    override fun onPreExecute() {

        super.onPreExecute()
    }

    // Thread 가 수행할 작업 (생성된 스레드)
    // 여기서 publishProgress() 메소드를 호출 하면 메인스레드 접근 가능
    override fun doInBackground(vararg params: Unit?): Bitmap {

        var bitmap: Bitmap? = null

        try {
            // 불러들여온 URL 이 HASH 맵에 이미 존재하는지를 확인
            if (bitmapHash.containsKey(urlStr)) {

                var oldBitmap = bitmapHash.remove(urlStr)

                if (oldBitmap != null) {
                    oldBitmap.recycle()
                    oldBitmap=null
                }

            }

            //URL로 형 변환
            var url = URL(urlStr)
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())

            bitmapHash[urlStr] = bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap!!
    }

    // 스레드가 수행되는 사이에 수행할 중간 작업(메인 스레드)
    // publishProgress() 메소드를 호출하여 중간 작업 수행 가능
    override fun onProgressUpdate(vararg values: Unit?) {
        super.onProgressUpdate(*values)
    }

    // 스레드 작업이 모두 끝난 후에 수행할 작업 (메인 스레드)
    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        Glide.with(imgView.context).load(result).into(imgView)

//        imgView.setImageBitmap(result)
//        imgView.invalidate()
    }

    // cancel() 메소드가 호출되었을 때
    // 강제로 취소하라는 명령이 호출되었을대
    // 쓰레드가 취소되기 전에 수행할 작업 (메인 쓰레드)
    override fun onCancelled() {
        super.onCancelled()
    }

    companion object {
        // 이전에 불러들였던 bitmap인지 확인 하기위한 HashMap
        private var bitmapHash = HashMap<String, Bitmap>()
    }
}