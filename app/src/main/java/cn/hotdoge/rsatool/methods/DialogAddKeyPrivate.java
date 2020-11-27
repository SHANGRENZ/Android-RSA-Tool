package cn.hotdoge.rsatool.methods;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import cn.hotdoge.rsatool.MainActivity;
import cn.hotdoge.rsatool.R;
import cn.hotdoge.rsatool.overrides.SQLKeyPrivateCreate;
import cn.hotdoge.rsatool.overrides.SQLKeyPublicCreate;
import cn.hotdoge.rsatool.vars.SQLiteInfo;

public class DialogAddKeyPrivate {
    public static void getDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.self);
        LayoutInflater inflater = MainActivity.self.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_addkeyprivate, null);

        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_add_key_private_button_confirm, null);
        builder.setNegativeButton(R.string.dialog_add_key_private_button_cancel, new DialogInterface.OnClickListener() {
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
                        EditText editText1 = view.findViewById(R.id.dialogAddKeyPriEditText1);
                        EditText editText2 = view.findViewById(R.id.dialogAddKeyPriEditText2);
                        if(editText1.getText().toString().equals("") || editText2.getText().toString().equals("")){
                            new AlertDialog.Builder(MainActivity.self).setMessage(R.string.dialog_add_key_public_fail).setNegativeButton(R.string.dialog_add_key_private_button_cancel, null).create().show();
                            return;
                        }
                        if(RSAKeysDataBase.isNickNameAlreadyExists(editText1.getText().toString(), false)){
                            new AlertDialog.Builder(MainActivity.self).setMessage(R.string.dialog_add_key_private_exists).setNegativeButton(R.string.dialog_add_key_private_button_cancel, null).create().show();
                            return;
                        }
                        SQLiteDatabase sqLiteDatabase = new SQLKeyPrivateCreate(MainActivity.self).getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(SQLiteInfo.TablePrivate.Items.NICKNAME, editText1.getText().toString());
                        values.put(SQLiteInfo.TablePrivate.Items.CONTENT, editText2.getText().toString());
                        sqLiteDatabase.insert(SQLiteInfo.TablePrivate.NAME, null, values);
                        sqLiteDatabase.close();

                        alertDialog.dismiss();
                        Snackbar.make(MainActivity.self.findViewById(R.id.fab), R.string.dialog_add_key_private_success, Snackbar.LENGTH_LONG).show();
                        MainActivityToDo.updateSpinnerPrivate(RSAKeysDataBase.getListPrivateNickname());
                    }
                });
            }
        });

        alertDialog.show();

    }
}
