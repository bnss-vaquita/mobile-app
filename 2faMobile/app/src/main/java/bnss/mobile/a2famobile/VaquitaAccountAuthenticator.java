package bnss.mobile.a2famobile;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
public class VaquitaAccountAuthenticator extends AbstractAccountAuthenticator {
    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String accountType, String authTokenType, String[] requiredFeatures, Bundle bundle) throws NetworkErrorException {
        Callback<TokenResponse> cbTokenResponse = registerAccountResponseCallback();
        NetworkRequests.getAuthToken(username, password, cbTokenResponse);
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        Callback<TokenResponse> cbTokenResponse = tokenResponseCallback()
        NetworkRequests.getAuthToken(username, password, cbTokenResponse);
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        return null;
    }



    private Callback<TokenResponse> registerAccountResponseCallback(final String username, final String password)
    {
        return new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                int statusCode = response.code();
                String access_token;
                Log.e("onCreate", Integer.toString(statusCode));
                switch (statusCode) {
                    case 200:
                        AccountManager accountManager = AccountManager.get(getApplicationContext());
                        final Account account = new Account(username, "Vaquita");
                        // TODO Change so we do not store the password in plaintext on the device
                        accountManager.addAccountExplicitly(account, password, null);
                        access_token = response.body().access_token;
                        Log.e("button_login_onClick", access_token);
                        sendMessage(access_token);
                        break;
                    case 401:
                        Log.e("button_login_onClick", "Invalid username or password!");
                        break;
                    default:
                        Log.e("button_login_onClick", "Statuscode: " + statusCode);
                        break;

                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("onCreate - err", "IOException: " + t.toString());

            }
        };
    }

}
*/
