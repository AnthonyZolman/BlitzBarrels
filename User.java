package com.example.blaznbarrels;


public class User extends Game{
    public String userName;
    public int highScore;


    public User(String userName, int highScore) {
        this.userName = userName;
        this.highScore = highScore;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    //Gets user high score for display
    public int getHighScore(){
        return this.highScore;
    }


    //Sets user high score
    public void setHighScore(int highScore){
        this.highScore = highScore;
    }

}