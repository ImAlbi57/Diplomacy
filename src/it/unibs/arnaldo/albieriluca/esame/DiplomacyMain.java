package it.unibs.arnaldo.albieriluca.esame;

import java.util.ArrayList;

public class DiplomacyMain {
    public static void main(String[] args) {
        int[][] adiacenza = {
                {0,1,0,1},
                {1,0,1,0},
                {0,1,0,1},
                {1,0,1,0},
        };
        ArrayList<Nation> nazioni = new ArrayList<>();
        nazioni.add(new Nation("WEE", Type.LAND));
        nazioni.add(new Nation("NOM", Type.LAND));
        nazioni.add(new Nation("LOL", Type.LAND));
        nazioni.add(new Nation("SUS", Type.LAND));

        GameManager game = new GameManager(nazioni, adiacenza);

        game.startGame();
    }
}