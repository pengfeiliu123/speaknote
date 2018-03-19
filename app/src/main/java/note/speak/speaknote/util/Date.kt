package note.speak.speaknote.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by liupengfei on 2018/3/19 19:40.
 */
object Date{

    public fun getCurrentDay(): String {
        var dayStr: String? = null
        val format = SimpleDateFormat("yyyy/MM/dd")

        dayStr = format.format(Date())
        Log.d("lpf", dayStr)
        return dayStr!!
    }

    public fun getCurrentTime(): String {
        var timeStr: String? = null
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss ")

        timeStr = format.format(Date())
        Log.d("lpf", timeStr)
        return timeStr!!
    }
}