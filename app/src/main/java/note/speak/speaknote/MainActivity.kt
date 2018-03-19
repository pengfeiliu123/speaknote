package note.speak.com.speaknote

import android.os.Bundle
import com.nuance.speechkit.Recognition
import com.nuance.speechkit.TransactionException
import kotlinx.android.synthetic.main.activity_main.*
import note.speak.speaknote.R
import note.speak.speaknote.RecordState
import note.speak.speaknote.base.BaseRecordActivity

class MainActivity : BaseRecordActivity() {

    override fun onCreate(savedInstanceRecordState: Bundle?) {
        super.onCreate(savedInstanceRecordState)
        setContentView(R.layout.activity_main)

        initViews()

        setRecordState(RecordState.IDLE)
    }


    private fun initViews() {
        btnRecord.setOnClickListener { toggleReco() }
    }

    private fun toggleReco() {
        when (state) {
            RecordState.IDLE -> recognize()
            RecordState.LISTENING -> stopRecording()
            RecordState.PROCESSING -> cancel()
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun onStartRecording() {
        tvState.text = "onStartRecording"
    }

    override fun onFinishRecording() {
        tvState.text = "onFinishRecording"
    }

    override fun onRecognition(recognition: Recognition?) {
        tvState.text = "onRecognition"
        tvSpeak.setText(recognition?.text)
    }

    override fun onSuccess(s: String?) {
        tvState.text = "onSuccess"
    }

    override fun onEror(s: String?, e: TransactionException?) {
        tvState.text = "onError" + e!!.message + "." + s
    }
}
