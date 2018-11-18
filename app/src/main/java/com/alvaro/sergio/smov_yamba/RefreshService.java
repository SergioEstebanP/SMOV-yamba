package com.alvaro.sergio.smov_yamba;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class RefreshService extends IntentService {

    private static final int retardo = 30000;
    private boolean runFlag  = false;

    public RefreshService() {
        super(SupportServices.TAGService);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(SupportServices.TAGService,"onStarted");

        runFlag = true;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken =  prefs.getString("accesstoken","1052940451936907264-5embE34o8XzKyjFmXdKcr4rY0GSqAm");
        String accessTokenSecret = prefs.getString("accesstokensecret","mMYubNM7VnSNBOPxjN0E8MimVafBYhDRYf1Kcbl7Gxt1s");
        while (runFlag){
            Log.d(SupportServices.TAGService,"Actualizando");
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey("rqFO2Q3L6HLGusJ43kpLSRh51")
                        .setOAuthConsumerSecret("JZUmn8jKwJ8af3S9y64O8U0KxY77Uq400F0b6qhU8iCUuYOFHl")
                        .setOAuthAccessToken(accessToken)
                        .setOAuthAccessTokenSecret(accessTokenSecret);
                TwitterFactory factory = new TwitterFactory(builder.build());
                Twitter twitter = factory.getInstance();
                Log.d(SupportServices.TAGService,"Updater running");
                try {
                    List<Status> timeline = twitter.getUserTimeline();
                    Collections.sort(timeline,Collections.<Status>reverseOrder());
                    for (Status status : timeline) {
                        Log.d(SupportServices.TAGService, String.format("%s: %s", status.getUser().getName(),
                                status.getText()));
                    }
                }
                catch (TwitterException e) {
                    Log.e(SupportServices.TAGService,
                            "Failed to fetch the timeline", e);
                }
                Thread.sleep(retardo);
            }
            catch (InterruptedException e) {
                runFlag = false;
            }
        }
    }




    @Override
    public void onDestroy(){
        super.onDestroy();
        runFlag = false;
        Log.d(SupportServices.TAGService,"onDestroyed");
    }
}
