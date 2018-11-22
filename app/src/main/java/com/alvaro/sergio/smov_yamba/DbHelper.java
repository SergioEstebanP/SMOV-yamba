package com.alvaro.sergio.smov_yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static final String tag = DbHelper.class.getSimpleName();

    public DbHelper(Context context){
        super(context,SupportServices.DB_NAME,null,SupportServices.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = String.format("create table %s (%s int primary key, %s text, %s text, %s int)",
                SupportServices.TABLE,
                SupportServices.ID,
                SupportServices.USER,
                SupportServices.MESSAGE,
                SupportServices.CREATED_AT);

        Log.d(tag,"db created");
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists "+SupportServices.TABLE);

        onCreate(db);
        Log.d(tag,"onUpgrade");
    }


}
