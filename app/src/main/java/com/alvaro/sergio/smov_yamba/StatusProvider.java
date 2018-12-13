package com.alvaro.sergio.smov_yamba;

import android.content.ContentProvider;
import android.support.annotation.*;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class StatusProvider extends ContentProvider {

    private static final String TAG = StatusProvider.class.getSimpleName();
    private DbHelper dbHelper;
    private static final UriMatcher sURIMatcher;


    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(SupportServices.AUTHORITY,SupportServices.TABLE,SupportServices.STATUS_DIR);
        sURIMatcher.addURI(SupportServices.AUTHORITY,SupportServices.TABLE+"/#",SupportServices.STATUS_ITEM);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        Log.d(TAG,"onCreated");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String where;
        switch (sURIMatcher.match(uri)) {
            case SupportServices.STATUS_DIR:
                where = selection;
                break;
            case SupportServices.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = SupportServices.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( " + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
        String orderBy = (TextUtils.isEmpty(sortOrder)) ? SupportServices.sort : sortOrder;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(SupportServices.TABLE, projection, where, selectionArgs, null, null,
                orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.d(TAG, "registros recuperados: " + cursor.getCount());
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case SupportServices.STATUS_DIR:
                Log.d(TAG, "gotType:vnd.android.cursor.dir/vnd.com.alvaro.sergio.smov_yamba.provider.status");
                return "vnd.android.cursor.dir/vnd.com.alvaro.sergio.smov_yamba.provider.status";
            case SupportServices.STATUS_ITEM:
                Log.d(TAG, "gotType:vnd.android.cursor.item/vnd.com.alvaro.sergio.smov_yamba.provider.status");
                return
                        "vnd.android.cursor.item/vnd.com.alvaro.sergio.smov_yamba.provider.status";
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri ret = null;
        if (sURIMatcher.match(uri) != SupportServices.STATUS_DIR) {
            throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insertWithOnConflict(SupportServices.TABLE, null,
                contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (rowId != -1) {
            long id = contentValues.getAsLong(SupportServices.ID);
            ret = ContentUris.withAppendedId(uri, id);
            Log.d(TAG,"uri insertada: " + ret);
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ret;    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String where;
        switch (sURIMatcher.match(uri)) {
            case SupportServices.STATUS_DIR:
                where = selection;
                break;
            case SupportServices.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = SupportServices.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( " + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.delete(SupportServices.TABLE, where, selectionArgs);
        if (ret > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "registros borrados: " + ret);
        return ret;    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String s, @Nullable String[] selectionArgs) {
        String where;
        switch (sURIMatcher.match(uri)) {
            case SupportServices.STATUS_DIR:
                where = s;
                break;
            case SupportServices.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = SupportServices.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(s) ? "" : " and ( " + s + " )");
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.update(SupportServices.TABLE, values, where, selectionArgs);
        if (ret > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "registros actualizados: " + ret);
        return ret;
    }
}
