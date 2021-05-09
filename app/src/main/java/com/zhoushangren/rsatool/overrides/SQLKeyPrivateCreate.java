package com.zhoushangren.rsatool.overrides;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zhoushangren.rsatool.vars.SQLiteInfo;

public class SQLKeyPrivateCreate extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = SQLiteInfo.DataBase.NAME;

    public SQLKeyPrivateCreate(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists " +
                SQLiteInfo.TablePrivate.NAME +
                " (Id integer primary key autoincrement, " +
                SQLiteInfo.TablePrivate.Items.NICKNAME + ", " +
                SQLiteInfo.TablePrivate.Items.CONTENT + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int k){

    }
}
