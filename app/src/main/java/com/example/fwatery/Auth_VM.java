package com.example.fwatery;

import android.util.Patterns;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Auth_VM extends ViewModel {

    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public MutableLiveData<String> emailError = new MutableLiveData<>(null);
    public MutableLiveData<String> passwordError = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> progress = new MutableLiveData<>(null);
    public MutableLiveData<String> x = new MutableLiveData<>("");

    public void LoginCLick(){
        progress.setValue(true);
        if (validate()){
            x.setValue("x");
        }else {
            progress.setValue(false);
        }
    }

    public Boolean validate(){
        boolean isValid = true;
        if (email.get().trim().isEmpty()){
            emailError.setValue("required");
            isValid = false;
        }else if (!ValidEmail()){
            emailError.setValue("Please enter a valid email");
            isValid = false;
        }else {
            emailError.setValue(null);
        }

        if (password.get().trim().isEmpty()) {
            passwordError.setValue("required");
            isValid = false;
        } else {
            passwordError.setValue(null);
        }
        return isValid;
    }

    private boolean ValidEmail() {
        if (Patterns.EMAIL_ADDRESS.matcher(email.get()).matches())
            return true;
        return false;
    }
}
