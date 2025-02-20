package com.example.lehi.http;

/**
 * Created by Lehi on 3/14/2015.
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadWebPage extends AsyncTask{

    private TextView dataField;
    private Context context;
    public DownloadWebPage(Context context,TextView dataField) {
        this.context = context;
        this.dataField = dataField;
    }


    //check Internet conenction.
    private void checkInternetConenction(){
        ConnectivityManager check = (ConnectivityManager) this.context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (check != null)
        {
            NetworkInfo[] info = check.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i <info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        Toast.makeText(context, "Internet is connected",
                                Toast.LENGTH_SHORT).show();
                    }

        }
        else{
            Toast.makeText(context, "Not connected to internet",
                    Toast.LENGTH_SHORT).show();
        }
    }


    protected void onPreExecute(){
        checkInternetConenction();
    }

    @Override
    protected Object doInBackground(Object[] arg0) {
        try{
            String link = (String)arg0[0];
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader
                    (is, "UTF-8") );
            String data = null;
            String webPage = "";
            while ((data = reader.readLine()) != null){
                webPage += data + "\n";
            }
            return webPage;
        }catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    protected void onPostExecute(Object result){
        String results = (String)result;
        this.dataField.setText(results);
    }
}