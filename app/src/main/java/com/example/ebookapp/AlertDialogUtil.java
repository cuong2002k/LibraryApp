package com.example.ebookapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogUtil {
    public static void showYesNoAlertDialog(Context context, String title, String message,
                                            DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Yes", yesListener);
        builder.setNegativeButton("No", noListener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showOkAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("OK", okListener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
