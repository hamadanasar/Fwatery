package com.example.fwatery;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fwatery.Database.Daos.UserDao;
import com.example.fwatery.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.hawk.Hawk;

public class Auth_VM extends ViewModel {

    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public MutableLiveData<String> emailError = new MutableLiveData<>(null);
    public MutableLiveData<String> passwordError = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> progress = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> success = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> Failed = new MutableLiveData<>(null);

    public void LoginCLick() {
        progress.setValue(true);
        if (validate()) {
            Login();
        }

    }

    private void Login() {
        getUser();
    }

    User user;
    private void getUser() {
        user = new User();
        UserDao.getAllUser(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    user = snapshot1.getValue(User.class);
                    if (user.getEmail().equals(email.get().trim()) && email.get().equals("yousef@gmail.com")) {
                        Hawk.put("User", true);
                        success.setValue(true);
                        break;
                    } else if (!user.isState()) {
                        Hawk.put("User", false);
                        success.setValue(true);
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public Boolean validate() {
        boolean isValid = true;
        if (email.get().trim().isEmpty()) {
            emailError.setValue("required");
            isValid = false;
        } else if (!ValidEmail()) {
            emailError.setValue("Please enter a valid email");
            isValid = false;
        } else {
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
