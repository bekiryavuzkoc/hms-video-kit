package com.android.hmsvideokit

import android.os.Bundle
import android.os.Handler
import android.view.SurfaceHolder
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.videokit.player.WisePlayer
import kotlinx.android.synthetic.main.activity_play_video.*


class PlayVideoActivity : AppCompatActivity(),WisePlayer.ReadyListener,SurfaceHolder.Callback,SeekBar.OnSeekBarChangeListener {

    private lateinit var player: WisePlayer
    private  val delayOfSeconds = 1000L

    private val handler:Handler = Handler()
    private val runnable: Runnable by lazy{
        Runnable {
            changeSeekbarView()
            handler.postDelayed(runnable,delayOfSeconds)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)
        initPlayerSettings()
        initListeners()
    }

    private fun changeSeekbarView() {
        current_time_tv.text = TimeUtil.formatLongToTimeStr(this.player.currentTime)
        total_time_tv.text = TimeUtil.formatLongToTimeStr(this.player.duration)
        seek_bar.max = this.player.duration
        seek_bar.progress = this.player.currentTime
        seek_bar.secondaryProgress = this.player.bufferTime

    }

    private fun initPlayerSettings(){
        player = App.getWisePlayerFactory()!!.createWisePlayer()
        val videoString = intent.getStringExtra("data")!!
        player.setVideoType(0)
        player.cycleMode = 1
        player.setView(surface_view)
        player.setPlayUrl(videoString.split(",").toTypedArray())
        player.ready()
    }

    private fun initListeners(){
        val surfaceHolder: SurfaceHolder = surface_view.holder
        surfaceHolder.addCallback(this)
        player.setReadyListener(this)
        seek_bar.setOnSeekBarChangeListener(this)
        play_btn.setOnClickListener {
            if(player.isPlaying.not()){
                playVideo()
            }else if(player.isPlaying){
                pauseVideo()
            }
        }
    }

    override fun onReady(p0: WisePlayer?) {
        player.start()
        runOnUiThread {
            handler.postDelayed(runnable,delayOfSeconds)
        }
    }


    override fun surfaceCreated(p0: SurfaceHolder?) {
        player.setView(surface_view)
        player.resume(-1)
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        player.setSurfaceChange()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        player.suspend()
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        seek_bar?.progress?.let { player.seek(it)}
    }

    private fun playVideo(){
        player.start()
        play_btn.setImageResource(R.drawable.ic_full_screen_suspend_normal)

    }
    private fun pauseVideo(){
        player.pause()
        play_btn.setImageResource(R.drawable.ic_play)
    }

    override fun onStop() {
        super.onStop()
        player.stop()
        player.release()
    }
}