package com.example.ebookapp;

import android.content.Context;
import android.content.DialogInterface;

import com.example.ebookapp.View.Author_Edit_Activity;

public class OKAlert {
    public static void ShowOkeAlert(Context context)
    {
        AlertDialogUtil.showOkAlertDialog(context, "Thông báo",
                "Dữ liệu trống hoặc không hợp lệ.",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng chọn OK
                        dialog.dismiss(); // Đóng cửa sổ thông báo
                    }
                });
    }
}
