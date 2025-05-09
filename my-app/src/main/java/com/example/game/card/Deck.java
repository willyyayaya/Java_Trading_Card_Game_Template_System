package com.example.game.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 牌組類 - 管理玩家的卡牌集合
 */
public class Deck {
    private List<Card> cards;
    private static final int MAX_DECK_SIZE = 30;
    
    public Deck() {
        this.cards = new ArrayList<>();
    }
    
    /**
     * 初始化牌組，從卡牌圖鑑中選擇卡牌
     */
    public void initialize() {
        // 從卡牌圖鑑中獲取所有可用卡牌
        List<Minion> allMinions = CardLibrary.getAllMinions();
        List<SpellCard> allSpells = CardLibrary.getAllSpells();
        
        if (allMinions.isEmpty() && allSpells.isEmpty()) {
            System.out.println("卡牌圖鑑尚未初始化，使用隨機生成的卡牌");
            initializeRandom();
            return;
        }
        
        // 從卡牌圖鑑中選擇20張隨從卡
        int minionCount = Math.min(20, allMinions.size());
        for (int i = 0; i < minionCount; i++) {
            cards.add(copyCard(allMinions.get(i % allMinions.size())));
        }
        
        // 從卡牌圖鑑中選擇10張法術卡
        int spellCount = Math.min(10, allSpells.size());
        for (int i = 0; i < spellCount; i++) {
            cards.add(copyCard(allSpells.get(i % allSpells.size())));
        }
        
        System.out.println("牌組初始化完成，共有 " + cards.size() + " 張卡牌");
    }
    
    /**
     * 初始化隨機牌組，用於卡牌圖鑑尚未初始化的情況
     */
    private void initializeRandom() {
        // 在這裡，我們簡單地生成一些隨機卡牌
        // 實際遊戲中，牌組應該是預設的或由玩家構建的
        
        Random random = new Random();
        
        // 生成一些隨從卡
        for (int i = 0; i < 20; i++) {
            int cost = random.nextInt(10) + 1; // 1-10費
            int attack = cost + random.nextInt(3) - 1; // 費用±1的攻擊力
            int health = cost + random.nextInt(3) - 1; // 費用±1的生命值
            
            Rarity rarity;
            double rarityRoll = random.nextDouble();
            if (rarityRoll < 0.6) {
                rarity = Rarity.COMMON;
            } else if (rarityRoll < 0.85) {
                rarity = Rarity.RARE;
            } else if (rarityRoll < 0.97) {
                rarity = Rarity.EPIC;
            } else {
                rarity = Rarity.LEGENDARY;
            }
            
            Minion minion = new Minion(
                    "隨從 #" + (i+1), 
                    cost, 
                    "一個普通的隨從", 
                    rarity, 
                    attack, 
                    health);
            
            // 隨機添加特效
            if (random.nextDouble() < 0.2) {
                minion.setTaunt(true);
            }
            if (random.nextDouble() < 0.15) {
                minion.setDivineShield(true);
            }
            if (random.nextDouble() < 0.1) {
                minion.setCharge(true);
            }
            
            cards.add(minion);
        }
        
        // 生成一些法術卡
        for (int i = 0; i < 10; i++) {
            int cost = random.nextInt(10) + 1; // 1-10費
            
            Rarity rarity;
            double rarityRoll = random.nextDouble();
            if (rarityRoll < 0.6) {
                rarity = Rarity.COMMON;
            } else if (rarityRoll < 0.85) {
                rarity = Rarity.RARE;
            } else if (rarityRoll < 0.97) {
                rarity = Rarity.EPIC;
            } else {
                rarity = Rarity.LEGENDARY;
            }
            
            SpellCard spell = new SpellCard(
                    "法術 #" + (i+1), 
                    cost, 
                    "一個普通的法術", 
                    rarity,
                    SpellType.DAMAGE); // 簡化處理，默認為傷害型法術
            
            cards.add(spell);
        }
        
        System.out.println("牌組隨機初始化完成，共有 " + cards.size() + " 張卡牌");
    }
    
    /**
     * 複製卡牌，確保每張卡牌是獨立的實例
     */
    private Card copyCard(Card original) {
        if (original instanceof Minion) {
            Minion originalMinion = (Minion) original;
            Minion copy = new Minion(
                    originalMinion.getName(),
                    originalMinion.getManaCost(),
                    originalMinion.getDescription(),
                    originalMinion.getRarity(),
                    originalMinion.getAttack(),
                    originalMinion.getHealth()
            );
            
            copy.setTaunt(originalMinion.hasTaunt());
            copy.setDivineShield(originalMinion.hasDivineShield());
            copy.setCharge(originalMinion.hasCharge());
            
            return copy;
        } else if (original instanceof SpellCard) {
            SpellCard originalSpell = (SpellCard) original;
            return new SpellCard(
                    originalSpell.getName(),
                    originalSpell.getManaCost(),
                    originalSpell.getDescription(),
                    originalSpell.getRarity(),
                    originalSpell.getSpellType()
            );
        }
        
        return null;
    }
    
    /**
     * 洗牌
     */
    public void shuffle() {
        Collections.shuffle(cards);
        System.out.println("牌組已洗牌");
    }
    
    /**
     * 從牌組頂部抽一張牌
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        
        return cards.remove(0);
    }
    
    /**
     * 檢查牌組是否為空
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
    
    /**
     * 獲取牌組大小
     */
    public int size() {
        return cards.size();
    }
    
    /**
     * 添加卡牌到牌組
     */
    public boolean addCard(Card card) {
        if (cards.size() >= MAX_DECK_SIZE) {
            return false;
        }
        
        cards.add(card);
        return true;
    }
} 