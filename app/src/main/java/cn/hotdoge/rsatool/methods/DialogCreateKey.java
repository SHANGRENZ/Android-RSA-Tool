package cn.hotdoge.rsatool.methods;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Method;

import cn.hotdoge.rsatool.MainActivity;
import cn.hotdoge.rsatool.R;
import cn.hotdoge.rsatool.overrides.SQLKeyPrivateCreate;
import cn.hotdoge.rsatool.overrides.SQLKeyPublicCreate;
import cn.hotdoge.rsatool.vars.SQLiteInfo;

public class DialogCreateKey {

    public static void getDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.self);
        LayoutInflater inflater = MainActivity.self.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_createkey, null);

        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_create_key_button_confirm, null);
        builder.setNegativeButton(R.string.dialog_create_key_button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //2do
            }
        });

        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText1 = view.findViewById(R.id.dialogCreateKeyEditText1);
                        EditText editText2 = view.findViewById(R.id.dialogCreateKeyEditText2);
                        EditText editText3 = view.findViewById(R.id.dialogCreateKeyEditText3);

                        if(editText1.getText().toString().equals("") || editText2.getText().toString().equals("")){
                            new AlertDialog.Builder(MainActivity.self).setMessage(R.string.dialog_create_key_fail).setNegativeButton(R.string.dialog_create_key_button_cancel, null).create().show();
                            return;
                        }
                        if(RSAKeysDataBase.isNickNameAlreadyExists(editText1.getText().toString(), false)){
                            new AlertDialog.Builder(MainActivity.self).setMessage(R.string.dialog_create_key_exists).setNegativeButton(R.string.dialog_create_key_button_cancel, null).create().show();
                            return;
                        }

                        //Adding private
                        SQLiteDatabase sqLiteDatabase = new SQLKeyPrivateCreate(MainActivity.self).getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(SQLiteInfo.TablePrivate.Items.NICKNAME, editText1.getText().toString());
                        values.put(SQLiteInfo.TablePrivate.Items.CONTENT, editText2.getText().toString());
                        sqLiteDatabase.insert(SQLiteInfo.TablePrivate.NAME, null, values);
                        sqLiteDatabase.close();

                        //Adding public
                        sqLiteDatabase = new SQLKeyPublicCreate(MainActivity.self).getWritableDatabase();
                        values = new ContentValues();
                        values.put(SQLiteInfo.TablePublic.Items.NICKNAME, editText1.getText().toString());
                        values.put(SQLiteInfo.TablePublic.Items.CONTENT, editText3.getText().toString());
                        sqLiteDatabase.insert(SQLiteInfo.TablePublic.NAME, null, values);
                        sqLiteDatabase.close();

                        alertDialog.dismiss();
                        Snackbar.make(MainActivity.self.findViewById(R.id.fab), R.string.dialog_create_key_success, Snackbar.LENGTH_LONG).show();
                        MainActivityToDo.updateSpinnerPrivate(RSAKeysDataBase.getListPrivateNickname());
                        MainActivityToDo.updateSpinnerPublic(RSAKeysDataBase.getListPublicNickname());
                    }
                });

                Button buttonGenerate = alertDialog.findViewById(R.id.dialogCreateKeyButton1);
                buttonGenerate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        String privateKey = new RSA().generateNewPrivateKey();
                        EditText editText1 = alertDialog.findViewById(R.id.dialogCreateKeyEditText2);
                        EditText editText2 = alertDialog.findViewById(R.id.dialogCreateKeyEditText3);
                        //Log.w("KEY", new RSA().getPublicKeyFromPrivateKey(privateKey));
                        editText1.setText(privateKey);
                        editText2.setText(new RSA().getPublicKeyFromPrivateKey(privateKey));
                        v.setEnabled(true);
                    }
                });
            }
        });

        alertDialog.show();

    }
}
