package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private Integer credentialId;
    private String url;
    private String userName;
    private String password;
    private String key;
    private Integer userId;

    public Credential(Integer credentialId, String url, String userName, String password, String key, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    public void printall(){
        System.out.println("credentialId: " + this.credentialId.toString());
        System.out.println("url: " + this.url);
        System.out.println("userName: " + this.userName);
        System.out.println("password: " + this.password);
        System.out.println("key: " + this.key);
        System.out.println("userId: " + this.userId.toString());
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}


