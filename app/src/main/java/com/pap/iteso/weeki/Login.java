package com.pap.iteso.weeki;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class Login extends AppCompatActivity implements View.OnClickListener , GoogleApiClient.OnConnectionFailedListener {

    private SignInButton googleSignin;
    private Button signOut;
    private Boolean login;
    private TextView token;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    private String not_log_in ="Conectiong Error ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login =false;
        //line1 = (LinearLayout) findViewById(R.id.line1);
        //line2 = (LinearLayout) findViewById(R.id.line2);

        signOut = (Button) findViewById(R.id.button_skip);
        googleSignin = (SignInButton) findViewById(R.id.google_button);

        googleSignin.setOnClickListener(this);
        signOut.setOnClickListener(this);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.google_button:
                singIn();break;
            case R.id.button_skip:
                skipLogin();break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void singIn(){
        login =true;
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        intent.putExtra("flag",login);
        startActivityForResult(intent, REQ_CODE);

    }

    private void skipLogin(){
        login = false;
        Intent intent = new Intent(Login.this,MenuActivity.class);
        intent.putExtra("flag",login);
        startActivity(intent);
    }

    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            login =true;
            Log.d("01","LOGIN SUCCESS");

            Intent intent = new Intent(Login.this,Main_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("flag",login);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, not_log_in, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
}
