package com.example.fish_bites;

public class loginresponse {
    private String token; // Assuming your API returns a token
    private String message; // Adjust according to your API response

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}

