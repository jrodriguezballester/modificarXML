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
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.*;

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
        String fileInString = "pelis.xml";           //Archivo de entrada
        String fileOutString = "ejercicio3.xml";     //Archivo de salida
        
        //Cargamos el documento
        Document document = CargarDocumento(fileInString);
       
        // Dos Metodos para leer el DOM Cualquiera es valido
        /*ObtenerDatos(document); //Sabiendo nombre nodos */
        ObtenerNodosyDatos(document);

        // Añadir una pelicula
        AddPeli(document);
        // Mostrar Peliculas que cumplan una condicion
        ShowSomePelis(document);
        // Modificar un texto de un nodo en particular
        EditEtiqueta(document);
        //Guardar Documento en el archivo definido en fileOutString
        SaveFile(document, fileOutString);

    }

    /**
     * Carga el documento a partir del archivo fileIn en XML para trabajar con
     * DOM
     *
     * @param fileIn
     * @return
     */
    private static Document CargarDocumento(String fileStringIn) {
        //String fileIn = "Pelis.xlm";
        try {
            // Creo una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
            // Creo un documentBuilder
            DocumentBuilder builder = factoria.newDocumentBuilder();
            // Obtengo el documento, a partir del XML
            Document document = builder.parse(new File(fileStringIn));

            document.getDocumentElement().normalize();

            /* Codigo para borrar nodos en blanco 
                                          que daban saltos de linea al formatear XML*/
            XPathExpression xpath = XPathFactory.newInstance().newXPath().compile("//text()[normalize-space(.) = '']");
            NodeList blankTextNodes = (NodeList) xpath.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < blankTextNodes.getLength(); i++) {
                blankTextNodes.item(i).getParentNode().removeChild(blankTextNodes.item(i));
            }

            return document;

        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException ex) {
            Logger.getLogger(Actividad_1_3_3.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * Muestra por consola los datos (titulo,ano,duracion,actor) de cada
     * pelicula
     *
     * @param document
     */
    private static void ObtenerDatos(Document document) {
        // Cojo todas las etiquetas peli del documento
        NodeList listaPelis = document.getElementsByTagName("peli");
        // Recorro los nodos (las etiquetas) de listaPelis
        for (int i = 0; i < listaPelis.getLength(); i++) {
            // Cojo el nodo actual
            Node nodo = listaPelis.item(i);
            //Compruebo si el nodo es un elemento (etiqueta XML)
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                // Lo transformo a Element 
                Element ePeli = (Element) nodo;
                // Utilizo metodos de Element para obtener los datos
                String titulo = ePeli.getElementsByTagName("titulo").item(0).getTextContent();
                String ano = ePeli.getElementsByTagName("ano").item(0).getTextContent();
                String duracion = ePeli.getElementsByTagName("duracion").item(0).getTextContent();
                String actor = ePeli.getElementsByTagName("actor").item(0).getTextContent();
                System.out.println("--+--");
                System.out.println("titulo " + titulo);
                System.out.println("ano " + ano);
                System.out.println("duracion " + duracion);
                System.out.println("actor " + actor);
            }
        }
    }

    /**
     * Muestra por consola las etiquetas XML y los datos de cada pelicula
     *
     * @param document
     */
    private static void ObtenerNodosyDatos(Document document) {
        // Cojo todas las etiquetas peli del documento
        NodeList listaPelis = document.getElementsByTagName("peli");
        // Recorro las etiquetas
        for (int i = 0; i < listaPelis.getLength(); i++) {
            // Cojo el nodo actual
            Node nodo = listaPelis.item(i);
            // Compruebo si el nodo es un elemento
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                // Lo transformo a Element
                Element e = (Element) nodo;
                // Obtengo sus hijos
                NodeList listahijos = e.getChildNodes();
                // Recorro sus hijos
                for (int j = 0; j < listahijos.getLength(); j++) {
                    // Obtengo al hijo actual
                    Node hijo = listahijos.item(j);
                    // Compruebo si es un nodo
                    if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                        // Muestro el contenido
                        System.out.println("<" + hijo.getNodeName() + ">"
                                + hijo.getTextContent() + "</" + hijo.getNodeName() + ">");
                    }

                }
                System.out.println("---");
            }
        }
    }

    /**
     * Añade una Pelicula al elemento raiz
     *
     * @param document
     */
    private static void AddPeli(Document document) {
        // obtenemos el elemento raíz del documento (para poder añadir la peli)
        Element eRaiz = document.getDocumentElement();

        // definimos el nodo que contendrá los elementos
        Element ePeli = document.createElement("peli");
        eRaiz.appendChild(ePeli); //añadimos al padre(raiz)

        /* definimos cada uno de los elementos 
         le asignamos un valor, y lo añadimos a su elemento padre*/
        Element eTitulo = document.createElement("titulo");//Creamos nodo-elemento
        Text textoTitulo = document.createTextNode("Titulo de mi peli");//Creamos nodo-texto
        eTitulo.appendChild(textoTitulo);//añadimos el nodo-texto al elemento
        ePeli.appendChild(eTitulo);//añadimos el elemento(con su arbol) al nodo padre ePeli
        // nuevo elemento
        Element eAno = document.createElement("ano");
        eAno.appendChild(document.createTextNode("ano de mi peli"));
        ePeli.appendChild(eAno);
        // nuevo elemento
        Element eDuracion = document.createElement("duracion");
        eDuracion.appendChild(document.createTextNode("130"));
        ePeli.appendChild(eDuracion);
        // nuevo elemento
        Element eActor = document.createElement("actor");
        eActor.appendChild(document.createTextNode("Actor de mi peli"));
        ePeli.appendChild(eActor);
    }

    /**
     * Guarda el documento indentado en el archivo fileOut
     *
     * @param document
     * @param fileOutString
     */
    private static void SaveFile(Document document, String fileOutString) {
        try {

            // clases necesarias finalizar la creación del archivo XML
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(fileOutString));
            transformer.transform(source, result);

        } catch (TransformerException ex) {
            Logger.getLogger(Actividad_1_3_3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Muestra por consola la lista de Peliculas que cumplen una determinada
     * condicion
     *
     * @param document
     */
    private static void ShowSomePelis(Document document) {
        // Cojo todas las etiquetas peli del documento
        NodeList listaPelis = document.getElementsByTagName("peli");
        // Recorro los nodos (las etiquetas) de listaPelis
        for (int i = 0; i < listaPelis.getLength(); i++) {
            // Cojo el nodo actual
            Node nodo = listaPelis.item(i);
            //Compruebo si el nodo es un elemento (etiqueta XML)
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                // Lo transformo a Element 
                Element ePeli = (Element) nodo;
                String duracion = ePeli.getElementsByTagName("duracion").item(0).getTextContent();
                // condicion que quiero cumplir para mostrar la pelicula 
                // Duracion mayor de 120 minutos
                if (Integer.parseInt(duracion) > 120) {
                    //Sabiendo la estructura del Documento es mas facil acceder a los datos
                    String titulo = ePeli.getElementsByTagName("titulo").item(0).getTextContent();
                    String ano = ePeli.getElementsByTagName("ano").item(0).getTextContent();
                    String actor = ePeli.getElementsByTagName("actor").item(0).getTextContent();
                    System.out.println("--+----+----+--");
                    System.out.println("titulo " + titulo);
                    System.out.println("ano " + ano);
                    System.out.println("duracion " + duracion);
                    System.out.println("actor " + actor);
                }
            }
        }
    }

    /**
     * Modificar texto de etiqueta Actor de la Primera Pelicula
     *
     * @param document
     */
    private static void EditEtiqueta(Document document) {
        // Cojo todas las etiquetas peli del documento
        NodeList listaPelis = document.getElementsByTagName("peli");
        //Selecciono el nodo que quiero cambiar
        Node nodo = listaPelis.item(0); // 0 es el indice de la pelicula que quiero cambiar
        //Compruebo si el nodo es un elemento (etiqueta XML)
        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
            // Lo transformo a Element 
            Element ePeli = (Element) nodo;
            //con setTextContent asigno un valor
            ePeli.getElementsByTagName("actor").item(0).setTextContent("Jose Rodriguez");
        }
    }
}
