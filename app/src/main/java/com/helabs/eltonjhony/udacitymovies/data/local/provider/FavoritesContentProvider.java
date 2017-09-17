package com.helabs.eltonjhony.udacitymovies.data.local.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.helabs.eltonjhony.udacitymovies.BuildConfig;
import com.helabs.eltonjhony.udacitymovies.data.local.LocalDatabase;
import com.helabs.eltonjhony.udacitymovies.data.local.table.FavoritesTable;

/**
 * Created by eltonjhony on 17/09/17.
 */

public class FavoritesContentProvider extends ContentProvider {

    private LocalDatabase localDatabase;

    private static final int FAVORITES_DIR = 1;
    private static final int FAVORITES_ID = 2;
    private static final String CONTENT_PATH = "favorite";

    private static final String AUTHORITY = BuildConfig.APPLICATION_ID;
    private static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    // Content URIs
    public static final Uri FAVORITES_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);

    private static final UriMatcher URI_MATCHER;
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, CONTENT_PATH, FAVORITES_DIR);
        URI_MATCHER.addURI(AUTHORITY, CONTENT_PATH + "/#", FAVORITES_ID);
    }

    @Override
    public boolean onCreate() {
        localDatabase = new LocalDatabase(getContext());
        return false;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case FAVORITES_DIR:
                return "vnd.android.cursor.dir/com.helabs.eltonjhony.udacitymovies.contentprovider.favorites";
            case FAVORITES_ID:
                return "vnd.android.cursor.item/com.helabs.eltonjhony.udacitymovies.contentprovider.favorites";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = localDatabase.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(FavoritesTable.TABLE_NAME);

        switch (URI_MATCHER.match(uri)) {

            case FAVORITES_DIR:
                //do nothing
                break;

            case FAVORITES_ID:
                queryBuilder.appendWhere(FavoritesTable.MOVIE_ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase db = localDatabase.getWritableDatabase();

        try {
            db.beginTransaction();
            switch (URI_MATCHER.match(uri)) {
                case FAVORITES_DIR:
                case FAVORITES_ID:
                    Long productId = db.insertOrThrow(FavoritesTable.TABLE_NAME, null, values);
                    Uri newFavorite = ContentUris.withAppendedId(FAVORITES_CONTENT_URI, productId);
                    getContext().getContentResolver().notifyChange(newFavorite, null);
                    db.setTransactionSuccessful();
                    return newFavorite;

                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = localDatabase.getWritableDatabase();
        int deleteCount = 0;

        try {
            db.beginTransaction();
            switch (URI_MATCHER.match(uri)) {

                case FAVORITES_DIR:
                    deleteCount = db.delete(FavoritesTable.TABLE_NAME, selection, selectionArgs);
                    db.setTransactionSuccessful();
                    break;
                case FAVORITES_ID:
                    deleteCount = db.delete(FavoritesTable.TABLE_NAME, FavoritesTable.WHERE_ID_EQUALS, new String[]{uri.getPathSegments().get(1)});
                    db.setTransactionSuccessful();
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        } finally {
            db.endTransaction();
        }

        if (deleteCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleteCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase dbConnection = localDatabase.getWritableDatabase();
        int updateCount = 0;

        try {

            dbConnection.beginTransaction();

            switch (URI_MATCHER.match(uri)) {

                case FAVORITES_DIR:
                    updateCount = dbConnection.update(FavoritesTable.TABLE_NAME, values, selection, selectionArgs);
                    dbConnection.setTransactionSuccessful();
                    break;
                case FAVORITES_ID:
                    Long favoriteId = ContentUris.parseId(uri);
                    updateCount = dbConnection.update(FavoritesTable.TABLE_NAME, values,
                            FavoritesTable.MOVIE_ID + "=" + favoriteId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")"),
                            selectionArgs);
                    dbConnection.setTransactionSuccessful();
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        } finally {
            dbConnection.endTransaction();
        }

        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updateCount;
    }
}
