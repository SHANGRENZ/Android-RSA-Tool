package com.zhoushangren.rsatool.overrides;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zhoushangren.rsatool.vars.SQLiteInfo;

public class SQLKeyPublicCreate extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = SQLiteInfo.DataBase.NAME;

    public SQLKeyPublicCreate(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists " +
                SQLiteInfo.TablePublic.NAME +
                " (Id integer primary key autoincrement, " +
                SQLiteInfo.TablePublic.Items.NICKNAME + ", " +
                SQLiteInfo.TablePublic.Items.CONTENT + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int k){

    }
}
