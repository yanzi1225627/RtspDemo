package com.yanzi.rtspdemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.yanzi.util.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
MediaPlayer.OnCompletionListener{
    VideoView videoview;
    Button btn_play;

    String mVideoUrl = "rtsp://mpv.cdn3.bigCDN.com:554/bigCDN/definst/mp4:bigbuckbunnyiphone_400.mp4";
    //    String mVideoUrl = "rtsp://192.168.0.6:8554/1.264";
    String VIDEO_URL = "/sdcard/video.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoview = (VideoView) findViewById(R.id.videoview);
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVideoUrl();
                playRtspStream(mVideoUrl);
                Toast.makeText(getApplicationContext(), "Please check " + VIDEO_URL + "whether exist", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void playRtspStream(String rtspUrl) {
        videoview.setVideoURI(Uri.parse(rtspUrl));
        videoview.requestFocus();
        videoview.setOnPreparedListener(this);
        videoview.setOnCompletionListener(this);
        videoview.setOnErrorListener(this);
//        videoview.setOnInfoListener(this);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        showToast("---onPrepared---dur = " + mp.getDuration());
        videoview.start();
    }

    private boolean getVideoUrl() {
        File f = new File(VIDEO_URL);

        if (!f.exists()) {
            return false;
        }
        try {
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            mVideoUrl = new String(buffer, "UTF-8");
            Toast.makeText(getApplicationContext(), "videoUrl=" + mVideoUrl, Toast.LENGTH_SHORT).show();
            fis.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showToast(String msg){
        ToastUtil.show(getApplicationContext(), msg);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        String msg = "---onError---, what = " + what + "extra = " + extra;
        showToast(msg);
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        showToast("---onCompletion---");
    }




}
