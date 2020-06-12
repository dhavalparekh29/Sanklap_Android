package com.universalapp.sankalp.learningapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.controller.chapter.ChapterListAdapter;
import com.universalapp.sankalp.learningapp.controller.subject.SubjectListAdapter;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.chapter.ChapterResponse;
import com.universalapp.sankalp.learningapp.model.subject.SubjectResponse;
import com.universalapp.sankalp.learningapp.model.user.UserDetails;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;
import com.universalapp.sankalp.learningapp.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterActivity extends AppCompatActivity {

    @BindView(R.id.text_title)
    TextView textViewChapterName;
    @BindView(R.id.image_menu)
    ImageView imageViewBack;
    @BindView(R.id.text_no_result_found)
    TextView textViewNoResultFound;
    @BindView(R.id.recycler_chapter_list)
    RecyclerView recyclerViewChapter;

    @BindView(R.id.layout_quiz)
    LinearLayout linearLayoutQuiz;
    @BindView(R.id.layout_leaders_board)
    LinearLayout linearLayoutLeadersBoard;
    RecyclerView.LayoutManager layoutManager;
    String subjectName, subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerViewChapter.setLayoutManager(layoutManager);
        subjectId = getIntent().getStringExtra(Constants.KEY_SUBJECT_ID);
        subjectName = getIntent().getStringExtra(Constants.KEY_SUBJECT_NAME);


        linearLayoutQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.isMembershipExpired() == false) {

                    if (Constants.USER_LOGIN_RESPONSE.getUserPlan().getQuiz().equals("1")) {
                        Constants.SELECTED_TEST_NAME = Constants.SELECTED_TEST_SUBJECT + " - Test";
                        Intent intent = new Intent(ChapterActivity.this, TestInstructionActivity.class);
                        intent.putExtra(Constants.KEY_SUBJECT_ID, getIntent().getStringExtra(Constants.KEY_SUBJECT_ID));
                        intent.putExtra(Constants.KEY_TEST_TYPE, Constants.TEST_TYPE_SUBJECT_WISE);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ChapterActivity.this, "Upgrade plan to participate in quiz", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ChapterActivity.this, "Your membership plan has been expired, please upgrade your membership plan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        linearLayoutLeadersBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChapterActivity.this, LeadersBoardActivity.class);
                intent.putExtra(Constants.KEY_LEADERS_BOARD_TYPE, Constants.TEST_TYPE_SUBJECT_WISE);
                intent.putExtra(Constants.KEY_SUBJECT_NAME, subjectName);
                intent.putExtra(Constants.KEY_SUBJECT_ID, subjectId);
                startActivity(intent);
            }
        });
        textViewChapterName.setText(getIntent().getStringExtra(Constants.KEY_SUBJECT_NAME));

        getChapter(getIntent().getStringExtra(Constants.KEY_SUBJECT_ID));
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getChapter(String subjectId){
        LoaderDialog dialog = new LoaderDialog(ChapterActivity.this);
        dialog.showProgress();


        Call<ChapterResponse> request = RestClient.getInstance(this)
                .getChapterByUserPlan(subjectId
                , Constants.USER_LOGIN_RESPONSE.getUser().getMediumId()
                , Constants.USER_LOGIN_RESPONSE.getUser().getStandardId()
                , Constants.USER_LOGIN_RESPONSE.getUserPlan().getOrderId()
                ,Constants.USER_LOGIN_RESPONSE.getUserPlan().getAccessLevel());


        request.enqueue(new Callback<ChapterResponse>() {
            @Override
            public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    ChapterListAdapter adapter = new ChapterListAdapter(ChapterActivity.this, response.body().getChapter());
                    recyclerViewChapter.setAdapter(adapter);
                    textViewNoResultFound.setVisibility(View.GONE);
                    recyclerViewChapter.setVisibility(View.VISIBLE);
                }else{
                    textViewNoResultFound.setVisibility(View.VISIBLE);
                    recyclerViewChapter.setVisibility(View.GONE);
                    //CustomDialog.commonDialog(ChapterActivity.this, "Sankalp", "Credential mismatch.", "Retry");
                }
                dialog.hideProgressBar();

            }


            @Override
            public void onFailure(Call<ChapterResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
}
