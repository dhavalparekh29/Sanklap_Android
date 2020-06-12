package com.universalapp.sankalp.learningapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.controller.chapter.ChapterListAdapter;
import com.universalapp.sankalp.learningapp.controller.video.VideoListAdatper;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.chapter.ChapterResponse;
import com.universalapp.sankalp.learningapp.model.video.VideoResponse;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListActivity extends AppCompatActivity {
    @BindView(R.id.text_title)
    TextView textViewChapterName;
    @BindView(R.id.image_menu)
    ImageView imageViewBack;
    @BindView(R.id.recycler_video_list)
    RecyclerView recyclerViewVideo;

    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewVideo.setLayoutManager(layoutManager);

        textViewChapterName.setText(getIntent().getStringExtra(Constants.KEY_CHAPTER_NAME));

        getVideo(getIntent().getStringExtra(Constants.KEY_SUBJECT_ID),
                getIntent().getStringExtra(Constants.KEY_CHAPTER_ID));

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getVideo(String subjectId, String chapterId){
        LoaderDialog dialog = new LoaderDialog(VideoListActivity.this);
        dialog.showProgress();


        Call<VideoResponse> request = RestClient.getInstance(this)
                .getVideo(subjectId
                , Constants.USER_LOGIN_RESPONSE.getUser().getMediumId()
                , Constants.USER_LOGIN_RESPONSE.getUser().getStandardId()
                ,chapterId);


        request.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {

                dialog.hideProgressBar();
                if(response.body().getSuccess() == 1){

                    VideoListAdatper adapter = new VideoListAdatper(VideoListActivity.this, response.body().getVideo());
                    recyclerViewVideo.setAdapter(adapter);

                }else{
                    //CustomDialog.commonDialog(VideoListActivity.this, "Login fail", "Credential mismatch.", "Retry");
                    Toast.makeText(VideoListActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
}
