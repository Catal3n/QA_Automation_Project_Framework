package Data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// Citește seturile de date din fișierul XML și le transformă în obiecte CheckoutData.
public class CheckoutDataReader {

    public static List<CheckoutData> readFromXml(String fileName) {

        // 1. Deschidem fișierul din src/test/resources.
        //    try ( ... ) îl închide automat la final.
        try (InputStream input = CheckoutDataReader.class.getClassLoader().getResourceAsStream(fileName)) {

            if (input == null) {
                throw new RuntimeException("Nu am găsit fișierul " + fileName + " în src/test/resources");
            }

            // 2. Java citește tot XML-ul și îl transformă într-un "arbore" de elemente.
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(input);

            // 3. Rădăcina arborelui e <dataSets>; copiii ei sunt <dataSet_1>, <dataSet_2>.
            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();

            List<CheckoutData> result = new ArrayList<>();

            // 4. Parcurgem fiecare copil al lui <dataSets>.
            for (int i = 0; i < children.getLength(); i++) {
                Node node = children.item(i);

                // Între tag-uri există și spații și Enter-uri: le sărim,
                // ne interesează doar elementele propriu-zise (<dataSet_N>).
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element dataSet = (Element) node;

                    // 5. Din fiecare <dataSet_N> construim un CheckoutData.
                    result.add(new CheckoutData(
                            getValue(dataSet, "firstName"),
                            getValue(dataSet, "lastName"),
                            getValue(dataSet, "address"),
                            getValue(dataSet, "cardNumber"),
                            getValue(dataSet, "expiry"),
                            getValue(dataSet, "cvv"),
                            getValue(dataSet, "nameOnCard")
                    ));
                }
            }
            return result;

        } catch (Exception e) {
            throw new RuntimeException("Nu am putut citi fișierul " + fileName, e);
        }
    }

    // Întoarce textul dintr-un tag, ex. <firstName>Catalin</firstName> -> "Catalin".
    // Dacă tag-ul lipsește, oprim testul cu un mesaj clar.
    private static String getValue(Element dataSet, String tagName) {
        NodeList elements = dataSet.getElementsByTagName(tagName);
        if (elements.getLength() == 0) {
            throw new RuntimeException("Lipsește <" + tagName + "> din <" + dataSet.getTagName() + ">");
        }
        return elements.item(0).getTextContent().trim();
    }
}
