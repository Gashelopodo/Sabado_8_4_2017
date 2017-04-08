package com.gashe.subirfoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cice on 8/4/17.
 */

public class UploadImage extends AsyncTask<Bitmap, Void, String> {

    private final static String URL_SERVIDOR = "http://192.168.3.77:8080/DniApp/UploadImage";
    private Context context;

    public UploadImage(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Bitmap... bitmaps) {

        String str_dev = null;

        URL url = null;
        HttpURLConnection httpURLConnection = null;

        try {
            url = new URL(URL_SERVIDOR);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            Bitmap bitmap = bitmaps[0];

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imagen_bytes = byteArrayOutputStream.toByteArray();
            String imagen_codificada = Base64.encodeToString(imagen_bytes, Base64.DEFAULT);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(imagen_codificada);

            outputStreamWriter.close();

            str_dev = (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) ? "OK" : null;


        }catch (Throwable t){
            Log.d(getClass().getCanonicalName(), "ERROR: " + t);
        }

        return str_dev;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals(s)){
            Toast.makeText(context, "Enviado", Toast.LENGTH_LONG);
        }
    }
}
