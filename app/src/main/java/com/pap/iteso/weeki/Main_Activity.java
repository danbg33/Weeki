package com.pap.iteso.weeki;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by DaNN on 25/09/2017.
 */

public class Main_Activity  extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private SignInButton googleSignin;
    private Button logoutbutton;
    private  Button revoke;
    private TextView token;
    private GoogleApiClient googleApiClient;
    private Boolean login;
    private static final int REQ_CODE = 9001;
    private String not_log_in ="Conecting Error ";

    Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_main);
        Log.d("Start Main","SUCCESS MAIN ACTIVITY!!!!!!!!!!!!!!!!!! ");
        login = getIntent().getExtras().getBoolean("flag");
        Log.d("flagvalue","The flag value is:" +login);
        logoutbutton = (Button) findViewById(R.id.button_logout);
        revoke = (Button) findViewById(R.id.button_revoke);

        //googleSignin = (SignInButton) findViewById(R.id.google_button);
        token = (TextView) findViewById(R.id.text_token);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        revoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login==true){
                    revoke(v);
                }
                else{
                    Toast.makeText(getApplicationContext(),"FAILED REVOKING",Toast.LENGTH_SHORT).show();
                }
            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login==true)
                    revoke(v);
                else {
                    Log.d("Logout","Push button logout has pressed");
                    logout();

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(login==true){
            Log.d("onSTart","Se accedio mediante login");
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            if (opr.isDone()) {
                GoogleSignInResult result = opr.get();
                handlerSignInResult(result);
            } else {
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(@NonNull GoogleSignInResult result) {
                        handlerSignInResult(result);
                    }
                });
            }
        }

    }

    public void handlerSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            /*
            * Utilizar la información del usuario
            *
            * */
        }
        else{
            goLoginScreen();
        }

    }
    private void singIn(){
        //Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        //startActivityForResult(intent, REQ_CODE);

    }
    private void goLoginScreen(){
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void logout(){
        if(login==true) {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        goLoginScreen();
                    } else
                        Toast.makeText(getApplicationContext(), "Error Logout", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            goLoginScreen();
            //Intent intent = new Intent(this, Login.class);
            //start
        }
    }

    public void revoke(View view){
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLoginScreen();
                }
                else
                    Toast.makeText(getApplicationContext(),"Error revoke access", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
