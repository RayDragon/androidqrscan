package com.example.ray.camera2;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import java.io.IOException;
import java.util.jar.Manifest;

public class MainActivity extends Activity {
    SurfaceView cameraView;
    TextView barcodeInfo,items;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);



          cameraView = (SurfaceView)findViewById(R.id.camera_view);
          barcodeInfo = (TextView)findViewById(R.id.code_info);
          items = (TextView)findViewById(R.id.tv2);


          barcodeDetector =
                  new BarcodeDetector.Builder(this)
                          .setBarcodeFormats(Barcode.QR_CODE)
                          .build();
          cameraSource = new CameraSource
                  .Builder(this, barcodeDetector)
                  .setRequestedPreviewSize(500, 500)
                  .setAutoFocusEnabled(true)
                  .build();


          cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
              @Override
              public void surfaceCreated(SurfaceHolder holder) {
                        try {
                            cameraSource.start(cameraView.getHolder());
                        } catch (IOException ie) {
                            Log.e("CAMERA SOURCE", ie.getMessage());
                        }
                    }

              @Override
              public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
              }

              @Override
              public void surfaceDestroyed(SurfaceHolder holder) {

                  cameraSource.stop();
              }
          });




          barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
              @Override
              public void release() {
              }

              @Override
              public void receiveDetections(Detector.Detections<Barcode> detections) {
                  final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                  if (barcodes.size() != 0) {
                      barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                          public void run() {
                              if(!barcodeInfo.getText().toString().contentEquals(barcodes.valueAt(0).displayValue)) {
                                  barcodeInfo.setText(    // Update the TextView
                                          barcodes.valueAt(0).displayValue
                                  );

                                  items.setText(
                                          barcodes.valueAt(0).displayValue
                                                  + "\n" +
                                                  items.getText().toString()
                                  );
                              }
                          }
                      });
                  }
              }
          });

          //----------my functions
          //1. caliber
          ImageButton bt=(ImageButton)findViewById(R.id.imageButton);
          bt.setOnClickListener(
                  new OnClickListener()
                  {

                        @Override
                        public void onClick(View v)
                        {

                        }

                  });


      }


    }
