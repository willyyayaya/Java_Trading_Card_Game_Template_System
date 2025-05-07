package com.example.game.card;

import com.example.game.player.Player;

/**
 * 隨從卡 - 可以放置在場上作戰的卡牌
 */
public class Minion extends Card {
    private int attack;
    private int health;
    private int maxHealth;
    private boolean canAttack;
    private boolean hasTaunt;
    private boolean hasDivineShield;
    private boolean hasCharge;
    
    public Minion(String name, int manaCost, String description, Rarity rarity, 
                 int attack, int health) {
        super(name, manaCost, description, rarity);
        this.attack = attack;
        this.health = health;
        this.maxHealth = health;
        this.canAttack = false;
        this.hasTaunt = false;
        this.hasDivineShield = false;
        this.hasCharge = false;
    }
    
    @Override
    public void play(Player player) {
        // 隨從牌的基本效果實現
        System.out.println(name + " 進入戰場!");
    }
    
    @Override
    protected void displaySpecificDetails() {
        System.out.println("類型: 隨從");
        System.out.println("攻擊力: " + attack);
        System.out.println("生命值: " + health);
        
        // 顯示特殊效果
        StringBuilder effects = new StringBuilder("特殊效果: ");
        boolean hasEffects = false;
        
        if (hasTaunt) {
            effects.append("嘲諷 ");
            hasEffects = true;
        }
        if (hasDivineShield) {
            effects.append("聖盾 ");
            hasEffects = true;
        }
        if (hasCharge) {
            effects.append("衝鋒 ");
            hasEffects = true;
        }
        
        if (hasEffects) {
            System.out.println(effects.toString());
        } else {
            System.out.println("特殊效果: 無");
        }
    }
    
    /**
     * 戰吼效果 - 隨從進場時觸發
     */
    public void battlecry() {
        System.out.println(name + " 的戰吼效果觸發!");
        // 默認實現，子類可覆寫
    }
    
    /**
     * 亡語效果 - 隨從死亡時觸發
     */
    public void deathrattle() {
        System.out.println(name + " 的亡語效果觸發!");
        // 默認實現，子類可覆寫
    }
    
    /**
     * 攻擊目標
     */
    public void attack(Minion target) {
        if (!canAttack) {
            System.out.println(name + " 無法攻擊!");
            return;
        }
        
        System.out.println(name + " 攻擊 " + target.getName() + "!");
        
        // 處理神聖護盾
        if (target.hasDivineShield) {
            target.hasDivineShield = false;
            System.out.println(target.getName() + " 的神聖護盾被破壞!");
        } else {
            target.takeDamage(attack);
        }
        
        // 處理反擊傷害
        if (hasDivineShield) {
            hasDivineShield = false;
            System.out.println(name + " 的神聖護盾被破壞!");
        } else {
            takeDamage(target.getAttack());
        }
        
        // 設置已攻擊狀態
        canAttack = false;
    }
    
    /**
     * 攻擊玩家
     */
    public void attackPlayer(Player player) {
        if (!canAttack) {
            System.out.println(name + " 無法攻擊!");
            return;
        }
        
        System.out.println(name + " 攻擊玩家 " + player.getName() + "!");
        player.takeDamage(attack);
        
        // 設置已攻擊狀態
        canAttack = false;
    }
    
    /**
     * 受到傷害
     */
    public void takeDamage(int amount) {
        health -= amount;
        System.out.println(name + " 受到 " + amount + " 點傷害，剩餘生命值: " + health);
        
        if (health <= 0) {
            System.out.println(name + " 死亡!");
            deathrattle();
        }
    }
    
    /**
     * 回復生命值
     */
    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
        System.out.println(name + " 回復 " + amount + " 點生命值，當前生命值: " + health);
    }
    
    /**
     * 回合開始時重置狀態
     */
    public void refreshForNewTurn() {
        canAttack = true;
    }
    
    // Getters and Setters
    public int getAttack() {
        return attack;
    }
    
    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    public int getHealth() {
        return health;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
    public boolean hasTaunt() {
        return hasTaunt;
    }
    
    public void setTaunt(boolean hasTaunt) {
        this.hasTaunt = hasTaunt;
    }
    
    public boolean hasDivineShield() {
        return hasDivineShield;
    }
    
    public void setDivineShield(boolean hasDivineShield) {
        this.hasDivineShield = hasDivineShield;
    }
    
    public boolean hasCharge() {
        return hasCharge;
    }
    
    public void setCharge(boolean hasCharge) {
        this.hasCharge = hasCharge;
        // 衝鋒隨從可以立即攻擊
        if (hasCharge) {
            canAttack = true;
        }
    }
} 