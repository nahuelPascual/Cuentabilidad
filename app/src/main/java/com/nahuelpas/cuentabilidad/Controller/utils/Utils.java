package com.nahuelpas.cuentabilidad.Controller.utils;

import android.widget.Spinner;

public class Utils {

    public static int getPosicionItemSpinner(Spinner spinner, String descripcion) {
        for (int i=0; i<spinner.getCount(); i++){
            String item = (String) spinner.getItemAtPosition(i);
            if(item.equals(descripcion)){
                return i;
            }
        }
        return -1;
    }

}
