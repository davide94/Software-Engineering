package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.exceptions.ParserErrorException;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;

/**
 * 
 */
public class XMLAdapter implements DOMParserInterface {

    /**
     * Default constructor
     */
    public XMLAdapter() {
        // Nothing to do here
    }

    /**
     * @param fileName
     * @param schemaName
     * @return Items
     */
    @Override
    public Document parse(String fileName, String schemaName) throws ParserErrorException {
        ClassLoader loader = XMLAdapter.class.getClassLoader();
        InputStream file = loader.getResourceAsStream(fileName);
        InputStream schema = loader.getResourceAsStream(schemaName);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaFile = new StreamSource(schema);
            Schema s = schemaFactory.newSchema(schemaFile);
            Validator validator = s.newValidator();
            validator.validate(new DOMSource(document));

            return document;
        } catch (Exception e) {
            throw new ParserErrorException(e);
        }
        /*try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaFile = new StreamSource(new File(schema));
            Schema s = schemaFactory.newSchema(schemaFile);
            Validator validator = s.newValidator();
            validator.validate(new DOMSource(document));

            return document;
        } catch (Exception e) {
            throw new ParserErrorException(e);
        }*/
    }
}