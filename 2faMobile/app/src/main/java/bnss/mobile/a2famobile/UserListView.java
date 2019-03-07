package bnss.mobile.a2famobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class UserListView extends ConstraintLayout {

    RecyclerView recylerView_users;
    VerifiedActiviy root_activiy;

    public UserListView(Context context, VerifiedActiviy activity) {
        super(context);
        root_activiy = activity;
        init();
    }

    public UserListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    private void init() {

        inflate(getContext(), R.layout.file_transfer_view, this);


        recylerView_users = findViewById(R.id.recylerView_users);
        recylerView_users.setLayoutManager(new LinearLayoutManager(getContext()));
        recylerView_users.setAdapter(new UserListAdapter(genFakeValues(), this, root_activiy));

    }

    private List<VaquitaUser> genFakeValues() {
        List<VaquitaUser> list = new ArrayList<VaquitaUser>();
        for(int i = 0; i < 10; i++) {
            list.add(new VaquitaUser("Name", "" + i));

        }
        return list;
    }
}
