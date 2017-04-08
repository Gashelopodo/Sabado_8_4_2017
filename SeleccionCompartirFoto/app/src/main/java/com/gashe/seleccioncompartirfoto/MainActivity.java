package com.gashe.seleccioncompartirfoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 500);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Log.d(getClass().getCanonicalName(), "El usuario ha seleccionado la foto");
            Uri uri = data.getData();

            Log.d(getClass().getCanonicalName(), "URI: " + uri.toString());

            // pintar imagen convirtiendo URI en BITMAP ( poder tratar el bitmap para escalar, peso , etc BitmapFactory options

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView) findViewById(R.id.myImagen);
                imageView.setImageBitmap(bitmap);

                Intent intent = new Intent(this, SendPicture.class);
                intent.setData(uri);

                startActivity(intent);

            }catch (Exception e){

            }

            // pintar imagen con URI directamente

            //ImageView imageView = (ImageView) findViewById(R.id.myImagen);
            //imageView.setImageURI(uri);

        }

    }
}
