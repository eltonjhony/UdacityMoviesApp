package com.helabs.eltonjhony.udacitymovies.common;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by eltonjhony on 08/04/17.
 */
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private static final int NUMBER_OF_REMAINING_ITEMS = 1;

    private int currentPage = 1;
    private boolean isLoading = false;

    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        int totalItemCount = mLayoutManager.getItemCount();

        if (!isLoading() && lastVisibleItemPosition == totalItemCount - NUMBER_OF_REMAINING_ITEMS) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, recyclerView);
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void resetCurrentPage() {
        this.currentPage = 1;
        setLoading(false);
    }

    public abstract void onLoadMore(int currentPage, int totalItemCount, RecyclerView recyclerView);
}
