package com.universalapp.sankalp.learningapp.controller.video;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.squareup.picasso.Picasso;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.customYoutubePlayer.Orientation;
import com.universalapp.sankalp.learningapp.customYoutubePlayer.YouTubePlayerActivity;
import com.universalapp.sankalp.learningapp.model.video.VideoDetail;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.view.activities.VideoPlayerActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListAdatper  extends RecyclerView.Adapter<VideoListAdatper.MyViewHolder> {

    List<VideoDetail> arrayListVideo = new ArrayList<>();
    Activity activity;

    public VideoListAdatper (Activity activity, List<VideoDetail> arrayListVideo){
        this.activity = activity;
        this.arrayListVideo = arrayListVideo;
    }

    @NonNull
    @Override
    public VideoListAdatper.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new VideoListAdatper.MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_video_layout, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull VideoListAdatper.MyViewHolder viewHolder, int position) {

        //viewHolder.textViewName.setText(arrayListTimeSlot.get(i).getName());
        viewHolder.textViewVideoTitle.setText(arrayListVideo.get(position).getTopicName());

        String videoId = "";
        if(arrayListVideo.get(position).getUrl().contains("youtube.com")) {
            videoId = arrayListVideo.get(position).getUrl().split("v=")[1];
        }else if(arrayListVideo.get(position).getUrl().contains("youtu.be")){
            videoId = arrayListVideo.get(position).getUrl().split("/")[3];
        }
        //String videoId = "ZaXovOahw6c";
        String thumbnailUrl = "https://img.youtube.com/vi/"+videoId+"/hqdefault.jpg";
        Picasso.get().load(thumbnailUrl).placeholder(R.drawable.ic_no_image).error(R.drawable.ic_no_image).into(viewHolder.imageViewVideoThumbnail);

        viewHolder.imageViewVideoThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String videoId1 = "";
                if(arrayListVideo.get(position).getUrl().contains("youtube.com")) {
                    videoId1 = arrayListVideo.get(position).getUrl().split("v=")[1];
                }else if(arrayListVideo.get(position).getUrl().contains("youtu.be")){
                    videoId1 = arrayListVideo.get(position).getUrl().split("/")[3];
                }

                Intent intent = new Intent(activity, VideoPlayerActivity.class);
                intent.putExtra(Constants.KEY_YOUTUBE_VIDEO_ID, videoId1);
                activity.startActivity(intent);

               /* Intent intent = new Intent(activity, YouTubePlayerActivity.class);
                intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, videoId);
                intent.putExtra(YouTubePlayerActivity.EXTRA_PLAYER_STYLE, YouTubePlayer.PlayerStyle.DEFAULT);
                intent.putExtra(YouTubePlayerActivity.EXTRA_ORIENTATION, Orientation.AUTO_START_WITH_LANDSCAPE);
                intent.putExtra(YouTubePlayerActivity.EXTRA_SHOW_AUDIO_UI, true);
                intent.putExtra(YouTubePlayerActivity.EXTRA_HANDLE_ERROR, true);

//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivityForResult(intent, 1);*/

            }
        });
    }

    @Override
    public int getItemCount() {
        //return arrayListTimeSlot.size();
        return arrayListVideo.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.text_video_title)
        TextView textViewVideoTitle;
        @BindView(R.id.image_video_thumbnail)
        ImageView imageViewVideoThumbnail;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }

    public  void updateList(List<VideoDetail> arrayListSubject){
        this.arrayListVideo = arrayListSubject;
        notifyDataSetChanged();
    }
}