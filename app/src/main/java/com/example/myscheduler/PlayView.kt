package com.example.myscheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_play_view.*


class PlayView : AppCompatActivity() {

//    private lateinit var realm: Realm
//    val text_Array: Array<String> = arrayOf()
//    val cardList: List<String> = realm.where<Schedule>().findAll()

    class MyAdapter(fm: FragmentManager) :  FragmentPagerAdapter(fm) {
        //1.RealmのTextデータを入れるリスト
        private val TEXT_DATA = listOf (
            R.drawable.id1title,R.drawable.id1datail,
            R.drawable.id2title,R.drawable.id2datail,
            R.drawable.id3title,R.drawable.id3datail,
            R.drawable.id4title,R.drawable.id4datail,
            R.drawable.id5title,R.drawable.id5datail
        )

        //フラグメントに表示可能なビューの数を返す
        override fun getCount(): Int {
            return TEXT_DATA.size
        }
        //指定位置に関連フラグメントを返す
        override fun getItem(position: Int): Fragment {
            return PlayFragment.newInstance(TEXT_DATA[position])
        }
    }
    //adapterプロパティでViewPagerとViewAdapterを関連付ける
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_view)
        pager.adapter = MyAdapter(supportFragmentManager)
//      realm = Realm.getDefaultInstance()
    }
}
