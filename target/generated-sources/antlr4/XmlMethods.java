import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class XmlMethods {
	/*
	 * Method: XmlMethods
	 * Parameter: 
	 * Return: 
	 * Use: constructor for the xml methods
	 */
	public XmlMethods(){
		createMetadataD();
	}
	
	/*
	 * Method: createMetadataD
	 * Parameter: 
	 * Return: 
	 * Use: metodo que sirve para crear metadata cuando se inicia el programa (Database)
	 */
	public void createMetadataD(){
		try{
			File fT = new File("DB/Databases.md");
			if (!fT.exists()){
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				
				Element rootElement = doc.createElement("Directory");
				doc.appendChild(rootElement);
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(fT);
				transformer.transform(source, result);
				fT.getParentFile().mkdirs(); 
				fT.createNewFile();
			}
		}catch(Exception e){}
	}
	
	/*
	 * Method: addMetadataD
	 * Parameter: String database
	 * Return: 
	 * Use: metodo que crea una nueva entrada en la metadata con el nombre de la database a crear. Se crea cuando se llama a create database
	 */
	public void addMetadataD(String database){
		try{
			File fT = new File("DB/Databases.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			Element nRoot = doc.getDocumentElement();
			Element dNode = doc.createElement("Database");
			Node nameN = doc.createElement("Name");
			Node tElement = doc.createElement("Tables");
			tElement.setTextContent("0");
			Node tNumberElement = doc.createElement("Records");
			tNumberElement.setTextContent("0");
			
			nameN.setTextContent(database);
			dNode.appendChild(nameN);
			dNode.appendChild(tElement);
			dNode.appendChild(tNumberElement);
			nRoot.appendChild(dNode);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fT);
			transformer.transform(source, result);
		}catch(Exception e){
			
		}
	}
	
	/*
	 * Method: createMetadataT
	 * Parameter: String database
	 * Return: 
	 * Use: metodo que crea la metadata cada vez que se crea una database.
	 */
	public void createMetadataT(String database){
		try{
			File fT = new File("DB/"+database+"/Tables.md");
			if (!fT.exists()){
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				
				Element rootElement = doc.createElement("Directory");
				doc.appendChild(rootElement);
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(fT);
				transformer.transform(source, result);
				fT.getParentFile().mkdirs(); 
				fT.createNewFile();
			}
		}catch(Exception e){}
	}
	
	/*
	 * Method: addMetadataT
	 * Parameter: String database actual y String nombre tabla
	 * Return: 
	 * Use: metodo que almacena dentro de la metadata de la base de datos, el nombre de la tabla
	 */
	public void addMetadataT(String DBActual, String table){
		try{
			File fT = new File("DB/"+DBActual+"/Tables.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			Element nRoot = doc.getDocumentElement();
			Element databasesElement = doc.createElement("Table");
			Node nombreNode = doc.createElement("Name");
			nombreNode.setTextContent(table);
			Node recordNode = doc.createElement("Records");
			recordNode.setTextContent("0");
			
			databasesElement.appendChild(nombreNode);
			databasesElement.appendChild(recordNode);
			nRoot.appendChild(databasesElement);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fT);
			transformer.transform(source, result);
		}catch(Exception e){}
	}
	
	/*
	 * Method: changeNameD 
	 * Parameter: String nombre database actual y String nombre database a cambiar
	 * Return: 
	 * Use: metodo que cambia el nombre de la database actual, por el nombre de la nueva; dentro de la metadata de bases de datos.
	 */
	public boolean changeNameD(String database, String databaseNew){
		boolean bExist = false;
		try{
			File fT = new File("DB/Databases.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			for (int i=0; i<doc.getElementsByTagName("Name").getLength(); i++){
				Node nActual = doc.getElementsByTagName("Name").item(i);
				if (nActual.getTextContent().equals(database)){
					nActual.setTextContent(databaseNew);
					bExist = true;
					break;
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fT);
			transformer.transform(source, result);

		}catch(Exception e){e.printStackTrace();}
		return bExist;
	}
	
	/*
	 * Method: changeNameT 
	 * Parameter: string database actual, String tabla actual y String tabla nueva
	 * Return: String tabla nueva
	 * Use: metodo que cambia el nombre de una tabla actual por una nueva, dentro de la metadata de la base de datos actual
	 */
	public String changeNameT(String DBActual, String tActual, String tNew){
		try{
			File fT = new File("DB/"+DBActual+"/Tables.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			for (int i=0; i<doc.getElementsByTagName("Name").getLength(); i++){
				Node nActual = doc.getElementsByTagName("Name").item(i);
				if (nActual.getTextContent().equals(tActual)){
					nActual.setTextContent(tNew);
					break;
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fT);
			transformer.transform(source, result);

		}catch(Exception e){}
		return tNew;
	}
	
	/*
	 * Method: modifyTable
	 * Parameter: String database actual, String nombre tabla
	 * Return: int de cantidades de registros
	 * Use: metodo que sirve para determinar la cantidad de registros dentro de una tabla
	 */
	public int modifyTable(String DBActual, String table){
		int cantRow = 0;
		try{
			File fT = new File("DB/"+DBActual+"/Tables.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			for (int i=0; i<doc.getElementsByTagName("Name").getLength(); i++){
				Node nActual = doc.getElementsByTagName("Name").item(i);
				if (nActual.getTextContent().equals(table)){
					Node nParent = nActual.getParentNode();
					cantRow = Integer.parseInt(nParent.getLastChild().getTextContent());
					nParent.getLastChild().setTextContent(""+(cantRow+1));
					break;
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fT);
			transformer.transform(source, result);

		}catch(Exception e){e.printStackTrace();}
		return cantRow;
	}
	
	/*
	 * Method: modifyDatabase 
	 * Parameter: String database, String tipo de cantidad de tablas o registros
	 * Return: int cantidad de tablas o registros
	 * Use: metodo que retorna la cantidad de tablas (si type = "Table") o la cantidad de registros (si type = "Registros (creo)")
	 */
	public int modifyDatabase(String database, String type){
		int cantRow = 0;
		try{
			File fT = new File("DB/Databases.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
					
			for (int i=0; i<doc.getElementsByTagName(type).getLength(); i++){
				Node nActual = doc.getElementsByTagName(type).item(i);
				if (nActual.getParentNode().getFirstChild().getTextContent().equals(database)){
					Node nParent = nActual.getParentNode();
					for (int j=0; j<nParent.getChildNodes().getLength(); j++){
						Node node = nParent.getChildNodes().item(j);
						if (node.getNodeName().equals(type)){
							cantRow = Integer.parseInt(node.getTextContent());
							node.setTextContent(""+(cantRow+1));
						}
					}					
					break;
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fT);
			transformer.transform(source, result);

		}catch(Exception e){e.printStackTrace();}
		return cantRow;
	}
	
	/*
	 * Method: deleteDatabase
	 * Parameter: String nombre database
	 * Return: 
	 * Use: metodo que elimina la database dentro de la metadata de las bases de datos.
	 */
	public void deleteDatabase(String database){
		try{
			File fT = new File("DB/Databases.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			Element nRoot = doc.getDocumentElement();
			
			for (int i=0; i<doc.getElementsByTagName("Name").getLength(); i++){
				Node nActual = doc.getElementsByTagName("Name").item(i);
				if (nActual.getTextContent().equals(database)){
					Node nParent = nActual.getParentNode();
					nRoot.removeChild(nParent);
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fT);
			transformer.transform(source, result);

		}catch(Exception e){e.printStackTrace();}

	}
	
	/*
	 * Method: deleteDatabase
	 * Parameter: String nombre database
	 * Return: 
	 * Use: metodo que elimina la database dentro de la metadata de las bases de datos.
	 */
	public void deleteTable(String database, String table){
		try{
			int cantRegistros = 0;
			File fT = new File("DB/"+database+"/Tables.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			Element nRoot = doc.getDocumentElement();
			
			for (int i=0; i<doc.getElementsByTagName("Name").getLength(); i++){
				Node nActual = doc.getElementsByTagName("Name").item(i);
				if (nActual.getTextContent().equals(table)){
					Node nParent = nActual.getParentNode();
					for (int j=0; j<nParent.getChildNodes().getLength(); j++){
						Node nActualP = nParent.getChildNodes().item(j);
						if (nActualP.getNodeName().equals("Records")){
							cantRegistros = Integer.parseInt(nActualP.getTextContent());
						}
					}
					nRoot.removeChild(nParent);
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fT);
			transformer.transform(source, result);
			
			//Eliminarlo de la metadata base de datos
			fT = new File("DB/Databases.md");
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			nRoot = doc.getDocumentElement();
			
			for (int i=0; i<doc.getElementsByTagName("Name").getLength(); i++){
				Node nActual = doc.getElementsByTagName("Name").item(i);
				if (nActual.getTextContent().equals(database)){
					Node nParent = nActual.getParentNode();
					for (int j=0; j<nParent.getChildNodes().getLength(); j++){
						Node nActualP = nParent.getChildNodes().item(j);
						if (nActualP.getNodeName().equals("Tables")){
							nActualP.setTextContent(""+(Integer.parseInt(nActualP.getTextContent())-1));
						}
						else if(nActualP.getNodeName().equals("Records")){
							nActualP.setTextContent(""+(Integer.parseInt(nActualP.getTextContent())-cantRegistros));
						}
					}
				}
			}
			
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			source = new DOMSource(doc);
			result = new StreamResult(fT);
			transformer.transform(source, result);
			
		}catch(Exception e){e.printStackTrace();}

	}
	
	/*
	 * Method: getCant
	 * Parameter: String nombre database actual
	 * Return: array int, cantidad de tablas y cantidad de registros
	 * Use: metodo que determina la cantidad de tablas y registros dentro de una base de datos.
	 */
	public int[] getCant(String database){
		int[] cant = new int[2];
		try{
			File fT = new File("DB/Databases.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
					
			
			
			for (int i=0; i<doc.getElementsByTagName("Name").getLength(); i++){
				Node nActual = doc.getElementsByTagName("Name").item(i);
				if (nActual.getTextContent().equals(database)){
					Node nParent = nActual.getParentNode();
					for (int j=1; j<nParent.getChildNodes().getLength(); j++){
						Node node = nParent.getChildNodes().item(j);
						cant[j-1] =	Integer.parseInt(node.getTextContent());
					}					
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fT);
			transformer.transform(source, result);

		}catch(Exception e){e.printStackTrace();}
		return cant;
	}
	
	/*
	 * Method: existDB
	 * Parameter: File f
	 * Return: boolean bExisteArchivo
	 * Use: returns true if theres an existing database, else otherwise
	 */
	public boolean existDB(String database){
		boolean bExist = false;
		try{
			File fT = new File("DB/Databases.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();	
			
			for (int i=0; i<doc.getElementsByTagName("Name").getLength(); i++){
				Node nActual = doc.getElementsByTagName("Name").item(i);
				if (nActual.getTextContent().equals(database)){
					bExist = true;
				}
			}
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer transformer = transFact.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fT);
			transformer.transform(source,result);
		
		}catch(Exception e){}
		return bExist;
	}
	
	/*
	 * Method: existTable
	 * Parameter: String table
	 * Return: boolean bExisteTable
	 * Use: returns true if there is an existing table, else otherwise
	 */
	public boolean existTable(String DBActual, String table){
		boolean bExist = false;
		try{
			File fT = new File("DB/"+DBActual+"/Tables.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();	
			
			for (int i=0; i<doc.getElementsByTagName("Name").getLength(); i++){
				Node nActual = doc.getElementsByTagName("Name").item(i);
				if (nActual.getTextContent().equals(table)){
					bExist = true;
				}
			}
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer transformer = transFact.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fT);
			transformer.transform(source,result);
		
		}catch(Exception e){}
		return bExist;
	}
	
}
