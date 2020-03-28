package com.dies.lionbuilding.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

import okhttp3.Request;

public class HttpPostAsyncTask extends AsyncTask<String, Void, String> {
    String stringUrl="";

    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    Map<String, String> postData;
    String Url;


//    public HttpPostAsyncTask(Map<String, String> postData,String Url) {
//        // Also create class level variables for type and callback
//        this.postData=postData;
//        this.Url=Url;
//
//    }
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//
//
//        Map<String, String> postData = new HashMap<>();
//        postData.put("param1", "dsd");
//        postData.put("anotherParam", "dsd");
////        PostTask task = new HttpPostAsyncTask(postData);
////        task.execute(baseUrl + "/some/path/goes/here");
//
//
//    }

    @Override
    protected String doInBackground(String... voids) {
        RequestHandler rh = new RequestHandler() {
            public boolean canHandleRequest(Request data) {
                return false;
            }
            public Result load(Request request, int networkPolicy) throws IOException {
                return null;
            }
        };

        String stringUrl = voids[0];
        String postDatalatlng=voids[1];

        String value = postDatalatlng;
        value = value.substring(1, value.length()-1);           //remove curly brackets
        String[] keyValuePairs = value.split(",");              //split the string to creat key-value pairs
        HashMap<String,String> map = new HashMap<>();

        for(String pair : keyValuePairs)                        //iterate over the pairs
        {
            String[] entry = pair.split("=");                   //split the pairs to get key and value
            map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
        }
//        HashMap<String, String> param = new HashMap<String, String>();
//        param.put("phone", "8888888888");
//        param.put("token", "1234567890");

        String result = rh.sendPostRequest(stringUrl , map);
        Log.i("result", result);
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject js=new JSONObject(s);
            if (js.getString("statusCode").equals(200));{
                String msg=js.getString("message");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected String doInBackground(String... strings) {
//        String stringUrl = strings[0];
//        String postDatalatlng=strings[1];
//
//        String result;
//        String inputLine;
//        try {
//            //Create a URL object holding our url
//            URL myUrl = new URL(stringUrl);
//            //Create a connection
//            HttpURLConnection connection =(HttpURLConnection)
//                    myUrl.openConnection();
//            //Set methods and timeouts
//            connection.setRequestMethod(REQUEST_METHOD);
//            connection.setReadTimeout(READ_TIMEOUT);
//            connection.setConnectTimeout(CONNECTION_TIMEOUT);
//
//            //Connect to our url
//            connection.connect();
//
//
//            if(!postDatalatlng.equals(null)) {
//                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
//                dataOutputStream.write(postDatalatlng.getBytes());
//
//            }
//
//            int code=connection.getResponseCode();
//
//            StringBuilder stringBuilder = null;
//            if(code==200) {
//                stringBuilder = new StringBuilder();
//                String s = "";
//
//
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                while ((s = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(s);
//                }
//            }
//
//            result=stringBuilder.toString();
//            JSONObject jsonObject=new JSONObject(stringBuilder.toString());
//            //Create a new InputStreamReader
////            InputStreamReader streamReader = new
////                    InputStreamReader(connection.getInputStream());
////            //Create a new buffered reader and String Builder
////            BufferedReader reader = new BufferedReader(streamReader);
////            StringBuilder stringBuilder = new StringBuilder();
////            //Check if the line we are reading is not null
////            while((inputLine = reader.readLine()) != null){
////                stringBuilder.append(inputLine);
////            }
////            //Close our InputStream and Buffered reader
////            reader.close();
////            streamReader.close();
//            //Set our result equal to our stringBuilder
//          //  result = stringBuilder.toString();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            result = null;
//        }
//        return result;
//
//
//
//
//    }





}
