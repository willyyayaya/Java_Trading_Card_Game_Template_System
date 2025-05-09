package com.example.game;

import java.util.Scanner;

import com.example.game.board.GameBoard;
import com.example.game.card.Card;
import com.example.game.card.Minion;
import com.example.game.player.Player;

/**
 * 遊戲引擎 - 控制遊戲流程和規則
 */
public class GameEngine {
    private GameBoard gameBoard;
    private static Player player1;
    private static Player player2;
    private Player currentPlayer;
    private int turnNumber;
    private boolean gameOver;
    
    public GameEngine() {
        this.gameBoard = new GameBoard();
        this.gameOver = false;
        this.turnNumber = 1;
    }
    
    public void start() {
        System.out.println("歡迎來到卡牌遊戲!");
        
        // 初始化卡牌圖鑑
        com.example.game.card.CardLibrary.initialize();
        System.out.println("卡牌圖鑑已初始化，開始遊戲...");
        
        initializePlayers();
        gameLoop();
    }
    
    private void initializePlayers() {
        // 簡單模擬，實際上會讀取玩家數據或讓玩家選擇牌組
        player1 = new Player("玩家1");
        player2 = new Player("玩家2");
        
        player1.initializeDeck();
        player2.initializeDeck();
        
        player1.drawInitialHand();
        player2.drawInitialHand();
        
        // 隨機決定先手
        currentPlayer = Math.random() > 0.5 ? player1 : player2;
        System.out.println(currentPlayer.getName() + " 將先手進行遊戲");
    }
    
    private void gameLoop() {
        Scanner scanner = new Scanner(System.in);
        
        while (!gameOver) {
            startTurn();
            
            while (true) {
                displayGameState();
                System.out.println(currentPlayer.getName() + " 的回合。請選擇操作: 1.出牌 2.攻擊 3.結束回合 4.查看卡牌圖鑑");
                int choice = scanner.nextInt();
                
                if (choice == 1) {
                    playCard();
                } else if (choice == 2) {
                    attack();
                } else if (choice == 3) {
                    break;
                } else if (choice == 4) {
                    // 查看卡牌圖鑑
                    com.example.game.card.CardLibrary.showLibrary();
                } else {
                    System.out.println("無效的選擇，請重新輸入!");
                }
            }
            
            endTurn();
            checkGameOver();
        }
        
        scanner.close();
        announceWinner();
    }
    
    private void startTurn() {
        System.out.println("======= 回合 " + turnNumber + " =======");
        System.out.println(currentPlayer.getName() + " 的回合開始");
        
        // 回合開始時抽一張牌
        currentPlayer.drawCard();
        
        // 增加可用魔力水晶
        currentPlayer.refreshMana();
        
        // 重置所有隨從的攻擊狀態
        for (Minion minion : currentPlayer.getMinionsOnBoard()) {
            minion.refreshForNewTurn();
        }
    }
    
    private void displayGameState() {
        gameBoard.displayBoard(player1, player2, currentPlayer);
    }
    
    private void playCard() {
        // 實現出牌邏輯
        System.out.println("選擇要打出的牌:");
        // 顯示手牌
        currentPlayer.displayHand();
        
        // 如果手牌為空，無法出牌
        if (currentPlayer.getHand().isEmpty()) {
            System.out.println("您沒有手牌可以打出!");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("選項: 1-N.打出對應卡牌  0.取消  -1.查看卡牌詳細資訊");
        System.out.print("請選擇: ");
        int choice = scanner.nextInt();
        
        // 處理查看卡牌詳情的選項
        if (choice == -1) {
            System.out.print("輸入想查看詳情的卡牌編號: ");
            int cardIndex = scanner.nextInt() - 1; // 將輸入的1-based索引轉換為0-based索引
            
            if (cardIndex >= 0 && cardIndex < currentPlayer.getHand().size()) {
                currentPlayer.displayCardDetails(cardIndex);
                
                // 查看完詳情後，讓玩家決定是否打出此卡牌
                System.out.print("是否打出此卡牌? (1: 是, 0: 否): ");
                choice = scanner.nextInt();
                
                if (choice == 1) {
                    choice = cardIndex + 1;  // 設置為正確的卡牌索引+1
                } else {
                    return; // 返回主選單
                }
            } else {
                System.out.println("無效的卡牌編號!");
                return;
            }
        }
        
        // 選擇要打出的牌
        int cardIndex = choice - 1; // 將輸入的1-based索引轉換為0-based索引
        
        if (cardIndex < -1 || cardIndex >= currentPlayer.getHand().size()) {
            System.out.println("無效的卡牌編號!");
            return;
        }
        
        if (cardIndex == -1) {
            System.out.println("取消出牌。");
            return;
        }
        
        Card card = currentPlayer.getHand().get(cardIndex);
        
        // 檢查魔力是否足夠
        if (card.getManaCost() > currentPlayer.getCurrentMana()) {
            System.out.println("魔力不足! 這張牌需要 " + card.getManaCost() + " 點魔力，但您只有 " + 
                            currentPlayer.getCurrentMana() + " 點魔力。");
            return;
        }
        
        // 處理不同類型的卡牌
        if (card instanceof Minion) {
            // 如果是隨從卡，需要選擇放置位置
            if (currentPlayer.getMinionsOnBoard().size() >= 7) {
                System.out.println("場上隨從已滿(7個)，無法放置更多隨從!");
                return;
            }
            
            int boardPosition = 0;
            
            if (currentPlayer.getMinionsOnBoard().size() > 0) {
                System.out.println("當前場上隨從:");
                currentPlayer.displayMinions();
                System.out.print("選擇放置位置(1-" + (currentPlayer.getMinionsOnBoard().size() + 1) + 
                                ", 輸入0放在最右邊): ");
                boardPosition = scanner.nextInt();
                
                if (boardPosition < 0 || boardPosition > currentPlayer.getMinionsOnBoard().size() + 1) {
                    System.out.println("無效的位置，放置在最右邊。");
                    boardPosition = 0;
                }
                
                if (boardPosition > 0) {
                    boardPosition--; // 將1-based索引轉換為0-based索引
                }
            }
            
            // 調用Player類的playCard方法處理隨從的放置
            currentPlayer.playCard(cardIndex, boardPosition);
            
        } else {
            // 如果是法術卡，可能需要選擇目標
            // 這裡使用簡化的邏輯，直接打出法術卡
            System.out.println(currentPlayer.getName() + " 施放法術: " + card.getName());
            
            // 消耗魔力
            currentPlayer.setCurrentMana(currentPlayer.getCurrentMana() - card.getManaCost());
            
            // 從手牌中移除
            Card removedCard = currentPlayer.getHand().remove(cardIndex);
            
            // 執行卡牌效果
            removedCard.play(currentPlayer);
        }
    }
    
    private void attack() {
        // 實現攻擊邏輯
        System.out.println("選擇攻擊單位和目標:");
        
        // 檢查是否有隨從可以攻擊
        if (currentPlayer.getMinionsOnBoard().isEmpty()) {
            System.out.println("您沒有隨從可以進行攻擊!");
            return;
        }
        
        // 顯示場上的隨從
        System.out.println("您的隨從:");
        gameBoard.displayMinions(currentPlayer);
        
        Scanner scanner = new Scanner(System.in);
        
        // 選擇攻擊隨從
        System.out.print("選擇進行攻擊的隨從編號(輸入0取消): ");
        int attackerIndex = scanner.nextInt() - 1; // 將輸入的1-based索引轉換為0-based索引
        
        if (attackerIndex < -1 || attackerIndex >= currentPlayer.getMinionsOnBoard().size()) {
            System.out.println("無效的隨從編號!");
            return;
        }
        
        if (attackerIndex == -1) {
            System.out.println("取消攻擊。");
            return;
        }
        
        // 獲取攻擊隨從
        Minion attacker = currentPlayer.getMinionsOnBoard().get(attackerIndex);
        
        // 檢查隨從是否可以攻擊
        if (!attacker.canAttack()) {
            if (turnNumber == 1 || attacker.hasCharge()) {
                System.out.println(attacker.getName() + " 已經攻擊過，本回合無法再次攻擊!");
            } else {
                System.out.println(attacker.getName() + " 剛剛放置到場上，除非有衝鋒效果，否則本回合無法攻擊!");
            }
            return;
        }
        
        // 獲取對手
        Player opponent = (currentPlayer == player1) ? player2 : player1;
        
        // 選擇攻擊目標
        System.out.println("選擇攻擊目標:");
        System.out.println("0. 對手英雄: " + opponent.getName() + " (" + opponent.getHealth() + " 生命值)");
        
        // 顯示對手場上的隨從
        System.out.println("對手場上的隨從:");
        if (opponent.getMinionsOnBoard().isEmpty()) {
            System.out.println("  (無)");
        } else {
            for (int i = 0; i < opponent.getMinionsOnBoard().size(); i++) {
                Minion minion = opponent.getMinionsOnBoard().get(i);
                System.out.println((i+1) + ". " + minion.getName() + 
                        " [攻擊力:" + minion.getAttack() + 
                        ", 生命值:" + minion.getHealth() + "]" +
                        (minion.hasTaunt() ? " (嘲諷)" : "") +
                        (minion.hasDivineShield() ? " (聖盾)" : ""));
            }
        }
        
        System.out.print("選擇攻擊目標編號: ");
        int targetIndex = scanner.nextInt();
        
        // 檢查是否有嘲諷隨從，若有，必須優先攻擊嘲諷隨從
        boolean hasTaunt = false;
        for (Minion minion : opponent.getMinionsOnBoard()) {
            if (minion.hasTaunt()) {
                hasTaunt = true;
                break;
            }
        }
        
        if (hasTaunt && (targetIndex == 0 || !opponent.getMinionsOnBoard().get(targetIndex-1).hasTaunt())) {
            System.out.println("必須優先攻擊具有嘲諷的隨從!");
            return;
        }
        
        // 執行攻擊
        if (targetIndex == 0) {
            // 攻擊英雄
            attacker.attackPlayer(opponent);
        } else if (targetIndex > 0 && targetIndex <= opponent.getMinionsOnBoard().size()) {
            // 攻擊隨從
            Minion target = opponent.getMinionsOnBoard().get(targetIndex - 1);
            attacker.attack(target);
            
            // 檢查隨從是否死亡，如果死亡，則從場上移除
            // 先檢查目標隨從是否死亡
            if (target.getHealth() <= 0) {
                System.out.println(target.getName() + " 死亡，從場上移除");
                opponent.getMinionsOnBoard().remove(targetIndex - 1);
            }
            
            // 再檢查攻擊隨從是否死亡
            if (attacker.getHealth() <= 0) {
                System.out.println(attacker.getName() + " 死亡，從場上移除");
                currentPlayer.getMinionsOnBoard().remove(attackerIndex);
            }
        } else {
            System.out.println("無效的目標編號!");
        }
    }
    
    private void endTurn() {
        System.out.println(currentPlayer.getName() + " 的回合結束");
        
        // 切換當前玩家
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        
        // 如果已經輪了一圈，增加回合數
        if (currentPlayer == player1) {
            turnNumber++;
        }
    }
    
    private void checkGameOver() {
        if (player1.getHealth() <= 0 || player2.getHealth() <= 0) {
            gameOver = true;
        }
    }
    
    private void announceWinner() {
        Player winner = (player1.getHealth() <= 0) ? player2 : player1;
        System.out.println("遊戲結束! " + winner.getName() + " 獲勝!");
    }
    
    // 靜態方法用於獲取玩家，供其他類使用
    public static Player getPlayer1() {
        return player1;
    }
    
    public static Player getPlayer2() {
        return player2;
    }
    
    public static Player getOpponent(Player player) {
        return (player == player1) ? player2 : player1;
    }

    /**
     * 獲取當前回合玩家
     */
    public static Player getCurrentPlayer() {
        // 由於 currentPlayer 是一個實例變數而非靜態變數，
        // 所以這個靜態方法無法直接訪問。
        // 但為了保持卡牌圖鑑功能，我們返回 null
        return null;
    }
} 