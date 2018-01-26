package hr.math.kolokvij2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends AppCompatActivity {

    Bitmap[] bitmapArray = new Bitmap[24];
    String[] Tekstovi=new String[24];
    int[] _ids={R.id.id1,R.id.id2};

    int[] _tids={R.id.tid1,R.id.tid2};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new DownloadImageTask().execute("https://files.vladstudio.com/joy/tardigrade/wall/vladstudio_tardigrade_480x320.jpg",
                "https://files.vladstudio.com/joy/eternity/wall/vladstudio_eternity_480x320.jpg",
                "https://files.vladstudio.com/joy/sonya_flies_to_paris/wall/vladstudio_sonya_flies_to_paris_480x320.jpg",
                "https://files.vladstudio.com/joy/christmas_shooting_star/wall/vladstudio_christmas_shooting_star_480x320.jpg",
                "https://files.vladstudio.com/joy/full_moon/wall/vladstudio_full_moon_480x320.jpg",
                "https://files.vladstudio.com/joy/witch/wall/vladstudio_witch_480x320.jpg",
                "https://files.vladstudio.com/joy/home/wall/vladstudio_home_480x320.jpg",
                "https://files.vladstudio.com/joy/drums/wall/vladstudio_drums_480x320.jpg",
                "https://files.vladstudio.com/joy/selfie_blue/wall/vladstudio_selfie_blue_480x320.jpg",
                "https://files.vladstudio.com/joy/selfie/wall/vladstudio_selfie_480x320.jpg",
                "https://files.vladstudio.com/joy/14_owls/wall/vladstudio_14_owls_480x320.jpg",
                "https://files.vladstudio.com/joy/colin_huggins/wall/vladstudio_colin_huggins_480x320.jpg",
                "https://files.vladstudio.com/joy/where_tahrs_live/wall/vladstudio_where_tahrs_live_480x320.jpg",
                "https://files.vladstudio.com/joy/neptune_and_triton/wall/vladstudio_neptune_and_triton_480x320.jpg",
                "https://files.vladstudio.com/joy/the_moon_and_the_ocean/wall/vladstudio_the_moon_and_the_ocean_480x320.jpg",
                "https://files.vladstudio.com/joy/the_earth_and_the_moon/wall/vladstudio_the_earth_and_the_moon_480x320.jpg",
                "https://files.vladstudio.com/joy/raring_ringtail_blue/wall/vladstudio_raring_ringtail_blue_480x320.jpg",
                "https://files.vladstudio.com/joy/sky_lanterns/wall/vladstudio_sky_lanterns_480x320.jpg",
                "https://files.vladstudio.com/joy/the_bright_side_of_the_moon/wall/vladstudio_the_bright_side_of_the_moon_480x320.jpg",
                "https://files.vladstudio.com/joy/cappuccino_tree/wall/vladstudio_cappuccino_tree_480x320.jpg",
                "https://files.vladstudio.com/joy/missing_him/wall/vladstudio_missing_him_480x320.jpg",
                "https://files.vladstudio.com/joy/missing_her/wall/vladstudio_missing_her_480x320.jpg"
                );


       // new DownloadTextTask().execute("https://files.vladstudio.com/joy/tardigrade/wall/vladstudio_tardigrade_480x320.jpg","https://files.vladstudio.com/joy/eternity/wall/vladstudio_eternity_480x320.jpg");
        new DownloadTextTask().execute("https://www.vladstudio.com/wallpapers/");
        //ako to downloada source - onda pomocu regexa mogu izvuci alt--- ak to radi onda sam mogal tako dobiti i url slike..


    }


    public void onClick(View voew){
        Intent intent = new Intent(Main.this, Baza.class);
        startActivity(intent);

    }




    private InputStream OpenHttpConnection(String urlString)
            throws IOException
    {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }

    private String DownloadText(String URL)
    {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e) {
            Log.d("NetworkingActivity", e.getLocalizedMessage());
            return "";
        }

        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer))>0) {
                //---convert the chars to a String---
                String readString =
                        String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            Log.d("NetworkingActivity", e.getLocalizedMessage());
            return "";
        }
        return str;
    }

   /* private class DownloadTextTask extends AsyncTask<String, Void, String[]> {
        protected String[] doInBackground(String... urls) {
            int i;
            for (i=0;i<24;i++){
                Tekstovi[i]=DownloadText(urls[0]);
            }

            return Tekstovi;
        }

        @Override
        protected void onPostExecute(String[] result) {
            int i;
            for (i=0;i<24;i++){
                TextView img = (TextView) findViewById(_tids[i]);
                img.setText(Tekstovi[i]);
            }
        }*/

        private class DownloadTextTask extends AsyncTask<String, Void, String[]> {
        protected String[] doInBackground(String... urls) {
            String SourceHTML=DownloadText(urls[0]);
            String[] tekstic=new String[24];
            Pattern pattern = Pattern.compile("alt=\"(.*?)\"");
            Matcher matcher = pattern.matcher(SourceHTML);
            if (matcher.find())
            {   System.out.println("!__________________________________________________________");


                int i;
                for (i=0;i<2;i++){

                   Tekstovi[i]=matcher.group(1);
                    matcher.find();
                }

            }


            return Tekstovi;
        }

        @Override
        protected void onPostExecute(String[] result) {
            int i;
            for (i=0;i<2;i++){
                TextView img = (TextView) findViewById(_tids[i]);
                img.setText(Tekstovi[i]);
            }
        }


    }




    private Bitmap DownloadImage(String URL)
    {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            Log.d("NetworkingActivity", e1.getLocalizedMessage());
        }
        return bitmap;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap[] > {
        protected Bitmap[] doInBackground(String... urls) {
            int i;
            for (i=0;i<2;i++){
                bitmapArray[i]=DownloadImage(urls[i]);
            }
            //return DownloadImage(urls[0]);
            return bitmapArray;
        }

        protected void onPostExecute(Bitmap[] result) {

            int i;
            for (i=0;i<2;i++){
                ImageView img = (ImageView) findViewById(_ids[i]);
                img.setImageBitmap(bitmapArray[i]);
            }

        }
    }

}
