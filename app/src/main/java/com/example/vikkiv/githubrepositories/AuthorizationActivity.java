package com.example.vikkiv.githubrepositories;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AuthorizationActivity extends AppCompatActivity {
    public static final String CLIENT_ID = "379d744556c743c090c8a2014779f59f";
    public static final String CLIENT_SECRET = "fd6ec75e44054da1a5088ad2d72f2253";
    public static final String CALLBACK_URL = "github.com";
    private GithubApp mApp;
    private Button btnConnect;
    private TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        EditText editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        final String login = editTextLogin.getText().toString();
        final String pasword = editTextPassword.getText().toString();
        final Activity activity = this;

//        Button btnConnect = (Button) findViewById(R.id.btnConnect2);
//        btnConnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        TextView textView = (TextView) findViewById(R.id.textViewAnon);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("authorization", false);
                startActivity(intent);
            }
        });

        mApp = new GithubApp(this, CLIENT_ID, CLIENT_SECRET, CALLBACK_URL);
        mApp.setListener(listener);

        tvSummary = (TextView) findViewById(R.id.tvSummary);

        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mApp.hasAccessToken()) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            AuthorizationActivity.this);
                    builder.setMessage("Disconnect from GitHub?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            mApp.resetAccessToken();
                                            btnConnect.setText("Connect");
                                            tvSummary.setText("Not connected");
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    final AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    mApp.authorize();
                }
            }
        });

        if (mApp.hasAccessToken()) {
            tvSummary.setText("Connected as " + mApp.getUserName());
            btnConnect.setText("Disconnect");
        }

    }

    GithubApp.OAuthAuthenticationListener listener = new GithubApp.OAuthAuthenticationListener() {

        @Override
        public void onSuccess() {
            tvSummary.setText("Connected as " + mApp.getUserName());
            btnConnect.setText("Disconnect");
        }

        @Override
        public void onFail(String error) {
            Toast.makeText(AuthorizationActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    };
}
