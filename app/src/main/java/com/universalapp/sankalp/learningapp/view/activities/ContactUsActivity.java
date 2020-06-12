package com.universalapp.sankalp.learningapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.BasicResponse;
import com.universalapp.sankalp.learningapp.model.login.LoginResponse;
import com.universalapp.sankalp.learningapp.utils.AppPrefs;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;

import java.util.HashMap;
import java.util.Map;

public class ContactUsActivity extends AppCompatActivity {


    @BindView(R.id.text_title)
    TextView textViewTitle;
    @BindView(R.id.image_menu)
    ImageView imageViewMenu;
    @BindView(R.id.edit_subject)
    EditText editTextSubject;
    @BindView(R.id.edit_message)
    EditText editTextMessage;
    @BindView(R.id.button_submit)
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactu_us);

        ButterKnife.bind(this);

        switch (getIntent().getStringExtra(Constants.KEY_CONTACT_US)){
            case "c":
                textViewTitle.setText("Contact Us");
                break;

            case "h":
                textViewTitle.setText("Help");
                break;
        }

        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextSubject.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(ContactUsActivity.this, "Please enter subject", Toast.LENGTH_SHORT).show();
                }else if(editTextMessage.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(ContactUsActivity.this, "Please enter message", Toast.LENGTH_SHORT).show();
                }else{
                    switch (getIntent().getStringExtra(Constants.KEY_CONTACT_US)){
                        case "c":
                            contactUs();
                            break;

                        case "h":
                            help();
                            break;
                    }
                }
            }
        });
    }

    private void contactUs(){
        LoaderDialog dialog = new LoaderDialog(ContactUsActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("user_id", Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
        params.put("subject", editTextSubject.getText().toString());
        params.put("message", editTextMessage.getText().toString());

        Call<BasicResponse> request = RestClient.getInstance(this).contactUs(params);

        request.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> calMl, Response<BasicResponse> response) {
                //Toast.makeText(ContactUsActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                dialog.hideProgressBar();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    finish();
                    Toast.makeText(ContactUsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }else{
                    //CustomDialog.commonDialog(ContactUsActivity.this, "Login fail", "Credential mismatch.", "Retry");
                    Toast.makeText(ContactUsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }

    private void help(){
        LoaderDialog dialog = new LoaderDialog(ContactUsActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("user_id", Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
        params.put("subject", editTextSubject.getText().toString());
        params.put("message", editTextMessage.getText().toString());

        Call<BasicResponse> request = RestClient.getInstance(this).helpUs(params);

        request.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> calMl, Response<BasicResponse> response) {
                //Toast.makeText(ContactUsActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                dialog.hideProgressBar();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    finish();
                    Toast.makeText(ContactUsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }else{
                    //CustomDialog.commonDialog(ContactUsActivity.this, "Login fail", "Credential mismatch.", "Retry");
                    Toast.makeText(ContactUsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
}
