package com.example.game.board;

import com.example.game.player.Player;

/**
 * 遊戲板類 - 負責顯示遊戲狀態
 */
public class GameBoard {
    
    public GameBoard() {
        // 初始化遊戲板
    }
    
    /**
     * 顯示當前遊戲狀態
     */
    public void displayBoard(Player player1, Player player2, Player currentPlayer) {
        System.out.println("\n======= 當前遊戲狀態 =======");
        
        // 顯示對手信息
        Player opponent = (currentPlayer == player1) ? player2 : player1;
        System.out.println("對手: " + opponent.getName() + " - 生命值: " + opponent.getHealth() + 
                ", 魔力: " + opponent.getCurrentMana());
        
        // 顯示對手場上的隨從
        System.out.println("對手場上的隨從:");
        if (opponent.getMinionsOnBoard().isEmpty()) {
            System.out.println("  (無)");
        } else {
            displayMinions(opponent);
        }
        
        System.out.println("\n----- 場地分隔線 -----\n");
        
        // 顯示當前玩家場上的隨從
        System.out.println("您場上的隨從:");
        if (currentPlayer.getMinionsOnBoard().isEmpty()) {
            System.out.println("  (無)");
        } else {
            displayMinions(currentPlayer);
        }
        
        // 顯示當前玩家信息
        System.out.println("您: " + currentPlayer.getName() + " - 生命值: " + currentPlayer.getHealth() + 
                ", 魔力: " + currentPlayer.getCurrentMana());
        
        System.out.println("==============================\n");
    }
    
    /**
     * 顯示玩家場上的隨從
     */
    public void displayMinions(Player player) {
        for (int i = 0; i < player.getMinionsOnBoard().size(); i++) {
            var minion = player.getMinionsOnBoard().get(i);
            StringBuilder minionInfo = new StringBuilder();
            minionInfo.append((i+1)).append(". ")
                    .append(minion.getName())
                    .append(" [").append(minion.getAttack()).append("/").append(minion.getHealth()).append("]");
            
            // 添加特效標記
            if (minion.hasTaunt()) {
                minionInfo.append(" (嘲諷)");
            }
            if (minion.hasDivineShield()) {
                minionInfo.append(" (聖盾)");
            }
            
            System.out.println(minionInfo.toString());
        }
    }
} 