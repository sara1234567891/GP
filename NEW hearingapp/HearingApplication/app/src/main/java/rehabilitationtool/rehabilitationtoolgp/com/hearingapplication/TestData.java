package rehabilitationtool.rehabilitationtoolgp.com.hearingapplication;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;

public class TestData extends AppCompatActivity {

    public final static String DESIRED_FILE = "ut.ewh.audiometrytest.DESIRED_FILE";
    private final int[] testingFrequencies = {60, 230, 60, 910, 3600, 14000, 16000};
    SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yyyy-HHmmss");
    String currentDateTime = sdf.format(new Date());

    public void gotoExport(View view, String string) {
        Intent exportIntent = new Intent(this, ExportData.class);
        exportIntent.putExtra(DESIRED_FILE, string);
        startActivity(exportIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

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
        String name = "وقت الاختبار " +time + ", " + names[2].replaceAll("_", ".");

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


        TableLayout tableResults = (TableLayout) findViewById(R.id.tableResults);
        tableResults.setPadding(15, 3, 15, 3);


        for (int i = 0; i < 7; i ++) {
            TableRow row = new TableRow(this);
            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            row.setPadding(15, 3, 15, 3);
            row.setBackgroundColor(Color.parseColor("#FF9DCEC1"));
            TextView Values = new TextView(this);
            Values.setPadding(15, 0, 15, 0);
            Values.setGravity(Gravity.LEFT);
            Values.setTextSize(25.0f);
            Values.setTextColor(Color.parseColor("#FFFFFF"));
            Values.setText(testingFrequencies[i] + " Hz: " + String.format("%.2f", testResultsLeft[i]) + "db HL Left");
            row.addView(Values);
            tableResults.addView(row);

            TableRow row2 = new TableRow(this);
            row2.setLayoutParams(lp);
            row2.setPadding(15, 3, 15, 3);
            row2.setBackgroundColor(Color.parseColor("#FF9DCEC1"));
            TextView Values2 = new TextView(this);
            Values2.setPadding(15, 0, 15, 0);
            Values2.setGravity(Gravity.LEFT);
            Values2.setTextSize(25.0f);
            Values2.setTextColor(Color.parseColor("#FFFFFF"));
            Values2.setText(testingFrequencies[i] + " Hz: " + String.format("%.2f", testResultsRight[i]) + "db HL Right");
            row2.addView(Values2);
            tableResults.addView(row2);
        }





    }




}

