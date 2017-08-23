package com.helabs.eltonjhony.udacitymovies.movies;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.common.BaseHolder;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.databinding.ContentItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    private List<Movie> mResults;
    private OnContentItemClickListener mOnItemClickListener;

    public MoviesRecyclerAdapter(OnContentItemClickListener listener) {
        this.mResults = new ArrayList<>(0);
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ContentItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.content_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mResults.get(position);
        holder.update(movie);
        holder.setListeners(movie);
    }

    @Override
    public int getItemCount() {
        return this.mResults.size();
    }

    public void replaceData(List<Movie> movies) {
        this.mResults.clear();
        this.mResults.addAll(movies);
        notifyDataSetChanged();
    }

    public void appendData(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            return;
        }
        this.mResults.addAll(movies);
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseHolder<Movie> {

        private ContentItemBinding mLayout;

        public ViewHolder(ContentItemBinding binding) {
            super(binding.getRoot());
            this.mLayout = binding;
        }

        @Override
        public void update(Movie movie) {
            Glide.with(mLayout.thumbnailView.getContext())
                    .load(movie.getPosterUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .placeholder(R.drawable.ic_insert_photo_black_48px)
                    .into(mLayout.thumbnailView);

            if (TextUtils.isEmpty(movie.getPosterUrl())) {
                mLayout.titleView.setText(movie.getTitle());
            } else {
                mLayout.titleView.setText(null);
            }
        }

        @Override
        public void setListeners(final Movie movie) {
            itemView.setOnClickListener(v -> {
                v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.image_click));
                int position = getAdapterPosition();
                mOnItemClickListener.onClicked(position, movie.getId());
            });
        }
    }

    public interface OnContentItemClickListener {
        void onClicked(int position, String id);
    }
}
