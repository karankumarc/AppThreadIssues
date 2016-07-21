package com.pluralsight.karan.appthreadissues;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_WRITES = 5;

    /**
     * Starting point of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void btnWriteToFileOnClick(View view){

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



}
