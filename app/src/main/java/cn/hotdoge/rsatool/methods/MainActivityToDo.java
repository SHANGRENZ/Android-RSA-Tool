package cn.hotdoge.rsatool.methods;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;

import cn.hotdoge.rsatool.MainActivity;
import cn.hotdoge.rsatool.R;

public class MainActivityToDo {

    public static void updateSpinnerPublic(ArrayList<String> arrayList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.self, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = MainActivity.self.findViewById(R.id.spinnerKeyListPublic);
        spinner.setAdapter(arrayAdapter);

    }

    public static void updateSpinnerPrivate(ArrayList<String> arrayList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.self, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = MainActivity.self.findViewById(R.id.spinnerKeyListPrivate);
        spinner.setAdapter(arrayAdapter);

    }

    public static void messageEncrypt() {
        Spinner spinner = MainActivity.self.findViewById(R.id.spinnerKeyListPublic);
        EditText editTextBefore = MainActivity.self.findViewById(R.id.mainContentEditTextBefore);
        EditText editTextAfter = MainActivity.self.findViewById(R.id.mainContentEditTextAfter);

        if(spinner.getSelectedItem() == null){
            Snackbar.make(MainActivity.self.findViewById(R.id.fab), R.string.encrypt_string_no_public_key, Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }
        if(!editTextBefore.getText().toString().equals("")) {
            editTextBefore.clearFocus();
        }
        RSA rsa = new RSA();
        String result = rsa.getEncryptedStringFromPublicKey(editTextBefore.getText().toString(), RSAKeysDataBase.getPublicKeyFromDatabase(spinner.getSelectedItem().toString()));
        editTextAfter.setText(result == null ? "null" : result);
    }

    public static void messageDecrypt() {
        Spinner spinner = MainActivity.self.findViewById(R.id.spinnerKeyListPrivate);
        EditText editTextBefore = MainActivity.self.findViewById(R.id.mainContentEditTextBefore);
        EditText editTextAfter = MainActivity.self.findViewById(R.id.mainContentEditTextAfter);

        if(spinner.getSelectedItem() == null){
            Snackbar.make(MainActivity.self.findViewById(R.id.fab), R.string.decrypt_string_no_private_key, Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }
        if(!editTextBefore.getText().toString().equals("")) {
            editTextBefore.clearFocus();
        }
        RSA rsa = new RSA();
        String result = rsa.getDecryptedStringFromPrivateKey(editTextBefore.getText().toString(), RSAKeysDataBase.getPrivateKeyFromDatabase(spinner.getSelectedItem().toString()));
        editTextAfter.setText(result == null ? "null" : result);
    }

    public static void floatingActionButtonClick(View view) {
        String snackbarMsg;
        EditText editTextAfter = MainActivity.self.findViewById(R.id.mainContentEditTextAfter);
        if(editTextAfter.getText().toString().equals("")){
            snackbarMsg = MainActivity.self.getString(R.string.floating_action_button_nothing_to_copy);
        } else {
            snackbarMsg = MainActivity.self.getString(R.string.floating_action_button_copy_success);
            ClipboardManager clipboardManager = (ClipboardManager) MainActivity.self.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Result", editTextAfter.getText().toString());
            clipboardManager.setPrimaryClip(clipData);
        }
        Snackbar.make(view, snackbarMsg, Snackbar.LENGTH_SHORT)
                .show();
    }

    public static void edittextBeforeOnFocus(View view, boolean hasFocus) {
        if(hasFocus) {
            ClipboardManager clipboardManager = (ClipboardManager) MainActivity.self.getSystemService(Context.CLIPBOARD_SERVICE);
            if(clipboardManager != null && clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClip().getItemCount() > 0) {
                CharSequence charSequence = clipboardManager.getPrimaryClip().getItemAt(0).getText();
                String charSequenceString = String.valueOf(charSequence);
                if(!TextUtils.isEmpty(charSequenceString)){
                    EditText editText = view.findViewById(R.id.mainContentEditTextBefore);
                    editText.setText(charSequenceString);
                    editText.setSelectAllOnFocus(true);
                    editText.selectAll();
                }
            }
        }
    }
}
