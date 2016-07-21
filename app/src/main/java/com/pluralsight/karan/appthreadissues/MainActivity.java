package com.pluralsight.karan.appthreadissues;

import android.content.DialogInterface;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_WRITES = 5;


    LocationListener mLocationListener;

    /**
     * Starting point of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.enableDefaults();
    }

    public void btnWriteToFileOnClick(View view){
        FileOutputStream outStream = openOutStream("testout.dat");
        for (int i = 0; i < MAX_WRITES; i++) {
            simpleWrite( outStream, "Hello World");
        }
        closeOutStream(outStream);
    }

    public void btnStartLocationMonitoringOnClick(View view) {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationListener = new MyLocationListener();
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0.0f, mLocationListener);
    }

    public void btnWriteToFileAsyncTask(View view){
        final String messageToWrite = "Message to write to file";

        final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBarMain);
        initializeProgressBar(pb);
        displayStartedMessage();

        new AsyncTask<String, Integer, Void>(){

            @Override
            protected Void doInBackground(String... strings) {
                FileOutputStream outStream = openOutStream("testout.dat");
                for (int i = 0; i < MAX_WRITES; i++) {
                    slowWrite(outStream, strings[0]);
                    publishProgress(i+1);
                }
                closeOutStream(outStream);
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                pb.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                displayCompletionMessage();
                cleanupProgressBar(pb);
            }
        }.execute(messageToWrite);
    }

    protected void initializeProgressBar(ProgressBar pb) {
        pb.setMax(MAX_WRITES);
        pb.setProgress(0);
        pb.setVisibility(View.VISIBLE);
    }

    protected void displayStartedMessage() {
        Toast.makeText(this, "File operation started", Toast.LENGTH_SHORT).show();
    }

    protected void cleanupProgressBar(ProgressBar pb) {
        pb.setProgress(0);
    }

    protected void displayCompletionMessage() {
        AlertDialog.Builder bldr = new AlertDialog.Builder(MainActivity.this);
        bldr.setTitle("Write to File")
                .setMessage("File operation complete")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private FileOutputStream openOutStream(String filename) {
        FileOutputStream outputStream = null;

        try{
            outputStream = openFileOutput(filename, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return outputStream;
    }

    protected void closeOutStream(FileOutputStream outStream){
        try{
            if(outStream != null){
                outStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void simpleWrite(FileOutputStream outStream, String buffer){
        try{
            outStream.write(buffer.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void slowWrite(FileOutputStream outStream, String buffer){
        try{
            outStream.write(buffer.getBytes());
            Thread.sleep(1500);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
