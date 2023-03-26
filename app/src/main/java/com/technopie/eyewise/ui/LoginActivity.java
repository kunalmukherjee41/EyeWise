package com.technopie.eyewise.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.technopie.eyewise.R;
import com.technopie.eyewise.api.RetrofitClient;
import com.technopie.eyewise.model.UserResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button login;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        TextView create_account = findViewById(R.id.create_account);
        TextView forgot_password = findViewById(R.id.forgot_password);

        //goto create activity
        create_account.setOnClickListener(
                v -> startActivity(new Intent(LoginActivity.this, CreateUserActivity.class))
        );

        //check email password and goto home activity
        login.setOnClickListener(
                v -> {
                    login.setBackgroundColor(getResources().getColor(R.color.btn));
                    userLogin();
                }
        );

//        forgot_password.setOnClickListener(
//                v -> startActivity(new Intent(LoginActivity.this, ForgotActivity.class))
//        );

    }

    public void ShowHidePass(View view) {

        if (view.getId() == R.id.show_pass_btn) {
            if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.password_hide_asset);
                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.password_visible_asset);
                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    //on user login
    private void userLogin() {

        progressBar = new ProgressDialog(this);
        progressBar.show();
        progressBar.setContentView(R.layout.progress_dialog);
        Objects.requireNonNull(progressBar.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        String txt_email = email.getText().toString().trim();
        String txt_password = password.getText().toString().trim();
        if (TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_email)) {
            Toast.makeText(LoginActivity.this, "Fill Both Requirements", Toast.LENGTH_LONG).show();
            login.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_bg, null));
            progressBar.dismiss();

        } else {

            Call<UserResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .userLogin(txt_email, txt_password);

            call.enqueue(new Callback<UserResponse>() {
                 @Override
                 public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                     assert response.body() != null;
                     if (!response.body().getError()) {
                         Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                         email.setText("");
                     } else {
                         Toast.makeText(LoginActivity.this, "Password Is Invalid", Toast.LENGTH_LONG).show();
                     }
                     login.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_bg, null));
                     password.setText("");
                     progressBar.dismiss();
                 }

                 @Override
                 public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                     Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                     password.setText("");
                     progressBar.dismiss();
                     login.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_bg, null));
                 }
             });
        }
    }

//    //check user previously login or not
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
//            Intent intent = new Intent(this, DisplayActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressBar.dismiss();
    }
}