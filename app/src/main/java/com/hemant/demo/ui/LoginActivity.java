package com.hemant.demo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hemant.demo.R;
import com.hemant.demo.data.User;
import com.hemant.demo.databinding.ActivityLoginBinding;
import com.hemant.demo.viewmodel.LoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginViewModel loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);

        loginViewModel.getUser().observe(this, loginUser -> {

            if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
                binding.inEmail.setError("Enter an E-Mail Address");
                binding.inEmail.requestFocus();
            }
            else if (!loginUser.isEmailValid()) {
                binding.inEmail.setError("Enter a Valid E-mail Address");
                binding.inEmail.requestFocus();
            }
            else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrPassword())) {
                binding.inPassword.setError("Enter a Password");
                binding.inPassword.requestFocus();
            }
            else if (!loginUser.isPasswordLengthGreaterThan5()) {
                binding.inPassword.setError("Enter at least 6 Digit password");
                binding.inPassword.requestFocus();
            }
            else if(loginUser.isValidCredential()){
                // https://stackoverflow.com/questions/26740185/android-login-registration-with-shared-preferences
                String email = binding.inEmail.getText().toString();
                String Password = binding.inPassword.getText().toString();
                final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("Registered", true);
                editor.putString("Username", email);
                editor.putString("Password", Password);
                editor.apply();
                Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
            }
        });
        binding.login.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this , MainActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}