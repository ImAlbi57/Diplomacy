package it.unibs.arnaldo.albieriluca.esame;

/***
 * Class used to manage the move
 * @author AlbieriLuca
 */
public class Move {
    private UnitType unitType;
    private String start;
    private String destination;
    private boolean valid;

    /***
     * Constructor that takes a string with the complete move and checks if it has 3 parts and the first string
     * If those aren't what we expect, it sets valid to false and it doesn't instanciate the attributes
     * @param move move
     */
    public Move(String move){
        String[] parts = move.split(" ");
        if(parts.length != 3 || (!parts[0].equals(GameStrings.A) && !parts[0].equals(GameStrings.F))){
            this.unitType = null;
            this.start = "";
            this.destination = "";
            this.valid = false;
        }
        else{
            if(parts[0].equals(GameStrings.A))
                this.unitType = UnitType.ARMY;
            if(parts[0].equals(GameStrings.F))
                this.unitType = UnitType.FLEET;
            this.start = parts[1];
            this.destination = parts[2];
            this.valid = true;
        }
    }

    /***
     * UnitType getter
     * @return UnitType
     */
    public UnitType getUnitType() {
        return unitType;
    }

    /***
     * start getter
     * @return start
     */
    public String getStart() {
        return start;
    }

    /***
     * destination getter
     * @return destination
     */
    public String getDestination() {
        return destination;
    }

    /***
     * isValid getter
     * @return true/false
     */
    public boolean isValid(){
        return valid;
    }

    @Override
    public String toString() {
        return unitType.toString() + GameStrings.SPACE + start + GameStrings.SPACE + destination;
    }
}
