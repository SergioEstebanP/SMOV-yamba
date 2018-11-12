// Sergio Esteban Pellejero
// Álvaro de Caso Morejón

package com.alvaro.sergio.smov_yamba;

import android.app.Fragment;
import android.app.admin.SystemUpdatePolicy;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import twitter4j.Twitter;

public class PostTask extends AsyncTask<String,Void,String> {

    private Twitter twitter;
    private Fragment main;
    private ProgressBar pb;

    PostTask(Twitter twitter, Fragment main, ProgressBar pb) {
        this.twitter = twitter;
        this.main = main;
        this.pb = pb;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            twitter.updateStatus(strings[0]);
            return SupportServices.MESSAGE_SEND;
        } catch (Exception e) {
            Log.d(SupportServices.TAG,e.toString());
            if (e.toString().contains("401:Authentication credentials"))
                return SupportServices.INVALID_CREDENTIALS;
            if (e.toString().contains("No address associated with hostname"))
                return SupportServices.INTERNET_FAIL;
            else
                return SupportServices.MESSAGE_SEND_NOT_SEND;


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
    }

}
