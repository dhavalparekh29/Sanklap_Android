package com.universalapp.sankalp.learningapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class TechnicalSupportActivity extends AppCompatActivity {

    @BindView(R.id.radio_group_technical_support)
    RadioGroup radioGroupTechnicalSupport;
    @BindView(R.id.radio_button_login)
    RadioButton radioButtonLogin;
    @BindView(R.id.radio_button_membership_plan)
    RadioButton radioButtonMembershipPlan;
    @BindView(R.id.radio_button_payment)
    RadioButton radioButtonPayment;
    @BindView(R.id.radio_button_other)
    RadioButton radioButtonOther;
    @BindView(R.id.button_submit)
    Button buttonSubmit;

    @BindView(R.id.edit_message)
    EditText editTextMessage;

    @BindView(R.id.text_title)
    TextView textViewTitle;

    String issueType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_support);
        ButterKnife.bind(this);

        radioButtonOther.setChecked(true);
        textViewTitle.setText("Technical Support");
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextMessage.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(TechnicalSupportActivity.this, "Please your query in detail", Toast.LENGTH_SHORT).show();
                }else{
                    sendTechnicalSupport();
                }
            }
        });

        radioGroupTechnicalSupport.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(group.getCheckedRadioButtonId());
                issueType = radioButton.getText().toString();
            }
        });
    }

    private void sendTechnicalSupport(){
        LoaderDialog dialog = new LoaderDialog(TechnicalSupportActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("user_id", Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
        params.put("standard_id", Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
        params.put("description", editTextMessage.getText().toString());
        params.put("issue_type", issueType);


        Call<BasicResponse> request = RestClient.getInstance(this).technicalSupport(params);

        request.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> calMl, Response<BasicResponse> response) {
                //Toast.makeText(ContactUsActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                dialog.hideProgressBar();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    finish();
                    Toast.makeText(TechnicalSupportActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }else{
                    //CustomDialog.commonDialog(ContactUsActivity.this, "Login fail", "Credential mismatch.", "Retry");
                    Toast.makeText(TechnicalSupportActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

                Toast.makeText(TechnicalSupportActivity.this, "Something went wrong please try again later", Toast.LENGTH_SHORT).show();
                dialog.hideProgressBar();
            }
        });
    }
}
