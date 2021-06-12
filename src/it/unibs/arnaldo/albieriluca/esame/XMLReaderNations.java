package it.unibs.arnaldo.albieriluca.esame;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;

/***
 * Class to initialize and manage the reading of the input XML file
 * @author AlbieriLuca
 */
public class XMLReaderNations {

    public static final String ERRORE_INIZ = "Errore nell'inizializzazione del reader:";
    public static final String NATIONS = "nations";
    public static final String N = "n";
    public static final String NATION = "nation";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String UNIT = "unit";
    public static final String NEIGHBOUR = "neighbour";

    private ArrayList<Nation> nations;
    private int[][] adjacency;

    private XMLStreamReader xmlr;


    /***
     * Instantiates the StreamReader and manages exceptions
     * @param path file path
     */
    public XMLReaderNations(String path) {
        nations = new ArrayList<>();
        try {
            XMLInputFactory xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(path, new FileInputStream(path));
        } catch (Exception e) {
            System.out.println(ERRORE_INIZ);
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Nation> getNations() {
        return nations;
    }

    public int[][] getAdjacency() {
        return adjacency;
    }

    /***
     * Reads the cities' data and add them in the arraylist
     */
    public void read() {
        //variabili
        String name = "";
        String type = "";
        String unit = "";
        Nation nation = new Nation("");

        try {
            while (xmlr.hasNext()) {
                //switch per determinare il tipo di evento che incontro nella lettur< dell'XML
                switch (xmlr.getEventType()) {
                    //inizio documento
                    case XMLStreamConstants.START_DOCUMENT: break;
                    //elemento iniziale
                    case XMLStreamConstants.START_ELEMENT:
                        //ricavo il nome dell'elemento iniziale
                        String src = xmlr.getLocalName();

                        //Prendo il numero di nazioni
                        if(src.equals(NATIONS)){
                            for (int i = 0; i < xmlr.getAttributeCount(); i++){
                                if(xmlr.getAttributeLocalName(i).equals(N)){
                                    int dim = Integer.parseInt(xmlr.getAttributeValue(i));
                                    adjacency = new int[dim][dim];
                                }
                            }
                        }

                        //se l'elemento iniziale corrisponde a "city"
                        if(src.equals(NATION)) {
                            //ciclo for per scorrere il numero di attributi presenti nell'elemento iniziale
                            for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                                //ricavo il nome dell'attributo
                                switch (xmlr.getAttributeLocalName(i)) {
                                    //caso dell'attributo name
                                    case NAME:
                                        name = xmlr.getAttributeValue(i);
                                        break;
                                    //caso dell'attributo nome
                                    case TYPE:
                                        type = xmlr.getAttributeValue(i).toUpperCase();
                                        break;
                                    //caso dell'attributo coordinate
                                    case UNIT:
                                        unit = xmlr.getAttributeValue(i).toUpperCase();
                                        break;
                                }
                            }
                            //genero una nuova nazione coi valori in input
                            if(unit.equals(""))
                                nation = new Nation(name, NationType.valueOf(type));
                            else nation = new Nation(name, NationType.valueOf(type), UnitType.valueOf(unit));
                            int pos = nations.indexOf(nation);
                            if(pos != -1){
                                nations.set(pos, nation);
                            }
                            else
                                nations.add(nation);
                        }

                        if(src.equals(NEIGHBOUR)){
                            for (int i = 0; i < xmlr.getAttributeCount(); i++){
                                if(xmlr.getAttributeLocalName(i).equals(NAME)) {
                                    Nation nat = new Nation(xmlr.getAttributeValue(i));
                                    if (!nations.contains(nat))
                                        nations.add(nat);
                                    adjacency[nations.indexOf(nation)][nations.indexOf(nat)] = 1;
                                }
                            }
                        }

                        //passo all'evento successivo
                        xmlr.next();
                        break;


                    //elemento finale
                    case XMLStreamConstants.END_ELEMENT:
                        //se è la chiusura di una nazione, riporto le variabili allo stato iniziale
                        if (xmlr.getLocalName().equals(NATION)){
                            name = "";
                            type = "";
                            unit = "";
                            nation = new Nation("");
                        }

                    //commenti
                    case XMLStreamConstants.COMMENT: break;
                    //caratteri
                    case XMLStreamConstants.CHARACTERS: break;
                }
                //passo all'evento successivo
                xmlr.next();
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
