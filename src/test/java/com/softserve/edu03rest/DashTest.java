package com.softserve.edu03rest;

import com.google.gson.Gson;
import okhttp3.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleEntity {

    private String content;

    public SimpleEntity(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "SimpleEntity [content=" + content + "]";
    }
}


public class DashTest {

    @Test
    public void checkTokenlifetime() throws Exception {
        Gson gson = new Gson();
        SimpleEntity simpleEntity;
        String token;
        String lifeTime;
        String result;
        //
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody;
        Request request;
        Response response;
        String resultJson;
        //
        // Get TokenLifetime
        request = new Request
                .Builder()
                .url("http://localhost:8080/tokenlifetime")
                .get()
                .build();
        response = client.newCall(request).execute();
        resultJson = response.body().string();
        simpleEntity = gson.fromJson(resultJson, SimpleEntity.class);
        lifeTime = simpleEntity.getContent();
        //
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(lifeTime, "300000");
        Assertions.assertEquals(response.code(), 200);
        System.out.println("resultJson: " + resultJson);
        System.out.println("simpleEntity: " + simpleEntity);
        //
        // Login
        formBody = new FormBody.Builder()
                .add("name", "admin")
                .add("password", "qwerty")
                .build();
        request = new Request.Builder()
                .url("http://localhost:8080/login")
                .post(formBody)
                .build();
        response = client.newCall(request).execute();
        resultJson = response.body().string();
        simpleEntity = gson.fromJson(resultJson, SimpleEntity.class);
        token = simpleEntity.getContent();
        //
        Assertions.assertTrue(response.isSuccessful());
        System.out.println("resultJson: " + resultJson);
        System.out.println("simpleEntity: " + simpleEntity);
        //
        // Update TokenLifetime
        formBody = new FormBody.Builder()
                .add("token", token)
                .add("time", "901000")
                .build();
        request = new Request.Builder()
                .url("http://localhost:8080/tokenlifetime")
                .put(formBody)
                .build();
        response = client.newCall(request).execute();
        resultJson = response.body().string();
        simpleEntity = gson.fromJson(resultJson, SimpleEntity.class);
        lifeTime = simpleEntity.getContent();
        //
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(lifeTime, "true");
        System.out.println("resultJson: " + resultJson);
        System.out.println("simpleEntity: " + simpleEntity);
        //
        // Get NEW TokenLifetime
        request = new Request.Builder()
                .url("http://localhost:8080/tokenlifetime")
                .get()
                .build();
        response = client.newCall(request).execute();
        resultJson = response.body().string();
        simpleEntity = gson.fromJson(resultJson, SimpleEntity.class);
        lifeTime = simpleEntity.getContent();
        //
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(lifeTime, "901000");
        System.out.println("resultJson: " + resultJson);
        System.out.println("simpleEntity: " + simpleEntity);
        //
        // Logout
        formBody = new FormBody.Builder()
                .add("name", "admin")
                .add("token", token)
                .build();
        request = new Request.Builder()
                .url("http://localhost:8080/logout")
                .post(formBody)
                .build();
        response = client.newCall(request).execute();
        resultJson = response.body().string();
        simpleEntity = gson.fromJson(resultJson, SimpleEntity.class);
        result = simpleEntity.getContent();
        //
        Assertions.assertTrue(response.isSuccessful());
        System.out.println("resultJson: " + resultJson);
        System.out.println("simpleEntity: " + simpleEntity);
        //
        // Reset Server
        request = new Request.Builder()
                .url("http://localhost:8080/reset")
                .get()
                .build();
        response = client.newCall(request).execute();
        resultJson = response.body().string();
        simpleEntity = gson.fromJson(resultJson, SimpleEntity.class);
        result = simpleEntity.getContent();
        //
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(result, "true");
        System.out.println("resultJson: " + resultJson);
        System.out.println("simpleEntity: " + simpleEntity);
        //
    }

}
