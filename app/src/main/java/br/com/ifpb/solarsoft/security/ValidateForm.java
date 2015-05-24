package br.com.ifpb.solarsoft.security;

import android.widget.EditText;

/**
 * Created by leonardo on 18/05/2015.
 */
public class ValidateForm {

    public static boolean validadeField(EditText... ets) {
        for (int i = 0; i <= (ets.length - 1); i++){
            if(ets[i].getText().toString().equals("") || ets[i].getText() == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean validadeField(String regex, EditText... ets) {
        for (int i = 0; i <= (ets.length - 1); i++){
            if(ets[i].getText().toString().equals("") || ets[i].getText() == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean validadeField(String[] regexArray, EditText et) {
        for (int i = 0; i <= (regexArray.length - 1); i++){
            if(!et.getText().toString().matches(regexArray[i])) {
                return false;
            }
        }
        return true;
    }
}
