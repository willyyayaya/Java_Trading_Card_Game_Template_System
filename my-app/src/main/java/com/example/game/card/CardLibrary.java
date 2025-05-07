package com.example.game.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 卡牌圖鑑 - 管理所有可用的卡牌
 */
public class CardLibrary {
    private static Map<String, Card> allCards = new HashMap<>();
    private static List<Minion> allMinions = new ArrayList<>();
    private static List<SpellCard> allSpells = new ArrayList<>();
    
    /**
     * 初始化卡牌圖鑑
     */
    public static void initialize() {
        // 初始化一些預設卡牌
        initializeMinions();
        initializeSpells();
        
        System.out.println("卡牌圖鑑初始化完成，共 " + allCards.size() + " 張卡牌。");
    }
    
    /**
     * 初始化隨從卡
     */
    private static void initializeMinions() {
        // 基礎隨從
        addMinion(new Minion("士兵", 1, "最基本的戰士單位", Rarity.COMMON, 1, 2));
        addMinion(new Minion("弓箭手", 2, "遠程攻擊單位", Rarity.COMMON, 2, 1));
        addMinion(new Minion("騎士", 3, "強大的近戰單位", Rarity.COMMON, 3, 3));
        
        // 嘲諷隨從
        Minion defender = new Minion("防禦者", 4, "保護友方單位的堅固戰士", Rarity.COMMON, 2, 5);
        defender.setTaunt(true);
        addMinion(defender);
        
        // 聖盾隨從
        Minion paladin = new Minion("聖騎士", 5, "受神力護佑的戰士", Rarity.RARE, 4, 2);
        paladin.setDivineShield(true);
        addMinion(paladin);
        
        // 衝鋒隨從
        Minion charger = new Minion("突擊兵", 3, "能夠迅速發動攻擊的精銳部隊", Rarity.RARE, 2, 1);
        charger.setCharge(true);
        addMinion(charger);
        
        // 稀有隨從
        addMinion(new Minion("精靈射手", 4, "精通弓術的精靈", Rarity.RARE, 3, 2));
        addMinion(new Minion("矮人戰士", 3, "頑固的矮人戰士", Rarity.RARE, 2, 4));
        
        // 史詩隨從
        Minion commander = new Minion("指揮官", 6, "激勵士氣的軍隊領袖", Rarity.EPIC, 5, 5);
        addMinion(commander);
        
        // 傳說隨從
        Minion dragon = new Minion("巨龍", 8, "恐怖的飛龍，噴吐烈焰", Rarity.LEGENDARY, 8, 8);
        addMinion(dragon);
    }
    
    /**
     * 初始化法術卡
     */
    private static void initializeSpells() {
        // 傷害法術
        addSpell(new SpellCard("火球術", 4, "發射一顆火球造成傷害", Rarity.COMMON, SpellType.DAMAGE));
        
        // 治療法術
        addSpell(new SpellCard("治療術", 3, "恢復生命值", Rarity.COMMON, SpellType.HEALING));
        
        // 抽牌法術
        addSpell(new SpellCard("智慧秘典", 5, "從牌庫中抽取卡牌", Rarity.RARE, SpellType.DRAW));
        
        // 範圍傷害法術
        addSpell(new SpellCard("烈焰風暴", 7, "對所有敵方隨從造成傷害", Rarity.RARE, SpellType.AOE));
        
        // 增益法術
        addSpell(new SpellCard("力量祝福", 2, "增強一個友方隨從", Rarity.COMMON, SpellType.BUFF));
        
        // 減益法術
        addSpell(new SpellCard("虛弱詛咒", 2, "降低一個敵方隨從的屬性", Rarity.COMMON, SpellType.DEBUFF));
        
        // 變形法術
        addSpell(new SpellCard("變形術", 4, "將敵方隨從變成一隻1/1的綿羊", Rarity.EPIC, SpellType.TRANSFORM));
        
        // 召喚法術
        addSpell(new SpellCard("召喚小兵", 5, "召喚數個1/1的小兵", Rarity.RARE, SpellType.SUMMON));
    }
    
    /**
     * 添加隨從卡到圖鑑
     */
    private static void addMinion(Minion minion) {
        allCards.put(minion.getName(), minion);
        allMinions.add(minion);
    }
    
    /**
     * 添加法術卡到圖鑑
     */
    private static void addSpell(SpellCard spell) {
        allCards.put(spell.getName(), spell);
        allSpells.add(spell);
    }
    
    /**
     * 顯示卡牌圖鑑介面
     */
    public static void showLibrary() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n======= 卡牌圖鑑 =======");
            System.out.println("1. 瀏覽所有隨從卡");
            System.out.println("2. 瀏覽所有法術卡");
            System.out.println("3. 搜尋卡牌");
            System.out.println("0. 返回");
            System.out.print("請選擇: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除輸入緩衝
            
            switch (choice) {
                case 0:
                    return;
                case 1:
                    browseMinions();
                    break;
                case 2:
                    browseSpells();
                    break;
                case 3:
                    searchCard(scanner);
                    break;
                default:
                    System.out.println("無效的選擇!");
            }
        }
    }
    
    /**
     * 瀏覽所有隨從卡
     */
    private static void browseMinions() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n隨從卡列表 (共 " + allMinions.size() + " 張):");
            for (int i = 0; i < allMinions.size(); i++) {
                Minion minion = allMinions.get(i);
                System.out.printf("%2d. %-15s [費用:%d, 攻擊力:%d, 生命值:%d] %s\n", 
                        i+1, minion.getName(), minion.getManaCost(), 
                        minion.getAttack(), minion.getHealth(), 
                        getRaritySymbol(minion.getRarity()));
            }
            
            System.out.println("\n輸入卡牌編號查看詳情，或輸入0返回: ");
            int choice = scanner.nextInt();
            
            if (choice == 0) {
                return;
            } else if (choice > 0 && choice <= allMinions.size()) {
                allMinions.get(choice-1).displayCardDetails();
                
                // 查看完後暫停一下
                System.out.println("按Enter繼續...");
                scanner.nextLine(); // 消耗前面的數字
                scanner.nextLine(); // 等待Enter
            } else {
                System.out.println("無效的選擇!");
            }
        }
    }
    
    /**
     * 瀏覽所有法術卡
     */
    private static void browseSpells() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n法術卡列表 (共 " + allSpells.size() + " 張):");
            for (int i = 0; i < allSpells.size(); i++) {
                SpellCard spell = allSpells.get(i);
                System.out.printf("%2d. %-15s [費用:%d] %s\n", 
                        i+1, spell.getName(), spell.getManaCost(), 
                        getRaritySymbol(spell.getRarity()));
            }
            
            System.out.println("\n輸入卡牌編號查看詳情，或輸入0返回: ");
            int choice = scanner.nextInt();
            
            if (choice == 0) {
                return;
            } else if (choice > 0 && choice <= allSpells.size()) {
                allSpells.get(choice-1).displayCardDetails();
                
                // 查看完後暫停一下
                System.out.println("按Enter繼續...");
                scanner.nextLine(); // 消耗前面的數字
                scanner.nextLine(); // 等待Enter
            } else {
                System.out.println("無效的選擇!");
            }
        }
    }
    
    /**
     * 搜尋卡牌
     */
    private static void searchCard(Scanner scanner) {
        System.out.print("請輸入卡牌名稱關鍵字: ");
        String keyword = scanner.nextLine().trim();
        
        if (keyword.isEmpty()) {
            System.out.println("搜尋關鍵字不能為空!");
            return;
        }
        
        List<Card> results = new ArrayList<>();
        
        // 搜尋含有關鍵字的卡牌
        for (Card card : allCards.values()) {
            if (card.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(card);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("沒有找到含有「" + keyword + "」的卡牌!");
            return;
        }
        
        while (true) {
            System.out.println("\n搜尋結果 (共 " + results.size() + " 張):");
            for (int i = 0; i < results.size(); i++) {
                Card card = results.get(i);
                System.out.printf("%2d. %-15s [費用:%d] %s\n", 
                        i+1, card.getName(), card.getManaCost(), 
                        getRaritySymbol(card.getRarity()));
            }
            
            System.out.println("\n輸入卡牌編號查看詳情，或輸入0返回: ");
            int choice = scanner.nextInt();
            
            if (choice == 0) {
                return;
            } else if (choice > 0 && choice <= results.size()) {
                results.get(choice-1).displayCardDetails();
                
                // 查看完後暫停一下
                System.out.println("按Enter繼續...");
                scanner.nextLine(); // 消耗前面的數字
                scanner.nextLine(); // 等待Enter
            } else {
                System.out.println("無效的選擇!");
            }
        }
    }
    
    /**
     * 根據稀有度返回顯示符號
     */
    private static String getRaritySymbol(Rarity rarity) {
        switch (rarity) {
            case COMMON:
                return "★";
            case RARE:
                return "★★";
            case EPIC:
                return "★★★";
            case LEGENDARY:
                return "★★★★";
            default:
                return "";
        }
    }
    
    /**
     * 根據名稱獲取卡牌
     */
    public static Card getCardByName(String name) {
        return allCards.get(name);
    }
    
    /**
     * 獲取所有隨從卡
     */
    public static List<Minion> getAllMinions() {
        return allMinions;
    }
    
    /**
     * 獲取所有法術卡
     */
    public static List<SpellCard> getAllSpells() {
        return allSpells;
    }
} 