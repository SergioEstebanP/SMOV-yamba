package com.alvaro.sergio.smov_yamba;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class StatusFragment extends Fragment implements View.OnClickListener, TextWatcher {

    EditText editStatus;
    TextView numberOfCharacters;
    Button buttonTweet;
    Twitter twitter;
    ProgressBar progressBar;
    ProgressBar progressBar2;

    public StatusFragment() {

    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

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
        PostTaskPrueba post = new PostTaskPrueba(twitter, StatusFragment.this, progressBar2);
        post.execute(status);
    }

}
