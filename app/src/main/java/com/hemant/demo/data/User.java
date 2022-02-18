package com.hemant.demo.data;

import android.util.Patterns;

public class User {

    public String strEmailAddress;
    public String strPassword;

    public User(String EmailAddress, String Password) {
        strEmailAddress = EmailAddress;
        strPassword = Password;
    }

    public String getStrEmailAddress() {
        return strEmailAddress;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getStrEmailAddress()).matches();
    }
    public boolean isValidCredential()
    {
        return this.strEmailAddress.equalsIgnoreCase("hemantjain1999@gmail.com") && this.strPassword.equals("123456");
    }

    public boolean isPasswordLengthGreaterThan5() {
        return getStrPassword().length() > 5;
    }
}
