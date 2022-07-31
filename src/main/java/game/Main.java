package game;

import engine.GameEngine;
import engine.IGameLogic;

public class Main {
    public static void main(String[] args){
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new DummyGame();
            GameEngine gameEngine = new GameEngine("JavaCube", 800, 600, vSync, gameLogic);
            gameEngine.run();
        }catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        }

    }

