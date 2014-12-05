package com.tucompualdia.app.incluapp;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by flia on 5/12/14.
 */
public class JsonParser {
    static JSONArray jArray = null;

    public JsonParser() {

    }

    //clase que se abre al momento de llamar JSONPArser
    public JSONArray GetJSONfromUrl(String url) throws IOException {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet getData = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getData);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e("==>", "Falló la descarga del archivo");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }

        try{
            jArray = new JSONArray( builder.toString());

        } catch (JSONException e){
            Log.e("JSON Parser", "Error traduciendo JSON "+ e.toString());
        }


        return jArray;
    }
}

