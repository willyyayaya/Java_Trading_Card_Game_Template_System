package com.example.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        try {
            System.out.println("🚀 正在啟動 TCG 卡牌遊戲...");
            SpringApplication.run(Application.class, args);
        } catch (Exception e) {
            System.err.println("❌ 啟動失敗: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        System.out.println("✅ TCG 卡牌遊戲啟動成功!");
        System.out.println("🌐 請訪問: http://localhost:8081/tcg/");
        System.out.println("🎮 開始享受遊戲吧!");
    }
} 