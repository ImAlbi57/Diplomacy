package it.unibs.arnaldo.albieriluca.esame;

import it.unibs.arnaldo.toBdefined.utility.InputDati;

import java.util.ArrayList;
import java.util.HashSet;

/***
 * Class of the Diplomacy Game Manager, it contains almost every method of the game
 * @author AlbieriLuca
 */
public class GameManager {
    private ArrayList<Nation> nations;
    private int[][] adjacency;

    HashSet<String> roundHasMoved;
    ArrayList<Move> roundMoves;

    /***
     * Constructor with nations and adjacency matrix
     * @param nations nations
     * @param adjacency adjacency matrix
     */
    public GameManager(ArrayList<Nation> nations, int[][] adjacency){
        this.nations = nations;
        this.adjacency = adjacency;
    }

    /***
     * Method that returns a nation from its name
     * @param name nation name
     * @return the nation or null if not found
     */
    public Nation getNation(String name){
        int pos = nations.indexOf(new Nation(name));
        if(pos == -1)
            return null;
        return nations.get(pos);
    }

    /***
     * Method that checks if a nation is valid
     * @param name name
     * @return true/false
     */
    public boolean isValidNation(String name){
        return nations.contains(new Nation(name));
    }

    /***
     * Method that checks if two Nations are Adjacent
     * @param t1 first nation
     * @param t2 second nation
     * @return true/false
     */
    public boolean areAdjacent(String t1, String t2){
        int i = nations.indexOf(new Nation(t1));
        int j = nations.indexOf(new Nation(t2));
        if(i<0 || j<0 || i>=adjacency.length || j>=adjacency.length)
            return false;
        else
            return adjacency[i][j] == 1;
    }

    /***
     * Method used to start the game, it calls other methods and prints messages
     */
    public void startGame(){
        while(true){
            //Gestione delle mosse
            ArrayList<Move> moves = getUsefulMoves(inputMoves());
            if(moves.size() == 0)
                break;
            //printMoves(moves);
            System.out.println();

            this.roundMoves = moves;
            this.roundHasMoved = new HashSet<>();
            //Controllo i conflitti
            manageMovements();
        }

        System.out.println(GameStrings.SALUTO);
    }

    /***
     * Method used to manage SYNTACTICALLY CORRECT movements, checking if nations are free or if they get free
     */
    private void manageMovements() {
        int i=0;
        while (i < roundMoves.size()) {
            Move move = roundMoves.get(i);

            //Controllo se si libera
            if(getNation(move.getDestination()).getUnit() == null){
                //controllo che la stessa nazione muova una volta sola
                if(!roundHasMoved.contains(move.getStart())){
                    performMove(move);
                    roundHasMoved.add(move.getStart());
                }
                i++;
            }
            else
                moveIfItGetsFree(roundMoves, i);
        }
    }

    /***
     * Method used to perform a move, it also tells the player
     * @param move move
     */
    private void performMove(Move move) {
        System.out.printf(GameStrings.MOSSA, GameStrings.getUnitText(move.getUnitType()), move.getStart(), move.getDestination());
        getNation(move.getDestination()).setUnit(new Unit(move.getUnitType()));
        getNation(move.getStart()).setUnit(null);
    }

    /***
     * Method used to check if a nation gets free RECURSIVELY, if it gets free it also performs the moves from the end
     * @param moves moves
     * @param indexToFree index of the nation to check if it gets free
     * @return true/false (used inside the method for recursion)
     */
    private boolean moveIfItGetsFree(ArrayList<Move> moves, int indexToFree) {
        Move move = moves.get(indexToFree);
        Nation dest = getNation(move.getDestination());

        //se il territorio ha già mosso ritorno false
        if(roundHasMoved.contains(move.getStart())){
            moves.remove(move);
            return false;
        }

        //se il territorio di destinazione non è occupato ritorno true
        if(dest.getUnit() == null){
            performMove(move);
            roundHasMoved.add(move.getStart());
            return true;
        }

        //controllo se si libera, considerando che NON può avvenire lo scambio di posizioni (IT<-->BR)
        for(int j = indexToFree; j< moves.size(); j++){
            //trovato
            Move newPos = moves.get(j);
            if(newPos.getStart().equals(dest.getName())){
                //se le truppe nel territorio di destinazione vanno verso il territorio corrente ritorno false
                if(newPos.getDestination().equals(move.getStart())){
                    moves.remove(move);
                    return false;
                }
                //se le truppe nel territorio di destinazione NON vanno verso il territorio corrente richiamo il metodo
                else{
                    boolean res = moveIfItGetsFree(moves, j);
                    if(res){
                        performMove(move);
                        roundHasMoved.add(move.getStart());
                    }
                    else{
                        moves.remove(move);
                    }
                    return res;
                }
            }
        }

        moves.remove(move);
        return false;
    }

    /***
     * Method to check if a UnitType is compatible with a NationType
      * @param ut unitType
     * @param nt nationType
     * @return true/false
     */
    public boolean isCompatible(UnitType ut, NationType nt){
        switch (ut){
            case ARMY: return nt.equals(NationType.LAND) || nt.equals(NationType.COAST);
            case FLEET: return nt.equals(NationType.WATER) || nt.equals(NationType.COAST);
            default: return false;
        }
    }

    /***
     * Method used to check if a move has to be performed (checks if it is syntactically valid, if the types are compatible and if the nations are adjacent)
     * It also prints the requested holds
     * @param moves moves
     * @return useful moves
     */
    //Controllo che non sia un hold, che il primo carattere sia A, che partenza e destinazione siano valide ed infine che siano adiacenti
    private ArrayList<Move> getUsefulMoves(ArrayList<Move> moves) {
        ArrayList<Move> useful = new ArrayList<>();
        ArrayList<Move> holds = new ArrayList<>();

        for (Move toCheck : moves) {

            //se non è valido salto il ciclo
            if(!toCheck.isValid()
                    || !isValidNation(toCheck.getStart())
                    || (!isValidNation(toCheck.getDestination()) && !toCheck.getDestination().equals("H"))
            )
                continue;

            //controllo se l'unità è nella nazione e se corrisponde al tipo passato
            if(getNation(toCheck.getStart()).getUnit() == null || toCheck.getUnitType() != getNation(toCheck.getStart()).getUnit().getUnitType())
                continue;

            //se è un hold lo metto in holds
            if(toCheck.getDestination().equals(GameStrings.H)){
                holds.add(toCheck);
                continue;
            }

            //controllo se l'unità si può spostare verso quel tipo di territorio
            if(!isCompatible(toCheck.getUnitType(), getNation(toCheck.getDestination()).getNationType())) {
                continue;
            }

            //se è adiacente lo metto negli utili
            if(areAdjacent(toCheck.getStart(), toCheck.getDestination()))
                useful.add(toCheck);
        }

        //stampo gli hold
        if(holds.size() > 0){
            if(holds.size() == 1)
                System.out.println(holds.get(0).getStart() + GameStrings.NON_MUOVE);
            else{
                System.out.print(GameStrings.NAZIONI);
                for (Move m : holds)
                    System.out.print('"' + m.getStart() + '"' + " ");
                System.out.println(GameStrings.NON_MUOVONO);
            }
        }

        return useful;
    }

    /***
     * Method that takes the moves in input untill the user writes "stop"
     * @return the arraylist of moves
     */
    private ArrayList<Move> inputMoves(){
        ArrayList<Move> moves = new ArrayList<>();
        boolean ciclo = true;

        System.out.println(GameStrings.inputMoves);
        while (ciclo){
            String input = InputDati.leggiStringaNonVuota("");
            if(!input.equalsIgnoreCase(GameStrings.STOP)){
                Move move = new Move(input);
                if(move.isValid())
                    moves.add(move);
            }
            else ciclo = false;
        }

        return moves;
    }

    /***
     * Method used to print the units in every nation
     */
    public void printUnits(){
        System.out.println(GameStrings.UNITS);
        for (Nation nat : nations) {
            Unit unit = nat.getUnit();
            if(unit != null)
                System.out.println(nat.getName() + ": " + unit.getUnitType());
        }
    }
}
