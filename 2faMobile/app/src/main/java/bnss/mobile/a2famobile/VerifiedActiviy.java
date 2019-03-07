package bnss.mobile.a2famobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class VerifiedActiviy extends AppCompatActivity {


    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    ViewPager viewPager;
    TextView textView_2fa;
    Button button_logout;

    public Handler handler_2fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified_activiy);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String access_token = intent.getStringExtra("access_token");



        button_logout = findViewById(R.id.button_logout);

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });



        final VerifiedActiviy root_activity = this;


        viewPager = findViewById(R.id.pager);


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            // return total amount of pages
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }
            @NonNull
            @Override
            public Object instantiateItem (ViewGroup container,
                                           int position) {

                switch(position) {
                    case 0:
                        // show 2fa view
                        SharedPreferences settings = getSharedPreferences("vaquita_creds", MODE_PRIVATE);

                        Verified2faView view2FA = new Verified2faView(getApplicationContext(), settings);
                        container.addView(view2FA, 0);
                        Log.e("VerifiedActivity", "Adding 2fa ");
                        return view2FA;
                    case 1:
                        // show file transfer view
                        UserListView userListView = new UserListView(getApplicationContext(), root_activity);
                        Log.e("VerifiedActivity", "Adding userListView ");

                        container.addView(userListView, 1);
                        return userListView;
                    default:
                }
                return null;
            }
        });




        // Capture the layout's TextView and set the string as its text
       // TextView textView = findViewById(R.id.textView);
       // textView.setText(access_token);


        SharedPreferences settings = getSharedPreferences("vaquita_creds", MODE_PRIVATE);
        String username = settings.getString("username", null);
        String password = settings.getString("password", null);

        Log.e("VerifiedActivity", "My account name: "+ username + ", password: " + password);


/*
        AccountManager accountManager = AccountManager.get(getApplicationContext());

        Account[] accounts = accountManager.getAccountsByType("bnss.mobile.a2famobile");
        Account myAccount = null;

        if (accounts.length == 0) {
            Log.e("MainActivity - onCreate", "No Vaquita account exists");
        } else if (accounts.length > 1) {
            Log.e("MainActivity - onCreate", "Vaquita account length > 1");
            throw new NullPointerException();
        } else {
            myAccount = accountManager.getAccountsByType("Vaquita")[0];
        }

        Log.e("VerifiedActivity", "My account name: "+ myAccount.name + ", toString: " + myAccount.toString());

*/



    }




    private void logout() {


        // TODO: Stop OTP generation when logged out
        SharedPreferences settings = getSharedPreferences("vaquita_creds", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();

        Intent intent = new Intent("ACTION_LOGOUT");

        this.setResult(RESULT_OK, intent);
        this.finish();
    }



    public void launchActivity(Intent intent) {
        startActivity(intent);
    }







}

