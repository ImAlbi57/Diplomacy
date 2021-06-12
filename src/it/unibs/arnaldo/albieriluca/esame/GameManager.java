package it.unibs.arnaldo.albieriluca.esame;

import it.unibs.arnaldo.toBdefined.utility.InputDati;

import java.util.ArrayList;

public class GameManager {
    private ArrayList<Unit> playerUnit;
    private ArrayList<Nation> nations;
    private int[][] adjacency;

    public GameManager(ArrayList<Nation> nations, int[][] adjacency){
        this.playerUnit = new ArrayList<>();
        this.nations = nations;
        this.adjacency = adjacency;
    }

    public GameManager(int numTerritori){
        this.playerUnit = new ArrayList<>();
        this.nations = new ArrayList<>();
        this.adjacency = new int[numTerritori][numTerritori]; //mette anche tutto a 0 -> giusto
    }

    public boolean isValidNation(String name){
        return nations.indexOf(new Nation(name)) != -1;
    }

    public boolean areAdjacent(String t1, String t2){
        int i = nations.indexOf(new Nation(t1));
        int j = nations.indexOf(new Nation(t2));
        if(i<0 || j<0 || i>=adjacency.length || j>=adjacency.length)
            return false;
        else
            return adjacency[i][j] == 1;
    }

    public ArrayList<Move> inputMoves(){
        ArrayList<Move> moves = new ArrayList<>();
        boolean ciclo = true;

        System.out.println(GameStrings.inputMoves);
        while (ciclo){
            String input = InputDati.leggiStringaNonVuota("");
            if(!input.equalsIgnoreCase("STOP")){
                Move move = new Move(input);
                if(move.isValid())
                    moves.add(move);
            }
            else ciclo = false;
        }

        return moves;
    }

    public void startGame(){
        //Gestione delle mosse
        ArrayList<Move> moves = inputMoves();
        printMoves(moves);
        moves = getUsefulMoves(moves);
        System.out.println();
        printMoves(moves);

        //Controllo i conflitti
    }

    //Controllo che non sia un hold, che il primo carattere sia A, che partenza e destinazione siano valide ed infine che siano adiacenti
    private ArrayList<Move> getUsefulMoves(ArrayList<Move> moves) {
        ArrayList<Move> useful = new ArrayList<>();
        ArrayList<Move> holds = new ArrayList<>();

        for (Move toCheck : moves) {

            //se non è valido salto il ciclo
            if((!toCheck.getUnitType().equals("A") && !toCheck.getUnitType().equals("F"))
                    || !isValidNation(toCheck.getStart())
                    || (!isValidNation(toCheck.getDestination()) && !toCheck.getDestination().equals("H"))
            )
                continue;

            //se è un hold lo metto in holds
            if(toCheck.getDestination().equals("H"))
                holds.add(toCheck);

            //se è adiacente lo metto negli utili
            if(areAdjacent(toCheck.getStart(), toCheck.getDestination()))
                useful.add(toCheck);
        }

        //stampo gli hold
        System.out.print("Le nazioni ");
        for (Move m : holds)
            System.out.print('"' + m.getStart() + '"' + " ");
        System.out.println("non si muovono");

        return useful;
    }

    private void printMoves(ArrayList<Move> moves) {
        for (Move m : moves) {
            System.out.println(m);
        }
    }

}
