package note.speak.speaknote.widgets.recyclerview.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int leftSpace, topSpace;
    private int rightSpace, bottomSpace;
    private int left, right;
    private int top, bottom;

    public SpaceItemDecoration(int left, int top, int right, int bottom, boolean flag){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        if (flag) {
            this.leftSpace = left;
            this.topSpace = top;
            this.rightSpace = right;
            this.bottomSpace = bottom;
        }
    }

    public SpaceItemDecoration(int left, int top, int right, int bottom, int leftSpace, int topSpace, int rightSpace, int bottomSpace){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.leftSpace = leftSpace;
        this.topSpace = topSpace;
        this.rightSpace = rightSpace;
        this.bottomSpace = bottomSpace;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = left;
        outRect.top = top;
        outRect.right = right;
        outRect.bottom = bottom;
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            int totalCount = layoutManager.getItemCount();
            int spanCount = ((GridLayoutManager)layoutManager).getSpanCount();
            //divide exactly
            int surplusCount = totalCount % spanCount;
            int childPosition = parent.getChildAdapterPosition(view);
            if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
                //left column
                if (childPosition % spanCount == 0){
                    outRect.left = leftSpace;
                }
                //top raw
                if (childPosition < spanCount){
                    outRect.top = topSpace;
                }
                //right column
                if ((childPosition + 1) % spanCount == 0) {
                    outRect.right = rightSpace;
                }
                //bottom raw
                if (surplusCount == 0 && childPosition > totalCount - spanCount - 1) {
                    outRect.bottom = bottomSpace;
                } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                    outRect.bottom = bottomSpace;
                }
            } else {
                //left column
                if (childPosition < spanCount){
                    outRect.left = leftSpace;
                }
                //top raw
                if (childPosition % spanCount == 0){
                    outRect.top = topSpace;
                }
                //right column
                if (surplusCount == 0 && childPosition > totalCount - spanCount - 1) {
                    outRect.right = rightSpace;
                } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                    outRect.right = rightSpace;
                }
                //bottom raw
                if ((childPosition + 1) % spanCount == 0) {
                    outRect.bottom = bottomSpace;
                }
            }
        } else {
            int totalCount = layoutManager.getItemCount();
            if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL){
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = topSpace;
                }
                if (parent.getChildAdapterPosition(view) == totalCount - 1) {
                    outRect.bottom = bottomSpace;
                }
            } else {
                if (parent.getChildAdapterPosition(view) == 0){
                    outRect.left = leftSpace;
                }
                if (parent.getChildAdapterPosition(view) == totalCount - 1) {
                    outRect.right = rightSpace;
                }
            }
        }
    }
}
