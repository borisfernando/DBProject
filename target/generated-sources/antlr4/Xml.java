import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Xml {
	/*
	 * Method: XmlMethods
	 * Parameter: 
	 * Return: 
	 * Use: constructor for the xml methods
	 */
	
	public static Document getElementByName(File f){
		Document doc = null;
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(f);
		}catch(Exception e){}
		return doc;
	}
	
	public static NamedNodeMap getTypeColumn(File fT, String column){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			Element modelElement = (Element) rootElement.getFirstChild();
			Element columnElement = (Element) modelElement.getFirstChild();
			NodeList nList = columnElement.getElementsByTagName(column); 
			if (nList.getLength()!=0){
				Element e = (Element)nList.item(0);
				return e.getAttributes();
			}
		}catch(Exception e){}
		return null;
	}
	
	public static boolean existReferenceColumn(String table, String column, File fT){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			Element constraintElement = (Element) doc.getDocumentElement().getFirstChild().getLastChild();
			Element fkConstraintElement = (Element) constraintElement.getLastChild();
			for (int i=0; i<fkConstraintElement.getChildNodes().getLength(); i++){
				Element eActual = (Element) fkConstraintElement.getChildNodes().item(i);
				if (eActual.getAttribute("Table").equals(table)){
					for (int j=0; j<eActual.getChildNodes().getLength(); j++){
						Element hActual = (Element) eActual.getChildNodes().item(j);
						if (hActual.getAttribute("Reference").equals(column)){
							return true;
						}
					}
				}
			}
		}catch(Exception e){}
		return false;
	}
	
	public static boolean existReferenceTable(String table, File fT){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			Element constraintElement = (Element) doc.getDocumentElement().getFirstChild().getLastChild();
			Element fkConstraintElement = (Element) constraintElement.getLastChild();
			for (int i=0; i<fkConstraintElement.getChildNodes().getLength(); i++){
				Element eActual = (Element) fkConstraintElement.getChildNodes().item(i);
				if (eActual.getAttribute("Table").equals(table)){
					return true;
				}
			}
		}catch(Exception e){}
		return false;
	}
	
	public static boolean existColumnInTable(File fT, String column){
		boolean retorno = true;
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			Element modelElement = (Element) rootElement.getFirstChild();
			Element columnElement = (Element) modelElement.getFirstChild();
			if (columnElement.getElementsByTagName(column).getLength()==0){
				return false;
			}
		}catch(Exception e){}
		return retorno;
	}
	
	public static void serializeArray(String DBActual, HashMap<String,ArrayList<String>> hm){
		try
        {
               FileOutputStream fos = new FileOutputStream("DB/"+DBActual+"/pk.ser");
               ObjectOutputStream oos = new ObjectOutputStream(fos);
               oos.writeObject(hm);
               oos.close();
               fos.close();               
        }catch(Exception ioe)
         {
               ioe.printStackTrace();
         }
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String,ArrayList<String>> deSerializeArray(String DBActual){
		HashMap<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		try
	      {
	         FileInputStream fis = new FileInputStream("DB/"+DBActual+"/pk.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         map = (HashMap<String, ArrayList<String>>) ois.readObject();
	         ois.close();
	         fis.close();
	      }catch(Exception ioe)
	      {
	         ioe.printStackTrace();
	      }
		return map;
	}
	
	public static String getPrimaryKeyReferenceTable(Element modelElement){
		String nombre = "";
		try{
			Element constraintElement = (Element) modelElement.getLastChild();
			Element pkNameElement = (Element) constraintElement.getFirstChild();
			Element pkRefElement = (Element) pkNameElement.getFirstChild();
			nombre = pkRefElement.getTextContent();
		}catch(Exception e){}
		return nombre;
	}
	
	public static String getPrimaryKeyTable(Element modelElement){
		String nombre = "";
		try{
			Element constraintElement = (Element) modelElement.getLastChild();
			Element pkNameElement = (Element) constraintElement.getFirstChild();
			nombre = pkNameElement.getAttribute("Name");
		}catch(Exception e){}
		return nombre;
	}
	
	public static ArrayList<String> getColumnsPKTable(Element modelElement){
		ArrayList<String> nombres = new ArrayList<String>();
		try{
			Element constraintElement = (Element) modelElement.getLastChild();
			Element pkElement = (Element) constraintElement.getFirstChild();

			for (int i=0; i<pkElement.getChildNodes().getLength(); i++){
				Node nActual = pkElement.getChildNodes().item(i);
				String aActual = nActual.getTextContent();
				nombres.add(aActual);
			}
		}catch(Exception e){}
		return nombres;
	}
	
	/*
	 * Method: createMetadataD
	 * Parameter: 
	 * Return: 
	 * Use: metodo que sirve para crear metadata cuando se inicia el programa (Database)
	 */
	public static void createMetadataD(){
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
	
	public static void updateDatabases(){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			File f = new File("DB/");
			File fMDD = new File("DB/");
			File[] fDBCarpeta = f.listFiles();
			HashMap<String, HashMap<String, Integer>> hmDatabase = new HashMap<String, HashMap<String,Integer>>();
			
			for (int i=0; i<fDBCarpeta.length; i++){
				File fDB = fDBCarpeta[i];
				HashMap<String, Integer> hmTables = new HashMap<String, Integer>();
				if (fDB.isDirectory()){
					File fMDT = new File("DB/");
					File[] fDBTable = fDB.listFiles();
					for (int j=0; j<fDBTable.length; j++){
						File fTable = fDBTable[j];
						if (fTable.isFile() && fTable.getName().contains(".xml")){
							String name = fTable.getName();
							String nameT = name.substring(0, name.indexOf(".xml"));
							
							docFactory = DocumentBuilderFactory.newInstance();
							docBuilder = docFactory.newDocumentBuilder();
							Document doc = docBuilder.parse(fTable);
							doc.getDocumentElement().normalize();

							Node rootNode = doc.getDocumentElement();
							Node databaseNode = rootNode.getLastChild();
							int cant = databaseNode.getChildNodes().getLength();
							
							hmTables.put(nameT, cant);
						}
						else if(fTable.getName().contains(".md")){
							fMDT = fTable;
						}
					}
					for (String key: hmTables.keySet()){
						docFactory = DocumentBuilderFactory.newInstance();
						docBuilder = docFactory.newDocumentBuilder();
						Document doc = docBuilder.parse(fMDT);
						doc.getDocumentElement().normalize();
						
						Node rootNode = doc.getDocumentElement();
						
						NodeList nodos = doc.getElementsByTagName("Name");
						boolean contiene = false;
						for (int k = 0; k<nodos.getLength(); k++){
							Node nActual = nodos.item(k);
							if (nActual.getTextContent().equals(key)){
								Node nParent = nActual.getParentNode();
								Node nRecords = nParent.getLastChild();
								nRecords.setTextContent(""+hmTables.get(key));
								contiene = true;
							}
						}
						if (!contiene){
							Node nName = doc.createElement("Name");
							Node nRecords = doc.createElement("Records");
							nName.setTextContent(key);
							nRecords.setTextContent(""+hmTables.get(key));
							Node nTable = doc.createElement("Table");
							nTable.appendChild(nName);
							nTable.appendChild(nRecords);
							rootNode.appendChild(nTable);
						}
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						DOMSource source = new DOMSource(doc);
						StreamResult result = new StreamResult(fMDT);
						transformer.transform(source, result);
					}
					hmDatabase.put(fDB.getName(), hmTables);
				}
				else{
					fMDD = fDB;
				}
			}
			for (String key: hmDatabase.keySet()){
				docFactory = DocumentBuilderFactory.newInstance();
				docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(fMDD);
				doc.getDocumentElement().normalize();
				
				Node rootNode = doc.getDocumentElement();
				
				NodeList nodos = doc.getElementsByTagName("Name");
				boolean contiene = false;
				for (int k = 0; k<nodos.getLength(); k++){
					Node nActual = nodos.item(k);
					if (nActual.getTextContent().equals(key)){
						Node nParent = nActual.getParentNode();
						Node nTables = nParent.getChildNodes().item(1);
						Node nRecords = nParent.getLastChild();
						int contador = 0;
						for (String key2: hmDatabase.get(key).keySet()){
							contador+=hmDatabase.get(key).get(key2);
						}
						nTables.setTextContent(""+hmDatabase.get(key).size());
						nRecords.setTextContent(""+contador);
						contiene = true;
					}
				}
				if (!contiene){
					if (!contiene){
						Node nDatabase = doc.createElement("Database");
						Node nName = doc.createElement("Name");
						Node nRecords = doc.createElement("Records");
						Node nTable = doc.createElement("Tables");
						
						nName.setTextContent(key);
						File fT = new File("DB/"+key+"/");
						nTable.setTextContent(""+fT.listFiles().length);
						int contador = 0;
						for (String key2: hmDatabase.get(key).keySet()){
							contador+=hmDatabase.get(key).get(key2);
						}
						nRecords.setTextContent(""+contador);
						
						nDatabase.appendChild(nName);
						nDatabase.appendChild(nTable);
						nDatabase.appendChild(nRecords);
						rootNode.appendChild(nDatabase);
					}
				}
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(fMDD);
				transformer.transform(source, result);
			}
			
		}catch(Exception e){}
	}
	
	public static void guardarDatabase(String database, HashMap<String, Document> hm){
		try{
			for ( String key : hm.keySet() ) {
			    File fT = new File("DB/"+database+"/"+key+".xml");
			    TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(hm.get(key));
				StreamResult result = new StreamResult(fT);
				transformer.transform(source, result);
			}
			
		}catch(Exception e){}
	}
	
	public static HashMap<String, Document> cargarDatabase(String database){
		HashMap<String, Document> hm = new HashMap<String, Document>();
		try{
			File f = new File("DB/"+database+"/");
			File[] fIn = f.listFiles();
			for (int i=0; i<fIn.length; i++) {
			    File fT = fIn[i];
			    if (fT.isFile() && fT.getName().contains(".xml")){
				    String t1 = fT.getName();
				    String t2 = t1.substring(0, t1.indexOf(".xml"));
				    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.parse(fT);
					hm.put(t2, doc);
			    }
			}
			
		}catch(Exception e){}
		return hm;
	}
	
	/*
	 * Method: addMetadataD
	 * Parameter: String database
	 * Return: 
	 * Use: metodo que crea una nueva entrada en la metadata con el nombre de la database a crear. Se crea cuando se llama a create database
	 */
	public static void addMetadataD(String database){
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
	public static void createMetadataT(String database){
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
	public static void addMetadataT(String DBActual, String table){
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
	public static boolean changeNameD(String database, String databaseNew){
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
	public static String changeNameT(String DBActual, String tActual, String tNew){
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
	 * Use: metodo que sirve para determinar la cantidad de registros dentro de una tabla y agregarle 1 a la cantidad
	 */
	public static int modifyTable(String DBActual, String table){
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
	 * Method: cantRegisTable
	 * Parameter: String database actual, String nombre tabla
	 * Return: int de cantidades de registros
	 * Use: metodo que sirve para determinar la cantidad de registros dentro de una tabla
	 */
	public static int cantRegisTable(String DBActual, String table){
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
	public static int modifyDatabase(String database, String type){
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
	public static void deleteDatabase(String database){
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
	public static void deleteTable(String database, String table){
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
	public static int[] getCant(String database){
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
	public static boolean existDB(String database){
		boolean bExist = false;
		try{
			File fT = new File("DB/Databases.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();	
			
			for (int i=0; i<doc.getElementsByTagName("Name").getLength(); i++){
				Node nActual = doc.getElementsByTagName("Name").item(i);
				if (nActual.getTextContent().toLowerCase().equals(database.toLowerCase())){
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
	public static boolean existTable(String DBActual, String table){
		boolean bExist = false;
		try{
			File fT = new File("DB/"+DBActual+"/Tables.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();	
			
			for (int i=0; i<doc.getElementsByTagName("Name").getLength(); i++){
				Node nActual = doc.getElementsByTagName("Name").item(i);
				if (nActual.getTextContent().toLowerCase().equals(table.toLowerCase())){
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
