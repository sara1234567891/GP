package com.example.lenovo.hearingtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ExportData extends AppCompatActivity {

    Button doneButton;
    EditText email_field;
    String email = "no email has yet been entered into this ridiculously long initialized field";

    public void gotoExportComplete() {
        Intent intent = new Intent(this, ExportComplete.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_data);

        Intent intent = getIntent();
        final String fileName = intent.getStringExtra(TestData.DESIRED_FILE);

        byte testResultsRightByte[] = new byte[7*8];

        try{
            FileInputStream fis = openFileInput(fileName);
            fis.read(testResultsRightByte, 0, testResultsRightByte.length);
            fis.close();
        } catch (IOException e) {};


        final double testResultsRight[] = new double[7];

        int counter = 0;

        for (int i = 0; i < testResultsRight.length; i++){
            byte tmpByteBuffer[] = new byte[8];
            for (int j = 0; j < 8; j++) {
                tmpByteBuffer[j] = testResultsRightByte[counter];
                counter++;
            }
            testResultsRight[i] = ByteBuffer.wrap(tmpByteBuffer).getDouble();
        }


        doneButton = (Button)findViewById(R.id.doneButton);
        email_field = (EditText)findViewById(R.id.email_address);
        /**
         * Takes entered email address and sends test results to that email
         */
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                email = email_field.getText().toString();
                Thread networkThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            String URL = "http://reecestevens.me/mail.php?";
                            URL += "t=" + email;
                            URL += "&s=" + "test";
                            URL += "&b=" + "Thresholds+for+test+" + fileName + "+:+[1000+Hz]+" + testResultsRight[0] + "+[500+Hz]+" + testResultsRight[1] + "+[1000+Hz+Repeated]+" + testResultsRight[2] + "+[3000+Hz]+" + testResultsRight[3] + "+[4000+Hz]+" + testResultsRight[4] + "+[6000+Hz]+" + testResultsRight[5] + "+[8000+Hz]+" + testResultsRight[6] + ".";
                            HttpResponse response = httpclient.execute(new HttpGet(URL));
                            StatusLine statusLine = response.getStatusLine();
                            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                                ByteArrayOutputStream out = new ByteArrayOutputStream();
                                response.getEntity().writeTo(out);
                                out.close();
                                String responseString = out.toString();
                            } else {
                                response.getEntity().getContent().close();
                                throw new IOException(statusLine.getReasonPhrase());
                            }
                            gotoExportComplete();
                        } catch (Exception e) {
                            System.err.println(e);
                           // gotoExportError();
                        }

                    }
                });
                networkThread.start();
            }
        });
    }

}
