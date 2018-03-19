package note.speak.speaknote.test

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*
import note.speak.speaknote.R
import note.speak.speaknote.db.Note
import note.speak.speaknote.util.Date.getCurrentDay
import note.speak.speaknote.util.Date.getCurrentTime
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.litepal.crud.DataSupport
import org.litepal.tablemanager.Connector

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        Connector.getDatabase()

        initViews()

        getCurrentTime()
    }

    private fun initViews() {

        btnAdd.setOnClickListener {
            val note = Note()
            note.title = "note title"
            note.content = "note content"
            note.time = getCurrentTime()
            note.day = getCurrentDay()
            note.save()
        }

        btnUpdate.setOnClickListener {
            val note = Note()
            note.title = "note title changed"
            note.updateAll("title = ?", "note title")
        }

        btnQuery.setOnClickListener {
            startActivity(Intent(this, NoteListActivity::class.java))
        }

        btnDelete.setOnClickListener{
            DataSupport.deleteAll(Note::class.java,"title =?","note title")
        }

    }


}
