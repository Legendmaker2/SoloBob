package com.codefair.solobob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements RetrofitManager.SuccessLoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RetrofitManager.getInstance().setOnSuccessLoginListener(this);

        findViewById(R.id.registerButton_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.submitButton_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.emailEditText_login)).getText().toString();
                String password = ((EditText) findViewById(R.id.passwordEditText_login)).getText().toString();
                RetrofitManager.getInstance().login(new LoginTO(email, password));
            }
        });
    }

    @Override
    protected void onDestroy() {
        RetrofitManager.getInstance().removeSuccessLoginListener();
        super.onDestroy();
    }

    @Override
    public void onSuccessLogin(UserInfo userInfo) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("userInfo", userInfo);
        startActivity(intent);
    }
}
