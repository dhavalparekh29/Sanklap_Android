package com.universalapp.sankalp.learningapp.view.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.BasicResponse;
import com.universalapp.sankalp.learningapp.utils.AppPrefs;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;

import java.util.HashMap;
import java.util.Map;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.services.common.Crash;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {

    @BindView(R.id.edit_otp_1)
    EditText editTextOTP1;
    @BindView(R.id.edit_otp_2)
    EditText editTextOTP2;
    @BindView(R.id.edit_otp_3)
    EditText editTextOTP3;
    @BindView(R.id.edit_otp_4)
    EditText editTextOTP4;
    @BindView(R.id.edit_otp_5)
    EditText editTextOTP5;
    @BindView(R.id.edit_otp_6)
    EditText editTextOTP6;
    @BindView(R.id.button_verify_otp)
    Button buttonVerifyOTP;
    @BindView(R.id.button_resend_otp)
    Button buttonResendOTP;
    @BindView(R.id.text_login)
    TextView textViewLogin;

    String otp;
    String deviceID= "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        try {
            ButterKnife.bind(this);
            FirebaseApp.initializeApp(this);
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
            initFireBase();

            buttonVerifyOTP.setOnClickListener(clickListener);
            buttonResendOTP.setOnClickListener(clickListener);
            textViewLogin.setOnClickListener(clickListener);


            sendOTP();

            editTextOTP1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    editTextOTP2.requestFocus();
                }
            });
            editTextOTP2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    editTextOTP3.requestFocus();

                }
            });
            editTextOTP3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    editTextOTP4.requestFocus();

                }
            });
            editTextOTP4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    editTextOTP5.requestFocus();

                }
            });
            editTextOTP5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    editTextOTP6.requestFocus();

                }
            });
            editTextOTP6.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editTextOTP1.getText().toString().equalsIgnoreCase("")
                            || editTextOTP2.getText().toString().equalsIgnoreCase("")
                            || editTextOTP3.getText().toString().equalsIgnoreCase("")
                            || editTextOTP4.getText().toString().equalsIgnoreCase("")
                            || editTextOTP5.getText().toString().equalsIgnoreCase("")
                            || editTextOTP6.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(OTPActivity.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                    } else {
                        otp = (editTextOTP1.getText().toString()
                                + editTextOTP2.getText().toString()
                                + editTextOTP3.getText().toString()
                                + editTextOTP4.getText().toString()
                                + editTextOTP5.getText().toString()
                                + editTextOTP6.getText().toString());
                        verifyOTP();
                    }
                }
            });
        }catch (Exception e){
            Crashlytics.logException(e);
        }
    }
    private void initFireBase(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("fcm exception "+ task.getException());

                            return;
                        }
                        deviceID = task.getResult().getToken();

                        System.out.println("fcm token "+deviceID);
                    }
                });
    }
    private void sendOTP(){
        if(Constants.USER_LOGIN_RESPONSE.getUser().getMobile().equalsIgnoreCase("1234567890")){
            Toast.makeText(this, "OTP sent to your registered number", Toast.LENGTH_SHORT).show();
        }else {
            generateOTP();
        }
    }

    private void generateOTP(){
        try {
            LoaderDialog dialog = new LoaderDialog(OTPActivity.this);
            dialog.showProgress();

            Map<String, String> params = new HashMap<>();
            params.put("user_id", Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
            Call<BasicResponse> request = RestClient.getInstance(this).genrateOTP(params);

            request.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                    //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();

                    try {
                        Intent intent;
                        dialog.hideProgressBar();

                        if (response.body().getSuccess() == 1) {
                            Toast.makeText(OTPActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OTPActivity.this, "Fail to send otp", Toast.LENGTH_SHORT).show();

                            //CustomDialog.commonDialog(OTPActivity.this, "Fail to send otp", response.body().getMsg(), "Retry");
                        }
                    } catch (Exception e) {
                        Crashlytics.logException(e);
                        Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    Crashlytics.logException(t);

                    dialog.hideProgressBar();
                }
            });
        }catch (Exception e){
            Crashlytics.logException(e);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()){

                case R.id.text_login:

                    intent = new Intent(OTPActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                    break;
                case R.id.button_resend_otp:

                    sendOTP();

                    break;
                case R.id.button_verify_otp:

                    if(editTextOTP1.getText().toString().equalsIgnoreCase("")
                            || editTextOTP2.getText().toString().equalsIgnoreCase("")
                            || editTextOTP3.getText().toString().equalsIgnoreCase("")
                            || editTextOTP4.getText().toString().equalsIgnoreCase("")
                            || editTextOTP5.getText().toString().equalsIgnoreCase("")
                            || editTextOTP6.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(OTPActivity.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                    }else{
                        verifyOTP();
                    }


                    break;
            }
        }
    };

    private void verifyOTP(){
        if(!deviceID.trim().equalsIgnoreCase("")) {

            LoaderDialog dialog = new LoaderDialog(OTPActivity.this);
            dialog.showProgress();

            Map<String, String> params = new HashMap<>();
            params.put("user_id", Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
            params.put("otp", otp);
            params.put("device_id", deviceID);
            Call<BasicResponse> request = RestClient.getInstance(this).verifyOTP(params);

            request.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                    System.out.println("Login response " + response.body().toString());
                    //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                    Intent intent;
                    if (response.body().getSuccess() == 1) {
                        AppPrefs.getInstance(OTPActivity.this).setIsLogin(true);
                        AppPrefs.getInstance(OTPActivity.this).setIsOtpVerified(true);
                        //Toast.makeText(OTPActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        intent = new Intent(OTPActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        CustomDialog.commonDialog(OTPActivity.this, response.body().getMsg(), "Credential mismatch.", "Retry");
                    }
                    dialog.hideProgressBar();
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    System.out.println("imp erro " + t.getLocalizedMessage());
                    dialog.hideProgressBar();
                }
            });
        }else{
            initFireBase();
            Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
        }
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");


            }
        }
    };
    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).
                registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
