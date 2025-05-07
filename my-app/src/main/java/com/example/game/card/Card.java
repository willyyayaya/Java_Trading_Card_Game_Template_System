package com.example.game.card;

import com.example.game.player.Player;

/**
 * 卡牌基類 - 所有卡牌的父類
 */
public abstract class Card {
    protected String name;
    protected int manaCost;
    protected String description;
    protected Rarity rarity;
    
    public Card(String name, int manaCost, String description, Rarity rarity) {
        this.name = name;
        this.manaCost = manaCost;
        this.description = description;
        this.rarity = rarity;
    }
    
    /**
     * 打出卡牌時的效果
     */
    public abstract void play(Player player);
    
    // Getters
    public String getName() {
        return name;
    }
    
    public int getManaCost() {
        return manaCost;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Rarity getRarity() {
        return rarity;
    }
    
    /**
     * 顯示卡牌詳細資訊
     */
    public void displayCardDetails() {
        System.out.println("======= 卡牌資訊 =======");
        System.out.println("名稱: " + name);
        System.out.println("費用: " + manaCost + " 點魔力");
        System.out.println("稀有度: " + getRarityText(rarity));
        System.out.println("描述: " + description);
        displaySpecificDetails();
        System.out.println("========================");
    }
    
    /**
     * 顯示特定類型卡牌的詳細資訊，由子類實現
     */
    protected void displaySpecificDetails() {
        // 基礎實現為空，由子類覆寫
    }
    
    /**
     * 獲取稀有度的中文文本
     */
    private String getRarityText(Rarity rarity) {
        switch (rarity) {
            case COMMON:
                return "普通";
            case RARE:
                return "稀有";
            case EPIC:
                return "史詩";
            case LEGENDARY:
                return "傳說";
            default:
                return "未知";
        }
    }
} 