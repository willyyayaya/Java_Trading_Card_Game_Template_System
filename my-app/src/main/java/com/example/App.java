package com.example;

import com.example.game.GameEngine;

/**
 * TCG卡牌遊戲主程式
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "啟動卡牌遊戲引擎..." );
        GameEngine gameEngine = new GameEngine();
        gameEngine.start();
    }
}
