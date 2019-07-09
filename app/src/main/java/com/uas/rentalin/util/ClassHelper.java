package com.uas.rentalin.util;

import android.content.Context;
import android.widget.Toast;

public class ClassHelper {

    public void setToast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
