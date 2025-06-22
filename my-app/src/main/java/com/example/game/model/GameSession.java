package com.example.game.model;

import java.time.LocalDateTime;

import com.example.game.GameEngine;

/**
 * 遊戲會話模型
 */
public class GameSession {
    private String gameId;
    private GameEngine gameEngine;
    private String player1Name;
    private String player2Name;
    private LocalDateTime createdAt;
    private boolean isActive;
    
    public GameSession(String gameId, GameEngine gameEngine, String player1Name, String player2Name) {
        this.gameId = gameId;
        this.gameEngine = gameEngine;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }
    
    // Getters and Setters
    public String getGameId() {
        return gameId;
    }
    
    public GameEngine getGameEngine() {
        return gameEngine;
    }
    
    public String getPlayer1Name() {
        return player1Name;
    }
    
    public String getPlayer2Name() {
        return player2Name;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
} 