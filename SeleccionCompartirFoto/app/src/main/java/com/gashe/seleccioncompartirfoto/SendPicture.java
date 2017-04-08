package com.gashe.seleccioncompartirfoto;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SendPicture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_picture);

        Uri uri = getIntent().getData();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        // para todos los tipos de imagenes
        //shareIntent.setType("image/*");

        startActivity(Intent.createChooser(shareIntent, "Enviar foto con ..."));
    }
}
