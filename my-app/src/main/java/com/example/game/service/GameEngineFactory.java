package com.example.game.service;

import org.springframework.stereotype.Component;

import com.example.game.GameEngine;

/**
 * 遊戲引擎工廠
 */
@Component
public class GameEngineFactory {
    
    /**
     * 創建新的遊戲引擎實例
     */
    public GameEngine createGameEngine() {
        return new GameEngine();
    }
} 