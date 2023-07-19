package com.cbi_solar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WinnerListModel {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("winner_name")
    @Expose
    private String winner_name;
    @SerializedName("token_no")
    @Expose
    private String token;
    @SerializedName("prize")
    @Expose
    private String prize;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWinner_name() {
        return "Winner Name:- "+winner_name;
    }

    public void setWinner_name(String winner_name) {
        this.winner_name = winner_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrize() {
        return "Prize:- "+ prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}