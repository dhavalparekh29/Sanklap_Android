package com.universalapp.sankalp.learningapp.view.activities;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SubChapterActivity extends AppCompatActivity {


    @BindView(R.id.image_menu)
    ImageView imageViewBackPress;
    @BindView(R.id.text_title)
    TextView textViewChapterName;
    @BindView(R.id.layout_video)
    LinearLayout linearLayoutVideo;
    @BindView(R.id.layout_quiz)
    LinearLayout linearLayoutQuiz;
    @BindView(R.id.layout_material)
    LinearLayout linearLayoutMaterial;
    @BindView(R.id.layout_leaders_board)
    LinearLayout linearLayoutLeadersBoard;

    String chapterId, subjectId, chapterName, materialUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_chapter);
        ButterKnife.bind(this);

        chapterId = getIntent().getStringExtra(Constants.KEY_CHAPTER_ID);
        subjectId = getIntent().getStringExtra(Constants.KEY_SUBJECT_ID);
        materialUrl = getIntent().getStringExtra(Constants.KEY_MATERIAL_URL);
        chapterName = getIntent().getStringExtra(Constants.KEY_CHAPTER_NAME);
        textViewChapterName.setText(chapterName);
        linearLayoutVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isMembershipExpired() == false ){
                    if (Constants.USER_LOGIN_RESPONSE.getUserPlan().getVideo().equals("1")) {

                        Intent intent = new Intent(SubChapterActivity.this, VideoListActivity.class);
                        intent.putExtra(Constants.KEY_CHAPTER_ID, chapterId);
                        intent.putExtra(Constants.KEY_SUBJECT_ID, subjectId);
                        intent.putExtra(Constants.KEY_SUBJECT_ID, subjectId);
                        intent.putExtra(Constants.KEY_CHAPTER_NAME, chapterName);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SubChapterActivity.this, "Upgrade plan to play video", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SubChapterActivity.this, "Your membership plan has been expired, please upgrade your membership plan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        linearLayoutLeadersBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubChapterActivity.this, LeadersBoardActivity.class);
                intent.putExtra(Constants.KEY_LEADERS_BOARD_TYPE, Constants.TEST_TYPE_CHAPTER_WISE);
                intent.putExtra(Constants.KEY_CHAPTER_ID, chapterId);
                intent.putExtra(Constants.KEY_CHAPTER_NAME, chapterName);
                intent.putExtra(Constants.KEY_SUBJECT_ID, subjectId);
                startActivity(intent);
            }
        });

        imageViewBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        linearLayoutMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isMembershipExpired() == false ){
                    if (Constants.USER_LOGIN_RESPONSE.getUserPlan().getMaterial().equals("1")) {

                        Intent intent = new Intent(SubChapterActivity.this, MaterialActivity.class);
                        intent.putExtra(Constants.KEY_MATERIAL_URL, materialUrl);
                        intent.putExtra(Constants.KEY_CHAPTER_NAME, chapterName);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SubChapterActivity.this, "Upgrade plan to view material", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SubChapterActivity.this, "Your membership plan has been expired, please upgrade your membership plan", Toast.LENGTH_SHORT).show();
                }
                /*Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setDataAndType(Uri.parse( "http://docs.google.com/viewer?url=" + materialUrl), "text/html");

                startActivity(intent);*/
            }
        });
        linearLayoutQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isMembershipExpired() == false) {
                    if (Constants.USER_LOGIN_RESPONSE.getUserPlan().getQuiz().equals("1")) {

                        Constants.SELECTED_TEST_NAME = chapterName + " - Test";

                        Intent intent = new Intent(SubChapterActivity.this, TestInstructionActivity.class);
                        intent.putExtra(Constants.KEY_CHAPTER_ID, chapterId);
                        intent.putExtra(Constants.KEY_SUBJECT_ID, subjectId);
                        intent.putExtra(Constants.KEY_CHAPTER_NAME, chapterName);
                        intent.putExtra(Constants.KEY_TEST_TYPE, Constants.TEST_TYPE_CHAPTER_WISE);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SubChapterActivity.this, "Upgrade plan to participate in quiz", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SubChapterActivity.this, "Your membership plan has been expired, please upgrade your membership plan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
