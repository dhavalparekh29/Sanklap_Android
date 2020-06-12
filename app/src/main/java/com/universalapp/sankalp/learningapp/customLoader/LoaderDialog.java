package com.universalapp.sankalp.learningapp.customLoader;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.TextView;

import com.universalapp.sankalp.learningapp.R;


public class LoaderDialog {

    private static Dialog dialog = null;

    public  LoaderDialog(Activity activity) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_progress_loader);

    }
    public  LoaderDialog(Activity activity,String message) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_progress_loader);
        TextView textViewMessage = dialog.findViewById(R.id.text_loader_message);
        textViewMessage.setText(message);
    }

    public void showProgress(){
        dialog.show();
    }
    public boolean isShowing(){
        return dialog.isShowing();
    }

    public void hideProgressBar(){
        dialog.dismiss();
    }


}
