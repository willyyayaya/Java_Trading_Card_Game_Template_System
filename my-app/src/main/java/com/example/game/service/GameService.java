package com.example.game.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.game.GameEngine;
import com.example.game.model.GameSession;
import com.example.game.player.Player;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 遊戲服務 - 管理遊戲會話和狀態
 */
@Service
public class GameService {
    
    private final Map<String, GameSession> gameSessions = new ConcurrentHashMap<>();
    
    @Autowired
    private GameEngineFactory gameEngineFactory;
    
    /**
     * 創建新的遊戲會話
     */
    public String createGame(String player1Name, String player2Name) {
        String gameId = UUID.randomUUID().toString();
        GameEngine gameEngine = gameEngineFactory.createGameEngine();
        
        GameSession session = new GameSession(gameId, gameEngine, player1Name, player2Name);
        gameSessions.put(gameId, session);
        
        // 初始化遊戲
        gameEngine.initializeGame(player1Name, player2Name);
        
        return gameId;
    }
    
    /**
     * 獲取遊戲會話
     */
    public GameSession getGameSession(String gameId) {
        return gameSessions.get(gameId);
    }
    
    /**
     * 打出卡牌
     */
    public String playCard(String gameId, int cardIndex, Integer boardPosition, boolean showDetail) {
        GameSession session = gameSessions.get(gameId);
        if (session == null) {
            return "遊戲會話不存在";
        }
        
        return session.getGameEngine().playCard(cardIndex, boardPosition, showDetail);
    }
    
    /**
     * 進行攻擊
     */
    public String attack(String gameId, int attackerIndex, int targetIndex) {
        GameSession session = gameSessions.get(gameId);
        if (session == null) {
            return "遊戲會話不存在";
        }
        
        return session.getGameEngine().attack(attackerIndex, targetIndex);
    }
    
    /**
     * 結束回合
     */
    public String endTurn(String gameId) {
        GameSession session = gameSessions.get(gameId);
        if (session == null) {
            return "遊戲會話不存在";
        }
        
        return session.getGameEngine().endTurn();
    }
    
    /**
     * 獲取遊戲狀態
     */
    public String getGameState(String gameId) {
        GameSession session = gameSessions.get(gameId);
        if (session == null) {
            return "遊戲會話不存在";
        }
        
        return session.getGameEngine().getGameState();
    }
    
    /**
     * 移除遊戲會話
     */
    public void removeGame(String gameId) {
        gameSessions.remove(gameId);
    }
} 