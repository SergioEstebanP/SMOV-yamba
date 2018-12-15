/*
 *
 * Álvaro de Caso Morejón
 * Sergio Esteban Pellejero
 *
 */
package com.alvaro.sergio.smov_yamba;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class StatusFragment extends Fragment implements View.OnClickListener, TextWatcher {

    EditText editStatus;
    TextView numberOfCharacters;
    Button buttonTweet;
    Twitter twitter;
    ProgressBar progressBar;
    ProgressBar progressBar2;
    SharedPreferences pref;

    public StatusFragment() {

    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        editStatus = view.findViewById(R.id.editText);
        numberOfCharacters = view.findViewById(R.id.numberOfCaharacters);
        buttonTweet = view.findViewById(R.id.button);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar2 = view.findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.INVISIBLE);

        progressBar.setMax(SupportServices.MAX_CHARACTERS);

        buttonTweet.setOnClickListener(this);
        editStatus.addTextChangedListener(this);

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(SupportServices.CONSUMER_KEY)
                .setOAuthConsumerSecret(SupportServices.CONSUMER_SECRET)
                .setOAuthAccessToken(SupportServices.ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(SupportServices.ACCESS_SECRET);
        TwitterFactory factory = new TwitterFactory(builder.build());
        twitter = factory.getInstance();
        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        progressBar.setProgress(s.length());
        numberOfCharacters.setText(Integer.toString(s.length()));
    }

    @Override
    public void onClick(View v) {
        String status = editStatus.getText().toString();
        PostTask post = new PostTask(twitter, StatusFragment.this, progressBar2);
        post.execute(status);
    }

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
            String accesstoken = pref.getString("accesstoken", "1052940451936907264-5embE34o8XzKyjFmXdKcr4rY0GSqAm");
            String accesstokensecret = pref.getString("accesstokensecret", "mMYubNM7VnSNBOPxjN0E8MimVafBYhDRYf1Kcbl7Gxt1s");
            if (TextUtils.isEmpty(accesstoken) || TextUtils.isEmpty(accesstokensecret)) {
                getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
                return "Por favor, actualiza tu nombre de usuario y tu contraseña";
            }
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(SupportServices.CONSUMER_KEY)
                    .setOAuthConsumerSecret(SupportServices.CONSUMER_SECRET)
                            .setOAuthAccessToken(accesstoken)
                            .setOAuthAccessTokenSecret(accesstokensecret);
            TwitterFactory factory = new TwitterFactory(builder.build());
            twitter = factory.getInstance();
            try {
                twitter.updateStatus(strings[0]);
                return "Tweet enviado correctamente";
            } catch (TwitterException e) {
                Log.e(SupportServices.TAG, "Fallo en el envío");
                e.printStackTrace();
                return "Fallo en el envío del tweet";
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


}
