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
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    public void makeHTTPRequstWithOauth(String token, URL url, String your_client_id, String your_client_secret) throws IOException {
        URLConnection conn = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("client_id", your_client_id);
        conn.addRequestProperty("client_secret", your_client_secret);
        conn.setRequestProperty("Authorization", "OAuth " + token);
        conn.connect();
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
