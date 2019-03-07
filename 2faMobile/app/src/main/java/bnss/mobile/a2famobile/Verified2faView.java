package bnss.mobile.a2famobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Verified2faView extends ConstraintLayout {



    TimeBasedOneTimePasswordGenerator totp;

    Timer timer_update2fa;
    android.os.Handler handler_2fa;

    TextView textView_2fa;
    TextView textView_timer;




    public Verified2faView(Context context, SharedPreferences settings) {
        super(context);
        this.handler_2fa = genHandler();
        init(settings);
    }

    public Verified2faView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Verified2faView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(SharedPreferences settings) {

        inflate(getContext(), R.layout.sample_verified_2fa_view, this);

        textView_2fa = findViewById(R.id.textView_2fa);
        textView_timer = findViewById(R.id.textView_timeout);

        textView_2fa.setText("hi!");

        try {
            totp = new TimeBasedOneTimePasswordGenerator();

        } catch (NoSuchAlgorithmException e) {
            Log.e("Verfied_2fa", "No such algorithm TOTP");

        }

        String OTP_key = settings.getString("OTP_key", null);
        if (OTP_key == null ) {
            setUp2fa(settings);
            OTP_key = settings.getString("OTP_key", null);
            if (OTP_key == null) {
                throw new RuntimeException("Unable to create OTP_key");
            }
        }
        timer_update2fa = new Timer(true);
        // TODO: Set to coincide with next 30 second interval
        timer_update2fa.scheduleAtFixedRate(generateTimerTask(OTP_key), 5000, 10000);


    }

    private void setUp2fa(SharedPreferences settings) {
        final SecretKey secretKey;

        {
            final KeyGenerator keyGenerator;
            try {
                keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());


            // SHA-1 and SHA-256 prefer 64-byte (512-bit) keys; SHA512 prefers 128-byte (1024-bit) keys
            keyGenerator.init(512);

            secretKey = keyGenerator.generateKey();


                SharedPreferences.Editor editor = settings.edit();
                editor.putString("OTP_key", Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT) );
                editor.apply();
            } catch (NoSuchAlgorithmException e) {
                Log.e("setup2fa", e.toString());
            }



        }
    }


    private TimerTask generateTimerTask(String OTP_key_string) {
        byte[] OTP_key_bytes = Base64.decode(OTP_key_string, Base64.DEFAULT);
        final SecretKey OTP_key = new SecretKeySpec(OTP_key_bytes, 0, OTP_key_bytes.length, totp.getAlgorithm() );
        return new TimerTask() {
            @Override
            public void run() {
                // Run OTP generation
                final Date now = new Date();
                Log.e("Verified_2fa", "Generating 2fa OTP for time: " + now.toString());
                try {
                    int oneTimePassword = totp.generateOneTimePassword(OTP_key, now);

                    Message msg = new Message();
                    msg.arg1 = oneTimePassword;
                    msg.obj = now.toString();
                    handler_2fa.sendMessage(msg);

                    Log.e("Verified_2fa", "2fa OTP: " + oneTimePassword);

                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        };
    }



    private Handler genHandler() {
        return new Handler() {
            public void handleMessage(Message msg) {
                textView_2fa.setText(Integer.toString(msg.arg1)); //this is the textview
                textView_timer.setText((String) msg.obj);
            }
        };
    }
}
