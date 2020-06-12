package com.universalapp.sankalp.learningapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.quiz.TestResponse;
import com.universalapp.sankalp.learningapp.model.quiz.submitQuiz.SubmitQuizQuestionDetails;
import com.universalapp.sankalp.learningapp.model.quiz.submitQuiz.SubmitQuizRequest;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;
import com.universalapp.sankalp.learningapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestInstructionActivity extends AppCompatActivity {

    @BindView(R.id.text_title)
    TextView textViewTitle;
    @BindView(R.id.image_menu)
    ImageView imageViewBack;

    @BindView(R.id.text_subject_name)
    TextView textViewSubjectName;
    @BindView(R.id.text_test_name)
    TextView textViewTestName;
    @BindView(R.id.text_duration)
    TextView textViewDuration;
    @BindView(R.id.text_number_of_question)
    TextView textViewNumberOfQuestions;
    @BindView(R.id.button_start)
    Button buttonStart;
    TestResponse testResponse;
    String chapterId, subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_instruction);

        ButterKnife.bind(this);

        switch (getIntent().getStringExtra(Constants.KEY_TEST_TYPE)) {
            case Constants.TEST_TYPE_CHAPTER_WISE:

                subjectId = getIntent().getStringExtra(Constants.KEY_SUBJECT_ID);
                chapterId = getIntent().getStringExtra(Constants.KEY_CHAPTER_ID);
                getChapterQuizQuestions(subjectId, chapterId);

                break;

            case Constants.TEST_TYPE_SUBJECT_WISE:

                subjectId = getIntent().getStringExtra(Constants.KEY_SUBJECT_ID);
                getSubjectQuizQuestions(subjectId);

                break;

            case Constants.TEST_TYPE_GENERAL_WISE:

                getGeneralQuizQuestions();

                break;
        }


        textViewSubjectName.setText(Constants.SELECTED_TEST_SUBJECT);
        textViewTestName.setText(Constants.SELECTED_TEST_NAME);

        textViewTitle.setText(Constants.SELECTED_TEST_SUBJECT + " Test");


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TestInstructionActivity.this, QuizActivity.class);
                intent.putExtra(Constants.KEY_TEST_RESPONSE, new Gson().toJson(testResponse, TestResponse.class));
                intent.putExtra(Constants.KEY_SUBJECT_NAME, textViewSubjectName.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getChapterQuizQuestions(String subjectId, String chapterId) {
        LoaderDialog dialog = new LoaderDialog(TestInstructionActivity.this);
        dialog.showProgress();


        Call<TestResponse> request = RestClient.getInstance(this)
                .getChapterQuizQuestions(subjectId
                        , Constants.USER_LOGIN_RESPONSE.getUser().getMediumId()
                        , Constants.USER_LOGIN_RESPONSE.getUser().getStandardId()
                        , chapterId);


        request.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();

                if (response.body().getSuccess() == 1) {

                    testResponse = response.body();
                    textViewNumberOfQuestions.setText(String.valueOf(testResponse.getQuestions().size()));
                    long totalSeconds = 0;
                    for (int i = 0; i < testResponse.getQuestions().size(); i++) {
                        totalSeconds = totalSeconds + testResponse.getTimePerQuestion();
                    }
                    textViewDuration.setText(Utils.getMinfromSec(totalSeconds) + " minutes");

                    //;

                    SubmitQuizRequest submitQuizRequest = new SubmitQuizRequest();
                    submitQuizRequest.setChapterId(chapterId);
                    submitQuizRequest.setSubjectId(subjectId);
                    submitQuizRequest.setUserId(Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
                    submitQuizRequest.setMediumId(Constants.USER_LOGIN_RESPONSE.getUser().getMediumId());
                    submitQuizRequest.setStandardId(Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
                    submitQuizRequest.setQuizType("c");
                    submitQuizRequest.setTotalQuestions(testResponse.getQuestions().size());
                    submitQuizRequest.setTotalAttempted(0);
                    submitQuizRequest.setTotalCorrect(0);
                    List<SubmitQuizQuestionDetails> submitQuizQuestionDetailsList = new ArrayList<>();
                    for(int i = 0 ; i < testResponse.getQuestions().size() ; i++){

                        SubmitQuizQuestionDetails submitQuizQuestionDetails = new SubmitQuizQuestionDetails();
                        submitQuizQuestionDetails.setQuestionId(Integer.parseInt(testResponse.getQuestions().get(i).getQuestionId()));
                        submitQuizQuestionDetails.setAnswer(0);
                        submitQuizQuestionDetails.setAttempted(0);
                        submitQuizQuestionDetails.setCorrect(0);
                        submitQuizQuestionDetailsList.add(submitQuizQuestionDetails);

                    }
                    submitQuizRequest.setQuestions(submitQuizQuestionDetailsList);


                    Constants.SUBMIT_QUIZ_REQUEST = submitQuizRequest;

                } else {
                    Toast.makeText(TestInstructionActivity.this, "No test available for this chapter", Toast.LENGTH_SHORT).show();
                    CustomDialog.commonDialog(TestInstructionActivity.this, "Sankalp", "Something went wrong please try again later.", "Retry");
                    finish();
                }
                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
    private void getSubjectQuizQuestions(String subjectId) {
        LoaderDialog dialog = new LoaderDialog(TestInstructionActivity.this);
        dialog.showProgress();


        Call<TestResponse> request = RestClient.getInstance(this)
                .getSubjectQuizQuestions(subjectId
                        , Constants.USER_LOGIN_RESPONSE.getUser().getMediumId()
                        , Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());


        request.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                if (response.body().getSuccess() == 1) {

                    testResponse = response.body();
                    textViewNumberOfQuestions.setText(String.valueOf(testResponse.getQuestions().size()));
                    long totalSeconds = 0;
                    for (int i = 0; i < testResponse.getQuestions().size(); i++) {
                        totalSeconds = totalSeconds + testResponse.getTimePerQuestion();
                    }
                    textViewDuration.setText(Utils.getMinfromSec(totalSeconds) + " minutes");


                    SubmitQuizRequest submitQuizRequest = new SubmitQuizRequest();
                    submitQuizRequest.setUserId(Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
                    submitQuizRequest.setSubjectId(subjectId);
                    submitQuizRequest.setMediumId(Constants.USER_LOGIN_RESPONSE.getUser().getMediumId());
                    submitQuizRequest.setStandardId(Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
                    submitQuizRequest.setQuizType("s");
                    submitQuizRequest.setTotalQuestions(testResponse.getQuestions().size());
                    submitQuizRequest.setTotalAttempted(0);
                    submitQuizRequest.setTotalCorrect(0);
                    List<SubmitQuizQuestionDetails> submitQuizQuestionDetailsList = new ArrayList<>();
                    for(int i = 0 ; i < testResponse.getQuestions().size() ; i++){

                        SubmitQuizQuestionDetails submitQuizQuestionDetails = new SubmitQuizQuestionDetails();
                        submitQuizQuestionDetails.setQuestionId(Integer.parseInt(testResponse.getQuestions().get(i).getQuestionId()));
                        submitQuizQuestionDetails.setAnswer(0);
                        submitQuizQuestionDetails.setAttempted(0);
                        submitQuizQuestionDetails.setCorrect(0);
                        submitQuizQuestionDetailsList.add(submitQuizQuestionDetails);

                    }
                    submitQuizRequest.setQuestions(submitQuizQuestionDetailsList);


                    Constants.SUBMIT_QUIZ_REQUEST = submitQuizRequest;

                } else {
                    Toast.makeText(TestInstructionActivity.this, "No test available for this subject", Toast.LENGTH_SHORT).show();
                    CustomDialog.commonDialog(TestInstructionActivity.this, "Sankalp", "Something went wrong please try again later.", "Retry");
                    finish();                }
                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
    private void getGeneralQuizQuestions() {
        LoaderDialog dialog = new LoaderDialog(TestInstructionActivity.this);
        dialog.showProgress();


        Call<TestResponse> request = RestClient.getInstance(this)
                .getGenralQuizQuestions(Constants.USER_LOGIN_RESPONSE.getUser().getMediumId()
                        , Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());


        request.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                if (response.body().getSuccess() == 1) {

                    testResponse = response.body();
                    textViewNumberOfQuestions.setText(String.valueOf(testResponse.getQuestions().size()));
                    long totalSeconds = 0;
                    for (int i = 0; i < testResponse.getQuestions().size(); i++) {
                        totalSeconds = totalSeconds + testResponse.getTimePerQuestion();
                    }
                    textViewDuration.setText(Utils.getMinfromSec(totalSeconds) + " minutes");
                    SubmitQuizRequest submitQuizRequest = new SubmitQuizRequest();
                    submitQuizRequest.setUserId(Constants.USER_LOGIN_RESPONSE.getUser().getUserId());

                    submitQuizRequest.setMediumId(Constants.USER_LOGIN_RESPONSE.getUser().getMediumId());
                    submitQuizRequest.setStandardId(Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
                    submitQuizRequest.setQuizType("g");
                    submitQuizRequest.setTotalQuestions(testResponse.getQuestions().size());
                    submitQuizRequest.setTotalAttempted(0);
                    submitQuizRequest.setTotalCorrect(0);
                    List<SubmitQuizQuestionDetails> submitQuizQuestionDetailsList = new ArrayList<>();
                    for(int i = 0 ; i < testResponse.getQuestions().size() ; i++){

                        SubmitQuizQuestionDetails submitQuizQuestionDetails = new SubmitQuizQuestionDetails();
                        submitQuizQuestionDetails.setQuestionId(Integer.parseInt(testResponse.getQuestions().get(i).getQuestionId()));
                        submitQuizQuestionDetails.setAnswer(0);
                        submitQuizQuestionDetails.setAttempted(0);
                        submitQuizQuestionDetails.setCorrect(0);
                        submitQuizQuestionDetailsList.add(submitQuizQuestionDetails);

                    }
                    submitQuizRequest.setQuestions(submitQuizQuestionDetailsList);


                    Constants.SUBMIT_QUIZ_REQUEST = submitQuizRequest;
                } else {
                    Toast.makeText(TestInstructionActivity.this, "No general test available", Toast.LENGTH_SHORT).show();
                    CustomDialog.commonDialog(TestInstructionActivity.this, "Sankalp", "Something went wrong please try again later.", "Retry");
                    finish();                }
                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
}
