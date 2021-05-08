package cn.hotdoge.rsatool.methods;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import cn.hotdoge.rsatool.MainActivity;
import cn.hotdoge.rsatool.overrides.SQLKeyPrivateCreate;
import cn.hotdoge.rsatool.overrides.SQLKeyPublicCreate;
import cn.hotdoge.rsatool.vars.SQLiteInfo;

public class RSAKeysDataBase {
    public static ArrayList<String> getListPublicNickname() {
        SQLiteDatabase sqLiteDatabase = new SQLKeyPublicCreate(MainActivity.self).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(SQLiteInfo.TablePublic.NAME, new String[]{SQLiteInfo.TablePublic.Items.NICKNAME}, null, null, null, null, null, null);
        ArrayList<String> arrayList = new ArrayList<>();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++){
                arrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteInfo.TablePublic.Items.NICKNAME)));
                cursor.moveToNext();
            }
        }

        cursor.close();
        sqLiteDatabase.close();

        return arrayList;
    }

    public static ArrayList<String> getListPrivateNickname() {
        SQLiteDatabase sqLiteDatabase = new SQLKeyPrivateCreate(MainActivity.self).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(SQLiteInfo.TablePrivate.NAME, new String[]{SQLiteInfo.TablePrivate.Items.NICKNAME}, null, null, null, null, null, null);
        ArrayList<String> arrayList = new ArrayList<>();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++){
                arrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteInfo.TablePrivate.Items.NICKNAME)));
                cursor.moveToNext();
            }
        }

        cursor.close();
        sqLiteDatabase.close();

        return arrayList;
    }

    public static boolean isNickNameAlreadyExists(String nickName, Boolean isPublic) {
        SQLiteDatabase sqLiteDatabase = isPublic ? new SQLKeyPublicCreate(MainActivity.self).getReadableDatabase() : new SQLKeyPrivateCreate(MainActivity.self).getReadableDatabase();
        String NICKNAME = isPublic ? SQLiteInfo.TablePublic.Items.NICKNAME : SQLiteInfo.TablePrivate.Items.NICKNAME;
        Cursor cursor = sqLiteDatabase.query(isPublic ? SQLiteInfo.TablePublic.NAME : SQLiteInfo.TablePrivate.NAME, new String[]{NICKNAME}, NICKNAME + "=?", new String[]{nickName}, null, null, null, null);
        Boolean alreadyExists = cursor.getCount() > 0;

        cursor.close();
        sqLiteDatabase.close();

        return alreadyExists;
    }

    public static String getPublicKeyFromDatabase(String nickname) {
        SQLiteDatabase sqLiteDatabase = new SQLKeyPublicCreate(MainActivity.self).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(SQLiteInfo.TablePublic.NAME, null, SQLiteInfo.TablePublic.Items.NICKNAME+"=?", new String[]{nickname}, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(SQLiteInfo.TablePublic.Items.CONTENT));
    }

    public static String getPrivateKeyFromDatabase(String nickname) {
        SQLiteDatabase sqLiteDatabase = new SQLKeyPrivateCreate(MainActivity.self).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(SQLiteInfo.TablePrivate.NAME, null, SQLiteInfo.TablePrivate.Items.NICKNAME+"=?", new String[]{nickname}, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(SQLiteInfo.TablePrivate.Items.CONTENT));
    }
}
