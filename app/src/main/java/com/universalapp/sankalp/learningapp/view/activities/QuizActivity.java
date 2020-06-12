package com.universalapp.sankalp.learningapp.view.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.controller.test.TestViewPagerAdapter;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.BasicResponse;
import com.universalapp.sankalp.learningapp.model.quiz.QuestionDetails;
import com.universalapp.sankalp.learningapp.model.quiz.TestResponse;
import com.universalapp.sankalp.learningapp.model.quiz.submitQuiz.SubmitQuizRequest;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;
import com.universalapp.sankalp.learningapp.utils.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    @BindView(R.id.image_menu)
    ImageView imageViewBack;
    @BindView(R.id.text_title)
    TextView textViewTitle;
    @BindView(R.id.text_number_of_question)
    TextView textViewNoOfQuestion;
    @BindView(R.id.text_timer)
    TextView textViewTimer;
    @BindView(R.id.viewpager_test)
    NonSwipeableViewPager viewPagerTest;
    @BindView(R.id.image_previous)
    ImageView imageViewPrevious;
    @BindView(R.id.image_next)
    ImageView imageViewNext;
    @BindView(R.id.button_submit)
    Button buttonSubmit;

    TestViewPagerAdapter adapter;
    List<QuestionDetails> arrayListQuestion = new ArrayList<>();

    TestResponse testResponse;
    boolean isFirstTime = true;
    boolean isResultShown = false;
    Handler handler;
    Runnable runnableCode;
    Timer timer ;

    SubmitQuizRequest submitQuizRequest = new SubmitQuizRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ButterKnife.bind(this);

        /*getQuizQuestions(getIntent().getStringExtra(Constants.KEY_SUBJECT_ID),
                getIntent().getStringExtra(Constants.KEY_CHAPTER_ID));*/

        testResponse = new Gson().fromJson(getIntent().getStringExtra(Constants.KEY_TEST_RESPONSE), TestResponse.class);

        imageViewBack.setVisibility(View.GONE);
        textViewTitle.setText(getIntent().getStringExtra(Constants.KEY_SUBJECT_NAME)+" - Test");

        textViewNoOfQuestion.setText(testResponse.getQuestions().size()+"");
        arrayListQuestion = testResponse.getQuestions();
        adapter = new TestViewPagerAdapter(getSupportFragmentManager(), arrayListQuestion, this);
        adapter.notifyDataSetChanged();
        viewPagerTest.setAdapter(adapter);
        textViewNoOfQuestion.setText((1)+"/"+arrayListQuestion.size());

        viewPagerTest.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                textViewNoOfQuestion.setText((i+1)+"/"+arrayListQuestion.size());
                if((i + 1) >= testResponse.getQuestions().size()){
                    imageViewNext.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPagerTest.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nextQuestion(viewPagerTest.getCurrentItem()+1);
                viewPagerTest.setCurrentItem(viewPagerTest.getCurrentItem());
                nextQuestion(viewPagerTest.getCurrentItem());
                //runThread("2");
            }
        });

        imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPagerTest.setCurrentItem(viewPagerTest.getCurrentItem()-1);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showResults();

            }
        });


        runThread("1");


    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "Submit test before going back", Toast.LENGTH_SHORT).show();
    }

    private void showResults(){
        isResultShown = true;
        textViewTimer.setVisibility(View.GONE);
        final Dialog dialog = new Dialog(QuizActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_show_quiz_result);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width)/7, (4 * height)/5);
        TextView textViewSubjectName =  dialog.findViewById(R.id.text_subject_name);
        TextView textViewChapterName =  dialog.findViewById(R.id.text_chapter_name);
        TextView textViewTotalAttempt =  dialog.findViewById(R.id.text_total_attempt);
        TextView textViewTotalQuestion =  dialog.findViewById(R.id.text_total_question);
        //TextView textViewTotalCorrect =  dialog.findViewById(R.id.text_total_correct);
        TextView textViewTotalScore =  dialog.findViewById(R.id.text_total_score);
        Button buttonSubmit =  dialog.findViewById(R.id.button_submit);

        SubmitQuizRequest submitQuizRequest = Constants.SUBMIT_QUIZ_REQUEST;

        textViewSubjectName.setText(Constants.SELECTED_TEST_SUBJECT+" ");
        textViewChapterName.setText(Constants.SELECTED_TEST_NAME+" ");
        textViewTotalAttempt.setText(String.valueOf(submitQuizRequest.getTotalAttempted()));
        textViewTotalQuestion.setText(String.valueOf(submitQuizRequest.getTotalQuestions()));
        textViewTotalScore.setText(String.valueOf(submitQuizRequest.getTotalCorrect()) +"/"+String.valueOf(submitQuizRequest.getTotalQuestions()));

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnableCode);

                dialog.cancel();
                submitTest(Constants.SUBMIT_QUIZ_REQUEST);
            }
        });

        dialog.show();
    }

    public void nextQuestion(int position){
        /*int currentItem = viewPagerTest.getCurrentItem() + 1;

        viewPagerTest.setCurrentItem(currentItem);*/
        runThread("2");
        if((position + 1) > testResponse.getQuestions().size()){
            showResults();
            //submitTest(Constants.SUBMIT_QUIZ_REQUEST);
        }
    }

    @Override
    protected void onDestroy() {
        if(handler!=null){
            handler.removeCallbacks(runnableCode);
            handler = null;
        }
        super.onDestroy();
    }

    private void runThread(String thread){

        if(handler != null){
            handler.removeCallbacks(runnableCode);
        }
        handler = new Handler();
// Define the code block to be executed


         runnableCode = new Runnable() {

            @Override
            public void run() {
                if(isFirstTime){

                }else{
                    int currentItem = viewPagerTest.getCurrentItem() + 1;
                    viewPagerTest.setCurrentItem(currentItem);
                }

                if(timer != null){

                    timer.cancel();
                    timer = null;
                }
                timer = new Timer();


                timer.schedule(new TimerTask() {

                    long seconds = testResponse.getTimePerQuestion();
                    @Override
                    public void run() {

                        seconds--;
                        runOnUiThread(new Runnable(){
                            public void run() {
                                textViewTimer.setText(seconds + " second");
                                if(seconds == 0) {
                                    if ((viewPagerTest.getCurrentItem() + 1) == testResponse.getTotalQuestions()) {
                                        if (isResultShown == false) {
                                            showResults();
                                        }
                                    }
                                }
                            }
                        });

                        System.out.println("Thread seconds- " + thread + " - "+seconds);

                    }

                },0,1000);

                isFirstTime = false;


                /*
                System.out.println("Thread - " + thread);
                int currentItem = viewPagerTest.getCurrentItem();
                if(currentItem >= arrayListQuestion.size()){
                    showResults();
                }else{
                    nextQuestion(currentItem + 1);
                }*/
                handler.postDelayed(this, (testResponse.getTimePerQuestion()*1000));
            }
        };
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode);

    }
    private void submitTest(SubmitQuizRequest submitQuizRequest){
        LoaderDialog dialog = new LoaderDialog(QuizActivity.this,"Submitting test please wait");
        dialog.showProgress();


        Call<BasicResponse> request = RestClient.getInstance(this).submitQuiz(submitQuizRequest);


        request.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    Toast.makeText(QuizActivity.this, "Test submitted successfully", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    CustomDialog.commonDialog(QuizActivity.this, getResources().getString(R.string.app_name), "Something went wrong please try again or contact support", "Retry");
                }
                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
}
