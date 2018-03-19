package note.speak.speaknote.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.nuance.speechkit.*
import kotlinx.android.synthetic.main.activity_main.*
import note.speak.speaknote.R
import note.speak.speaknote.RecordState
import note.speak.speaknote.config.Configuration

public abstract class BaseRecordActivity : AppCompatActivity() {


    protected var recoTransaction: Transaction? = null
    protected var speechSession: Session? = null
    protected var state: RecordState = RecordState.IDLE
    protected var startEarcon: Audio? = null
    protected var stopEarcon: Audio? = null
    protected var errorEarcon: Audio? = null

    protected val handler = Handler()

    protected val audioPoller = object : Runnable {
        override fun run() {
            val level = recoTransaction!!.audioLevel
            barVolume.progress = level.toInt()
            handler.postDelayed(this, 50)
        }
    }

    protected abstract fun getContentView():Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())

        speechSession = Session.Factory.session(this, Configuration.SERVER_URI, Configuration.APP_KEY)
        loadEarcons()
    }

    override fun onPause() {
        when (state) {
            RecordState.IDLE -> {
            }
            RecordState.LISTENING -> stopRecording()
            RecordState.PROCESSING -> cancel()
        }
        super.onPause()

    }

    protected fun recognize() {
        val options = Transaction.Options()
        options.recognitionType = RecognitionType.DICTATION
        options.detection = DetectionType.Long
        options.language = Language("eng-USA")
        options.setEarcons(startEarcon, stopEarcon, errorEarcon, null)

        options.resultDeliveryType = ResultDeliveryType.PROGRESSIVE

        recoTransaction = speechSession!!.recognize(options, recoListener)
    }

    protected fun stopRecording() {
        recoTransaction!!.stopRecording()
    }

    /**
     * Cancel the Reco transaction.
     * This will only cancel if we have not received a response from the server yet.
     */
    protected fun cancel() {
        recoTransaction!!.cancel()
        setRecordState(RecordState.IDLE)
    }

    protected abstract fun onStartRecording()
    protected abstract fun onFinishRecording()
    protected abstract fun onRecognition(recognition: Recognition?)
    protected abstract fun onSuccess(s: String?)
    protected abstract fun onEror(s: String?, e: TransactionException?)

    protected val recoListener: Transaction.Listener = object : Transaction.Listener() {
        override fun onStartedRecording(transaction: Transaction?) {
            setRecordState(RecordState.LISTENING)
            onStartRecording()
            startAudioLevelPoll()
        }

        override fun onFinishedRecording(transaction: Transaction?) {
            setRecordState(RecordState.IDLE)
            onFinishRecording()
            stopAudioLevelPoll()
        }

        override fun onRecognition(transaction: Transaction?, recognition: Recognition?) {
            tvSpeak.setText(recognition?.text)
            onRecognition(recognition)
        }

        override fun onSuccess(transaction: Transaction?, s: String?) {
            setRecordState(RecordState.IDLE)
            onSuccess(s)
        }

        override fun onError(transaction: Transaction?, s: String?, e: TransactionException?) {
            setRecordState(RecordState.IDLE)
            onEror(s,e)
        }
    }

    protected fun setRecordState(idle: RecordState) {
        state = idle
        when (idle) {
            RecordState.IDLE -> btnRecord.text = resources.getString(R.string.recognize)
            RecordState.LISTENING -> btnRecord.text = resources.getString(R.string.listening)
            RecordState.PROCESSING -> btnRecord.text = resources.getString(R.string.processing)
        }
    }

    protected fun loadEarcons() {
        //Load all the earcons from disk
        startEarcon = Audio(this, R.raw.sk_start, Configuration.PCM_FORMAT)
        stopEarcon = Audio(this, R.raw.sk_stop, Configuration.PCM_FORMAT)
        errorEarcon = Audio(this, R.raw.sk_error, Configuration.PCM_FORMAT)
    }

    protected fun startAudioLevelPoll() {
        audioPoller.run()
    }

    /**
     * Stop polling the users audio level.
     */
    protected fun stopAudioLevelPoll() {
        handler.removeCallbacks(audioPoller)
        barVolume.progress = 0
    }
}
