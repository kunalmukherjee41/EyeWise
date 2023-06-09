package com.technopie.eyewise.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.technopie.eyewise.R;
import com.technopie.eyewise.api.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUserActivity extends AppCompatActivity {

    private ProgressDialog progressBar;
    private EditText name, email, password, phoneNo, rPassword, dob, pinCode;
    private ScrollView layout;
    private Button create_user;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        rPassword = findViewById(R.id.rPassword);
        phoneNo = findViewById(R.id.phone_no);
        dob = findViewById(R.id.dob);
        pinCode = findViewById(R.id.pin_code);
        create_user = findViewById(R.id.create_user);
        layout = findViewById(R.id.register_layout);

        forgotPassword = findViewById(R.id.forgot_password);

        TextView login = findViewById(R.id.login);

        //goto login page
        login.setOnClickListener(
                v -> startActivity(new Intent(CreateUserActivity.this, LoginActivity.class))
        );

        //crate new user and save the data to database
        create_user.setOnClickListener(
                v -> {
                    create_user.setBackgroundColor(getResources().getColor(R.color.btn));
                    createUser();
                }
        );

//        forgotPassword.setOnClickListener(
//                v -> startActivity(new Intent(CreateUserActivity.this, ForgotActivity.class))
//        );

    }

    private void createUser() {

        progressBar = new ProgressDialog(this);
        progressBar.show();
        progressBar.setContentView(R.layout.progress_dialog);
        Objects.requireNonNull(progressBar.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        String txt_email = email.getText().toString();
        String txt_phone = phoneNo.getText().toString();
        String txt_name = name.getText().toString();
        String txt_password = password.getText().toString();
        String txt_rPassword = rPassword.getText().toString();
        String txt_dob = dob.getText().toString();
        String txt_pinCode = pinCode.getText().toString();

        if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_phone)) {
            Toast.makeText(CreateUserActivity.this, "All Field are Required", Toast.LENGTH_LONG).show();
            create_user.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.custom_btn, null));
            progressBar.dismiss();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()) {
            Toast.makeText(CreateUserActivity.this, "Provide a Valid Email Address", Toast.LENGTH_LONG).show();
            create_user.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.custom_btn, null));
            progressBar.dismiss();

        } else if (txt_password.length() < 6) {
            Toast.makeText(CreateUserActivity.this, "Password should be atLeast 6 character", Toast.LENGTH_LONG).show();
            create_user.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.custom_btn, null));
            progressBar.dismiss();

        } else if (!txt_password.equals(txt_rPassword)) {
//            Toast.makeText(RegisterActivity.this, "Passwords are not match", Toast.LENGTH_LONG).show();
            Snackbar.make(layout, "Passwords are not match", Snackbar.LENGTH_LONG)
                    .setAction("Close", v -> {
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorAccent)).show();
            create_user.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.custom_btn, null));
            progressBar.dismiss();

        } else {
            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .createUser(txt_name, txt_phone, txt_email, txt_password, txt_dob, txt_pinCode);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        progressBar.dismiss();
                        Toast.makeText(CreateUserActivity.this, "User Created!", Toast.LENGTH_LONG).show();
                        if (response.code() == 201) {
                            Intent intent = new Intent(CreateUserActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        email.setText("");
                        password.setText("");
                        name.setText("");
                        phoneNo.setText("");
                        create_user.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.custom_btn, null));
                        finish();

                    } else {
                        progressBar.dismiss();
                        Toast.makeText(CreateUserActivity.this, "User Is Already Register", Toast.LENGTH_LONG).show();
                        create_user.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.custom_btn, null));
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressBar.dismiss();
                    Toast.makeText(CreateUserActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    create_user.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.custom_btn, null));
                }
            });
        }
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

    public void ShowHidePass1(View view) {

        if (view.getId() == R.id.show_pass_btn1) {
            if (rPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.password_hide_asset);
                //Show Password
                rPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.password_visible_asset);
                //Hide Password
                rPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}