package com.universalapp.sankalp.learningapp.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.Task;
import com.google.gson.Gson;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.model.login.LoginResponse;
import com.universalapp.sankalp.learningapp.model.login.LoginUser;
import com.universalapp.sankalp.learningapp.utils.AppPrefs;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.text_app_version)
    TextView textViewAppVersion;

    private static final int TIME_OUT = 2000;

    private static  final int APP_UPDATE_REQUEST_CODE = 1000;
    AppUpdateManager appUpdateManager;
    Task<AppUpdateInfo> appUpdateInfoTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        try {
            appUpdateManager = AppUpdateManagerFactory.create(this);

            appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
            //checkForUpdate();
            processLogin();
        }catch (Exception e){

        }




    }

    private void processLogin(){
        if(AppPrefs.getInstance(MainActivity.this).getIsLogin() == true) {
            if(getIntent().hasExtra("link")){
                String link = getIntent().getStringExtra("link");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
                finish();
            }
        }

        ButterKnife.bind(this);

        textViewAppVersion.setText("v" + Utils.getAppVersionName() + "(" + Utils.getAppVersionCode() + ")");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;

                if (AppPrefs.getInstance(MainActivity.this).getIsLogin() == true) {

                    if (AppPrefs.getInstance(MainActivity.this).getOTPVerificed() == true){
                        Constants.USER_LOGIN_RESPONSE = new Gson().fromJson(AppPrefs.getInstance(MainActivity.this).getLoginUserDetails(), LoginResponse.class);
                        intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Constants.USER_LOGIN_RESPONSE = new Gson().fromJson(AppPrefs.getInstance(MainActivity.this).getLoginUserDetails(), LoginResponse.class);
                        intent = new Intent(MainActivity.this, OTPActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, TIME_OUT);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            textViewAppVersion.setText("v" + Utils.getAppVersionName() + "(" + Utils.getAppVersionCode() + ")");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent;

                    if (AppPrefs.getInstance(MainActivity.this).getIsLogin() == true) {
                        Constants.USER_LOGIN_RESPONSE = new Gson().fromJson(AppPrefs.getInstance(MainActivity.this).getLoginUserDetails(), LoginResponse.class);
                        intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }, TIME_OUT);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == APP_UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.e("Update ",  ""+resultCode);
                checkForUpdate();
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    private void checkForUpdate(){
        try{

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.updatePriority() >= 5
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            APP_UPDATE_REQUEST_CODE);
                }catch (Exception e){

                }
            } else {
                processLogin();
            }
        });
        appUpdateInfoTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e("update fail ", e.getLocalizedMessage());
            }
        });
        }catch (Exception e){
            throw e;
        }
    }
}
