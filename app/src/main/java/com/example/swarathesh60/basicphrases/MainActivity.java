package com.example.swarathesh60.basicphrases;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    MediaPlayer mediaPlayer= new MediaPlayer();;
    SeekBar seekbar ;
    int duration  ;
    TextView timerem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initiate stuff
        listView = (ListView) findViewById(R.id.list);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        timerem =(TextView) findViewById(R.id.time);
        // get the file names using fields
        Field[] fields=R.raw.class.getFields();
        List<String> data = new ArrayList<String>();
        for(int count=1; count < fields.length-1; count++){
            Log.i("Raw Asset: ", fields[count].getName());
            data.add(fields[count].getName());
        }
        //initiate arrayadapter
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = listView.getItemAtPosition(i).toString();
                playMusic(title);
            }


        });
        //seekbar
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                mediaPlayer.seekTo(i);

            }@Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }

    private void playMusic(String title) {

        int resId = getResources().getIdentifier(title, "raw", getPackageName());
        Log.i(title, String.valueOf(resId));
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), resId);
        duration = mediaPlayer.getDuration();
        int timerseconds = duration/1000;
        int timemin = timerseconds/60;
        int timersecrem = timerseconds%60;
        timerem.setVisibility(View.VISIBLE);
        timerem.setText(String.valueOf(timemin+":"+timersecrem));
        seekbar.setMax(duration);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekbar.setProgress(mediaPlayer.getCurrentPosition());
                //Log.i("duration",Integer.toString(mediaPlayer.getDuration()));
            }
        },0,10000);
        mediaPlayer.start();

    }


    @Override
    public void onDestroy() {

        super.onDestroy();
        mediaPlayer.release();

    }

}
