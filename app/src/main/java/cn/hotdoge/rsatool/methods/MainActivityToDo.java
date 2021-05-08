package cn.hotdoge.rsatool.methods;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

        RSA rsa = new RSA();
        String result = rsa.getEncryptedStringFromPublicKey(editTextBefore.getText().toString(), RSAKeysDataBase.getPublicKeyFromDatabase(spinner.getSelectedItem().toString()));
        editTextAfter.setText(result == null ? "null" : result);
    }

    public static void messageDecrypt() {
        Spinner spinner = MainActivity.self.findViewById(R.id.spinnerKeyListPrivate);
        EditText editTextBefore = MainActivity.self.findViewById(R.id.mainContentEditTextBefore);
        EditText editTextAfter = MainActivity.self.findViewById(R.id.mainContentEditTextAfter);

        RSA rsa = new RSA();
        String result = rsa.getDecryptedStringFromPrivateKey(editTextBefore.getText().toString(), RSAKeysDataBase.getPrivateKeyFromDatabase(spinner.getSelectedItem().toString()));
        editTextAfter.setText(result == null ? "null" : result);
    }
}
