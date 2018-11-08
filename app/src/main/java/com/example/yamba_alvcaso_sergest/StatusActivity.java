package com.example.yamba_alvcaso_sergest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class StatusActivity extends AppCompatActivity implements TextWatcher {

    EditText nuevoEstado;
    Button botonEnviar;
    ProgressBar barraProgreso;
    Twitter twitter;
    private static final String tag ="StatusActivity";
    private final int MAXCHARACTERS = 140;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        nuevoEstado = (EditText) findViewById(R.id.editStatus);
        botonEnviar = (Button) findViewById(R.id.buttonTweet);
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(tag,"mensaje");
                new PostTwet().execute(nuevoEstado.getText().toString());

            }
        });
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("rqFO2Q3L6HLGusJ43kpLSRh51")
                .setOAuthConsumerSecret("JZUmn8jKwJ8af3S9y64O8U0KxY77Uq400F0b6qhU8iCUuYOFHl")
                .setOAuthAccessToken("1052940451936907264-5embE34o8XzKyjFmXdKcr4rY0GSqAm")
                .setOAuthAccessTokenSecret("mMYubNM7VnSNBOPxjN0E8MimVafBYhDRYf1Kcbl7Gxt1s");
        barraProgreso =(ProgressBar) findViewById(R.id.barraProgreso);
        barraProgreso.setMax(MAXCHARACTERS);
        nuevoEstado.addTextChangedListener(this);
        TwitterFactory factory = new TwitterFactory(builder.build());
        twitter = factory.getInstance();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        barraProgreso.setProgress(s.length());
    }

    public class PostTwet extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                twitter.updateStatus(strings[0]);
                return "Mensaje enviado";
            } catch (TwitterException e) {
                Log.d(tag,e.toString());
                return "Mensaje no enviado";
            }
        }

        protected void onPostExecute(String result){
            Toast.makeText(StatusActivity.this,result,Toast.LENGTH_LONG).show();
            nuevoEstado.setText("");
        }
    }
}
