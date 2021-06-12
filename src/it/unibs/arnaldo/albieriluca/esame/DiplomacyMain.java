package it.unibs.arnaldo.albieriluca.esame;

/***
 * Main class of the Diplomacy Game
 * @author AlbieriLuca
 */
public class DiplomacyMain {

    /***
     * main method, it welcomes the user and uses methods from other classes to start the game
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println(GameStrings.BENVENUTO);

        XMLReaderNations xmlr = new XMLReaderNations(GameStrings.FILE_PATH);
        xmlr.read();

        GameManager game = new GameManager(xmlr.getNations(), xmlr.getAdjacency());
        game.printUnits();
        game.startGame();
    }
}