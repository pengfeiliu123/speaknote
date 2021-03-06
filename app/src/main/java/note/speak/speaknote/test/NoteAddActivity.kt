package note.speak.speaknote.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.nuance.speechkit.Recognition
import com.nuance.speechkit.TransactionException
import kotlinx.android.synthetic.main.activity_note_add.*
import note.speak.speaknote.R
import note.speak.speaknote.RecordState
import note.speak.speaknote.base.BaseRecordActivity
import note.speak.speaknote.db.Note
import note.speak.speaknote.util.Date.getCurrentDay
import note.speak.speaknote.util.Date.getCurrentTime

class NoteAddActivity : BaseRecordActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()

        setRecordState(RecordState.IDLE)
    }

    private fun initViews() {
        btnRecord.setOnClickListener(this)
        btnUpdateNote.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnRecord -> toggleReco()
            btnUpdateNote -> addNote()
        }
    }

    private fun addNote() {

        val note = Note()
        note.title = tvSpeak.text.toString()
        note.content = "addnote content"
        note.time = getCurrentTime()
        note.day = getCurrentDay()
        note.save()
        startActivity(Intent(this,NoteListActivity::class.java))
    }

    private fun toggleReco() {
        when (state) {
            RecordState.IDLE -> recognize()
            RecordState.LISTENING -> stopRecording()
            RecordState.PROCESSING -> cancel()
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_note_add
    }

    override fun onStartRecording() {
        tvState.text = "onStartRecording"
    }

    override fun onFinishRecording() {
        tvState.text = "onFinishRecording"
    }

    override fun onRecognition(recognition: Recognition?) {
        tvState.text = "onRecognition"
//        tvSpeak.setText(recognition?.text)
        Log.d("lpftag", tvSpeak.isFocused.toString() + tvContent.isFocused.toString())
        if (tvSpeak.isFocused) {
            tvSpeak.setText(recognition?.text)
        } else if (tvContent.isFocused) {
            tvContent.setText(recognition?.text)
        }
    }

    override fun onSuccess(s: String?) {
        tvState.text = "onSuccess"
    }

    override fun onEror(s: String?, e: TransactionException?) {
        tvState.text = "onError" + e!!.message + "." + s
    }
}
