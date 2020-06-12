package com.universalapp.sankalp.learningapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.controller.spinner.MediumSpinnerAdapter;
import com.universalapp.sankalp.learningapp.controller.spinner.StandardSpinnerAdapter;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.login.LoginResponse;
import com.universalapp.sankalp.learningapp.model.medium.MediumDetails;
import com.universalapp.sankalp.learningapp.model.medium.MediumResponse;
import com.universalapp.sankalp.learningapp.model.standard.StandardList;
import com.universalapp.sankalp.learningapp.model.standard.StandardResponse;
import com.universalapp.sankalp.learningapp.utils.AppPrefs;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;
import com.universalapp.sankalp.learningapp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.text_signup)
    TextView textViewSignup;

    @BindView(R.id.button_login)
    Button buttonLogin;

    @BindView(R.id.edit_mobile_number)
    EditText editTextMobileNumber;
    @BindView(R.id.spinner_cources)
    Spinner spinnerCources;
    @BindView(R.id.spinner_medium)
    Spinner spinnerMedium;


    List<StandardList> standardLists = new ArrayList<>();
    List<MediumDetails> mediumLists = new ArrayList<>();

    StandardSpinnerAdapter standardSpinnerAdapter;
    MediumSpinnerAdapter mediumSpinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);


        textViewSignup.setOnClickListener(clickListener);
        buttonLogin.setOnClickListener(clickListener);
        getStandards();
        spinnerCources.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(spinnerCources.getSelectedItemPosition()!= 0) {
                    getMediums();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Utils.checkAndRequestPermissions(this);
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()){
                case R.id.text_signup:

                    intent = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intent);

                    break;

                case R.id.button_login:

                    if(editTextMobileNumber.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(LoginActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    }else if(editTextMobileNumber.getText().toString().length() < 10){
                        Toast.makeText(LoginActivity.this, "Please enter 10 digit mobile number", Toast.LENGTH_SHORT).show();
                    }else if(spinnerCources.getSelectedItemPosition()==0){
                        Toast.makeText(LoginActivity.this, "Please choose standard", Toast.LENGTH_SHORT).show();
                    }else{
                        login();
                    }
                    break;
            }
        }
    };




    private void login(){
        LoaderDialog dialog = new LoaderDialog(LoginActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("mobile", editTextMobileNumber.getText().toString());
        params.put("standard_id", standardLists.get(spinnerCources.getSelectedItemPosition()).getStandardId());
        params.put("medium_id", mediumLists.get(spinnerMedium.getSelectedItemPosition()).getMediumId());
        Call<LoginResponse> request = RestClient.getInstance(this).login(params);

        request.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> calMl, Response<LoginResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                dialog.hideProgressBar();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    if(response.body().getUserPlan() != null) {
                        AppPrefs.getInstance(LoginActivity.this).setIsLogin(true);
                        AppPrefs.getInstance(LoginActivity.this).setLoginUserDetails(new Gson().toJson(response.body()));
                        Constants.USER_LOGIN_RESPONSE = response.body();
                        intent = new Intent(LoginActivity.this, OTPActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Please choose plan first", Toast.LENGTH_SHORT).show();
                        AppPrefs.getInstance(LoginActivity.this).setIsLogin(false);
                        AppPrefs.getInstance(LoginActivity.this).setLoginUserDetails(new Gson().toJson(response.body()));
                        Constants.USER_LOGIN_RESPONSE = response.body();

                        intent = new Intent(LoginActivity.this, MembershipPackActivity.class);
                        startActivity(intent);
                        //finish();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    //CustomDialog.commonDialog(LoginActivity.this, "Login fail", "Credential mismatch.", "Retry");
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }


    private void getStandards(){
        LoaderDialog dialog = new LoaderDialog(LoginActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        Call<StandardResponse> request = RestClient.getInstance(this).getStandardList(params);

        request.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    standardLists = response.body().getStandard();
                    standardLists.add(0, new StandardList("0", "Choose standard"));
                    standardSpinnerAdapter = new StandardSpinnerAdapter(LoginActivity.this, standardLists);
                    spinnerCources.setAdapter(standardSpinnerAdapter);

                }else{
                    CustomDialog.commonDialog(LoginActivity.this, "Login fail", "Credential mismatch.", "Retry");
                }
                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }

    private void getMediums(){
        LoaderDialog dialog = new LoaderDialog(LoginActivity.this);
        dialog.showProgress();

        String params = standardLists.get(spinnerCources.getSelectedItemPosition()).getStandardId();
        Call<MediumResponse> request = RestClient.getInstance(this).getMediumList(params);

        request.enqueue(new Callback<MediumResponse>() {
            @Override
            public void onResponse(Call<MediumResponse> call, Response<MediumResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    mediumLists = response.body().getMedium();
                    mediumLists.add(0, new MediumDetails("0", "Choose medium"));
                    mediumSpinnerAdapter = new MediumSpinnerAdapter(LoginActivity.this, mediumLists);
                    spinnerMedium.setAdapter(mediumSpinnerAdapter);

                }else{
                    CustomDialog.commonDialog(LoginActivity.this, "Login fail", "Credential mismatch.", "Retry");
                }
                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<MediumResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }



}
