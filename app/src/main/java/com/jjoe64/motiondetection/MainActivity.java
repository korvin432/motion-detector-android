package com.jjoe64.motiondetection;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.Image;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import com.jjoe64.motiondetection.motiondetection.MotionDetector;
import com.jjoe64.motiondetection.motiondetection.MotionDetectorCallback;

import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    private TextView txtStatus, txtStatus2;
    private MotionDetector motionDetector;
    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");

        //ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap1 = BitmapFactory.decodeFile(mediaStorageDir.getPath() + File.separator +
                "1" + ".jpg");
        Bitmap bitmap2 = BitmapFactory.decodeFile(mediaStorageDir.getPath() + File.separator +
                "2" + ".jpg");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtStatus2 = (TextView) findViewById(R.id.txtStatus2);

        motionDetector = new MotionDetector(this, (SurfaceView) findViewById(R.id.surfaceView));
        motionDetector.setMotionDetectorCallback(new MotionDetectorCallback() {

            @Override
            public void onMotionDetected() {
                total++;
                //Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                //v.vibrate(80);
                txtStatus.setText("Motion detected");
                txtStatus2.setText("Total images: " + total);

            }

            @Override
            public void onTooDark() {
                txtStatus.setText("Too dark here");
            }
        });

        OpenActivity open = new OpenActivity();

        Intent intent = getIntent();

        ////// Config Options
        motionDetector.setCheckInterval(intent.getIntExtra("check_interval", 290));
        motionDetector.setLeniency(intent.getIntExtra("leniency", 3));
        motionDetector.setMinLuma(intent.getIntExtra("min_luma", 1000));


    }

    boolean imagesAreEqual(Bitmap i1, Bitmap i2)
    {
        int c = 0;
        //if (i1.getHeight() != i2.getHeight()) return false;
        //if (i1.getWidth() != i2.getWidth()) return false;

        for (int y = 0; y < i1.getHeight(); ++y) {
            for (int x = 0; x < i1.getWidth(); ++x)
                if (i1.getPixel(x, y) != i2.getPixel(x, y)) {
                    c++;
                }
        }
            if (c >= 150000) {
                return false;
            } else {
                return true;
            }

    }


    @Override
    protected void onResume() {
        super.onResume();
        motionDetector.onResume();

        if (motionDetector.checkCameraHardware()) {
            txtStatus.setText("Camera found");
        } else {
            txtStatus.setText("No camera available");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        motionDetector.onPause();
    }

}
