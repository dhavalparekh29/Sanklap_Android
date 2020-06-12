package com.universalapp.sankalp.learningapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.controller.selection.ChapterListSelectorAdapter;
import com.universalapp.sankalp.learningapp.controller.spinner.ChapterSpinnerAdapter;
import com.universalapp.sankalp.learningapp.controller.spinner.SubjectSpinnerAdapter;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.BasicResponse;
import com.universalapp.sankalp.learningapp.model.chapter.ChapterDetails;
import com.universalapp.sankalp.learningapp.model.chapter.ChapterResponse;
import com.universalapp.sankalp.learningapp.model.subject.SubjectDetails;
import com.universalapp.sankalp.learningapp.model.subject.SubjectResponse;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EducationalSupportActivity extends AppCompatActivity {


    @BindView(R.id.text_title)
    TextView textViewTitle;
    @BindView(R.id.spinner_subject)
    Spinner spinnerSubject;
    @BindView(R.id.spinner_chapter)
    Spinner spinnerChapter;

    @BindView(R.id.edit_subject)
    EditText editTextSubject;
    @BindView(R.id.edit_message)
    EditText editTextMessage;

    @BindView(R.id.button_submit)
    Button buttonSubmit;
    Activity activity;
    List<SubjectDetails> subjectList = new ArrayList<>();
    List<ChapterDetails> chapterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_support);

        activity = this;
        ButterKnife.bind(this);
        textViewTitle.setText("Educational Support");

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextSubject.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(EducationalSupportActivity.this, "Please enter topic", Toast.LENGTH_SHORT).show();
                }else if(spinnerSubject.getSelectedItemPosition()==0){
                    Toast.makeText(activity, "Please choose subject", Toast.LENGTH_SHORT).show();
                }/*else if(spinnerChapter.getSelectedItemPosition()==0){

                }*/
                else if(editTextMessage.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(activity, "Please write your question in detail", Toast.LENGTH_SHORT).show();
                }else{
                    sendEducationalSupport();
                }
            }
        });
        initData();
        getSubjectListSpinner();
        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    getChapterList(subjectList.get(spinnerSubject.getSelectedItemPosition()).getSubjectId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initData(){
        ChapterDetails chapterDetails = new ChapterDetails();
        chapterDetails.setSubjectId("0");
        chapterDetails.setChapterName("Choose Chapter");
        chapterList.add(0, chapterDetails);

        SubjectDetails subjectDetails = new SubjectDetails();
        subjectDetails.setSubjectId("0");
        subjectDetails.setSubjectName("Choose subject");
        subjectList.add(0, subjectDetails);

        SubjectSpinnerAdapter adapter = new SubjectSpinnerAdapter(activity, subjectList);
        spinnerSubject.setAdapter(adapter);
        ChapterSpinnerAdapter adapter1 = new ChapterSpinnerAdapter(activity, chapterList);
        spinnerChapter.setAdapter(adapter1);
    }

    private void getSubjectListSpinner(){

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
                    subjectList.clear();
                    subjectList = response.body().getSubject();
                    SubjectDetails subjectDetails = new SubjectDetails();
                    subjectDetails.setSubjectId("0");
                    subjectDetails.setSubjectName("Choose subject");
                    subjectList.add(0, subjectDetails);

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

    private void getChapterList(String subjectId){

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

                        chapterList.clear();
                        chapterList = response.body().getChapter();
                        ChapterDetails chapterDetails = new ChapterDetails();
                        chapterDetails.setSubjectId("0");
                        chapterDetails.setChapterName("Choose Chapter");
                        chapterList.add(0, chapterDetails);


                        ChapterSpinnerAdapter adapter = new ChapterSpinnerAdapter(activity, chapterList);
                        spinnerChapter.setAdapter(adapter);
                        spinnerChapter.setVisibility(View.VISIBLE);

                    }
                }else{
                    CustomDialog.commonDialog(activity, "Sankalp", "No chapter available", "Retry");
                }
                if(response.body().getChapter() != null) {
                    if (response.body().getChapter().size() > 0) {
                        spinnerChapter.setVisibility(View.VISIBLE);

                    } else {
                        spinnerChapter.setVisibility(View.GONE);

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
    private void sendEducationalSupport(){
        LoaderDialog dialog = new LoaderDialog(EducationalSupportActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("user_id", Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
        params.put("topic", editTextSubject.getText().toString());
        params.put("subject_id", subjectList.get(spinnerSubject.getSelectedItemPosition()).getSubjectId());
        params.put("chapter_id", chapterList.get(spinnerChapter.getSelectedItemPosition()).getChapterId());
        params.put("standard_id", Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
        params.put("description", editTextMessage.getText().toString());


        Call<BasicResponse> request = RestClient.getInstance(this).educationSupport(params);

        request.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> calMl, Response<BasicResponse> response) {
                //Toast.makeText(ContactUsActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                dialog.hideProgressBar();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    finish();
                    Toast.makeText(EducationalSupportActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }else{
                    //CustomDialog.commonDialog(ContactUsActivity.this, "Login fail", "Credential mismatch.", "Retry");
                    Toast.makeText(EducationalSupportActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {

                Toast.makeText(EducationalSupportActivity.this, "Something went wrong please try again later", Toast.LENGTH_SHORT).show();
                dialog.hideProgressBar();
            }
        });
    }
}
