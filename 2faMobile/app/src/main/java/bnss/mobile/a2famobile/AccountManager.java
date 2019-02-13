package bnss.mobile.a2famobile;

import android.accounts.Account;

public class AccountManager {


    public static Account createVaquitaAccount() {
        return new Account("MyAccount", "VaquitaAccount");
    }
}
