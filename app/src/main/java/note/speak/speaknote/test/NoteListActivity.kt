package note.speak.speaknote.test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_note_list.*
import me.drakeet.multitype.MultiTypeAdapter
import note.speak.speaknote.R
import note.speak.speaknote.db.Note
import org.jetbrains.anko.toast
import org.litepal.crud.DataSupport

class NoteListActivity : AppCompatActivity() {

    private var note: Note? = null
    private var loadMore: Boolean = false
    private var noteList: List<Note>? = null
    private var noteAdapter: MultiTypeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        requestLocalNotes()
        initViews()
    }

    private fun requestLocalNotes() {

        noteList = DataSupport.findAll(Note::class.java)

    }

    private fun initViews() {

        initRecyclerView();

        //floatingbutton
        initFloatButton();
    }

    private fun initFloatButton() {

        btnFloat.setOnClickListener {
            startActivity(Intent(this,NoteAddActivity::class.java))
        }

    }

    private fun initRecyclerView() {
        initLayoutManager()
        // need modify
        noteAdapter = MultiTypeAdapter(noteList)
        initAdapter(noteAdapter!!)
    }

    private fun initAdapter(noteAdapter: MultiTypeAdapter) {
        noteAdapter.register(Note::class.java, NoteViewBinder())
        rvNote.adapter = noteAdapter
    }

    private fun initLayoutManager() {
        rvNote.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(AppCompatResources.getDrawable(this, R.drawable.rv_divider)!!)
        rvNote.addItemDecoration(dividerItemDecoration)
        rvNote.disableLoadingMore()
    }
}
