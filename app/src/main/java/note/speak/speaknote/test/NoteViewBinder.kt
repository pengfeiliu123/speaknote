package note.speak.speaknote.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import me.drakeet.multitype.ItemViewBinder
import note.speak.speaknote.R
import note.speak.speaknote.db.Note
import org.jetbrains.anko.startActivity

/**
 * Created by liupengfei on 2018/2/11 17:31.
 */
class NoteViewBinder() : ItemViewBinder<Note, NoteViewBinder.ViewHolder>() {

    private var mContext: Context? = null

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    override fun onCreateViewHolder(
            inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_note, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, bean: Note) {
        holder.bindData(bean, holder.adapterPosition)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val context: Context = itemView.context

        var noteTitle: TextView

        init {
            noteTitle = itemView.findViewById(R.id.item_title)
        }

        fun bindData(bean: Note?, position: Int) {
            if (bean == null) {
                return
            }
            noteTitle.text = bean.title
            noteTitle.setOnClickListener {
                var intent = Intent(context, NoteDetailActivity::class.java)
                intent.putExtra("noteId",bean.id)
                itemView.context.startActivity(intent)
            }

            //            ImageLoader.getInstance().loadCircleImage(itemView.getContext(), bean.getImages().getMedium(), movieImage, R.mipmap.ic_launcher);
        }
    }
}
