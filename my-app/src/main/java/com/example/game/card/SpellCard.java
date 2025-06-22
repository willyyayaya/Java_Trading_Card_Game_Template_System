package com.example.game.card;

import java.util.List;
import java.util.Random;

import com.example.game.player.Player;

/**
 * 法術卡 - 一次性效果的卡牌
 */
public class SpellCard extends Card {
    private SpellType spellType;
    private int value; // 效果值，如傷害量、治療量等
    
    public SpellCard(String name, int manaCost, String description, Rarity rarity, SpellType spellType) {
        super(name, manaCost, description, rarity);
        this.spellType = spellType;
        
        // 根據費用生成效果值，通常費用越高效果越強
        this.value = manaCost + (int)(Math.random() * 3);
    }
    
    @Override
    public void play(Player player) {
        System.out.println("施放法術: " + name);
        
        // 根據法術類型執行不同效果
        switch (spellType) {
            case DAMAGE:
                applyDamage(player);
                break;
            case HEALING:
                applyHealing(player);
                break;
            case DRAW:
                applyDraw(player);
                break;
            case AOE:
                applyAOE(player);
                break;
            case BUFF:
                applyBuff(player);
                break;
            case DEBUFF:
                applyDebuff(player);
                break;
            case TRANSFORM:
                applyTransform(player);
                break;
            case SUMMON:
                applySummon(player);
                break;
        }
    }
    
    // 各種法術效果的實現
    private void applyDamage(Player player) {
        System.out.println("造成 " + value + " 點傷害");
        // TODO: 重構後實現目標選擇功能
        System.out.println("法術效果暫時禁用，需要重構為Spring服務");
    }
    
    private void applyHealing(Player player) {
        System.out.println("恢復 " + value + " 點生命值");
        // 治療自己
        if (player.getHealth() < 30) {
            player.heal(value);
        } else if (!player.getMinionsOnBoard().isEmpty()) {
            // 或者治療受傷的隨從
            Random random = new Random();
            List<Minion> minions = player.getMinionsOnBoard();
            int targetIndex = random.nextInt(minions.size());
            minions.get(targetIndex).heal(value);
        }
    }
    
    private void applyDraw(Player player) {
        int drawAmount = Math.min(value, 3); // 最多抽3張
        System.out.println("抽 " + drawAmount + " 張牌");
        for (int i = 0; i < drawAmount; i++) {
            player.drawCard();
        }
    }
    
    private void applyAOE(Player player) {
        System.out.println("對所有敵方隨從造成 " + value + " 點傷害");
        // TODO: 重構後實現範圍攻擊功能
        System.out.println("AOE法術效果暫時禁用，需要重構為Spring服務");
    }
    
    private void applyBuff(Player player) {
        System.out.println("使一個友方隨從獲得+" + value + "/+" + value);
        if (!player.getMinionsOnBoard().isEmpty()) {
            Random random = new Random();
            List<Minion> minions = player.getMinionsOnBoard();
            int targetIndex = random.nextInt(minions.size());
            Minion target = minions.get(targetIndex);
            target.setAttack(target.getAttack() + value);
            target.setHealth(target.getHealth() + value);
            System.out.println(target.getName() + " 獲得增益，現在是 " + 
                    target.getAttack() + "/" + target.getHealth());
        }
    }
    
    private void applyDebuff(Player player) {
        System.out.println("使一個敵方隨從獲得-" + value + "/-" + value);
        // TODO: 重構後實現減益功能
        System.out.println("減益法術效果暫時禁用，需要重構為Spring服務");
    }
    
    private void applyTransform(Player player) {
        System.out.println("變形法術效果");
        // TODO: 重構後實現變形功能
        System.out.println("變形法術效果暫時禁用，需要重構為Spring服務");
    }
    
    private void applySummon(Player player) {
        System.out.println("召喚 " + value + " 個1/1的小兵");
        for (int i = 0; i < value && player.getMinionsOnBoard().size() < 7; i++) {
            Minion token = new Minion("小兵", 1, "被召喚的1/1小兵", Rarity.COMMON, 1, 1);
            player.getMinionsOnBoard().add(token);
        }
    }
    
    @Override
    protected void displaySpecificDetails() {
        System.out.println("類型: 法術");
        System.out.println("法術類型: " + getSpellTypeText(spellType));
        System.out.println("效果值: " + value);
    }
    
    /**
     * 獲取法術類型的中文文本描述
     */
    private String getSpellTypeText(SpellType type) {
        switch (type) {
            case DAMAGE:
                return "傷害 (對目標造成" + value + "點傷害)";
            case HEALING:
                return "治療 (回復" + value + "點生命值)";
            case DRAW:
                return "抽牌 (抽" + Math.min(value, 3) + "張牌)";
            case AOE:
                return "範圍傷害 (對所有敵方隨從造成" + value + "點傷害)";
            case BUFF:
                return "增益 (使一個友方隨從獲得+" + value + "/+" + value + ")";
            case DEBUFF:
                return "減益 (使一個敵方隨從獲得-" + value + "/-" + value + ")";
            case TRANSFORM:
                return "變形 (將一個隨從變成1/1小羊)";
            case SUMMON:
                return "召喚 (召喚" + value + "個1/1小兵)";
            default:
                return "未知";
        }
    }
    
    // Getters
    public SpellType getSpellType() {
        return spellType;
    }
    
    public int getValue() {
        return value;
    }
} 