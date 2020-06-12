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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.paytm.pgsdk.PaytmConstants;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.login.LoginResponse;
import com.universalapp.sankalp.learningapp.utils.AppPrefs;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;

import java.util.HashMap;
import java.util.Map;

public class PaymentResponseActivity extends AppCompatActivity {

    @BindView(R.id.button_continue)
    Button buttonContinue;
    @BindView(R.id.image_transaction_status)
    ImageView imageViewTransactionStatus;
    @BindView(R.id.text_transaction_message)
    TextView textViewTransactionMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_response);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString(PaytmConstants.STATUS).equalsIgnoreCase("TXN_FAILURE")){
            textViewTransactionMessage.setText("Unsuccessful payment");
            imageViewTransactionStatus.setImageDrawable(getDrawable(R.drawable.ic_payment_fail));
        }else{
            textViewTransactionMessage.setText("We received your payment successfully");
            imageViewTransactionStatus.setImageDrawable(getDrawable(R.drawable.ic_payment_success));
        }
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bundle.getString(PaytmConstants.STATUS).equalsIgnoreCase("TXN_FAILURE")) {
                }else {
                    getUserDetails();
                }
            }
        });
    }
    private void getUserDetails(){
        LoaderDialog dialog = new LoaderDialog(PaymentResponseActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("mobile", Constants.USER_LOGIN_RESPONSE.getUser().getMobile());
        params.put("standard_id", Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
        Call<LoginResponse> request = RestClient.getInstance(this).login(params);

        request.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(PaymentResponseActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getSuccess() == 1){
                    AppPrefs.getInstance(PaymentResponseActivity.this).setIsLogin(true);
                    AppPrefs.getInstance(PaymentResponseActivity.this).setLoginUserDetails(new Gson().toJson(response.body()));
                    Constants.USER_LOGIN_RESPONSE = response.body();

                    }else{
                    //CustomDialog.commonDialog(PaymentResponseActivity.this, "Login fail", "Credential mismatch.", "Retry");
                }
                dialog.hideProgressBar();
                finish();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
}
