package Wordle.model;

import java.text.DecimalFormat;

public class User {
    private String username;
    private String passwordHash;
    private int wins;
    private int losses;
    private int currentStreak;
    private int maxStreak;
    private DecimalFormat decFormat = new DecimalFormat("#%");

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.wins = 0;
        this.losses = 0;
        this.currentStreak = 0;
        this.maxStreak = 0;
    }

    public User(String username, String passwordHash, int wins, int losses, int currentStreak, int maxStreak) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.wins = wins;
        this.losses = losses;
        this.currentStreak = currentStreak;
        this.maxStreak = maxStreak;
    }

    public String getUsername() {
        return username; 
    }

    public String getPasswordHash() {
        return passwordHash; 
    }

    public int getWins() {
        return wins;
    }
    
    public int getLosses() {
        return losses;
    }

    public int getTotalPlayed() {
        return (getWins() + getLosses());
    }

    public String getWinRate() {
        int totalPlayed = getTotalPlayed();
        if(totalPlayed == 0) totalPlayed = 1; 
        return decFormat.format((double)((double)getWins()/(double)totalPlayed));
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void setMaxStreak(int maxStreak) {
        this.maxStreak = maxStreak;
    }
}
