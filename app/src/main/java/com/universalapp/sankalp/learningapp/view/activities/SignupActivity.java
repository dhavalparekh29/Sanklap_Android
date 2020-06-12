package com.universalapp.sankalp.learningapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.controller.spinner.MediumSpinnerAdapter;
import com.universalapp.sankalp.learningapp.controller.spinner.StandardSpinnerAdapter;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.login.LoginResponse;
import com.universalapp.sankalp.learningapp.model.medium.MediumDetails;
import com.universalapp.sankalp.learningapp.model.medium.MediumResponse;
import com.universalapp.sankalp.learningapp.model.signup.SignupResponse;
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

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.text_login)
    TextView textViewLogin;

    @BindView(R.id.edit_name)
    EditText editTextName;
    @BindView(R.id.edit_email)
    EditText editTextEmail;
    @BindView(R.id.edit_mobile_number)
    EditText editTextMobileNumber;
    @BindView(R.id.edit_current_location)
    EditText editTextCurrentLocation;
    @BindView(R.id.spinner_country_code)
    Spinner spinnerCountryCode;
    @BindView(R.id.spinner_cources)
    Spinner spinnerCources;
    @BindView(R.id.spinner_medium)
    Spinner spinnerMedium;

    @BindView(R.id.button_signup)
    Button buttonSignup;


    List<StandardList> standardLists = new ArrayList<>();
    List<MediumDetails> mediumLists = new ArrayList<>();

    StandardSpinnerAdapter standardSpinnerAdapter;
    MediumSpinnerAdapter mediumSpinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);


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

        buttonSignup.setOnClickListener(clickListener);
        textViewLogin.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.text_login:

                    finish();

                    break;
                case R.id.button_signup:

                    if(editTextName.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(SignupActivity.this, "Please enter full name", Toast.LENGTH_SHORT).show();
                    }else if(editTextMobileNumber.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(SignupActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    }else if(editTextMobileNumber.getText().toString().length() < 10){
                        Toast.makeText(SignupActivity.this, "Please enter 10 digit mobile number", Toast.LENGTH_SHORT).show();
                    }else if(spinnerCources.getSelectedItemPosition() <= 0){
                        Toast.makeText(SignupActivity.this, "Please choose standard", Toast.LENGTH_SHORT).show();
                    }else if(spinnerMedium.getSelectedItemPosition() <= 0){
                        Toast.makeText(SignupActivity.this, "Please choose medium", Toast.LENGTH_SHORT).show();
                    }else {
                        signup();
                    }
                    break;


            }
        }
    };

    private void getStandards(){
        LoaderDialog dialog = new LoaderDialog(SignupActivity.this);
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
                    standardSpinnerAdapter = new StandardSpinnerAdapter(SignupActivity.this, standardLists);
                    spinnerCources.setAdapter(standardSpinnerAdapter);

                }else{
                    CustomDialog.commonDialog(SignupActivity.this, "Login fail", "Credential mismatch.", "Retry");
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
        LoaderDialog dialog = new LoaderDialog(SignupActivity.this);
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
                    mediumSpinnerAdapter = new MediumSpinnerAdapter(SignupActivity.this, mediumLists);
                    spinnerMedium.setAdapter(mediumSpinnerAdapter);

                }else{
                    CustomDialog.commonDialog(SignupActivity.this, "Login fail", "Credential mismatch.", "Retry");
                }
                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<MediumResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
    
    private void signup(){
        LoaderDialog dialog = new LoaderDialog(SignupActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("mobile",editTextMobileNumber.getText().toString());
        params.put("email",editTextEmail.getText().toString());
        params.put("name",editTextName.getText().toString());
        params.put("standard_id",standardLists.get(spinnerCources.getSelectedItemPosition()).getStandardId());
        params.put("medium_id",mediumLists.get(spinnerMedium.getSelectedItemPosition()).getMediumId());
        params.put("location",editTextCurrentLocation.getText().toString());

        Call<LoginResponse> request = RestClient.getInstance(this).signup(params);

        request.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    AppPrefs.getInstance(SignupActivity.this).setIsLogin(false);
                    AppPrefs.getInstance(SignupActivity.this).setLoginUserDetails(new Gson().toJson(response.body()));
                    Constants.USER_LOGIN_RESPONSE = response.body();

                    intent = new Intent(SignupActivity.this, MembershipPackActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(SignupActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                }else{
                    CustomDialog.commonDialog(SignupActivity.this, "Sankalp", "Something went wrong please try again.", "Retry");
                }
                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
}
