package com.example.myscheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        //クリックイベントをセット
        inButton.setOnClickListener { inButtonTapped(it) }
        outButton.setOnClickListener { outButtonTapped() }
    }

    //MainActivity カードメニューへ
    fun inButtonTapped(view: View?){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    //PlayView 表示画面へ
    fun outButtonTapped() {
        val intent = Intent(this, PlayView::class.java)
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}