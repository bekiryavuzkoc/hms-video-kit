package com.android.hmsvideokit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playVideoEditText.setText("http://192.168.0.100:8080/a.mp4,http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")

        playVideoButton.setOnClickListener{
            val videoUrl = playVideoEditText.text.toString()
            val intent = Intent(this, PlayVideoActivity::class.java)
            intent.putExtra("data",videoUrl)
            startActivity(intent)
        }

    }
}