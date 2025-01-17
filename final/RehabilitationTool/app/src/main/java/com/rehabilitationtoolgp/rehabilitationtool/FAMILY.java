package com.rehabilitationtoolgp.rehabilitationtool;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rehabilitationtoolgp.rehabilitationtool.Helper.LocalHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;

public class FAMILY extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private static final String TAG = "FAMILY";
    Globalrecycler globalv;

    TextView brother2,mother2,sister2,father2,grandma,grandpa,uncle3,uncle4,aunt3,aunt4,cousg,cousb;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelper.onAttach(newBase,"ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        brother2 = (TextView)findViewById(R.id.brother2);
        sister2 = (TextView)findViewById(R.id.sister2);
        father2 = (TextView)findViewById(R.id.father2);
        mother2 = (TextView)findViewById(R.id.mother2);
        grandma = (TextView)findViewById(R.id.grandma);
        grandpa = (TextView)findViewById(R.id.grandpa);
        uncle3 = (TextView)findViewById(R.id.uncle2);
        uncle4 = (TextView)findViewById(R.id.uncle4);
        aunt3 = (TextView)findViewById(R.id.aunt2);
        aunt4 = (TextView)findViewById(R.id.aunt4);
        cousb = (TextView)findViewById(R.id.cousinb);
        cousg = (TextView)findViewById(R.id.cousing);

        Paper.init(this);

        String language = Paper.book().read("language");
        if(language == null)
            Paper.book().write("language","ar");


        updateView((String)Paper.book().read("language"));


        ImageView sister = (ImageView) findViewById(R.id.sister);
        ImageView brother= (ImageView) findViewById(R.id.brother);
        ImageView father = (ImageView) findViewById(R.id.father);
        ImageView mother = (ImageView) findViewById(R.id.mother);
        ImageView grandfather = (ImageView) findViewById(R.id.grandfather);
        ImageView grandmother = (ImageView) findViewById(R.id.grandmother);
        ImageView uncle = (ImageView) findViewById(R.id.uncle);
        ImageView aunt = (ImageView) findViewById(R.id.aunt);
        final ImageView uncle2 = (ImageView) findViewById(R.id.uncle3);
        final ImageView aunt2 = (ImageView) findViewById(R.id.Aunt3);
        ImageView boy = (ImageView) findViewById(R.id.cousinBoy);
        ImageView girl = (ImageView) findViewById(R.id.cousinGirl);
        ImageButton play = (ImageButton) findViewById(R.id.playall);
        Button back = (Button) findViewById(R.id.back);


        globalv=(Globalrecycler)getApplicationContext();
        initRecyclerView();

        final MediaPlayer mbroplayer =MediaPlayer.create(FAMILY.this, R.raw.motherbro);
        uncle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uncle3.getText().toString().equalsIgnoreCase("My Uncle")){
                    String data="my uncle";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.uncle);
                    globalv.addmNames(uncle3);

                }
                else{
                    mbroplayer .start();
                    globalv.addMrecords(R.raw.motherbro);
                    globalv.addmImageUrls(R.drawable.uncle);
                    globalv.addmNames(uncle3);
                }


                initRecyclerView();

            }
        });

        final MediaPlayer msisterplayer =MediaPlayer.create(FAMILY.this, R.raw.mothersis);
        aunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(aunt3.getText().toString().equalsIgnoreCase("My Aunt")){
                    String data="my aunt";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.auntt);
                    globalv.addmNames(aunt3);;

                }
                else{
                    msisterplayer .start();
                    globalv.addMrecords(R.raw.mothersis);
                    globalv.addmImageUrls(R.drawable.auntt);
                    globalv.addmNames(aunt3);
                }
                initRecyclerView();

            }
        });

        final MediaPlayer uncleplayer =MediaPlayer.create(FAMILY.this, R.raw.unkle);
        uncle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uncle4.getText().toString().equalsIgnoreCase("My Uncle")){
                    String data="my uncle";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.unclee);
                    globalv.addmNames(uncle4);;

                }
                else{
                    uncleplayer .start();
                    globalv.addMrecords(R.raw.unkle);
                    globalv.addmImageUrls(R.drawable.unclee);
                    globalv.addmNames(uncle4);
                }
                initRecyclerView();

            }
        });

        final MediaPlayer antplayer =MediaPlayer.create(FAMILY.this, R.raw.ant);
        aunt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aunt4.getText().toString().equalsIgnoreCase("My Aunt")){
                    String data="my aunt";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.aunt);
                    globalv.addmNames(aunt4);;

                }
                else{
                    antplayer .start();
                    globalv.addMrecords(R.raw.ant);
                    globalv.addmImageUrls(R.drawable.aunt);
                    globalv.addmNames(aunt4);
                }
                initRecyclerView();

            }
        });

        final MediaPlayer boyplayer =MediaPlayer.create(FAMILY.this, R.raw.sonofunkle);
        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cousb.getText().toString().equalsIgnoreCase("My Cousin boy")){
                    String data="my cousin boy";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.cousinboy);
                    globalv.addmNames(cousb);;

                }
                else{
                    boyplayer .start();
                    globalv.addMrecords(R.raw.sonofunkle);
                    globalv.addmImageUrls(R.drawable.cousinboy);
                    globalv.addmNames(cousb);
                }
                initRecyclerView();

            }
        });

        final MediaPlayer girlplayer =MediaPlayer.create(FAMILY.this, R.raw.cosgirl);
        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cousg.getText().toString().equalsIgnoreCase("My Cousin girl")){
                    String data="my cousin girl";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.cousingirl);
                    globalv.addmNames(cousg);;

                }
                else{
                    girlplayer .start();
                    globalv.addMrecords(R.raw.cosgirl);
                    globalv.addmImageUrls(R.drawable.cousingirl);
                    globalv.addmNames(cousg);
                }
                initRecyclerView();

            }
        });

        //SISTER
        final MediaPlayer sisterplayer =MediaPlayer.create(FAMILY.this, R.raw.sisterrr);
        sister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sister2.getText().toString().equalsIgnoreCase("My Sister")){
                    String data="my sister";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.sis);
                    globalv.addmNames(sister2);;

                }
                else{
                    sisterplayer .start();
                    globalv.addMrecords(R.raw.sisterrr);
                    globalv.addmImageUrls(R.drawable.sis);
                    globalv.addmNames(sister2);
                }
                initRecyclerView();

            }
        });


        //BROTHER
        final MediaPlayer brotherplayer =MediaPlayer.create(FAMILY.this, R.raw.brotherrr );
        brother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(brother2.getText().toString().equalsIgnoreCase("My Brother")){
                    String data="my brother";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.bro);
                    globalv.addmNames(brother2);;

                }
                else{
                    brotherplayer .start();
                    globalv.addMrecords(R.raw.brotherrr);
                    globalv.addmImageUrls(R.drawable.bro);
                    globalv.addmNames(brother2);
                }
                initRecyclerView();

            }
        });


        //FATHER
        final MediaPlayer fatherplayer =MediaPlayer.create(FAMILY.this, R.raw.fatherrr );
        father.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(father2.getText().toString().equalsIgnoreCase("My Father")){
                    String data="my father";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.dad);
                    globalv.addmNames(father2);;

                }
                else{
                    fatherplayer .start();
                    globalv.addMrecords(R.raw.fatherrr);
                    globalv.addmImageUrls(R.drawable.date);
                    globalv.addmNames(father2);
                }
                initRecyclerView();

            }
        });


        //MOTHER
        final MediaPlayer motherplayer =MediaPlayer.create(FAMILY.this, R.raw.motherrr);
        mother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mother2.getText().toString().equalsIgnoreCase("My Mother")){
                    String data="my mother";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.mom);
                    globalv.addmNames(mother2);;

                }
                else{
                    motherplayer .start();
                    globalv.addMrecords(R.raw.motherrr);
                    globalv.addmImageUrls(R.drawable.mom);
                    globalv.addmNames(mother2);
                }
                initRecyclerView();

            }
        });


        //GRANDFATHER
        final MediaPlayer grandfplayer =MediaPlayer.create(FAMILY.this, R.raw.grandpaaa);
        grandfather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(grandpa.getText().toString().equalsIgnoreCase("My Grandfather")){
                    String data="my grandfather";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.grandfather);
                    globalv.addmNames(grandpa);;

                }
                else{
                    grandfplayer .start();
                    globalv.addMrecords(R.raw.grandpaaa);
                    globalv.addmImageUrls(R.drawable.grandfather);
                    globalv.addmNames(grandpa);
                }
                initRecyclerView();

            }
        });


        //GRANDMOTHER
        final MediaPlayer grandmplayer =MediaPlayer.create(FAMILY.this, R.raw.grandmaaa);
        grandmother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(grandma.getText().toString().equalsIgnoreCase("My Grandmother")){
                    String data="my grandmother";
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    globalv.addmImageUrls(R.drawable.gradmother2);
                    globalv.addmNames(grandma);;

                }
                else{
                    grandmplayer .start();
                    globalv.addMrecords(R.raw.grandmaaa);
                    globalv.addmImageUrls(R.drawable.gradmother2);
                    globalv.addmNames(grandma);
                }
                initRecyclerView();

            }
        });


        //PLAY ALL

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i=0;i<  globalv.getMrecords().size();i++) {
                    /*final MediaPlayer mediaPlay = MediaPlayer.create(FAMILY.this, globalv.getMrecords().get(i));
                    mediaPlay.start();*/

                    if (globalv.getMrecords().get(i) instanceof Integer) {
                        // The Object is an instance of a String
                        Integer M = (Integer) globalv.getMrecords().get(i);

                        MediaPlayer mediaPlayer=MediaPlayer.create(view.getContext(),M);
                        mediaPlayer.start();
                    }
                    else if (globalv.getMrecords().get(i) instanceof byte[]) {
                        // The Object is an instance of a Double
                        byte[] g = (byte[]) globalv.getMrecords().get(i);
                        playMp3FromByte(g);
                    }
                    try {
                        Thread.sleep(700);
                    } catch(InterruptedException e) {
                    }
                }
            }



        });


        //BACK

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalv.getmImageUrls();
                globalv.getmNames();
                globalv.getMrecords();

                Intent intent1 = new Intent(FAMILY.this,Main2Activity.class);
                startActivity(intent1);
            }
        });
    }
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,  globalv.getmNames(), globalv.getmImageUrls(),globalv.getMrecords());
        recyclerView.setAdapter(adapter);
    }


    private void updateView(String lang) {
        Context context = LocalHelper.setLocale(this,lang);
        Resources resources = context.getResources();
        sister2.setText(resources.getString(R.string.sister));
        brother2.setText(resources.getString(R.string.brother));
        mother2.setText(resources.getString(R.string.mother));
        father2.setText(resources.getString(R.string.father));
        grandpa.setText(resources.getString(R.string.grandfather));
        grandma.setText(resources.getString(R.string.grandmother));
        aunt3.setText(resources.getString(R.string.auntt));
        aunt4.setText(resources.getString(R.string.aunt));
        uncle3.setText(resources.getString(R.string.unclee));
        uncle4.setText(resources.getString(R.string.uncle));
        cousb.setText(resources.getString(R.string.cousineboy));
        cousg.setText(resources.getString(R.string.cousinegirl));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.language_en){
            Paper.book().write("language","en");
            updateView((String)Paper.book().read("language"));
        }
        else  if(item.getItemId() == R.id.language_ar){
            Paper.book().write("language","ar");
            updateView((String)Paper.book().read("language"));
        }
        return true;
    }
    private void playMp3FromByte(byte[] mp3SoundByteArray) {
        try {
            File tempMp3 = File.createTempFile("kurchina", "mp3", getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();

            MediaPlayer mediaPlayer = new MediaPlayer();

            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
