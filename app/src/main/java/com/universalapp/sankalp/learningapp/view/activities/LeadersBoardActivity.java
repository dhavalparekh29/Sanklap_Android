package com.universalapp.sankalp.learningapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.controller.leadersBoard.LeadersBoardListAdapter;
import com.universalapp.sankalp.learningapp.controller.reportList.TestReportGeneralListAdapter;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.leadersBoard.LeadersBoardResponse;
import com.universalapp.sankalp.learningapp.model.testReport.GeneralWiseReport;
import com.universalapp.sankalp.learningapp.utils.Constants;

import java.util.List;

public class LeadersBoardActivity extends AppCompatActivity {

    @BindView(R.id.card_self_score)
    CardView cardViewSelfScore;

    @BindView(R.id.recycler_leaders_board_report)
    RecyclerView recyclerViewLeadersBoardReport;
    @BindView(R.id.text_score)
    TextView textViewScore;
    @BindView(R.id.text_no_result_found)
    TextView textViewNoResultFound;
    @BindView(R.id.text_user_name)
    TextView textViewUserNameRank;
    @BindView(R.id.text_title)
    TextView textViewTitle;
    @BindView(R.id.image_menu)
    ImageView imageViewBack;

    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaders_board);

        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerViewLeadersBoardReport.setLayoutManager(layoutManager);


        switch (getIntent().getStringExtra(Constants.KEY_LEADERS_BOARD_TYPE)){
            case Constants.TEST_TYPE_GENERAL_WISE:

                getLeadersBoardGeneralReport();
                textViewTitle.setText("General Leaders Board");

                break;

            case Constants.TEST_TYPE_SUBJECT_WISE:

                getLeadersBoardSubjectReport(getIntent().getStringExtra(Constants.KEY_SUBJECT_ID));
                textViewTitle.setText(getIntent().getStringExtra(Constants.KEY_SUBJECT_NAME)+" Leaders Board");

                break;

            case Constants.TEST_TYPE_CHAPTER_WISE:

                getLeadersBoardChapterReport(getIntent().getStringExtra(Constants.KEY_SUBJECT_ID), getIntent().getStringExtra(Constants.KEY_CHAPTER_ID));
                textViewTitle.setText(getIntent().getStringExtra(Constants.KEY_CHAPTER_NAME)+" Leaders Board");

                break;
        }


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getLeadersBoardGeneralReport(){
        LoaderDialog dialog = new LoaderDialog(LeadersBoardActivity.this);
        //dialog.showProgress();


        Call<LeadersBoardResponse> request = RestClient.getInstance(LeadersBoardActivity.this).getLeadersBoardGeneral(Constants.USER_LOGIN_RESPONSE.getUser().getUserId()
                , Constants.USER_LOGIN_RESPONSE.getUser().getMediumId()
                , Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());


        request.enqueue(new Callback<LeadersBoardResponse>() {
            @Override
            public void onResponse(Call<LeadersBoardResponse> call, Response<LeadersBoardResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getScore().size() > 0){

                    LeadersBoardListAdapter adapter = new LeadersBoardListAdapter(LeadersBoardActivity.this, response.body());
                    recyclerViewLeadersBoardReport.setAdapter(adapter);
                    if(response.body().getUserScore()!=null){
                        textViewUserNameRank.setText("Your rank: "+response.body().getUserScore().getRank());
                    }else{
                        cardViewSelfScore.setVisibility(View.GONE);
                    }

                }else{
                    recyclerViewLeadersBoardReport.setVisibility(View.GONE);
                    textViewNoResultFound.setVisibility(View.VISIBLE);
                    //Toast.makeText(LeadersBoardActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }


                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<LeadersBoardResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }

    private void getLeadersBoardSubjectReport(String subjectId){
        LoaderDialog dialog = new LoaderDialog(LeadersBoardActivity.this);
        //dialog.showProgress();


        Call<LeadersBoardResponse> request = RestClient.getInstance(LeadersBoardActivity.this).getLeadersBoardSubject(Constants.USER_LOGIN_RESPONSE.getUser().getUserId()
                , subjectId
                , Constants.USER_LOGIN_RESPONSE.getUser().getStandardId()
                , Constants.USER_LOGIN_RESPONSE.getUser().getMediumId());


        request.enqueue(new Callback<LeadersBoardResponse>() {
            @Override
            public void onResponse(Call<LeadersBoardResponse> call, Response<LeadersBoardResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getScore().size() > 0){

                    LeadersBoardListAdapter adapter = new LeadersBoardListAdapter(LeadersBoardActivity.this, response.body());
                    recyclerViewLeadersBoardReport.setAdapter(adapter);
                    if(response.body().getUserScore()!=null){
                        textViewUserNameRank.setText("Your rank: "+response.body().getUserScore().getRank());
                    }else{
                        cardViewSelfScore.setVisibility(View.GONE);
                    }

                }else{

                    recyclerViewLeadersBoardReport.setVisibility(View.GONE);
                    textViewNoResultFound.setVisibility(View.VISIBLE);
                    //Toast.makeText(LeadersBoardActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }


                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<LeadersBoardResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }

    private void getLeadersBoardChapterReport(String subjectId, String chapterid){
        LoaderDialog dialog = new LoaderDialog(LeadersBoardActivity.this);
        //dialog.showProgress();


        Call<LeadersBoardResponse> request = RestClient.getInstance(LeadersBoardActivity.this).getLeadersBoardChapter(Constants.USER_LOGIN_RESPONSE.getUser().getUserId()
                , subjectId
                , chapterid
                , Constants.USER_LOGIN_RESPONSE.getUser().getStandardId()
                , Constants.USER_LOGIN_RESPONSE.getUser().getMediumId());


        request.enqueue(new Callback<LeadersBoardResponse>() {
            @Override
            public void onResponse(Call<LeadersBoardResponse> call, Response<LeadersBoardResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getScore().size() > 0){

                    LeadersBoardListAdapter adapter = new LeadersBoardListAdapter(LeadersBoardActivity.this, response.body());
                    recyclerViewLeadersBoardReport.setAdapter(adapter);
                    if(response.body().getUserScore()!=null){
                        textViewUserNameRank.setText("Your rank: "+response.body().getUserScore().getRank());
                    }else{

                        cardViewSelfScore.setVisibility(View.GONE);
                    }

                }else{

                    recyclerViewLeadersBoardReport.setVisibility(View.GONE);
                    textViewNoResultFound.setVisibility(View.VISIBLE);
                    //Toast.makeText(LeadersBoardActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }


                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<LeadersBoardResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
}
