package com.example.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.game.service.GameService;

@RestController
@RequestMapping("/game")
public class GameController {
    
    @Autowired
    private GameService gameService;

    @PostMapping("/create")
    public String createGame(@RequestParam String player1Name, 
                           @RequestParam String player2Name) {
        String gameId = gameService.createGame(player1Name, player2Name);
        return "遊戲已創建，遊戲ID: " + gameId;
    }

    @PostMapping("/{gameId}/playCard")
    public String playCard(@PathVariable String gameId,
                          @RequestParam int cardIndex,
                          @RequestParam(required = false) Integer boardPosition,
                          @RequestParam(defaultValue = "false") boolean showDetail) {
        return gameService.playCard(gameId, cardIndex, boardPosition, showDetail);
    }

    @PostMapping("/{gameId}/attack")
    public String attack(@PathVariable String gameId,
                        @RequestParam int attackerIndex, 
                        @RequestParam int targetIndex) {
        return gameService.attack(gameId, attackerIndex, targetIndex);
    }
    
    @PostMapping("/{gameId}/endTurn")
    public String endTurn(@PathVariable String gameId) {
        return gameService.endTurn(gameId);
    }

    @GetMapping("/{gameId}/state")
    public String getGameState(@PathVariable String gameId) {
        return gameService.getGameState(gameId);
    }
    
    @DeleteMapping("/{gameId}")
    public String deleteGame(@PathVariable String gameId) {
        gameService.removeGame(gameId);
        return "遊戲會話已刪除";
    }
} 