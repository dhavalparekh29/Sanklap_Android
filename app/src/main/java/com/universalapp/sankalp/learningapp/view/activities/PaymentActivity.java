    package com.universalapp.sankalp.learningapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paytm.pgsdk.BuildConfig;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.Config;
import com.universalapp.sankalp.learningapp.model.membershipPack.MembershipSubmitResponse;
import com.universalapp.sankalp.learningapp.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    MembershipSubmitResponse membershipSubmitResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        membershipSubmitResponse = new Gson().fromJson(getIntent().getStringExtra(Constants.KEY_TRANSACTION_DETAILS), MembershipSubmitResponse.class);
        PaytmPGService Service = PaytmPGService.getStagingService(membershipSubmitResponse.getCallbackUrl());
        HashMap<String, String> paramMap = new HashMap<>();

        paramMap.put( "MID" , membershipSubmitResponse.getMID());
        paramMap.put( "ORDER_ID" , membershipSubmitResponse.getORDERID());
        paramMap.put( "CUST_ID" , membershipSubmitResponse.getCUSTID());
        //paramMap.put( "MOBILE_NO" , Constants.USER_DETAILS.getMobile());
        //paramMap.put( "EMAIL" , "prince.patel72925@gmail.com");
        paramMap.put( "CHANNEL_ID" , membershipSubmitResponse.getCHANNELID());
        //paramMap.put( "TXN_AMOUNT" , String.valueOf(Float.parseFloat(membershipSubmitResponse.getTXNAMOUNT())));
        paramMap.put( "TXN_AMOUNT" , "499.56");
        paramMap.put( "WEBSITE" , membershipSubmitResponse.getWEBSITE());
// This is the staging value. Production value is available in your dashboard
        paramMap.put( "INDUSTRY_TYPE_ID" , membershipSubmitResponse.getINDUSTRYTYPEID());
// This is the staging value. Production value is available in your dashboard
        paramMap.put( "CALLBACK_URL", membershipSubmitResponse.getCallbackUrl());
        paramMap.put( "CHECKSUMHASH" , membershipSubmitResponse.getChecksumhash());

        PaytmOrder Order = new PaytmOrder(paramMap);
        Service.initialize(Order, null);
        Service.startPaymentTransaction(this, true, false, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/

            public void someUIErrorOccurred(String inErrorMessage) {}
            public void onTransactionResponse(Bundle inResponse) {
                System.out.println("Transaction response:- "+ inResponse.toString());
                Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
            }
            public void networkNotAvailable() {}
            public void clientAuthenticationFailed(String inErrorMessage) {}
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {}
            public void onBackPressedCancelTransaction() {}
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {}

        });
    }
}
