package luv.zoey.edwith_pr.MovieDetail.Gallery

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import luv.zoey.edwith_pr.R

class VideoPlayerActivity : AppCompatActivity() {

    private var player : SimpleExoPlayer? = null
    private lateinit var playerView : PlayerView
    private var playWhenReady = true
    private var currentWindow : Int  = 0
    private var playbackPosition : Long = 0
    private val bandwidthMeter by lazy{
        DefaultBandwidthMeter.Builder(this).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        Log.d("onCreate","onCreate in")
        playerView = findViewById(R.id.playerView)
    }

    override fun onResume() {
        super.onResume()
//        hideSystemUi()
        if (Util.SDK_INT <= 23 || player==null) {
            initPlayer()
            Log.d("onResume","onResume in")
        }

    }

    override fun onStart() {
        super.onStart()
        if(Util.SDK_INT>23){
            initPlayer()
            Log.d("onStart","onStart in")
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
            Log.d("onPause","onPause in")
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
            Log.d("onStop","onStop in")
        }
    }

    private fun initPlayer(){
        //TODO

        player = ExoPlayerFactory.newSimpleInstance(this)
        playerView.player = player

        val uri = Uri.parse("https://www.youtube.com/watch?v=6VjF638VObA")
        val mediaSource = buildMediaSource(uri)

        player?.playWhenReady=playWhenReady
        player?.seekTo(currentWindow,playbackPosition)
        player?.prepare(mediaSource,false,false)

    }

    private fun releasePlayer() {
        if (player != null) {
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            playWhenReady = player!!.playWhenReady
            player!!.release()
            player = null
        }
    }

    private fun buildMediaSource(uri : Uri) : MediaSource
    {
//        val dataSourceFactory: DataSource.Factory =
//            DefaultDataSourceFactory(this, "exoplayer-codelab")
//        return ProgressiveMediaSource.Factory(dataSourceFactory)
//            .createMediaSource(uri)

        val dataSourceFactory = DefaultHttpDataSourceFactory("ua",bandwidthMeter)
        val dashChunkSourceFactory = DefaultDashChunkSource.Factory(dataSourceFactory)
        return DashMediaSource(uri,dataSourceFactory,dashChunkSourceFactory,null,null)
    }

    private fun prepareExoplayer(){




    }

 /*   @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                )
    }
*/
}
