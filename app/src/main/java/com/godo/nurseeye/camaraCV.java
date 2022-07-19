package com.godo.nurseeye;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class camaraCV extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String TAG="MainActivity";
    double Vpix ;
    double Dpix;
    Mat mRGBA;
    Mat mRGBAT;
    int take_image = 0;
    String rutdelpaciente, llave = "rutdelpaciente";
    String nombreherida, key = "nombreherida";
    CameraBridgeViewBase cameraBridgeViewBase;

    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface.SUCCESS:{
                    Log.i(TAG,"onmanagerConected: opencv loadded");
                    cameraBridgeViewBase.enableView();

                }
            }
            super.onManagerConnected(status);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(camaraCV.this,new String[]{Manifest.permission.CAMERA}, 1);
        ActivityCompat.requestPermissions(camaraCV.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(camaraCV.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        setContentView(R.layout.activity_camara_cv);
        cameraBridgeViewBase = (CameraBridgeViewBase) findViewById(R.id.camaracv);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);
        Bundle bundle = getIntent().getExtras();
        rutdelpaciente = bundle.getString(llave);
        nombreherida = bundle.getString(key);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:{
                if (grantResults.length> 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    cameraBridgeViewBase.setCameraPermissionGranted();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(OpenCVLoader.initDebug()){
            Log.d(TAG,"onresume: opencv iniciado");
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else
        {
            Log.d(TAG,"onresume: opencv no iniciado ");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION,this,baseLoaderCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(cameraBridgeViewBase !=null);
        cameraBridgeViewBase.disableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cameraBridgeViewBase !=null);
        cameraBridgeViewBase.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRGBA= new Mat(height,width, CvType.CV_8UC4);
        mRGBAT= new Mat(height,width, CvType.CV_8UC1);


    }

    @Override
    public void onCameraViewStopped() {
        mRGBA.release();

    }


    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
       // mRGBA = inputFrame.rgba();
       // mRGBA = inputFrame.gray();
        mRGBA = inputFrame.rgba();
        Mat mRgbaT = mRGBA.t();
        //Core.flip(mRGBA.t(), mRgbaT, 1);
        Imgproc.resize(mRgbaT, mRgbaT, mRGBA.size());


        Mat input = inputFrame.gray();
        Mat circles = new Mat();
        Imgproc.blur(input, input, new Size(7, 7), new Point(2, 2));
        Imgproc.HoughCircles(input, circles, Imgproc.CV_HOUGH_GRADIENT, 2, 100, 100, 90, 0, 1000);

        Log.i(TAG, String.valueOf("size: " + circles.cols()) + ", " + String.valueOf(circles.rows()));

        if (circles.cols() > 0) {
            //modificacion de cantidad de circuos detectados en el ultimo valor
            for (int x=0; x < Math.min(circles.cols(), 1); x++ ) {
                double circleVec[] = circles.get(0, x);

                if (circleVec == null) {
                    break;
                }

                Point center = new Point((int) circleVec[0], (int) circleVec[1]);
                int radius = (int) circleVec[2];
                double radio = circleVec[2];
                double area = ((radio * radio)* 3.141519);
                double area2 = area;

                Imgproc.circle(input, center, 3, new Scalar(255, 255, 255), 5);
                Imgproc.circle(input, center, radius, new Scalar(0, 255, 0), 2);
                //Imgproc.putText(input, "el area es "+ area2,center,4,0.5,new Scalar(0,0,0),2);





                Vpix = 57.0/ area2;
                Dpix = 2 * radius;
                 //Vpix = 1.35/ radio;




            }
        }

        circles.release();
        input.release();
        Mat RGBA = inputFrame.rgba();

        take_image = take_picture_function_rgb(take_image,RGBA);
        return inputFrame.rgba();


    }
    public void fotoopencv(View view) {
        if (take_image ==0){
            take_image = 1;
        }
        else{
            take_image =0;
        }


    }
    private int take_picture_function_rgb(int take_image, Mat RGBA) {
        if (take_image ==1){
            Mat mRgbaT = RGBA.t();
            Mat save_mat = new Mat();
            Size sz = new Size(720,480);

            // Imgproc.resize(save_mat.t(), mRgbaT, sz);

            Imgproc.resize(mRgbaT, save_mat,sz);
            Imgproc.cvtColor(RGBA,save_mat,Imgproc.COLOR_RGBA2BGRA);
            //Core.flip(RGBA.t(), mRgbaT, 1);
            File fotodir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if(!fotodir.exists())
                fotodir.mkdir();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String currentDateandTime = sdf.format(new Date(1));
            String Filename = "/storage/self/primary/Android/data/com.godo.nurseeye/files/Pictures/fototemporal.jpg";


            Imgcodecs.imwrite(Filename,save_mat);
            take_image = 0;



            Intent intent = new Intent(this,Drawing.class);

            String valorint = Double.toString(Vpix);

            intent.putExtra("valor pixel",Vpix);
            intent.putExtra("valor diametro", Dpix);
            intent.putExtra("rutdelpaciente", rutdelpaciente);
            intent.putExtra("nombreherida", nombreherida);

            startActivity(intent);
        }



        return take_image;
    }


}