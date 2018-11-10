package com.alvaro.sergio.smov_yamba;

import android.app.Fragment;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import twitter4j.Twitter;

public class PostTaskPrueba extends AsyncTask<String,Void,String> {

    private Twitter twitter;
    private Fragment main;
    private ProgressBar pb;

    PostTaskPrueba(Twitter twitter, Fragment main, ProgressBar pb) {
        this.twitter = twitter;
        this.main = main;
        this.pb = pb;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            twitter.updateStatus(strings[0]);
            return "Message send";
        } catch (Exception e) {
            Log.d(SupportServices.TAG,e.toString());
            if (e.toString().contains("401:Authentication credentials"))
                return "Invalid Credentials";
            if (e.toString().contains("No address associated with hostname"))
                return "Internet connection OFF";
            else
                return "Message not send";


        }
    }

    @Override
    protected  void onPreExecute () {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String result){
        pb.setVisibility(View.GONE);
        Snackbar.make(main.getView(), result, Snackbar.LENGTH_LONG).show();
//        Toast.makeText(main.getActivity(),result,Toast.LENGTH_LONG).show();
    }

}
