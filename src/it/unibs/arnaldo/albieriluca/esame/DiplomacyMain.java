package it.unibs.arnaldo.albieriluca.esame;

/***
 * Main class of the Diplomacy Game
 * @author AlbieriLuca
 */
public class DiplomacyMain {

    public static void main(String[] args) {
        XMLReaderNations xmlr = new XMLReaderNations(GameStrings.FILE_PATH);
        xmlr.read();

        GameManager game = new GameManager(xmlr.getNations(), xmlr.getAdjacency());

        game.printUnits();

        game.startGame();
    }
}