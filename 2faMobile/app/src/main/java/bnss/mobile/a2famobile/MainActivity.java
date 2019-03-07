package bnss.mobile.a2famobile;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {


    public static final int LOGIN_ACTIVITY_REQUEST_CODE = 1;
    public static final int VERIFIED_ACTIVITY_REQUEST_CODE = 2;

    public static String access_token;
    public static boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        String access_token;


/*
        accountManager = AccountManager.get(getApplicationContext());
        Account[] accounts = accountManager.getAccountsByType("Vaquita");
        Account myAccount = null;
*/
        SharedPreferences settings = getSharedPreferences("vaquita_creds", MODE_PRIVATE);
        String username = settings.getString("username", null);
        String password = settings.getString("password", null);
        if (username == null || password == null) {
            loggedIn = false;
        } else {
            loggedIn = true;
        }
        if (!loggedIn) {
            openLogInView();
        } else {
            openVerifiedView("test");
        }



    }

    public void openVerifiedView(String access_token) {
        Intent intent = new Intent(this, VerifiedActiviy.class);
        intent.putExtra("access_token", access_token);
        startActivityForResult(intent, VERIFIED_ACTIVITY_REQUEST_CODE);
    }

    public void openLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent,LOGIN_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case LOGIN_ACTIVITY_REQUEST_CODE:
                // logInView
                switch (resultCode) {
                    case RESULT_OK:
                        String access_token = data.getStringExtra("access_token");
                        openVerifiedView(access_token);
                        break;
                    default:
                        Log.e("MainActiviy: - result", "LoginActivity - resultCode: "+ resultCode);
                        Log.e("MainActiviy: - result", "LoginActivity - intent: "+ (data != null? data.toString(): "null"));


                }
                break;
            case VERIFIED_ACTIVITY_REQUEST_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        Log.e("MainActivity", "User manually logged out!");
                        openLogInView();
                        break;
                    default:
                        Log.e("MainActiviy: - result", "VerifiedActivity - resultCode: "+ resultCode);
                        Log.e("MainActiviy: - result", "VerifiedActivity - intent: "+ (data != null? data.toString(): "null"));

                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }
    /*
        "grant_type": "password",
    "client_id": "test_client",
    "client_secret": "secret",
    "username": "test",
    "password": "password"
     */



/*

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

    */
}
