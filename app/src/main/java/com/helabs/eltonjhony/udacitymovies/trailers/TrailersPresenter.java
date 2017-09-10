package com.helabs.eltonjhony.udacitymovies.trailers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.helabs.eltonjhony.udacitymovies.common.BasePresenter;
import com.helabs.eltonjhony.udacitymovies.data.repository.TrailersRepository;
import com.helabs.eltonjhony.udacitymovies.infrastructure.MyLog;
import com.helabs.eltonjhony.udacitymovies.data.model.Video;
import com.helabs.eltonjhony.udacitymovies.data.model.VideoWrapper;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class TrailersPresenter extends BasePresenter<TrailersContract.View> implements TrailersContract.Actions {

    private Subscription mSubscription;
    private TrailersRepository trailersRepository;

    @Inject
    public TrailersPresenter(WeakReference<TrailersContract.View> view, TrailersRepository trailersRepository) {
        super(view);
        this.trailersRepository = trailersRepository;
    }

    @Override
    public void fetchTrailers(String movieId) {
        mSubscription = trailersRepository.getVideosById(movieId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoWrapper>() {
                    @Override
                    public void onCompleted() {
                        mSubscription.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(VideoWrapper videoWrapper) {
                        if (videoWrapper != null && !videoWrapper.getResults().isEmpty()) {
                            getView().showTrailers(videoWrapper.getResults());
                        } else {
                            getView().hideTrailerSection();
                        }
                    }
                });
    }

    @Override
    public void loadThumbnail(Video video, String url) {
        mSubscription = Observable.just(url)
                .flatMap(loadImageBitmap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onCompleted() {
                        mSubscription.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideTrailerSection();
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null) {
                            getView().addTrailerToGallery(video, bitmap);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        super.detachView();
    }

    @NonNull
    private Func1<String, Observable<Bitmap>> loadImageBitmap() {
        return src -> {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return Observable.from(new Bitmap[]{BitmapFactory.decodeStream(input)});
            } catch (Exception e) {
                MyLog.info(e.getMessage());
                return Observable.from((Bitmap[]) null);
            }
        };
    }
}
