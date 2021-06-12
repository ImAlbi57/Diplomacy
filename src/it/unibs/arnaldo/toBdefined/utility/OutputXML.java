package it.unibs.arnaldo.toBdefined.utility;

import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


class E{
    private String XMLtagName;
    private HashMap<String,String> XMLattributes;
    private String XMLvalue;
    private ArrayList<E> XMLsons;

    public String getXMLtagName(){
        return XMLtagName;
    }

    public String getXMLvalue(){
        return XMLvalue;
    }

    public HashMap<String, String> getXMLattributes() {
        return XMLattributes;
    }

    public ArrayList<E> getXMLsons() {
        return XMLsons;
    }
}


/**
 * Metodo per la scrittura del file contenenti i dati elaborati
 * @author toBdefined
 *
 */
public class OutputXML {
    private XMLOutputFactory xmlof = null;
    private XMLStreamWriter xmlw = null;
    private int nTabs = 0;

    /**
     * Metodo costruttore del writer, crea un writer dato il path
     * @param path percorso del file
     */
    public OutputXML(String path) {
        try {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(path), "utf-8");
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del writer:");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo per la scrittura dell'XML completo,
     * richiama i metodi creati in seguito, usati opportunamente e con dei cicli
     * @param root arraylists contenenti i dati
     */
    public void scriviXML(String rootName, E... root) {
        try {
            iniziaXML();

            apriTag(rootName);

            for (E part: root) {
                apriTagConAttr(part.getXMLtagName(), part.getXMLattributes());
                for(E element : part.getXMLsons()) {
                    writeElement(element);
                }
                chiudiTag();
            }

            chiudiTag();

            chiudiXML();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo che inizia il file XML, scrivendo l'intestazione (va a capo)
     * @throws XMLStreamException
     */
    private void iniziaXML() throws XMLStreamException {
        xmlw.writeStartDocument("utf-8", "1.0");aCapo();
    }

    /**
     * Metodo che termina l'XML, scrive i dati immagazzinati nella cache, dentro il file e chiude
     * @throws XMLStreamException
     */
    private void chiudiXML() throws XMLStreamException {
        xmlw.writeEndDocument();
        xmlw.flush();
        xmlw.close();
    }

    /**
     * Metodo da usare per aprire i tag (tabula, stampa e va a capo)
     * @param tagName stringa del nome del tag
     * @throws XMLStreamException
     */
    private void apriTag(String tagName) throws XMLStreamException {
        tabula(nTabs++);
        xmlw.writeStartElement(tagName);
        aCapo();
    }

    /**
     * Metodo da usare per aprire i tag con attributo (tabula, stampa e va a capo)
     * @param tagName stringa col nome del tag
     * @param attr hashmap con le corrispondenze nomeAttr-valAttr
     * @throws XMLStreamException
     */
    private void apriTagConAttr(String tagName, HashMap<String,String> attr) throws XMLStreamException {
        tabula(nTabs++);
        xmlw.writeStartElement(tagName);
        attr.forEach((key, val) -> {
            try {
                xmlw.writeAttribute(key, val);
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        });
        aCapo();
    }

    /**
     * Metodo da usare per chiudere i tag (tabula, stampa e va a capo)
     * @throws XMLStreamException
     */
    private void chiudiTag() throws XMLStreamException {
        tabula(--nTabs);
        xmlw.writeEndElement();
        aCapo();
    }

    /**
     * Metodo da usare per la scrittura di una persona (tabula, stampa e va a capo)
     * @param element persona da stampare
     * @throws XMLStreamException
     */
    private void writeElement(E element) throws XMLStreamException {
        tabula(nTabs++);
        xmlw.writeStartElement(element.getClass().getSimpleName());

        for (Map.Entry<String, String> entry : element.getXMLattributes().entrySet()) {
            xmlw.writeAttribute(entry.getKey(), entry.getValue());
        }
        aCapo();

        for (E el : element.getXMLsons()) {
            writeElement(el);
        }

        chiudiTag();
    }

    /**
     * Metodo da usare per la scrittura dei tag semplici (tabula, stampa e va a capo)
     * tag semplici =solo testo, no attributi, no sotto-tag
     * @param tagName stringa col nome del tag
     * @param characters stringa da stampare tra i tag
     */
    public void writeSimpleTag(String tagName, String characters) {
        try {
            tabula(nTabs);
            xmlw.writeStartElement(tagName);
            xmlw.writeCharacters(characters);
            xmlw.writeEndElement();
            aCapo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo da usare per inserire n tabulazioni
     * @param n numero di indentazioni
     */
    public void tabula(int n) {
        try {
            for(;n>0;n--)
                xmlw.writeCharacters("\t");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo da usare per andare a capo
     */
    public void aCapo() {
        try {
            xmlw.writeCharacters("\r\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

