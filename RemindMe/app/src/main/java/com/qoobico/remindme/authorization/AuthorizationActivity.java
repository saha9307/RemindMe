package com.qoobico.remindme.authorization;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qoobico.remindme.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(MAIN_LAYOUT);

        mAccountManager = AccountManager.get(this);
        Button buttonSignIn = (Button) findViewById(R.id.sing_in_button);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextEmail = (EditText) findViewById(R.id.text_email);
                String text_email = editTextEmail.getText().toString();
                if (text_email.length() == 0 || !VerificationAEmail(text_email))
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
        // Se obtiene el usuario y contrasena ingresados
        final String userName = "Oleksandr";
        final String userPass = "123456789";

        // Se loguea de forma asincronica para no entorpecer el UI thread
        new AsyncTask<Void, Void, Intent>() {
            @Override
            protected Intent doInBackground(Void... params) {
                // Se loguea en el servidor y retorna token
                String authtoken = logIn(userName, userPass);
                // Informacion necesaria para enviar al authenticator
                final Intent res = new Intent();
                res.putExtra(AccountManager.KEY_ACCOUNT_NAME, userName);
                res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, AccountAuthenticator.ACCOUNT_TYPE);
                res.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);
                res.putExtra(PARAM_USER_PASS, userPass);
                return res;
            }
            @Override
            protected void onPostExecute(Intent intent) {
                finishLogin(intent);
            }
        }.execute();
    }

    private String logIn(String user, String pass){
        // Método para fines demostrativos :)
        return "tokentokentokentoken1234";
    }

    private void finishLogin(Intent intent) {
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName,
                intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        // Si es que se esta anadiendo una nueva cuenta
        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {

            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = AccountAuthenticator.AUTHTOKEN_TYPE;
            // Creando cuenta en el dispositivo y seteando el token que obtuvimos.
            Boolean st = mAccountManager.addAccountExplicitly(account, accountPassword, null);

            // Ojo: hay que setear el token explicitamente si la cuenta no existe,
            // no basta con mandarlo al authenticator
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        }
        // Si no se está añadiendo cuenta, el token estaba antiguo invalidado.
        // Seteamos contraseña nueva por si la cambio.
        else {
            // Solo seteamos contraseña.  Aca no es necesario setear el token explicitamente,
            // basta con enviarlo al Authenticator
            mAccountManager.setPassword(account, accountPassword);
        }
        // Setea el resultado para que lo reciba el Authenticator
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        // Cerramos la actividad
        finish();
    }
}


