package com.gashe.subirfoto;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cice on 8/4/17.
 */

public class UploadImageWithTime extends AsyncTask<Bitmap, Float, String> {

    private final static String URL_SERVIDOR = "http://192.168.3.77:8080/DniApp/UploadImage";
    private Context context;

    public UploadImageWithTime(Context context) {
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

            /*OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(imagen_codificada);

            outputStreamWriter.close();*/

            int size_image = imagen_codificada.length();
            int bloque = 1; // bytes que se mandan por cada petición
            int escrito = 0;
            int faltan = size_image;

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());

            while(faltan >= bloque){
                outputStreamWriter.write(imagen_codificada, escrito, bloque);
                faltan = faltan - bloque;
                escrito = escrito + bloque;

                publishProgress(new Float((escrito*100)/size_image));
            }

            if(faltan > 0){
                outputStreamWriter.write(imagen_codificada, escrito, faltan);
                publishProgress(100f); // deberia ir en el postexecute ( cuando termina )
            }

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

    @Override
    protected void onProgressUpdate(Float... values) {
        int valor = Math.round(values[0]);
        Activity activity = (Activity)context;
        ProgressBar progressBar = (ProgressBar)activity.findViewById(R.id.myProgressBar);
        TextView textView = (TextView)activity.findViewById(R.id.myTextProgressBar);

        textView.setText("PROGRESO " + valor + " / 100");
        progressBar.setProgress(valor);

        super.onProgressUpdate(values);
    }
}
