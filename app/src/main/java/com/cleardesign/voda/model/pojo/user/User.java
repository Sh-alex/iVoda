package com.cleardesign.voda.model.pojo.user;

import android.app.Activity;
import android.content.Context;


import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class User {
    private static User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }


    private String fio;
    private String email;
    private String address;
    private String phone;

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void writeUserToFile(Context context, Activity activity) {
        String filename = "user";

        Gson gson = new Gson();
        String json = gson.toJson(this);

        FileOutputStream outputStream;
        try {
            outputStream = context.getApplicationContext().openFileOutput(filename, activity.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readUserFromFile(Context context) {
        String filename = "user";
        FileInputStream fis = null;
        StringBuilder sb = null;

        try {
            fis = context.getApplicationContext().openFileInput(filename);

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();

            Gson gson = new Gson();
            User userFromFile = gson.fromJson(json, User.class);

            this.setFio(userFromFile.getFio());
            this.setEmail(userFromFile.getEmail());
            this.setAddress(userFromFile.getAddress());
            this.setPhone(userFromFile.getPhone());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
