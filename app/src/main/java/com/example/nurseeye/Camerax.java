package com.example.nurseeye;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class Camerax extends AppCompatActivity {
    PreviewView previewView;
    private StorageReference mStorageRef;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    ImageCapture imageCapture;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camerax);
        previewView = findViewById(R.id.previewView);
        mStorageRef = FirebaseStorage.getInstance().getReference();


        CameraX.initialize(this, Camera2Config.defaultConfig());
        PermissionManager pManager = new PermissionManager(this, this.getActivityResultRegistry());

        if(pManager.allPermissionGranted())
        {
            startCamera();
        }

    }
    public void startCamera()
    {
        //Solicitar el proveedor de la Camara
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {

            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, ContextCompat.getMainExecutor(this));

    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        cameraProvider.unbindAll();
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build();

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview, imageCapture);
        Log.i("MAINACTIVITY", camera.getCameraInfo().toString());
    }

    //cuando se abre la camara por primera vez pide de esta forma los permisos

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("PERMISSIONS", String.valueOf(requestCode));
        Log.i("PERMISSIONS", Arrays.toString(grantResults));
        Log.i("PERMISSIONS", Arrays.toString(permissions));
        startCamera();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private Executor getExecutor(){
        return ContextCompat.getMainExecutor(this);
    }
    String rutdelpaciente, llave = "rutdelpaciente";
    String nombreherida, key = "nombreherida";

    public void sacarfoto(View view) {

        String directorio =Environment.getExternalStorageDirectory().getPath();

        File fotodir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(!fotodir.exists())
            fotodir.mkdir();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());

        //String FFP = fotodir.getAbsolutePath() + "/" + currentDateandTime + ".jpg";
        String FFP = fotodir.getAbsolutePath() + "/fototemporal.jpg";
        File FotoFile = new File(FFP);



        imageCapture.takePicture(new ImageCapture.OutputFileOptions.Builder(FotoFile).build(),getExecutor(), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Bundle bundle = getIntent().getExtras();
                rutdelpaciente = bundle.getString(llave);
                nombreherida = bundle.getString(key);

                ///datosPacienteins/123456-7/resultados/pie derecho03-11-2021
                /*Uri file = Uri.fromFile(new File("/storage/emulated/0/Android/data/com.example.firebaseauth/files/Pictures/"+currentDateandTime+".jpg"));
                StorageReference riversRef = mStorageRef.child("Pictures/"+ currentDateandTime+ ".jpg");
                */
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();


                Uri file = Uri.fromFile(new File("/storage/self/primary/Android/data/com.example.firebaseauth/files/Pictures/fototemporal.jpg"));
                StorageReference riversRef = storageRef.child("images/"+rutdelpaciente+nombreherida+currentDateandTime);
                UploadTask uploadTask = riversRef.putFile(file);

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                    }
                });
                Toast.makeText(Camerax.this, "fotografia tomada correctamente",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Toast.makeText(Camerax.this, "erroR ERROR ERROR",Toast.LENGTH_LONG).show();
            }
        });

    }


    /*
    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(allPermissionsGranted()){
            startCamera();
        } else{
            Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private boolean allPermissionsGranted(){

        for(String permission : CAMERA_PERMISSION){

            if(ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }


    public void startCamera(){
        //ListenableFuture cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                // Camera provider is now guaranteed to be available
                ProcessCameraProvider cameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                // Set up the view finder use case to display camera preview
                Preview preview = new Preview.Builder().build();
                // Choose the camera by requiring a lens facing
                CameraSelector cameraSelector = new CameraSelector.Builder()

                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();
                //Images are processed by passing an executor in which the image analysis is run
                ImageAnalysis imageAnalysis =
                        new ImageAnalysis.Builder()
                                //set the resolution of the view
                                .setTargetResolution(new Size(1280, 720))
                                //the executor receives the last available frame from the camera at the time that the analyze() method is called
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build();

                // Connect the preview use case to the previewView
                preview.setSurfaceProvider(previewView.getSurfaceProvider());
                // Attach use cases to the camera with the same lifecycle owner
                Camera camera = cameraProvider.bindToLifecycle(
                        ((LifecycleOwner)this),
                        cameraSelector,
                        preview,
                        imageAnalysis);

            } catch (InterruptedException | ExecutionException e) {
                // Currently no exceptions thrown. cameraProviderFuture.get() should
                // not block since the listener is being called, so no need to

                // handle InterruptedException.
            }
        }, ContextCompat.getMainExecutor(this));
    }*/
}