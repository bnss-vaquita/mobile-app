package bnss.mobile.a2famobile;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



    static final String TARGETURL = "130.229.132.247";
    static final int TARGETPORT = 3000;
    static final String TARGET_ENDPOINT = "/auth";
    static final String CLIENT_ID = "test_client";
    static final String CLIENT_SERCRET = "secret";

    // auth
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + TARGETURL + ":" + TARGETPORT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthTokenService service = retrofit.create(AuthTokenService.class);
        TokenRequester tokenRequester = new TokenRequester(CLIENT_ID, CLIENT_SERCRET, "test", "password", "asd");
        Call<TokenResponse> authCall = service.getToken(tokenRequester);
        authCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                Log.e("onCreate", Integer.toString(response.code()));
                Log.e("onCreate", response.body().access_token);
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("onCreate - err", "IOException: " + t.toString());

            }
        });



    }

    /*
        "grant_type": "password",
    "client_id": "test_client",
    "client_secret": "secret",
    "username": "test",
    "password": "password"
     */





    public void getAuthToken(URL url, String username, String password) throws IOException {
        Log.e("getAuthToken", "Setting up connection");
        URLConnection conn = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("grant_type", "password");
        conn.addRequestProperty("client_id", CLIENT_ID);
        conn.addRequestProperty("client_secret", CLIENT_SERCRET);
        conn.addRequestProperty("username", username);
        conn.addRequestProperty("password", password);
        Log.e("getAuthToken", "Opening up connection");

        conn.connect();
        Log.e("getAuthToken", "Getting content from connection");

        Object o = conn.getContent();

    }

    public void getToken(AccountManagerCallback amcb, Handler errorHandler) throws Exception {


        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccountsByType("VaquitaAccount");
        Account vaqAccount;
        if (accounts.length == 1) {
            vaqAccount = accounts[0];
        } else {
            Log.e("getToken AM error","Not exactly one vaquita account in accountManager!");
            throw new Exception("Not exactly one vaquita account in accountManager!");
        }
        Bundle options = new Bundle();



        am.getAuthToken(
                vaqAccount,                     // Account retrieved using getAccountsByType()
                "Manage your tasks",            // Auth scope
                options,                        // Authenticator-specific options
                this,                           // Your activity
                amcb,          // Callback called when a token is successfully acquired
                errorHandler);
    }


    private class OnTokenAcquired implements AccountManagerCallback {
        @Override
        public void run(AccountManagerFuture result) {
            Bundle bundle = null;
            try {
                bundle = (Bundle) result.getResult();
            } catch (OperationCanceledException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AuthenticatorException e) {
                e.printStackTrace();
            }

            Intent launch = null;
            launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
            if (launch != null) {
                startActivityForResult(launch, 0);
                return;
            }

            // The token is a named value in the bundle. The name of the value
            // is stored in the constant AccountManager.KEY_AUTHTOKEN.
            String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
            Log.w("OnTokenAcquired -Token:", token);
        }
    };
}
