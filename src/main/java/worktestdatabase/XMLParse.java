package worktestdatabase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class XMLParse {

    //файлы
    private static final String FILE_XML1 = "1.xml";
    private static final String FILE_XML2 = "2.xml";
    private static final String FILE_XSL1 = "template.xsl";

    //теги
    private static final String TAG_ENTRIES = "entries";
    private static final String TAG_ENTRY = "entry";
    private static final String TAG_FIELD = "field";

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;

    public XMLParse() {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    //создание xml документа
    public void createXmlFile(ResultSet resultSet){
        try {
            Document document = documentBuilder.newDocument();
            Element entries = document.createElement(TAG_ENTRIES);
            document.appendChild(entries);
            while (resultSet.next()){
                Element entry = document.createElement(TAG_ENTRY);
                entries.appendChild(entry);
				
                Element field = document.createElement(TAG_FIELD);
                field.setTextContent(String.valueOf(resultSet.getInt(1)));
                entry.appendChild(field);
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(System.getProperty("user.dir")+File.separator+FILE_XML1));
            transformer.transform(source, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

   //трансформация документа
    public void transformXmlFile(){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(System.getProperty("user.dir")+File.separator+FILE_XSL1));
            transformer.transform(new StreamSource(System.getProperty("user.dir")+File.separator+FILE_XML1),
                    new StreamResult(System.getProperty("user.dir")+File.separator+FILE_XML2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //вывод арифметической суммы
    public int getSum(){
        int result = 0;
        try {
            Document document = documentBuilder.parse(new File(System.getProperty("user.dir")+File.separator+FILE_XML2));
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName(TAG_ENTRY);
            for (int i = 0; i<nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    result += Integer.parseInt(element.getAttribute(TAG_FIELD));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
