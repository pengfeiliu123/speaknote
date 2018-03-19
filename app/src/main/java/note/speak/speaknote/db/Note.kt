package note.speak.speaknote.db

import org.litepal.crud.DataSupport

/**
 * Created by liupengfei on 2018/3/19 18:56.
 */
public class Note : DataSupport() {

    val id: Int = 0
    var day: String? = null
    var time: String? = null
    var title: String? = null
    var content: String? = null

}