package com.zhoushangren.rsatool;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.zhoushangren.rsatool.methods.DialogAddKeyPrivate;
import com.zhoushangren.rsatool.methods.DialogAddKeyPublic;
import com.zhoushangren.rsatool.methods.DialogCreateKey;
import com.zhoushangren.rsatool.methods.DialogFirstRun;
import com.zhoushangren.rsatool.methods.MainActivityToDo;
import com.zhoushangren.rsatool.methods.RSAKeysDataBase;

public class MainActivity extends AppCompatActivity {

    public static MainActivity self;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivityToDo.floatingActionButtonClick(view);
            }
        });

        self = this;

        //first run
        firstRun();

        MainActivityToDo.updateSpinnerPublic(RSAKeysDataBase.getListPublicNickname());
        MainActivityToDo.updateSpinnerPrivate(RSAKeysDataBase.getListPrivateNickname());

        //onclick events
        this.findViewById(R.id.mainContentButtonEncrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityToDo.messageEncrypt();
            }
        });
        this.findViewById(R.id.mainContentButtonDecrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityToDo.messageDecrypt();
            }
        });

        //edittext on focus events
        this.findViewById(R.id.mainContentEditTextBefore).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                MainActivityToDo.edittextBeforeOnFocus(v, hasFocus);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_help) {
            DialogFirstRun.getDialog();
            return true;
        } else if (id == R.id.menu_add_key_public){
            DialogAddKeyPublic.getDialog();
            return true;
        } else if (id == R.id.menu_add_key_private){
            DialogAddKeyPrivate.getDialog();
            return true;
        } else if (id == R.id.menu_create_key){
            DialogCreateKey.getDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void firstRun() {
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        Boolean firstRun = sharedPreferences.getBoolean("showFirstRunDialog", true);
        if (firstRun){
            DialogFirstRun.getDialog();
        }
    }
}
