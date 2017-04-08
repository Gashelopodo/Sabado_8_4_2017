package com.gashe.subirfoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = null;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.url);
        /*UploadImage uploadImage = new UploadImage(this);
        uploadImage.execute(bitmap);*/
        UploadImageWithTime uploadImageWithTime = new UploadImageWithTime(this);
        uploadImageWithTime.execute(bitmap);

    }
}
