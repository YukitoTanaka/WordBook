package com.example.myscheduler


import android.content.Context
import android.os.Bundle
import android.system.Os.read
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.RealmList
import io.realm.internal.OsObject.create
import io.realm.internal.OsObject.createRow
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_play.*
import io.realm.Sort
import io.realm.RealmResults



val TEXT_RES_ID = "TEXT_RES_ID"
var text1 = "Python"
//var TEXT_DATA = "TEXT_DATA"

//1.RealmのTextデータを入れるリスト
//val textArray: Array<String> = arrayOf()

class PlayFragment : Fragment() {
//    private lateinit var realm: Realm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    //クラスのインスタンスを作る処理
    companion object{
        fun newInstance(textResourceId: Int) : PlayFragment {
            val bundle = Bundle()
            bundle.putInt(TEXT_RES_ID, textResourceId)
            val playFragment = PlayFragment()
            playFragment.arguments = bundle
            //arguments プロパティ でデータを保持できる (by Kotlin)
            return playFragment
        }
    }
    //Bundleから値を取り出す処理
    //textリソースIDを textResI　変数に保持
    private var textResId: Int? = null

    //フラグメント生成時（再生成）の時にonCreateが呼ばれる
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            textResId = it.getInt(TEXT_RES_ID)
        }
    }

    //フラグメントのtextViewにtextResIdに保持したデータを表示する
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        textResId?.let{
            imageView2.setImageResource(it)    //image
//            textView.setText(text1)          //text
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        realm.close()
    }
}
