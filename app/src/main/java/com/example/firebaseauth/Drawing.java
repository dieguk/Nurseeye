package com.example.firebaseauth;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    int validador = 0;
    int A,Rs,G,B;
    Paint paint;
    View view;
    Path path2;
    Bitmap bitmap;
    Canvas canvas;
    Button button, boton2;
    double area;

    double Valorpixel, valordiametro;
    int valorperimetro;
    int Count =0, count1 = 0, count2 = 0;
    Bitmap bitmap2 = BitmapFactory.decodeFile("/storage/self/primary/Android/data/com.example.firebaseauth/files/Pictures/fototemporal.jpg");
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    String rutdelpaciente, llave = "rutdelpaciente";
    String nombreherida, key = "nombreherida";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        imagedrawing = findViewById(R.id.imagedrawing);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

       // imagedrawing.setImageBitmap(Bitmap.createScaledBitmap(bitmap,720,480,false));
       // imagedrawing.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        //imagedrawing.setScaleType(ImageView.ScaleType.FIT_XY);
        imagedrawing.setImageBitmap(bitmap2);

        //scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        Bundle bundle = getIntent().getExtras();
        Valorpixel = bundle.getDouble("valor pixel");
        valordiametro = bundle.getDouble("valor diametro");
        rutdelpaciente = bundle.getString(llave);
        nombreherida =bundle.getString(key);

        Log.i(TAG, String.valueOf("size: " + Valorpixel + ", " ));
        Toast.makeText(this, "valor de pixel" + Valorpixel + "diametro" + valordiametro, Toast.LENGTH_SHORT).show();

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


        paint.setStrokeWidth(1);




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
                double valorperimetro = 27/ valordiametro * contadorblancos;
                double vdiametromedido= valorperimetro/3.1415;
                double radio = vdiametromedido /2 ;
                area = ((radio * radio) * 3.1415);
                Toast.makeText(Drawing.this, "el area en MM es = " + area, Toast.LENGTH_SHORT).show();

            }


        });
    }

    public void medir(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Drawing.this);
        builder.setTitle("Herida medida exitosamente");
        builder.setMessage("¿esta satisfecha con la medicion?").setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();


                Uri file = Uri.fromFile(new File("/storage/self/primary/Android/data/com.example.firebaseauth/files/Pictures/fototemporal.jpg"));
                StorageReference riversRef = storageRef.child("images/" + rutdelpaciente + nombreherida + currentDateandTime);
                UploadTask uploadTask = riversRef.putFile(file);
                Intent intent = new Intent(Drawing.this, EscalasanElian.class);
                intent.putExtra("rutdelpaciente", rutdelpaciente);
                intent.putExtra("nombreherida", nombreherida);
                intent.putExtra("areacv",area);
                startActivity(intent);
                Toast.makeText(Drawing.this, "Paciente registrado correctamente", Toast.LENGTH_LONG).show();


                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Intent intent = new Intent(Drawing.this, camaraCV.class);
                        startActivity(intent);
                        Toast.makeText(Drawing.this, "Error al guardar Datos", Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
            }
        }).setNegativeButton("volver a medir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Drawing.this, camaraCV.class);
                startActivity(intent);
                Toast.makeText(Drawing.this, "Error al guardar Datos", Toast.LENGTH_LONG).show();
            }
        }).show();




    }


    /*
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


    }*/

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

    int ancho = bitmap2.getWidth();
    int largo = bitmap2.getHeight();
    public ArrayList<DrawingClass> DrawingClassArrayList = new ArrayList<DrawingClass>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String currentDateandTime = sdf.format(new Date());









    class SketchSheetView extends View {

        public SketchSheetView(Context context) {

            super(context);
            //Bitmap bitmap = BitmapFactory.decodeFile("/storage/self/primary/Android/data/com.example.firebaseauth/files/Pictures/fototemporal.jpg");
            bitmap = Bitmap.createBitmap(ancho,largo, Bitmap.Config.ARGB_4444);

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