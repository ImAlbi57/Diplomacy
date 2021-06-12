package it.unibs.arnaldo.albieriluca.esame;

/***
 * Class used for units, it just contains their type
 */
public class Unit {
    private UnitType unitType;

    /***
     * Constructor with the type
     * @param unitType unitType
     */
    public Unit(UnitType unitType){
        this.unitType = unitType;
    }

    /***
     * unitType getter
     * @return unitType
     */
    public UnitType getUnitType(){
        return unitType;
    }
}
