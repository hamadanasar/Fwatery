package com.example.fwatery.Auth;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.fwatery.Auth_VM;
import com.example.fwatery.BaseActivity;
import com.example.fwatery.MainActivity;
import com.example.fwatery.R;
import com.example.fwatery.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {

    Auth_VM authVm;
    ActivityLoginBinding loginBinding;

    // oncreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authVm = new ViewModelProvider(this).get(Auth_VM.class);

        loginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginBinding.setLvm(authVm);

        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        authVm.emailError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null){
                    loginBinding.Lemail.setError(s);
                }
            }
        });

        authVm.passwordError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null){
                    loginBinding.Lpassword.setError(s);
                }
            }
        });

        authVm.progress.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null && aBoolean){
                    showProgressDialog("Loading...");
                }else if (aBoolean != null && !aBoolean) {
                    hideProgressDialog();
                }
            }
        });

        authVm.Failed.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean!=null){
                    hideProgressDialog();
                    showMessage("Email or Password Error", "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    },true);
                }
            }
        });

        authVm.success.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

    }

}