package com.qoobico.remindme.authorization;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.qoobico.remindme.R;

/**
 * Created by oleksandr.pachkovsky on 04.01.2016.
 */
public class AuthorizationActivity extends AccountAuthenticatorActivity {

    private static final int MAIN_LAYOUT = R.layout.authorization_layout;
    private AccountManager mAccountManager;
    final Activity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(MAIN_LAYOUT);

        mAccountManager = AccountManager.get(this);

        findViewById(R.id.sing_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text_email = (EditText) findViewById(R.id.text_email);
                AccountGeneral ac = new AccountGeneral();
                if(text_email.getText().toString().length() > 0 ) {
//                    ac.createNewAccount(thisActivity, text_email.getText().toString());

                    String username = "sasha";
                    String accountType = "modern";
                    String password = "123456789";
                    AccountManager accMgr = AccountManager.get(thisActivity);
                    final Account account = new Account(username, accountType);
                    accMgr.addAccountExplicitly(account, password, null);

                    // Now we tell our caller, could be the Andreoid Account Manager or even our own application
                    // that the process was successful

                    final Intent intent = new Intent();
                    intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
                    intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                    intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);
                    setAccountAuthenticatorResult(intent.getExtras());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }


}


