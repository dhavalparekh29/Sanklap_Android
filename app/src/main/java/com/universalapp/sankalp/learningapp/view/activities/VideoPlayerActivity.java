package com.universalapp.sankalp.learningapp.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayerActivity extends AppCompatActivity {

    /*@BindView(R.id.player_video)
    YouTubePlayerView youTubePlayerViewVideo;*/
    /*@BindView(R.id.seekbar_video)
    SeekBar seekBarVideo;*/
    YouTubePlayer youTubePlayer;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private List<Pair<Date, String>> playerStatesHistory = new ArrayList<>();

    String videoId ="";
    YouTubePlayerView youTubePlayerViewVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        //ButterKnife.bind(this);
        videoId = getIntent().getStringExtra(Constants.KEY_YOUTUBE_VIDEO_ID);
        youTubePlayerViewVideo = findViewById(R.id.player_video);

        initYouTubePlayerView();

    }
    private void initYouTubePlayerView() {
        getLifecycle().addObserver(youTubePlayerViewVideo);

        youTubePlayerViewVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                addToList("READY", playerStatesHistory);

                /*setPlayNextVideoButtonClickListener(youTubePlayer);

                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),
                        videoId, 0f
                );*/
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),
                        videoId, 0f);
            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                onNewState(state);
            }

            @Override
            public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError error) {
                addToList("ERROR: " +error.name(), playerStatesHistory);
            }
        });
    }

    private void onNewState(PlayerConstants.PlayerState newState) {
        //TextView playerStatusTextView = findViewById(R.id.player_status_text_view);
        String playerState = playerStateToString(newState);

        addToList(playerState, playerStatesHistory);
        printStates(playerStatesHistory);
    }

    private void addToList(String playerState, List<Pair<Date, String>> stateHistory) {
        if(stateHistory.size() >= 15)
            stateHistory.remove(0);
        stateHistory.add(new Pair<>(new Date(), playerState));
    }

    private void printStates(List<Pair<Date, String>> states) {
        SimpleDateFormat dt = new SimpleDateFormat("MM-dd hh:mm:ss.SSS", Locale.US);

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<states.size(); i++) {
            Pair<Date, String> pair = states.get(i);
            sb.append(dt.format(pair.first)).append(": ").append(pair.second);
            if(i != states.size()-1)
                sb.append("\n");
        }

        //playerStatusTextView.setText(sb.toString());
    }

    private String playerStateToString(PlayerConstants.PlayerState state) {
        switch (state) {
            case UNKNOWN:
                return "UNKNOWN";
            case UNSTARTED:
                return "UNSTARTED";
            case ENDED:
                return "ENDED";
            case PLAYING:
                return "PLAYING";
            case PAUSED:
                return "PAUSED";
            case BUFFERING:
                return "BUFFERING";
            case VIDEO_CUED:
                return "VIDEO_CUED";
            default:
                return "status unknown";
        }
    }



}
