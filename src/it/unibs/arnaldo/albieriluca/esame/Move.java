package it.unibs.arnaldo.albieriluca.esame;

public class Move {
    private String unitType;
    private String start;
    private String destination;
    private boolean valid;


    public Move(String move){
        String[] parts = move.split(" ");
        if(parts.length != 3){
            this.unitType = "";
            this.start = "";
            this.destination = "";
            this.valid = false;
        }
        else{
            this.unitType = parts[0];
            this.start = parts[1];
            this.destination = parts[2];
            this.valid = true;
        }
    }

    public String getUnitType() {
        return unitType;
    }

    public String getStart() {
        return start;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isValid(){
        return valid;
    }

    @Override
    public String toString() {
        return unitType + ' ' + start + ' ' + destination;
    }
}
