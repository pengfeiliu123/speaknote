package note.speak.speaknote.widgets.recyclerview;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import note.speak.speaknote.widgets.recyclerview.footer.Footer;
import note.speak.speaknote.widgets.recyclerview.footer.FooterAnimationViewBinder;
import note.speak.speaknote.widgets.recyclerview.interfaces.IReleasable;

public class MXRecyclerView extends RecyclerView {
    private OnActionListener mActionListener;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean isLoadingMore;
    private boolean canLoadMore = true;
    private boolean canRefresh = true;

    public MXRecyclerView(Context context) {
        this(context, null);
    }

    public MXRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MXRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.addOnChildAttachStateChangeListener(new OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                ViewHolder holder = getChildViewHolder(view);
                if (holder != null && holder instanceof IReleasable) {
                    ((IReleasable) holder).release();
                }
            }
        });
    }

    public void setOnActionListener(OnActionListener listener) {
        mActionListener = listener;
        if (!canRefresh) {
            return;
        }
        if (mRefreshLayout == null) {
            initRefreshLayout();
        }
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mActionListener != null) {
                    mActionListener.onRefresh();
                }
            }
        });
    }

    public void finishRefreshing() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    public void finishLoadingMore() {
        if (!canLoadMore || !isLoadingMore) {
            return;
        }
        isLoadingMore = false;
        MultiTypeAdapter adapter = (MultiTypeAdapter) getAdapter();
        List<?> items = adapter.getItems();
        if (items == null || items.size() == 0) {
            return;
        }
        int index = items.size() - 1;
        items.remove(index);
        adapter.notifyItemRemoved(index);
    }

    public void showRefreshing() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(true);
        }
    }

    private void initRefreshLayout() {
        if (getParent() != null) {
            if (getParent() instanceof SwipeRefreshLayout) {
                mRefreshLayout = (SwipeRefreshLayout) getParent();
            } else if (getParent() instanceof ViewGroup) {
                SwipeRefreshLayout refreshLayout = new SwipeRefreshLayout(getContext());
//                refreshLayout.setColorSchemeResources(R.color.main_orange_color);
                refreshLayout.setProgressViewOffset(false, 0, dip2px(getContext(), 33));
                refreshLayout.setDistanceToTriggerSync(192);
                if (this.getLayoutParams() != null) {
                    refreshLayout.setLayoutParams(this.getLayoutParams());
                }
                ViewGroup vp = (ViewGroup) getParent();
                for (int i = 0; i < vp.getChildCount(); i++) {
                    if (vp.getChildAt(i) == this) {
                        vp.removeViewAt(i);
                        refreshLayout.addView(this);
                        vp.addView(refreshLayout, i);
                    }
                }
                mRefreshLayout = refreshLayout;
            } else {
                throw new RuntimeException("You must attach this RecyclerView to a ViewGroup!!");
            }
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && (this.computeVerticalScrollExtent() + this.computeVerticalScrollOffset() >= this.computeVerticalScrollRange())) {
            this.onBottom();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof MultiTypeAdapter) {
            MultiTypeAdapter multiTypeAdapter = (MultiTypeAdapter) adapter;
            multiTypeAdapter.register(Footer.class, new FooterAnimationViewBinder());
        } else {
            Log.e(MXRecyclerView.class.getSimpleName(), "The EPocketRecyclerView only support MultiTypeAdapter");
        }
        super.setAdapter(adapter);
    }

    public void onBottom() {
        if (!canLoadMore || isLoadingMore) {
            return;
        }
        Footer footer = new Footer();
        MultiTypeAdapter adapter = (MultiTypeAdapter) getAdapter();
        List<Object> items = (List<Object>) adapter.getItems();
        if (items == null || items.size() == 0) {
            return;
        }
        items.add(footer);
        adapter.notifyItemInserted(items.size() - 1);
        isLoadingMore = true;

        this.scrollToPosition(items.size() - 1);

        if (mActionListener != null) {
            mActionListener.onLoadMore();
        }
    }

    public void enableLoadingMore() {
        canLoadMore = true;
    }

    public void disableLoadingMore() {
        canLoadMore = false;
    }

    public void enableRefresh() {
        canRefresh = true;
    }

    //要在setOnActionListener前禁用刷新才有效
    public void disableRefresh() {
        canRefresh = false;
    }

    public interface OnActionListener {
        void onLoadMore();

        void onRefresh();
    }

    public static int dip2px(Context context, double dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * (double) density + 0.5D);
    }

    public int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
