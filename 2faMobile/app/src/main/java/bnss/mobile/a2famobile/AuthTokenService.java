package bnss.mobile.a2famobile;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthTokenService {
    @POST("auth")
    Call<TokenResponse> getToken(@Body TokenRequester tokenRequester);
}
