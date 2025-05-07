package com.example.game.player;

import java.util.ArrayList;
import java.util.List;

import com.example.game.card.Card;
import com.example.game.card.Deck;
import com.example.game.card.Minion;

/**
 * 玩家類 - 代表一個遊戲參與者
 */
public class Player {
    private String name;
    private int health;
    private int maxMana;
    private int currentMana;
    private Deck deck;
    private List<Card> hand;
    private List<Minion> minionsOnBoard;
    
    public Player(String name) {
        this.name = name;
        this.health = 30; // 預設生命值
        this.maxMana = 0;
        this.currentMana = 0;
        this.hand = new ArrayList<>();
        this.minionsOnBoard = new ArrayList<>();
    }
    
    public void initializeDeck() {
        // 初始化牌組，這裡簡單模擬
        this.deck = new Deck();
        deck.initialize();
        deck.shuffle();
    }
    
    public void drawCard() {
        if (deck.isEmpty()) {
            // 牌庫耗盡，受到疲勞傷害
            takeFatigueDamage();
            return;
        }
        
        Card card = deck.drawCard();
        hand.add(card);
        System.out.println(name + " 抽了一張牌: " + card.getName());
    }
    
    public void drawInitialHand() {
        // 初始手牌，通常是3-4張
        for (int i = 0; i < 3; i++) {
            drawCard();
        }
    }
    
    public void playCard(int handIndex, int boardPosition) {
        if (handIndex < 0 || handIndex >= hand.size()) {
            System.out.println("無效的手牌索引!");
            return;
        }
        
        Card card = hand.get(handIndex);
        
        if (card.getManaCost() > currentMana) {
            System.out.println("魔力不足!");
            return;
        }
        
        // 消耗魔力
        currentMana -= card.getManaCost();
        
        // 從手牌中移除
        hand.remove(handIndex);
        
        // 如果是隨從卡，放到場上
        if (card instanceof Minion) {
            Minion minion = (Minion) card;
            if (minionsOnBoard.size() < 7) { // 場上最多7個隨從
                if (boardPosition < 0 || boardPosition > minionsOnBoard.size()) {
                    // 無效位置，附加到最後
                    minionsOnBoard.add(minion);
                } else {
                    minionsOnBoard.add(boardPosition, minion);
                }
                System.out.println(name + " 打出隨從: " + minion.getName());
                
                // 觸發戰吼效果
                minion.battlecry();
            } else {
                System.out.println("場上隨從已滿!");
            }
        } else {
            // 如果是法術卡，處理法術效果
            System.out.println(name + " 施放法術: " + card.getName());
            card.play(this);
        }
    }
    
    public void refreshMana() {
        // 每回合開始，魔力水晶+1，最多10個
        if (maxMana < 10) {
            maxMana++;
        }
        currentMana = maxMana;
        System.out.println(name + " 現在有 " + currentMana + " 點魔力");
    }
    
    public void takeDamage(int amount) {
        health -= amount;
        System.out.println(name + " 受到 " + amount + " 點傷害，剩餘生命值: " + health);
    }
    
    public void heal(int amount) {
        health = Math.min(health + amount, 30); // 最大生命值為30
        System.out.println(name + " 恢復 " + amount + " 點生命值，目前生命值: " + health);
    }
    
    private void takeFatigueDamage() {
        // 牌庫耗盡時受到的疲勞傷害，每次+1
        int fatigueDamage = minionsOnBoard.size() + 1; // 簡單計算疲勞傷害
        takeDamage(fatigueDamage);
    }
    
    public void displayHand() {
        System.out.println(name + " 的手牌:");
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            System.out.println((i+1) + ". " + card.getName() + " [費用:" + card.getManaCost() + "]");
        }
    }
    
    /**
     * 顯示指定手牌的詳細資訊
     */
    public void displayCardDetails(int handIndex) {
        if (handIndex < 0 || handIndex >= hand.size()) {
            System.out.println("無效的手牌索引!");
            return;
        }
        
        Card card = hand.get(handIndex);
        card.displayCardDetails();
    }
    
    public void displayMinions() {
        System.out.println(name + " 的場上隨從:");
        for (int i = 0; i < minionsOnBoard.size(); i++) {
            Minion minion = minionsOnBoard.get(i);
            System.out.println((i+1) + ". " + minion.getName() + 
                    " [攻擊:" + minion.getAttack() + 
                    ", 生命:" + minion.getHealth() + "]");
        }
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getCurrentMana() {
        return currentMana;
    }
    
    public List<Minion> getMinionsOnBoard() {
        return minionsOnBoard;
    }
    
    public List<Card> getHand() {
        return hand;
    }
    
    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }
} 