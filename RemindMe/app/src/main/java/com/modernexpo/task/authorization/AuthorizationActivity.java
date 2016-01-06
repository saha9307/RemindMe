package com.modernexpo.task.authorization;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.modernexpo.remindme.R;
import com.modernexpo.task.rippledrawable.RippleDrawable;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * Created by oleksandr.pachkovsky on 04.01.2016.
 */
public class AuthorizationActivity extends AccountAuthenticatorActivity {

    private static final int MAIN_LAYOUT = R.layout.authorization_layout;

    public static final String ARG_ACCOUNT_TYPE = "1";
    public static final String ARG_AUTH_TYPE = "2";
    public static final String ARG_IS_ADDING_NEW_ACCOUNT = "3";
    public static final String PARAM_USER_PASS = "4";
    private AccountManager mAccountManager;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(MAIN_LAYOUT);

        Button button = (Button) findViewById(R.id.sing_in_button);
        RippleDrawable rippleDrawable = new RippleDrawable();
        rippleDrawable.attachToView(button);

        mAccountManager = AccountManager.get(this);
        Button buttonSignIn = (Button) findViewById(R.id.sing_in_button);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextEmail = (EditText) findViewById(R.id.text_email);
                email = editTextEmail.getText().toString();
                if (email.length() == 0 || !VerificationAEmail(email))
                    return;

                CreateNewAccount();
            }
        });
    }

    private boolean VerificationAEmail(String email){
        boolean isValid = false;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            isValid = true;
        } catch (AddressException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    private void CreateNewAccount() {

        new AsyncTask<Void, Void, Intent>() {
            @Override
            protected Intent doInBackground(Void... params) {
                final Intent res = new Intent();
                res.putExtra(AccountManager.KEY_ACCOUNT_NAME, email);
                res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, AccountAuthenticator.ACCOUNT_TYPE);
                res.putExtra(AccountManager.KEY_AUTHTOKEN, email);
                res.putExtra(PARAM_USER_PASS, email);
                return res;
            }
            @Override
            protected void onPostExecute(Intent intent) {
                finishLogin(intent);
            }
        }.execute();
    }


    private void finishLogin(Intent intent) {
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName,
                intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {

            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = AccountAuthenticator.AUTHTOKEN_TYPE;
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        }
        else {
            mAccountManager.setPassword(account, accountPassword);
        }
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
}


