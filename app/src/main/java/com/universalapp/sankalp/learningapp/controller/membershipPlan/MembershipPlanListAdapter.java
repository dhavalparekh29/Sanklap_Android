package com.universalapp.sankalp.learningapp.controller.membershipPlan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.controller.chapter.ChapterListAdapter;
import com.universalapp.sankalp.learningapp.controller.selection.ChapterListSelectorAdapter;
import com.universalapp.sankalp.learningapp.controller.selection.SubjectListSelectorAdapter;
import com.universalapp.sankalp.learningapp.controller.spinner.SubjectSpinnerAdapter;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.BasicResponse;
import com.universalapp.sankalp.learningapp.model.chapter.ChapterResponse;
import com.universalapp.sankalp.learningapp.model.membershipPack.CallbackChecksum;
import com.universalapp.sankalp.learningapp.model.membershipPack.MembershipChapterSelection;
import com.universalapp.sankalp.learningapp.model.membershipPack.MembershipDetail;
import com.universalapp.sankalp.learningapp.model.membershipPack.MembershipRequest;
import com.universalapp.sankalp.learningapp.model.membershipPack.MembershipSubjectSelection;
import com.universalapp.sankalp.learningapp.model.membershipPack.MembershipSubmitResponse;
import com.universalapp.sankalp.learningapp.model.payment.PaytmVerifyResponse;
import com.universalapp.sankalp.learningapp.model.subject.SubjectDetails;
import com.universalapp.sankalp.learningapp.model.subject.SubjectResponse;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;
import com.universalapp.sankalp.learningapp.utils.Utils;
import com.universalapp.sankalp.learningapp.view.activities.ChapterActivity;
import com.universalapp.sankalp.learningapp.view.activities.PaymentActivity;
import com.universalapp.sankalp.learningapp.view.activities.PaymentResponseActivity;
import com.universalapp.sankalp.learningapp.view.activities.QuizActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MembershipPlanListAdapter extends RecyclerView.Adapter<MembershipPlanListAdapter.MyViewHolder> {

    List<MembershipDetail> arrayListMembershipPlan = new ArrayList<>();
    Activity activity;
    List<SubjectDetails> subjectList = new ArrayList<>();
    public static List<MembershipSubjectSelection> SUBJECT_SELECTION = new ArrayList<>();
    public static List<MembershipChapterSelection> CHAPTER_SELECTION = new ArrayList<>();

    public MembershipPlanListAdapter(Activity activity, List<MembershipDetail> arrayListChapter){
        this.activity = activity;
        this.arrayListMembershipPlan = arrayListChapter;
    }

    @NonNull
    @Override
    public MembershipPlanListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MembershipPlanListAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_membership_layout, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MembershipPlanListAdapter.MyViewHolder viewHolder, int position) {

        //viewHolder.textViewName.setText(arrayListTimeSlot.get(i).getName());
        viewHolder.textViewPlanName.setText(arrayListMembershipPlan.get(position).getPlanName());
        viewHolder.textViewPlanPrice.setText(arrayListMembershipPlan.get(position).getPrice()+" Rs");
        viewHolder.textViewPlanValidiy.setText(arrayListMembershipPlan.get(position).getValidity() +" "+arrayListMembershipPlan.get(position).getValidityType());
        //viewHolder.textViewPlanName.setText(arrayListMembershipPlan.get(position).getPlanName());

        if(arrayListMembershipPlan.get(position).getMaterial().equals("1")){
            viewHolder.imageViewMaterialStatus.setImageDrawable(activity.getDrawable(R.drawable.ic_payment_success));
        }else{
            viewHolder.imageViewMaterialStatus.setImageDrawable(activity.getDrawable(R.drawable.ic_payment_fail));
        }
        if(arrayListMembershipPlan.get(position).getQuiz().equals("1")){
            viewHolder.imageViewQuizStatus.setImageDrawable(activity.getDrawable(R.drawable.ic_payment_success));
        }else{
            viewHolder.imageViewQuizStatus.setImageDrawable(activity.getDrawable(R.drawable.ic_payment_fail));
        }
        if(arrayListMembershipPlan.get(position).getVideo().equals("1")){
            viewHolder.imageViewVideoStatus.setImageDrawable(activity.getDrawable(R.drawable.ic_payment_success));
        }else{
            viewHolder.imageViewVideoStatus.setImageDrawable(activity.getDrawable(R.drawable.ic_payment_fail));
        }

        if(arrayListMembershipPlan.get(position).getTotal().equals("0")) {
            if (arrayListMembershipPlan.get(position).getAccessLevel().equals("s")) {
                viewHolder.textViewAccessLevel.setText("Subject allowed: All");
            } else if (arrayListMembershipPlan.get(position).getAccessLevel().equals("c")) {
                viewHolder.textViewAccessLevel.setText("Chapter allowed: All ");
            }
        }else{
            if (arrayListMembershipPlan.get(position).getAccessLevel().equals("s")) {
                viewHolder.textViewAccessLevel.setText("Subject allowed " + arrayListMembershipPlan.get(position).getTotal());
            } else if (arrayListMembershipPlan.get(position).getAccessLevel().equals("c")) {
                viewHolder.textViewAccessLevel.setText("Chapter allowed " + arrayListMembershipPlan.get(position).getTotal());
            }
        }
        viewHolder.linearLayoutPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (arrayListMembershipPlan.get(position).getAccessLevel()){
                    case "s":

                        if(Integer.parseInt(arrayListMembershipPlan.get(position).getTotal()) > 0){

                            showSubjectPopup(Integer.parseInt(arrayListMembershipPlan.get(position).getTotal()), position, false);

                        }else if(Integer.parseInt(arrayListMembershipPlan.get(position).getTotal()) == 0){
                            if(Integer.parseInt(arrayListMembershipPlan.get(position).getPrice())>0) {
                                getSubjectListFullAccess(position, false);
                            }else{
                                getSubjectListFullAccess(position, true);
                            }
                        }
                        break;

                    case "c":
                        if(Integer.parseInt(arrayListMembershipPlan.get(position).getTotal()) > 0){

                            showChapterPopup(Integer.parseInt(arrayListMembershipPlan.get(position).getTotal()), position, false);

                        }else{

                        }
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //return arrayListTimeSlot.size();
        return arrayListMembershipPlan.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.text_access_level)
        TextView textViewAccessLevel;
        @BindView(R.id.text_membership_plan_name)
        TextView textViewPlanName;
        @BindView(R.id.text_membership_plan_price)
        TextView textViewPlanPrice;
        @BindView(R.id.text_membership_plan_validiy)
        TextView textViewPlanValidiy;
        @BindView(R.id.layout_plan)
        LinearLayout linearLayoutPlan;
        @BindView(R.id.image_material_status)
        ImageView imageViewMaterialStatus;
        @BindView(R.id.image_video_status)
        ImageView imageViewVideoStatus;
        @BindView(R.id.image_quiz_status)
        ImageView imageViewQuizStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }

    public  void updateList(List<MembershipDetail> arrayListMembershipPlan){
        this.arrayListMembershipPlan = arrayListMembershipPlan;
        notifyDataSetChanged();
    }


    private void showSubjectPopup(int numberOfSelection, int position, boolean isFreeTrial){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_subject_selection);

        TextView tvTitle =  dialog.findViewById(R.id.tv_title);
        TextView tvCancel =  dialog.findViewById(R.id.tv_cancel);
        TextView tvSubmit =  dialog.findViewById(R.id.tv_submit);
        RecyclerView recyclerViewSubjectList =  dialog.findViewById(R.id.recycler_subject_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerViewSubjectList.setLayoutManager(layoutManager);

        getSubjectList(recyclerViewSubjectList, numberOfSelection);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MembershipRequest membershipRequest = new MembershipRequest();
                membershipRequest.setUserId(Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
                membershipRequest.setStandardId(Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
                membershipRequest.setMediumId(Constants.USER_LOGIN_RESPONSE.getUser().getMediumId());
                membershipRequest.setPlanId(arrayListMembershipPlan.get(position).getPlanId());
                membershipRequest.setPlanDetailId(arrayListMembershipPlan.get(position).getPlanDetailId());
                membershipRequest.setSubjects(SUBJECT_SELECTION);

                privacyPolicyPopup(membershipRequest, position, isFreeTrial);


            }
        });

        dialog.show();
    }
    private void showChapterPopup(int numberOfSelection, int position, boolean isFreeTrial){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_chapter_selection);
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width)/7, (4 * height)/7);

        TextView tvTitle =  dialog.findViewById(R.id.tv_title);
        TextView tvCancel =  dialog.findViewById(R.id.tv_cancel);
        TextView tvSubmit =  dialog.findViewById(R.id.tv_submit);
        TextView tvNoResultFound =  dialog.findViewById(R.id.text_no_result_found);
        Spinner spinnerSubject =  dialog.findViewById(R.id.spinner_subject);
        RecyclerView recyclerViewChapterList =  dialog.findViewById(R.id.recycler_chapter_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerViewChapterList.setLayoutManager(layoutManager);
        tvNoResultFound.setVisibility(View.VISIBLE);
        recyclerViewChapterList.setVisibility(View.GONE);
        getSubjectListSpinner(spinnerSubject);
        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0) {
                    getChapterList(recyclerViewChapterList, numberOfSelection, tvNoResultFound, subjectList.get(i).getSubjectId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MembershipRequest membershipRequest = new MembershipRequest();
                membershipRequest.setUserId(Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
                membershipRequest.setStandardId(Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
                membershipRequest.setMediumId(Constants.USER_LOGIN_RESPONSE.getUser().getMediumId());
                membershipRequest.setPlanId(arrayListMembershipPlan.get(position).getPlanId());
                membershipRequest.setPlanDetailId(arrayListMembershipPlan.get(position).getPlanDetailId());
                membershipRequest.setChapters(CHAPTER_SELECTION);

                privacyPolicyPopup(membershipRequest, position, isFreeTrial);
                /*try {
                    JSONObject jsonObjectMain = new JSONObject();
                    jsonObjectMain.put("user_id", "");
                    jsonObjectMain.put("plan_id", "");
                    jsonObjectMain.put("standard_id", "");
                    jsonObjectMain.put("medium_id", "");
                    JSONArray jsonArray = new JSONArray();
                    for(int i = 0 ; i < SUBJECT_SELECTION.size() ; i++){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("subject_id", SUBJECT_SELECTION.get(i));
                        jsonArray.put(jsonObject);
                    }
                    jsonObjectMain.put("subjects", jsonArray);

                }catch (Exception e){
                    Toast.makeText(activity, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                }*/

            }
        });

        dialog.show();
    }

    private void getSubjectListFullAccess(int position, boolean isFreeTrial){

        LoaderDialog dialog = new LoaderDialog(activity);
        dialog.showProgress();

        String params = Constants.USER_LOGIN_RESPONSE.getUser().getUserId();
        Call<SubjectResponse> request = RestClient.getInstance(activity).getSubject(params);

        request.enqueue(new Callback<SubjectResponse>() {
            @Override
            public void onResponse(Call<SubjectResponse> call, Response<SubjectResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                dialog.hideProgressBar();
                if(response.body().getSuccess() == 1){

                        for(int i = 0 ; i < response.body().getSubject().size() ; i++){
                            MembershipSubjectSelection membershipSubjectSelection = new MembershipSubjectSelection(response.body().getSubject().get(i).getSubjectId());
                            SUBJECT_SELECTION.add(membershipSubjectSelection);
                        }
                    MembershipRequest membershipRequest = new MembershipRequest();
                    membershipRequest.setUserId(Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
                    membershipRequest.setStandardId(Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
                    membershipRequest.setMediumId(Constants.USER_LOGIN_RESPONSE.getUser().getMediumId());
                    membershipRequest.setPlanId(arrayListMembershipPlan.get(position).getPlanId());
                    membershipRequest.setPlanDetailId(arrayListMembershipPlan.get(position).getPlanDetailId());
                    membershipRequest.setSubjects(SUBJECT_SELECTION);

                    privacyPolicyPopup(membershipRequest, position, isFreeTrial);


                }else{
                    CustomDialog.commonDialog(activity, "Sankalp", "Something went wrong.", "Retry");
                }
            }

            @Override
            public void onFailure(Call<SubjectResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });

    }

    private void getSubjectList(RecyclerView recyclerViewSubject, int numberOfSelection){

        LoaderDialog dialog = new LoaderDialog(activity);
        dialog.showProgress();

        String params = Constants.USER_LOGIN_RESPONSE.getUser().getUserId();
        Call<SubjectResponse> request = RestClient.getInstance(activity).getSubject(params);

        request.enqueue(new Callback<SubjectResponse>() {
            @Override
            public void onResponse(Call<SubjectResponse> call, Response<SubjectResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                dialog.hideProgressBar();
                if(response.body().getSuccess() == 1){

                        SubjectListSelectorAdapter adapter = new SubjectListSelectorAdapter(activity, response.body().getSubject(), numberOfSelection);
                        recyclerViewSubject.setAdapter(adapter);


                }else{
                    CustomDialog.commonDialog(activity, "Sankalp", "Something went wrong.", "Retry");
                }

            }

            @Override
            public void onFailure(Call<SubjectResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });

    }

    private void getSubjectListSpinner(Spinner spinnerSubject){

        LoaderDialog dialog = new LoaderDialog(activity);
        dialog.showProgress();

        String params = Constants.USER_LOGIN_RESPONSE.getUser().getUserId();
        Call<SubjectResponse> request = RestClient.getInstance(activity).getSubject(params);

        request.enqueue(new Callback<SubjectResponse>() {
            @Override
            public void onResponse(Call<SubjectResponse> call, Response<SubjectResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                dialog.hideProgressBar();
                if(response.body().getSuccess() == 1){
                    subjectList = response.body().getSubject();
                    SubjectDetails subjectDetails = new SubjectDetails();
                    subjectDetails.setSubjectId("0");
                    subjectDetails.setSubjectName("Choose subject");

                    subjectList.set(0, subjectDetails);
                        SubjectSpinnerAdapter adapter = new SubjectSpinnerAdapter(activity, subjectList);
                        spinnerSubject.setAdapter(adapter);


                }else{
                    CustomDialog.commonDialog(activity, "Sankalp", "Something went wrong.", "Retry");
                }

            }

            @Override
            public void onFailure(Call<SubjectResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });

    }

    private void getChapterList(RecyclerView recyclerViewChapterList, int numberOfSelection, TextView tvNoResultFound, String subjectId){

        LoaderDialog dialog = new LoaderDialog(activity);
        dialog.showProgress();


        Call<ChapterResponse> request = RestClient.getInstance(activity).getChapter(subjectId, Constants.USER_LOGIN_RESPONSE.getUser().getMediumId(), Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());


        request.enqueue(new Callback<ChapterResponse>() {
            @Override
            public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                dialog.hideProgressBar();
                if(response.body().getSuccess() == 1){
                    if(response.body().getChapter().size()>0) {
                        ChapterListSelectorAdapter adapter = new ChapterListSelectorAdapter(activity, response.body().getChapter(), numberOfSelection);
                        recyclerViewChapterList.setAdapter(adapter);
                        recyclerViewChapterList.setVisibility(View.VISIBLE);
                        tvNoResultFound.setVisibility(View.GONE);
                    }
                }else{
                    CustomDialog.commonDialog(activity, "Sankalp", "No chapter available", "Retry");
                }
                if(response.body().getChapter() != null) {
                    if (response.body().getChapter().size() > 0) {
                        recyclerViewChapterList.setVisibility(View.VISIBLE);
                        tvNoResultFound.setVisibility(View.GONE);
                    } else {
                        recyclerViewChapterList.setVisibility(View.GONE);
                        tvNoResultFound.setVisibility(View.VISIBLE);
                        tvNoResultFound.setText("No chapter found");
                    }
                }else{
                    Toast.makeText(activity, "No subject available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChapterResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }

    private void privacyPolicyPopup(MembershipRequest membershipRequest, int position, boolean isFreeTrial){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_privacy_policy);

        TextView textViewPrivacyPolicy =  dialog.findViewById(R.id.textView_PrivacyPolicy);
        TextView textViewPrivacyPolicyLink =  dialog.findViewById(R.id.textView_PrivacyPolicy_Link);
        Button buttonSubmit =  dialog.findViewById(R.id.button_submit);
        CheckBox checkBoxPrivacyPolicy =  dialog.findViewById(R.id.checkbox_Accept_Privacy_Policy);

        textViewPrivacyPolicy.setText(Html.fromHtml(Constants.PRIVACY_POLICY));

        textViewPrivacyPolicyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.diwalipurayouth.com/privacy-policy-2/"));
                activity.startActivity(browserIntent);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxPrivacyPolicy.isChecked()) {
                    dialog.dismiss();
                    submitMembershipPlan(membershipRequest, position, isFreeTrial);
                }else{
                    Toast.makeText(activity, "Please accept privacy policy", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
    }

    private void submitMembershipPlan(MembershipRequest membershipRequest, int position, boolean isFreeTrial){
        LoaderDialog dialog = new LoaderDialog(activity,"Submitting test please wait");
        dialog.showProgress();
        Call<MembershipSubmitResponse> request = RestClient.getInstance(activity).submitMemebership(membershipRequest);
        request.enqueue(new Callback<MembershipSubmitResponse>() {
            @Override
            public void onResponse(Call<MembershipSubmitResponse> call, Response<MembershipSubmitResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                dialog.hideProgressBar();

                if(response.body().getSuccess() == 1){

                    if(isFreeTrial == false) {
                        PaytmPGService Service = Utils.paytmPGService();
                        HashMap<String, String> paramMap = new HashMap<>();

                        paramMap.put("MID", response.body().getMID());
                        paramMap.put("ORDER_ID", response.body().getORDERID());
                        paramMap.put("CUST_ID", response.body().getCUSTID());
                        paramMap.put("CHANNEL_ID", response.body().getCHANNELID());
                        paramMap.put("TXN_AMOUNT", response.body().getTXNAMOUNT());
                        paramMap.put("WEBSITE", response.body().getWEBSITE());
                        paramMap.put("INDUSTRY_TYPE_ID", response.body().getINDUSTRYTYPEID());
                        paramMap.put("CALLBACK_URL", response.body().getCallbackUrl());
                        paramMap.put("CHECKSUMHASH", response.body().getChecksumhash());

                        PaytmOrder Order = new PaytmOrder(paramMap);
                        Service.initialize(Order, null);
                        Service.startPaymentTransaction(activity, true, false, new PaytmPaymentTransactionCallback() {
                            /*Call Backs*/
                            public void someUIErrorOccurred(String inErrorMessage) {
                                System.out.println("Transaction response:- " + inErrorMessage.toString());

                            }

                            public void onTransactionResponse(Bundle inResponse) {
                                verifyPayment(response.body(), inResponse);

                                System.out.println("Transaction response:- " + inResponse.getString(""));
                                //Toast.makeText(activity, "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                            }

                            public void networkNotAvailable() {
                                System.out.println("Transaction response:- ");

                            }

                            public void clientAuthenticationFailed(String inErrorMessage) {
                                System.out.println("Transaction response:- " + inErrorMessage.toString());

                            }

                            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                                System.out.println("Transaction response:- " + inErrorMessage.toString());
                            }

                            public void onBackPressedCancelTransaction() {
                            }

                            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                                System.out.println("Transaction response:- " + inErrorMessage.toString());
                                System.out.println("Transaction response:- " + inErrorMessage);
                            }
                        });
                        //genrateChecksum(response.body().getORDERID(), response.body().getTXNAMOUNT());
                    }else{
                        Toast.makeText(activity, "Free trial activated successfully", Toast.LENGTH_SHORT).show();
                        activity.finish();
                    }
                }else{
                    CustomDialog.commonDialog(activity, activity.getResources().getString(R.string.app_name), "Something went wrong please try again or contact support", "Retry");
                }
            }

            @Override
            public void onFailure(Call<MembershipSubmitResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }

    private void verifyPayment(MembershipSubmitResponse membershipSubmitResponse,Bundle inResponse){
        LoaderDialog dialog = new LoaderDialog(activity,"Submitting test please wait");
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("ORDER_ID",membershipSubmitResponse.getORDERID());
        params.put("MID",membershipSubmitResponse.getMID());
        params.put("CUST_ID",membershipSubmitResponse.getCUSTID());
        params.put("INDUSTRY_TYPE_ID",membershipSubmitResponse.getINDUSTRYTYPEID());
        params.put("CHANNEL_ID",membershipSubmitResponse.getCHANNELID());
        params.put("TXN_AMOUNT",membershipSubmitResponse.getTXNAMOUNT());
        params.put("WEBSITE",membershipSubmitResponse.getWEBSITE());
        params.put("CHECKSUMHASH",membershipSubmitResponse.getChecksumhash());
        params.put("CALLBACK_URL",membershipSubmitResponse.getCallbackUrl());
        Call<PaytmVerifyResponse> request = RestClient.getInstance(activity).verifyTransaction(params);
        request.enqueue(new Callback<PaytmVerifyResponse>() {
            @Override
            public void onResponse(Call<PaytmVerifyResponse> call, Response<PaytmVerifyResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                dialog.hideProgressBar();
                if(response.body().getSuccess() == 1){
                    Intent intent1 = new Intent(activity, PaymentResponseActivity.class);
                    intent1.putExtras(inResponse);
                    activity.startActivity(intent1);
                    activity.finish();
                }else{
                    CustomDialog.commonDialog(activity, activity.getResources().getString(R.string.app_name), "Something went wrong please try again or contact support", "Retry");
                }

            }

            @Override
            public void onFailure(Call<PaytmVerifyResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }


}