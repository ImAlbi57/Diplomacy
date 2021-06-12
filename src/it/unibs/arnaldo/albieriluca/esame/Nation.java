package it.unibs.arnaldo.albieriluca.esame;

/***
 * Class that describes a nation
 */
public class Nation {
    private String name;
    private NationType nationType;
    private Unit unit;

    /***
     * Constructor with every attribute
     * @param name name
     * @param nationType nationType
     * @param unitType unitType
     */
    public Nation(String name, NationType nationType, UnitType unitType){
        this.name = name;
        this.nationType = nationType;
        this.unit = new Unit(unitType);
    }

    /***
     * Constructor with some attribute
     * @param name name
     * @param nationType nationType
     */
    public Nation(String name, NationType nationType){
        this.name = name;
        this.nationType = nationType;
        this.unit = null;
    }

    /***
     * Constructor with the name attribute (used for the equals method)
     * @param name name
     */
    public Nation(String name){
        this.name = name;
    }

    /***
     * name getter
      * @return name
     */
    public String getName() {
        return name;
    }

    /***
     * nationType getter
     * @return nationType
     */
    public NationType getNationType() {
        return nationType;
    }

    /***
     * unit getter
     * @return unit
     */
    public Unit getUnit() {
        return unit;
    }

    /***
     * unit setter
     * @param unit unit
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /***
     * Override of the Object class equals method
     * @param o object
     * @return true/false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nation nation = (Nation) o;
        return this.name.equals(nation.getName());
    }
}
