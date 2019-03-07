package bnss.mobile.a2famobile;

import android.net.Uri;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRequests {


    static final String TARGETURL = "[2001:6b0:1:1041:8cca:a58e:3a87:3051]";
 // static final String TARGETURL = "109.104.5.4";
    static final int TARGETPORT = 3000;
    static final String TARGET_ENDPOINT = "/auth";
    static final String CLIENT_ID = "test_client";
    static final String CLIENT_SERCRET = "secret";

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://" + TARGETURL + ":" + TARGETPORT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    static AuthTokenService service = retrofit.create(AuthTokenService.class);

    public static void getAuthToken(String username, String password, Callback<TokenResponse> tokenResponseCallback) {
        Log.e("getAuthToken", "username:" + username + ", password: "+ password);
        TokenRequester tokenRequester = new TokenRequester(CLIENT_ID, CLIENT_SERCRET, username, password, "asd");
        Call<TokenResponse> authCall = service.getToken(tokenRequester);
        authCall.enqueue(tokenResponseCallback);
    }

    // TODO: Convert file to base64 and sign with the above method.
    public static void sendFile(Uri file) {

    }


    /*


            new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                Log.e("onCreate", Integer.toString(response.code()));
                Log.e("onCreate", response.body().access_token);
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("onCreate - err", "IOException: " + t.toString());

            }
        }
     */

}
