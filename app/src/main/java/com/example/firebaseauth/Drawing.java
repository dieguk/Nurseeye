package com.example.firebaseauth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Drawing extends AppCompatActivity {

    private static final String TAG = "value";
    ImageView imagedrawing;
    RelativeLayout relativeLayout;
    int take_image = 0;

    int A,Rs,G,B;
    Paint paint;
    View view;
    Path path2;
    Bitmap bitmap;
    Canvas canvas;
    Button button, boton2;
    double Valorpixel;
    int valorperimetro;
    int Count =0, count1 = 0, count2 = 0;
    Bitmap bitmap2 = BitmapFactory.decodeFile("/storage/self/primary/Android/data/com.example.firebaseauth/files/Pictures/fototemporal.jpg");
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        imagedrawing = findViewById(R.id.imagedrawing);


       // imagedrawing.setImageBitmap(Bitmap.createScaledBitmap(bitmap,720,480,false));
       // imagedrawing.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        //imagedrawing.setScaleType(ImageView.ScaleType.FIT_XY);
        imagedrawing.setImageBitmap(bitmap2);

        //scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        Bundle bundle = getIntent().getExtras();
        Valorpixel = bundle.getDouble("valor pixel");
        Log.i(TAG, String.valueOf("size: " + Valorpixel + ", " ));
        Toast.makeText(this, "valor de pixel" + Valorpixel, Toast.LENGTH_SHORT).show();

        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout1);

        button = (Button)findViewById(R.id.button);


        view = new SketchSheetView(Drawing.this);

        paint = new Paint();

        path2 = new Path();

        relativeLayout.addView(view, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        paint.setDither(true);

        paint.setColor(Color.parseColor("#FFffffff"));

        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeJoin(Paint.Join.ROUND);

        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(2);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                take_image = take_picture_function_rgb(take_image,bitmap);
                path2.reset();
                if (take_image ==0){
                    take_image = 1;
                }
                else{
                    take_image =0;
                }

                if (DrawingClassArrayList.size()  != 0){
                    DrawingClassArrayList.clear();
                }

                int contadorblancos = 0;
                int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];

                bitmap.getPixels(pixels,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());

                for (int i = 0; i < pixels.length; i++) {
                    if (pixels[i] == 0xFFffffff) {
                        contadorblancos++;


                    }

                }
                 double perimetro = Valorpixel * contadorblancos;
                /*
                if(contadorblancos>1) {
                    //Toast.makeText(Drawing.this, "pixeles blancos = " + perimetro, Toast.LENGTH_SHORT).show();

                    CameraCharacteristics.Key<SizeF> cameraCharacteristics = CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE;
                    SizeF.parseSizeF(String.valueOf(cameraCharacteristics)).getHeight();
                    SizeF.parseSizeF(String.valueOf(cameraCharacteristics)).getWidth();

                    int cameraatras = CameraCharacteristics.LENS_FACING_BACK;


                    Camera camera = null;
                    Camera.Parameters p = camera.getParameters();
                    double thetaV = Math.toRadians(p.getVerticalViewAngle());
                    double thetaH = Math.toRadians(p.getHorizontalViewAngle());
                    String size = p.get(String.valueOf(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE));
                    double focalLength = p.getFocalLength();


                    double sensorwidith = (Math.tan(thetaH/2)*2*focalLength);
                    double sensorvertical = (Math.tan(thetaV/2)*2*focalLength);


                    Toast.makeText(Drawing.this, "pixeles blancos = " + sensorwidith + sensorvertical, Toast.LENGTH_SHORT).show();

                    CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String[] cameraIds = manager.getCameraIdList();
                        if (cameraIds.length == 1) {
                            CameraCharacteristics character = manager.getCameraCharacteristics(cameraIds[0]);
                            SizeF porte = character.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
                        }
                    }
                    catch (CameraAccessException e)
                    {
                        Log.e("YourLogString", e.getMessage(), e);
                    }

                    /*Camera camera = null;
                    Camera.Parameters p = camera.getParameters();
                    double thetaV = Math.toRadians(p.getVerticalViewAngle());
                    double thetaH = Math.toRadians(p.getHorizontalViewAngle());
                    String size = p.get(String.valueOf(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE));
                    double focalLength = p.getFocalLength();
                    double horizontalViewAngle = p.getHorizontalViewAngle();
                    double verticalViewAngle = p.getVerticalViewAngle();*/




             /*
               int index = 0;
                int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];

                bitmap.getPixels(pixels,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
                for (int x = 0; x < bitmap.getWidth(); x++) {
                    for (int y = 0; y < bitmap.getHeight(); y++) {

                       /* A = (pixels[index] >> 24) & 0xFF;
                        Rs = (pixels[index] >> 16) & 0xFF;
                        G = (pixels[index] >> 8) & 0xFF;
                        B = pixels[index] & 0xFF;
                        ++index;

                    }
                }*/




            }


        });
    }


    public void get_area () {
        Camera camera = null;
        Camera.Parameters p = camera.getParameters();
        double thetaV = Math.toRadians(p.getVerticalViewAngle());
        double thetaH = Math.toRadians(p.getHorizontalViewAngle());
        String size = p.get(String.valueOf(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE));
        double focalLength = p.getFocalLength();
        double horizontalViewAngle = p.getHorizontalViewAngle();
        double verticalViewAngle = p.getVerticalViewAngle();
        Toast.makeText(Drawing.this, "pixeles blancos = " + size, Toast.LENGTH_SHORT).show();


    }

    private int take_picture_function_rgb(int take_image, Bitmap bitmap) {
        if (take_image ==1){

            Mat save_mat = new Mat();
            Mat src = new Mat();
            Utils.bitmapToMat(bitmap, src);

            Imgproc.cvtColor(src,save_mat,Imgproc.COLOR_RGBA2BGRA);

            File folder = new File("/storage/self/primary/Android/data/com.example.firebaseauth/files/Pictures");

            boolean succes = true;
            if(!folder.exists()){
                succes = folder.mkdirs();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String currentDateandTime = sdf.format(new Date(1));
            String Filename = "/storage/self/primary/Android/data/com.example.firebaseauth/files/Pictures/fotocontorno.jpg";
            Imgcodecs.imwrite(Filename,save_mat);
            take_image = 0;
          //  Intent intent = new Intent(this,Drawing.class);

            /*intent.putExtra("valor pixel",valorint);
            startActivity(intent);*/
        }

        return take_image;
    }
  /*  @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            imagedrawing.setScaleX(mScaleFactor);
            imagedrawing.setScaleY(mScaleFactor);
            return true;
        }
    }*/
    public ArrayList<DrawingClass> DrawingClassArrayList = new ArrayList<DrawingClass>();

    class SketchSheetView extends View {

        public SketchSheetView(Context context) {

            super(context);
            //Bitmap bitmap = BitmapFactory.decodeFile("/storage/self/primary/Android/data/com.example.firebaseauth/files/Pictures/fototemporal.jpg");
            bitmap = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(), Bitmap.Config.ARGB_4444);

            canvas = new Canvas(bitmap);

            //this.setBackgroundColor(Color.WHITE);
        }




        @Override
        public boolean onTouchEvent(MotionEvent event) {

            DrawingClass pathWithPaint = new DrawingClass();

            canvas.drawPath(path2, paint);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                count1++;

                path2.moveTo(event.getX(), event.getY());

                path2.lineTo(event.getX(), event.getY());


            }
            else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                count2++;

                path2.lineTo(event.getX(), event.getY());

                pathWithPaint.setPath(path2);

                pathWithPaint.setPaint(paint);

                DrawingClassArrayList.add(pathWithPaint);

            }


            invalidate();
            return true;
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (DrawingClassArrayList.size() > 0) {
                Count ++;

                //Toast.makeText(Drawing.this, "pase por aqui ,./.."+ DrawingClassArrayList.size(), Toast.LENGTH_SHORT).show();
                canvas.drawPath(
                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPath(),

                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPaint());

            }
        }


    }
    public int largo = DrawingClassArrayList.size();
 /*
    public void medir(View view) {
        Valores Pperimetro = new Valores();
        int vp = Integer.parseInt(Valorpixel);

        Pperimetro.CalculoPerimetro(largo,vp);
        valorperimetro= Pperimetro.perimetro;


    }*/
    public class DrawingClass {

        Path DrawingClassPath;
        Paint DrawingClassPaint;

        public Path getPath() {
            return DrawingClassPath;
        }

        public void setPath(Path path) {
            this.DrawingClassPath = path;
        }


        public Paint getPaint() {
            return DrawingClassPaint;
        }

        public void setPaint(Paint paint) {
            this.DrawingClassPaint = paint;
        }
    }


}