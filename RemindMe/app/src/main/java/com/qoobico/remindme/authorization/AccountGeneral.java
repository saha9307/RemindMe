package com.qoobico.remindme.authorization;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by oleksandr.pachkovsky on 04.01.2016.
 */
public class AccountGeneral extends AccountAuthenticatorActivity {

    public static final String MAIN_ACCOUNT_TYPE = "modern-expo.com";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";

    private static boolean AccountIsCreated = false;
    private static Account MAIN_ACCOUNT;

    public static Account getMainAccount() {
        return MAIN_ACCOUNT;
    }

    public static void setMainAccount(Account mainAccount) {
        MAIN_ACCOUNT = mainAccount;
    }

    public static boolean isAccountIsCreated() {
        return AccountIsCreated;
    }

    public static void setAccountIsCreated(boolean accountIsCreated) {
        AccountIsCreated = accountIsCreated;
    }

    //set state account: created or not created
    public static boolean StateAccountCreated(Context context)
    {
        AccountManager am = AccountManager.get(context); // current Context

        Account[] accounts = am.getAccounts();
        for(Account account: accounts)
        {
            if(account.type.equalsIgnoreCase(MAIN_ACCOUNT_TYPE))
            {
                MAIN_ACCOUNT = account;
                return true;
            }
        }
        return false;
    }

    public void createNewAccount(Context context, String email){

        Account account=new Account(email, MAIN_ACCOUNT_TYPE);
        Bundle userdata = new Bundle();
        String username = "sasha";
        userdata.putString("sasha", "modern-expo.com");

        AccountManager am = AccountManager.get(context);

        if (am.addAccountExplicitly(account, email, userdata))
        {
            Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, username);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, MAIN_ACCOUNT_TYPE);
            setAccountAuthenticatorResult(result);
        }
    }


}
