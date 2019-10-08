package com.example.myscheduler

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_schedule_edit.*
import java.util.*


class ScheduleEditActivity : AppCompatActivity(), View.OnClickListener, TextToSpeech.OnInitListener {
    private lateinit var realm: Realm
    //TextToSpeech
    private var tts: TextToSpeech? = null
    private var talkText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_edit)
        realm = Realm.getDefaultInstance()

        // TextToSpeech 初期化
        tts = TextToSpeech(this, this)
        btn.setOnClickListener(this)
        //Realmインスタンス初期化
        val scheduleId = intent?.getLongExtra("schedule_id", -1L)
        if (scheduleId != -1L) {
            val schedule = realm.where<Schedule>()
                .equalTo("id", scheduleId).findFirst()

            //Realmの各データ変数
            titleEdit.setText(schedule?.title)
            detailEdit.setText(schedule?.detail)

            //SpeakTo :外側のtalkTextにRealmのdetailをセット
            talkText = schedule?.detail.toString()
            delete.visibility = View.VISIBLE
        } else {
            delete.visibility = View.INVISIBLE
        }

        //登録、更新 + スナックバー表示　
        //文字制限の追加 11.10
        save.setOnClickListener { view: View ->
            when (scheduleId) {
                -1L -> {
                    realm.executeTransaction { db: Realm ->
                        val maxId = db.where<Schedule>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1
                        val schedule = db.createObject<Schedule>(nextId)
//                        val date = dateEdit.text.toString().toDate("yyyy/MM/dd")
//                        if (date != null) schedule.date = date
                        schedule.title = titleEdit.text.toString()
                        schedule.detail = detailEdit.text.toString()
                    }
                    Snackbar.make(view, "追加しました", Snackbar.LENGTH_SHORT)
                        .setAction("戻る") { finish() }
                        .setActionTextColor(Color.YELLOW)
                        .show()
                }
                else -> {
                    realm.executeTransaction { db: Realm ->
                        val schedule = db.where<Schedule>()
                            .equalTo("id", scheduleId).findFirst()
//                        val date = dateEdit.text.toString()
//                            .toDate("yyyy/MM/dd")
//                        if (date != null) schedule?.date = date
                        schedule?.title = titleEdit.text.toString()
                        schedule?.detail = detailEdit.text.toString()

                    }
                    Snackbar.make(view, "修正しました", Snackbar.LENGTH_SHORT)
                        .setAction("戻る") { finish() }
                        .setActionTextColor(Color.YELLOW)
                        .show()
                }
            }
        }
//        Realm データ削除　+ スナックバー表示
        delete.setOnClickListener { view: View ->
            realm.executeTransaction { db: Realm ->
                db.where<Schedule>().equalTo("id", scheduleId)
                    ?.findFirst()
                    ?.deleteFromRealm()
            }
            Snackbar.make(view, "削除しました", Snackbar.LENGTH_SHORT)
                .setAction("戻る") { finish() }
                .setActionTextColor(Color.YELLOW)
                .show()
        }
    }

    //TTS TextToSpeech
    override fun onClick(v: View) {
        var input = ""
        var langLocale: Locale? = null

        when (v.id) {
            R.id.btn -> {
                // 日本語を指定する
                input = talkText.toString()
                langLocale = Locale.JAPANESE
            }
            R.id.btn -> {
                // 英語を指定する
                input = talkText.toString()
                langLocale = Locale.ENGLISH
            }
        }

        if (langLocale != null && input.isNotBlank()) {
            // 指定された言語でテキストを読み上げる
            speakText(langLocale, input)
        }
    }

    private fun speakText(langLocale: Locale, text: String) {
        tts!!.setLanguage(langLocale)
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "speech1")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            Log.d("TTS", "TextToSpeechが初期化されました。")

            // 音声再生のイベントリスナを登録
            val listener: SpeechListener? = SpeechListener()
            tts!!.setOnUtteranceProgressListener(listener)
        } else {
            Log.e("TTS", "TextToSpeechの初期化に失敗しました。")
        }
    }

    class SpeechListener : UtteranceProgressListener() {
        var tag: String = "TTS"

        override fun onDone(utteranceId: String?) {
            Log.d(tag, "音声再生が完了しました。")
        }

        override fun onError(utteranceId: String?) {
            Log.d(tag, "音声再生中にエラーが発生しました。")
        }

        override fun onError(utteranceId: String?, errorCode: Int) {
            Log.d(tag, "音声再生中にエラーが発生しました。")
        }

        override fun onStart(utteranceId: String?) {
            Log.d(tag, "音声再生を開始します。")
        }

        override fun onStop(utteranceId: String?, interrupted: Boolean) {
            Log.d(tag, "音声再生を中止します。")
        }

        override fun onBeginSynthesis(utteranceId: String?, sampleRateInHz: Int, audioFormat: Int, channelCount: Int) {
            Log.d(tag, "音声の合成を開始します。")
        }

        override fun onAudioAvailable(utteranceId: String?, audio: ByteArray?) {
            Log.d(tag, "音声が利用可能になりました。")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
        //TTS 閉じる Runtime解決
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
    }
}
