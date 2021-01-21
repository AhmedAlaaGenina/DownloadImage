package com.ahmedg.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    Button btnDownloadImage;
    EditText edtTextURL;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDownloadImage = findViewById(R.id.btnDownloadImage);
        edtTextURL = findViewById(R.id.edtTxtUrl);
        imageView = findViewById(R.id.imageView);
        btnDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadImage downloadImage = new DownloadImage();
                downloadImage.execute(edtTextURL.getText().toString());
            }
        });
    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... url) {
            Bitmap result = null;
            try {
                result = download(url[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            Toast.makeText(MainActivity.this, "Downloading Success", Toast.LENGTH_SHORT).show();
        }
    }

    Bitmap download(String url) throws IOException {
        Bitmap result = null;
        URL urlObj = null;
        HttpsURLConnection httpConn ;
        InputStream inputStream ;

        try {
            urlObj = new URL(url);
            httpConn = (HttpsURLConnection)urlObj.openConnection();
            httpConn.connect();
            inputStream = httpConn.getInputStream();
            result = BitmapFactory.decodeStream(inputStream);
            httpConn.disconnect();
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }
}