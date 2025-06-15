package com.example.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameEngine gameEngine;

    @PostMapping("/start")
    public String startGame() {
        gameEngine.start();
        return "遊戲已啟動";
    }

    @PostMapping("/playCard")
    public String playCard(@RequestParam int cardIndex,
                          @RequestParam(required = false) Integer boardPosition,
                          @RequestParam(defaultValue = "false") boolean showDetail) {
        return gameEngine.playCard(cardIndex, boardPosition, showDetail);
    }

    @PostMapping("/attack")
    public String attack(@RequestParam int attackerIndex, @RequestParam int targetIndex) {
        return gameEngine.attack(attackerIndex, targetIndex);
    }

    @GetMapping("/state")
    public String getGameState() {
        return gameEngine.getGameState();
    }

    // 之後可以加上出牌、攻擊等 API
} 