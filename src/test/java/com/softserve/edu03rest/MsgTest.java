package com.softserve.edu03rest;

import com.google.gson.Gson;
import okhttp3.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class SigninOk {
    private int id;
    private String email;
    private String roleName;
    private String accessToken;
    private String refreshToken;

    public SigninOk(int id, String email, String roleName, String accessToken, String refreshToken) {
        this.id = id;
        this.email = email;
        this.roleName = roleName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}

public class MsgTest {

    private static String token;

    @Test
    @Order(1)
    public void checkLogin() throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody;
        Request request;
        Response response;
        String resultJson;
        Gson gson = new Gson();
        /*
        formBody = new FormBody.Builder()
                .add("email", "admin@gmail.com")
                .add("password", "Admin_QC24")
                .build();
        */
        String jsonBody =  new StringBuilder()
                .append("{")
                .append("\"email\":\"admin@gmail.com\",")
                .append("\"password\":\"Admin_QC24\"")
                .append("}").toString();

        RequestBody requestBody = RequestBody.create(jsonBody,
                MediaType.parse("application/json; charset=utf-8"));
        //
        request = new Request
                .Builder()
                .url("http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/api/signin")
                .post(requestBody)
                .addHeader("accept", "*/*")
                .addHeader("Content-Type", "application/json")
                .build();
        response = client.newCall(request).execute();
        resultJson = response.body().string();
        //
        System.out.println("resultJson: " + resultJson);
        SigninOk signinOk = gson.fromJson(resultJson, SigninOk.class);
        token = signinOk.getAccessToken();
        System.out.println("AccessToken: " + token);
    }

    @Test
    @Order(2)
    public void checkUser() throws Exception {
        String lifeTime;
        String result;
        //
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody;
        Request request;
        Response response;
        String resultJson;
        //
        request = new Request
                .Builder()
                //.url("https://speak-ukrainian.org.ua/dev/api/messages/recipient/1")
                //.url("https://speak-ukrainian.org.ua/api/roles")
                .url("http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/api/user/2")
                .get()
                .addHeader("accept", "*/*")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        response = client.newCall(request).execute();
        resultJson = response.body().string();
        //
        System.out.println("resultJson: " + resultJson);
    }


}
