package note.speak.speaknote.widgets.recyclerview.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewBinder;
import note.speak.speaknote.R;
import note.speak.speaknote.db.Note;

/**
 * Created by liupengfei on 2018/2/11 17:31.
 */
public class ResponseEntityViewBinder extends ItemViewBinder<Note, ResponseEntityViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_note, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Note bean) {
        holder.bindData(bean, holder.getAdapterPosition());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle;

        ViewHolder(View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.item_title);
        }

        void bindData(final Note bean, final int position) {
            if (bean == null) {
                return;
            }
            noteTitle.setText(bean.getTitle());
//            ImageLoader.getInstance().loadCircleImage(itemView.getContext(), bean.getImages().getMedium(), movieImage, R.mipmap.ic_launcher);
        }
    }
}
