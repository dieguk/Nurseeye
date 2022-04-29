package com.example.firebaseauth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Drawing extends AppCompatActivity {

    ImageView imagedrawing;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        imagedrawing = findViewById(R.id.imagedrawing);
        Bitmap bitmap = BitmapFactory.decodeFile("/storage/self/primary/Android/data/com.example.firebaseauth/files/Pictures/fototemporal.jpg");
        imagedrawing.setImageBitmap(bitmap);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            imagedrawing.setScaleX(mScaleFactor);
            imagedrawing.setScaleY(mScaleFactor);
            return true;
        }
    }



}