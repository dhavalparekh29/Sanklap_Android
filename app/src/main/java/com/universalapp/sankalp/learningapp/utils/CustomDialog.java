package com.universalapp.sankalp.learningapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.universalapp.sankalp.learningapp.R;

/**
 * Created by user on 2/7/2018.
 */

public class CustomDialog {


    public static void commonDialog(Activity activity, String title, String message, String confirmButtonText) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_common);

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_message);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        TextView tvConfirm = (TextView) dialog.findViewById(R.id.tv_submit);

        tvTitle.setText(title);
        tvMessage.setText(message);
        tvConfirm.setText(confirmButtonText);

        tvCancel.setVisibility(View.GONE);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!activity.isFinishing())
            dialog.show();
    }


}
