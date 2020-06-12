package com.universalapp.sankalp.learningapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.BasicResponse;
import com.universalapp.sankalp.learningapp.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends AppCompatActivity {

    @BindView(R.id.edit_subject)
    EditText editTextSubject;
    @BindView(R.id.edit_mobile)
    EditText editTextMobile;
    @BindView(R.id.edit_message)
    EditText editTextMessage;
    @BindView(R.id.button_submit)
    Button buttonSubmit;
    @BindView(R.id.text_title)
    TextView textViewTitle;

    private String supportType = "";

    private String API_KEY_TOPIC = "";
    private String API_KEY_MESSAGE = "";
    private String API_KEY_MOBILE = "mobile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ButterKnife.bind(this);

        if(getIntent().getExtras().containsKey(Constants.KEY_SUPPORT_TYPE)){
            supportType = getIntent().getStringExtra(Constants.KEY_SUPPORT_TYPE);
        }

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int errorCount = 0;
                if(editTextSubject.getVisibility() == View.VISIBLE){
                    if(editTextSubject.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(SupportActivity.this, "Please enter problem title", Toast.LENGTH_SHORT).show();
                        errorCount += errorCount;
                    }
                }
                if(editTextMessage.getVisibility() == View.VISIBLE){
                    if(editTextMessage.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(SupportActivity.this, "Please enter problem description", Toast.LENGTH_SHORT).show();
                        errorCount += errorCount;
                    }
                }
                if(editTextMobile.getVisibility() == View.VISIBLE){
                    if(editTextMobile.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(SupportActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                        errorCount += errorCount;
                    }else if(editTextMobile.getText().toString().length()<10){
                        Toast.makeText(SupportActivity.this, "Please enter valid 10 digit mobile number", Toast.LENGTH_SHORT).show();
                        errorCount += errorCount;
                    }
                }
                if(errorCount<=0) {
                    sendSupport();
                }
            }
        });
        setupUI();
    }

    private void setupUI(){

        /*if(supportType.equalsIgnoreCase(Constants.SUPPORT_CONTACT_EDUCATION)){
            editTextSubject.setHint("Topic");
            editTextMessage.setHint("Description");
            editTextMobile.setVisibility(View.GONE);
            API_KEY_TOPIC = "topic";
            API_KEY_MESSAGE = "description";

        }
        if(supportType.equalsIgnoreCase(Constants.SUPPORT_CONTACT_TECHNICAL)){
            editTextSubject.setHint("Issue Type");
            editTextMessage.setHint("Description");
            editTextMobile.setVisibility(View.GONE);
            API_KEY_TOPIC = "issue_type";
            API_KEY_MESSAGE = "description";
        }*/
        if(supportType.equalsIgnoreCase(Constants.SUPPORT_CONTACT_CALLBACK)){
            editTextSubject.setHint("Problem");
            editTextMessage.setHint("Description");
            editTextMobile.setHint("Callback Number");
            editTextMobile.setVisibility(View.VISIBLE);
            API_KEY_TOPIC = "problem";
            API_KEY_MESSAGE = "description";
            textViewTitle.setText("Callback Support");
        }
        if(supportType.equalsIgnoreCase(Constants.SUPPORT_CONTACT_EMAIL)){
            editTextSubject.setHint("Problem");
            editTextMessage.setHint("Description");
            editTextMobile.setVisibility(View.VISIBLE);
            API_KEY_TOPIC = "problem";
            API_KEY_MESSAGE = "description";

            textViewTitle.setText("Email Support");
        }
    }

    private void sendSupport(){
        LoaderDialog dialog = new LoaderDialog(SupportActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("user_id", Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
        params.put("standard_id", Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
        params.put(API_KEY_TOPIC, editTextSubject.getText().toString());
        params.put(API_KEY_MESSAGE, editTextMessage.getText().toString());
        params.put(API_KEY_MOBILE, editTextMobile.getText().toString());


        Call<BasicResponse> request = RestClient.getInstance(this).educationSupport(params);
        if(supportType.equalsIgnoreCase(Constants.SUPPORT_CONTACT_EDUCATION)) {
            request = RestClient.getInstance(this).educationSupport(params);
        }
        if(supportType.equalsIgnoreCase(Constants.SUPPORT_CONTACT_TECHNICAL)) {
            request = RestClient.getInstance(this).technicalSupport(params);
        }
        if(supportType.equalsIgnoreCase(Constants.SUPPORT_CONTACT_CALLBACK)) {
            request = RestClient.getInstance(this).callbackSupport(params);
        }
        if(supportType.equalsIgnoreCase(Constants.SUPPORT_CONTACT_EMAIL)) {
            request = RestClient.getInstance(this).emailSupport(params);
        }


        request.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> calMl, Response<BasicResponse> response) {
                //Toast.makeText(ContactUsActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                dialog.hideProgressBar();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    finish();
                    Toast.makeText(SupportActivity.this, "Successfully submitted, our team will get back to you soon ", Toast.LENGTH_LONG).show();

                }else{
                    //CustomDialog.commonDialog(ContactUsActivity.this, "Login fail", "Credential mismatch.", "Retry");
                    Toast.makeText(SupportActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Toast.makeText(SupportActivity.this, "Something went wrong please try again later", Toast.LENGTH_SHORT).show();
                dialog.hideProgressBar();
            }
        });
    }
}
