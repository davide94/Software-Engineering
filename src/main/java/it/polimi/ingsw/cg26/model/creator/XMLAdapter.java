package it.polimi.ingsw.cg26.model.creator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * 
 */
public class XMLAdapter implements DOMParserInterface {

    /**
     * Default constructor
     */
    public XMLAdapter() {
    }

    /**
     * @param
     * @return Items
     */
    public Document parse(String file, String schema) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new File(schema));
        Schema s = schemaFactory.newSchema(schemaFile);
        Validator validator = s.newValidator();
        validator.validate(new DOMSource(document));

        return document;
    }

}