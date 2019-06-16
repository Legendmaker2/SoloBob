package com.codefair.solobob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements RetrofitManager.SuccessRegisterListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RetrofitManager.getInstance().setOnSuccessRegisterListener(this);

        findViewById(R.id.submitButton_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.emailEditText_register)).getText().toString();
                String name = ((EditText) findViewById(R.id.nameEditText_register)).getText().toString();
                String password = ((EditText) findViewById(R.id.passwordEditText_register)).getText().toString();

                RetrofitManager.getInstance().registerUser(new RegisterUserTO(email, password, name));
            }
        });
    }

    @Override
    protected void onDestroy() {
        RetrofitManager.getInstance().removeSuccessRegisterListener();
        super.onDestroy();
    }

    @Override
    public void onSuccessRegister(UserInfo userInfo) {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.putExtra("userInfo", userInfo);
        startActivity(intent);
    }
}
