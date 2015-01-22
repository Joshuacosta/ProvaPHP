package com.example.it00046.provaphp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        pb=(ProgressBar)findViewById(R.id.progressBar);
        pb.setVisibility(View.GONE);
        // Definim la operativa del boto PHP
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implement event handling
                //Toast.makeText(getApplicationContext(),"Hola", Toast.LENGTH_LONG).show();
                pb.setVisibility(View.VISIBLE);
                new MyAsyncTask().execute("un altres phpss");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Tema PHP
    private class MyAsyncTask extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            // TODO Auto-generated method stub
            //postData(params[0]);
            return postData(params[0]);

        }

        protected void onPostExecute(Integer result){
            pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Resultat: " + result, Toast.LENGTH_LONG).show();
            String xml = ParseXMLMethods.getXML();
        }

        protected void onProgressUpdate(Integer... progress){
            pb.setProgress(progress[0]);
        }

        public Integer postData(String valueIWantToSend) {
            Integer Aux = 0;
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://bodina.virtuol.com/prova.php");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("usuari", "Joshua"));
                nameValuePairs.add(new BasicNameValuePair("email", "email"));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                Aux = response.getStatusLine().getStatusCode();
                Log.i("Hola", EntityUtils.toString(response.getEntity()));
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }

            return Aux;
        }

    }
}
