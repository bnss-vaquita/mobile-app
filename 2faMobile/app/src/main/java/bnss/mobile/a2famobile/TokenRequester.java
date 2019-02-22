package bnss.mobile.a2famobile;

public class TokenRequester {
    String grant_type = "password";
    String client_id;
    String client_secret;
    String username;
    String password;
    String file_hash;

    public TokenRequester(String client_id, String client_secret, String username, String password, String file_hash) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.username = username;
        this.password = password;
        this.file_hash = file_hash;
    }
}
