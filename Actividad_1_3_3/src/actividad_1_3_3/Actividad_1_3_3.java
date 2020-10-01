/*
 * ACTIVIDAD 3
a) Lee el documento “pelis.xml” con DOM
b) Añade una nueva película al documento “pelis.xml” con DOM.
c) Muestra la/s películas que duran más de 120 minutos con DOM.
d) Modifica un actor de cualquiera de las películas introducidas y pon tu nombre, usando
DOM. 

 */
package actividad_1_3_3;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Jose Rodriguez
 */
public class Actividad_1_3_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factoria.newDocumentBuilder();
            Document documento =builder.parse(new File("Pelis.xml"));
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(Actividad_1_3_3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
