package com.rehabilitationtoolgp.rehabilitationtool;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TRANSPORTATION extends AppCompatActivity {
    private static final String TAG = "TRANSPORTATION";
    Globalrecycler globalv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);

       Button metro = (Button) findViewById(R.id.metro);
        Button car = (Button) findViewById(R.id.car);
        Button taxi = (Button) findViewById(R.id.taxi);
        Button bus = (Button) findViewById(R.id.autobus);
        Button bike = (Button) findViewById(R.id.bike);
        Button plane = (Button) findViewById(R.id.plane);
        Button ship = (Button) findViewById(R.id.ship);
        Button train = (Button) findViewById(R.id.train);
        Button play = (Button) findViewById(R.id.playall);
        Button back = (Button) findViewById(R.id.back);


        globalv=(Globalrecycler)getApplicationContext();
        initRecyclerView();



        //METRO
        final MediaPlayer metroplayer = MediaPlayer.create(TRANSPORTATION.this,R.raw.metrooo);
        metro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metroplayer.start();
                globalv.addmImageUrls(R.drawable.metrooo);
                globalv.addmNames("مترو");
                globalv.addMrecords(R.raw.metrooo);
                initRecyclerView();



            }
        });

        //CAR
        final MediaPlayer carplayer = MediaPlayer.create(TRANSPORTATION.this,R.raw.carrr);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carplayer.start();
                globalv.addmImageUrls(R.drawable.car);
                globalv.addmNames("عربية");
                globalv.addMrecords(R.raw.carrr);
                initRecyclerView();



            }
        });


        //TAXI
        final MediaPlayer taxiplayer = MediaPlayer.create(TRANSPORTATION.this,R.raw.taxiii);
        taxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taxiplayer.start();
                globalv.addmImageUrls(R.drawable.taxii);
                globalv.addmNames("تاكسي");
                globalv.addMrecords(R.raw.taxiii);
                initRecyclerView();



            }
        });


        //AUTOBUS
        final MediaPlayer autobusplayer = MediaPlayer.create(TRANSPORTATION.this,R.raw.busss);
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autobusplayer.start();
                globalv.addmImageUrls(R.drawable.busss);
                globalv.addmNames("أوتوبيس");
                globalv.addMrecords(R.raw.busss);
                initRecyclerView();



            }
        });


        //BIKE
        final MediaPlayer bikeplayer = MediaPlayer.create(TRANSPORTATION.this,R.raw.bicycleee);
        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bikeplayer.start();
                globalv.addmImageUrls(R.drawable.bicycle);
                globalv.addmNames("عجلة");
                globalv.addMrecords(R.raw.bicycleee);
                initRecyclerView();



            }
        });


        //PLANE
        final MediaPlayer planeplayer = MediaPlayer.create(TRANSPORTATION.this,R.raw.planeee);
        plane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planeplayer.start();
                globalv.addmImageUrls(R.drawable.plane);
                globalv.addmNames("طيارة");
                globalv.addMrecords(R.raw.planeee);
                initRecyclerView();



            }
        });

        //SHIP
        final MediaPlayer shipplayer = MediaPlayer.create(TRANSPORTATION.this,R.raw.shippp);
        ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shipplayer.start();
                globalv.addmImageUrls(R.drawable.ship);
                globalv.addmNames("سفينة");
                globalv.addMrecords(R.raw.shippp);
                initRecyclerView();



            }
        });

        //TRAIN
        final MediaPlayer trainplayer = MediaPlayer.create(TRANSPORTATION.this,R.raw.trainnn);
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trainplayer.start();
                globalv.addmImageUrls(R.drawable.train);
                globalv.addmNames("قطار");
                globalv.addMrecords(R.raw.trainnn);
                initRecyclerView();



            }
        });


        //PLAY ALL
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i=0;i<  globalv.getMrecords().size();i++) {
                    final MediaPlayer mediaPlay = MediaPlayer.create(TRANSPORTATION.this, globalv.getMrecords().get(i));
                    mediaPlay.start();
                    try {
                        Thread.sleep(1000);
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

                Intent intent1 = new Intent(TRANSPORTATION.this,MainActivity.class);
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
}
