package com.example.huchuan.wordsbook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.huchuan.wordsbook.util.WordsAction;

import java.util.Set;



public class WordsBookProvider extends ContentProvider{


    public static final String AUTHORITY = "com.huchuan.wordsbook";
    public static final String CONTENT_URI_STRING = "content://" + AUTHORITY;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Log.i("provider", "query: query");
        Context context=getContext();
        WordsAction wordsAction= WordsAction.getInstance(context);
        Cursor cursor=wordsAction.getAllWordsFromSQLite();
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
