package cn.hotdoge.rsatool.methods;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AlertDialog;

import cn.hotdoge.rsatool.MainActivity;
import cn.hotdoge.rsatool.R;

public class DialogFirstRun {
    public static void getDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.self);
        LayoutInflater inflater = MainActivity.self.getLayoutInflater();
        final SharedPreferences sharedPreferences = MainActivity.self.getSharedPreferences("settings", MainActivity.self.MODE_PRIVATE);
        final View view = inflater.inflate(R.layout.dialog_firstrun, null);

        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_first_run_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CheckBox checkBox = view.findViewById(R.id.checkBoxFirstRunDialog);
                if(checkBox.isChecked()){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("showFirstRunDialog", false);
                    editor.apply();
                }
            }
        });
        if(!sharedPreferences.getBoolean("showFirstRunDialog", true))
            view.findViewById(R.id.checkBoxFirstRunDialog).setVisibility(View.GONE);
        builder.create().show();
    }
}
