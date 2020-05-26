package com.polaris.alertdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
int position;
ArrayList<File>arrayList;
public MediaPlayer mediaPlayer;
ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView=findViewById(R.id.btnPlay);

        position=getIntent().getExtras().getInt("POS");
        arrayList= (ArrayList<File>) getIntent().getExtras().getSerializable("FILE");

            playIt();

    }

    public void process(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    imageView.setImageResource(R.drawable.ic_pause_);
                }
                else {
                    mediaPlayer.start();
                    imageView.setImageResource(R.drawable.play_arrow);
                    playIt();
                }

                break;
            case R.id.btnNext:
                position++;
                if (position==arrayList.size())
                {
                    position=0;
                }
                mediaPlayer.stop();
                playIt();
                break;

                case R.id.btnPre:
                position--;
                if (position<0)
                {
                    position=arrayList.size()-1;
                }
                mediaPlayer.stop();
                playIt();

                break;

                case R.id.btnFastBack:
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
                    break;


                    case R.id.btnFastFwd:
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+5000);
                break;


        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }




    public void playIt()
    {

        File file=arrayList.get(position);

        getSupportActionBar().setTitle(file.getName());
        Uri uri= Uri.parse(file.toString());

        mediaPlayer=MediaPlayer.create(Main2Activity.this,uri);
        mediaPlayer.start();

    }
}
