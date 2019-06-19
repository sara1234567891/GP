package com.example.lenovo.hearingtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class TestData extends AppCompatActivity {

    private final int[] testingFrequencies = {1000, 500, 1000, 3000, 4000, 6000, 8000};
    public final static String DESIRED_FILE = "com.example.lenovo.hearingtest.DESIRED_FILE";

    public void gotoExport(View view, String string) {
        Intent exportIntent = new Intent(this, ExportData.class);
        exportIntent.putExtra(DESIRED_FILE, string);
        startActivity(exportIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_data);

        Intent intent = getIntent();
        final String fileName = intent.getStringExtra(TestLookup.DESIRED_FILE);

        String[] names = fileName.split("-");
        String fileNameLeft = names[0] + "-Left-" + names[2] + "-" + names[3];
        String time = "";
        for (int j=0;j<4;j = j + 2){
            if (j != 2){
                time += String.valueOf(names[3].charAt(j)) + String.valueOf(names[3].charAt(j+1)) + ":";
            } else {
                time += String.valueOf(names[3].charAt(j)) + String.valueOf(names[3].charAt(j+1));
            }
        }
        String name = "Test at " +time + ", " + names[2].replaceAll("_", ".");

        Button b = (Button) findViewById(R.id.email_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoExport(view, fileName);
            }
        });

        TextView title = (TextView) findViewById(R.id.test_title);
        title.setText(name);

        byte testResultsRightByte[] = new byte[7*8];

        try{
            FileInputStream fis = openFileInput(fileName);
            fis.read(testResultsRightByte, 0, testResultsRightByte.length);
            fis.close();
        } catch (IOException e) {};

        byte testResultsLeftByte[] = new byte[7*8];

        try{
            FileInputStream fis = openFileInput(fileNameLeft);
            fis.read(testResultsLeftByte, 0, testResultsLeftByte.length);
            fis.close();
        } catch (IOException e) {};


        final double testResultsRight[] = new double[7];
        final double testResultsLeft[] = new double[7];

        int counter = 0;

        for (int i = 0; i < testResultsRight.length; i++){
            byte tmpByteBuffer[] = new byte[8];
            for (int j = 0; j < 8; j++) {
                tmpByteBuffer[j] = testResultsRightByte[counter];
                counter++;
            }
            testResultsRight[i] = ByteBuffer.wrap(tmpByteBuffer).getDouble();
        }

        counter = 0;

        for (int i = 0; i < testResultsLeft.length; i++){
            byte tmpByteBuffer[] = new byte[8];
            for (int j = 0; j < 8; j++) {
                tmpByteBuffer[j] = testResultsLeftByte[counter];
                counter++;
            }
            testResultsLeft[i] = ByteBuffer.wrap(tmpByteBuffer).getDouble();
        }

        // Draw Graph
        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setNoDataTextDescription("Whoops! No data was found. Try again!");
        chart.setDescription("Hearing Thresholds (dB HL)");
        ArrayList<Entry> dataLeft = new ArrayList<Entry>();
        for (int i = 0; i < testResultsLeft.length; i ++){
            Entry dataPoint = new Entry((float) testResultsLeft[i] , i);
            dataLeft.add(dataPoint);
        }
        LineDataSet setLeft = new LineDataSet(dataLeft, "Left");
        ArrayList<Entry> dataRight = new ArrayList<Entry>();
        for (int i = 0; i < testResultsRight.length; i ++){
            Entry dataPoint = new Entry((float) testResultsRight[i] , i);
            dataRight.add(dataPoint);
        }
        LineDataSet setRight = new LineDataSet(dataRight, "Right");
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setLeft);
        dataSets.add(setRight);
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < testingFrequencies.length; i++){
            xVals.add("" + testingFrequencies[i]);
        }
        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);
        chart.invalidate(); // refresh
    }
}