package com.example.android.morningaarti;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer aartiAudio;
    private AudioManager audioManager;
    private ImageView playButton;
    private ImageView loop;
    private ImageView stop;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                aartiAudio.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                aartiAudio.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                try {
                    releaseMediaPlayer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        stop = findViewById(R.id.stopper);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop.setBackgroundColor(Color.parseColor("#D2B4DE"));
                try {
                    releaseMediaPlayer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        final MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    releaseMediaPlayer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        final ArrayList<Aarti> Aartis = new ArrayList<>();

        Aartis.add(new Aarti("Jai Ganesh Aarti", R.drawable.ganesh_ji, R.raw.jai_ganesh));
        Aartis.add(new Aarti("Om Jai Jagdish", R.drawable.jagdish_ji, R.raw.om_jai_jagdish));
        Aartis.add(new Aarti("jai Ammbe Gauri", R.drawable.ambe_gauri, R.raw.jai_ambey_gauri));
        Aartis.add(new Aarti("jai Laxmi Mata", R.drawable.laxmi_mata, R.raw.jai_laxmi_mata));
        Aartis.add(new Aarti("Jai Shiv Omkara", R.drawable.shiv_ji, R.raw.jai_shiv_omkara));
        Aartis.add(new Aarti("Hanuman Chalisa", R.drawable.hanuman_ji, R.raw.hanuman_chalisa));

        AartiAdapter AartiAdapter = new AartiAdapter(this, Aartis);
        ListView AartiView = findViewById(R.id.list_item);
        AartiView.setAdapter(AartiAdapter);

        AartiView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    releaseMediaPlayer();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Aarti aarti = Aartis.get(position);

                int result = audioManager.requestAudioFocus(audioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    aartiAudio = MediaPlayer.create(MainActivity.this, aarti.getAudio_aarti());
                    aartiAudio.start();
                    aartiAudio.setOnCompletionListener(completionListener);
                    stop.setBackgroundColor(Color.parseColor("#E8DAEF"));
                }

                playButton = view.findViewById(R.id.playButton);
                if (aartiAudio.isPlaying()) {
                    playButton.setImageResource(R.drawable.ic_pause_black_24dp);
                }

                loop = findViewById(R.id.looper);
                loop.setEnabled(true);
                loop.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View v) {
                        if (aartiAudio.isLooping()) {
                            loop.setBackgroundColor(Color.parseColor("#E8DAEF"));
                            aartiAudio.setLooping(false);
                        } else {
                            loop.setBackgroundColor(Color.parseColor("#D2B4DE"));
                            aartiAudio.setLooping(true);
                        }
                    }
                });
            }
        });
    }

    private void releaseMediaPlayer() throws IOException {
        if (aartiAudio != null) {
            aartiAudio.stop();
            aartiAudio.prepare();
            aartiAudio = null;
            loop.setEnabled(false);
            playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}
