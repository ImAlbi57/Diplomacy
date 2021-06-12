package it.unibs.arnaldo.albieriluca.esame;

/***
 * Class used to manage refactored strings
 * @author AlbieriLuca
 */
public class GameStrings {
    public static final String FILE_PATH = "XML/standard_map.xml";
    public static final String inputMoves = "\n>INSERISCI LE MOSSE CHE VUOI ESEGUIRE ('STOP' per terminare, nessun istruzione per uscire dal gioco):";
    public static final String H = "H";
    public static final String A = "A";
    public static final String F = "F";
    public static final String SPACE = " ";
    public static final String NAZIONI = "Le nazioni ";
    public static final String NON_MUOVE = " non si muove";
    public static final String NON_MUOVONO = "non si muovono";
    public static final String STOP = "STOP";
    public static final String MOSSA = "Muovo %s da %s a %s\n";
    public static final String ARMATA = "un'armata";
    public static final String FLOTTA = "una flotta";
    public static final String SALUTO = "\nGRAZIE PER AVER GIOCATO!!";

    /***
     * Method that returns the right string for the given type
     * @param ut UnitType
     * @return the correct string
     */
    public static String getUnitText(UnitType ut){
        if(ut == UnitType.ARMY)
            return ARMATA;
        else return FLOTTA;
    }
}
