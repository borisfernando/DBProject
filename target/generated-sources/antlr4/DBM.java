/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			DBM.java
 * Proposito:
 * 			Clase que realiza diferentes acciones para la base de datos
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DBM {
	
	/*
	 * Method deleteDirectory
	 * Parameter File directory
	 * Return boolean delete
	 * Use: returns true if the directory was deleted, false otherwise
	 */

	/*
	 * Nombre: getNamesConstraint
	 * Proposito: Retorna los nombres de todas las constraints
	 * Parametro: Elemento constraint
	 * Retorno: ArrayList donde estan almacenados todos los nombres de las constraints
	 */
	public static ArrayList<String> getNamesConstraint(Element constraintElement){
		ArrayList<String> names = new ArrayList<String>();
		try{
			Element pkElement = (Element) constraintElement.getFirstChild();
			Element pkElementChild = (Element) pkElement.getChildNodes().item(0);
			names.add(pkElementChild.getAttribute("Name"));
			Element fkElement = (Element) constraintElement.getLastChild();
			NodeList nlist = fkElement.getElementsByTagName("FK");
			for (int i=0; i<nlist.getLength(); i++){
				Element eActual = (Element) nlist.item(i);
				names.add(eActual.getAttribute("Name"));
			}
		}catch(Exception e){}
		return names;
	}
	
	/*
	 * Nombre: getColumnsByTable
	 * Proposito: Retorna todas las columnas que tiene una tabla
	 * Parametro: Tabla y Database actual
	 * Retorno: ArrayList donde estan almacenados todos los nombres de las columnas de la tabla especificada.
	 */
	public static ArrayList<String> getColumnsByTable(String table,String DBActual){
		ArrayList<String> retorno = new ArrayList<String>();
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File("DB/"+DBActual+"/"+table+".xml"));
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			Element columnElement = (Element) rootElement.getFirstChild();
			Element modelElement = (Element) columnElement.getFirstChild();
			
			for (int i=0; i<modelElement.getChildNodes().getLength(); i++){
				retorno.add(modelElement.getChildNodes().item(i).getNodeName());
			}
		}catch(Exception e){e.printStackTrace();}
		return retorno;
	}
	
	/*
	 * Nombre: isFk
	 * Proposito: Retorna si la columna dada es primary key
	 * Parametro: Columna a revisar y el constraintElement
	 * Retorno: True si es primary key y false de lo contrario.
	 */
	public static boolean isFk(String column, Element constraintElement){
		try{
			Element fkElement = (Element) constraintElement.getLastChild();
			NodeList nodeFK = fkElement.getElementsByTagName("FK");
			for (int i=0; i<nodeFK.getLength(); i++){
				Element eActual = (Element) nodeFK.item(i);
				if (eActual.getTextContent().equals(column)){
					return true;
				}
			}
		}catch(Exception e){
			
		}
		return false;
	}

	/*
	 * Nombre: isFk
	 * Proposito: Retorna si la columna dada es foreign key
	 * Parametro: Columna a revisar y el constraintElement
	 * Retorno: True si es foreign key y false de lo contrario.
	 */
	public static boolean isPk(String column, Element constraintElement){
		try{
			Element pkElement = (Element) constraintElement.getFirstChild();
			for (int i=0; i<pkElement.getChildNodes().getLength(); i++){
				Element e = (Element) pkElement.getChildNodes().item(i);
				if (e.getTextContent().equals(column)){
					return true;
				}
			}
		}catch(Exception e){
			
		}
		return false;
	}
	
	/*
	 * Nombre: getTypeColumnInDocument
	 * Proposito: Retorna el tipo de la columna en el documento actual
	 * Parametro: Columna a revisar y el documento
	 * Retorno: El tipo de la columna actual
	 */
	public static String getTypeColumnInDocument(String column, Document doc){
		doc.getDocumentElement().normalize();
		Element rootElement = doc.getDocumentElement();
		Element modelElement = (Element) rootElement.getFirstChild();
		Element columnElement = (Element) modelElement.getFirstChild();
		Element nlist = (Element) columnElement.getElementsByTagName(column).item(0);
		return nlist.getAttribute("Type");
	}
	
	/*
	 * Nombre: changeColumnValue
	 * Proposito: Modifica todos los valores de la columna dada
	 * Parametro: Tabla actual, columna a modificar, texto anterior, texto actual y el documento
	 * Retorno: El documento actualizado
	 */
	public static Document changeColumnValue(String table,String column, String oldValue, String value, Document doc){
		try{
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			Element databaseElement = (Element) rootElement.getLastChild();
			
			NodeList listRows = databaseElement.getElementsByTagName(column);
			int cont=0;
			for (int i=0; i<listRows.getLength(); i++){
				Element eActual = (Element) listRows.item(i);
				if (eActual.getTextContent().equals(oldValue)){
					eActual.setTextContent(value);
					cont++;
				}
			}
			System.out.println("UPDATE("+cont+") in table "+table);
		}catch(Exception e){e.printStackTrace();}
		return doc;
	}

	/*
	 * Nombre: deleteColumnValue
	 * Proposito: Elimina el valor de la columna actual
	 * Parametro: Tabla actual, columna actual y el documento
	 * Retorno: El documento actualizado
	 */
	public static Document deleteColumnValue(String TableActual, String condition, Document doc){
		try{
			String exp = "/Table/Database/"+TableActual+"_Row"+condition;
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			Element databaseElement = (Element) rootElement.getLastChild();
			
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(exp);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			
			int cont = nodes.getLength();
			for (int i=0; i<nodes.getLength(); i++){
				Node n = nodes.item(i);
				databaseElement.removeChild(n);
			}
			System.out.println("DELETE("+cont+") in table "+TableActual);
		}catch(Exception e){}
		return doc;
	}
	
	/*
	 * Nombre: deleteDirectory
	 * Proposito: Elimina el directorio
	 * Parametro: Archivo del directorio a eliminar
	 * Retorno: True si se elimino y false de lo contrario
	 */
	public static boolean deleteDirectory(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}
	
	/*
	 * Nombre: getElementByName
	 * Proposito: Retorna el documento de la tabla (archivo) dado
	 * Parametro: Archivo o Tabla actual
	 * Retorno: Documento pedido
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
	
	/*
	 * Nombre: getTypeColumn
	 * Proposito: Retorna las caracteristicas de la columna actual
	 * Parametro: Columna actual y tabla de donde obtenerlo
	 * Retorno: Atributos con los elementos pedidos
	 */
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
	
	/*
	 * Nombre: existReferenceColumn
	 * Proposito: Retorna si existe alguna referencia a esa columna en el archivo dado
	 * Parametro: Tabla actual, columna a revisar y archivo actual
	 * Retorno: True si es tiene referencia y false de lo contrario.
	 */
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
		}catch(Exception e){e.printStackTrace();}
		return false;
	}
	
	/*
	 * Nombre: existReferenceTable
	 * Proposito: Retorna si existe alguna referencia a esa tabla en el archivo dado
	 * Parametro: Tabla a revisar y archivo actual
	 * Retorno: True si es tiene referencia y false de lo contrario.
	 */
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
	
	/*
	 * Nombre: existColumnInTable
	 * Proposito: Retorna si existe una columna en una tabla
	 * Parametro: Archivo (tabla) actual y la columna a revisar
	 * Retorno: True si existe y false de lo contrario.
	 */
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
		}catch(Exception e){e.printStackTrace();}
		return retorno;
	}
	
	/*
	 * Nombre: existColumnInTable
	 * Proposito: Retorna si existe una columna en una tabla
	 * Parametro: Documento (tabla) actual y la columna a revisar
	 * Retorno: True si existe y false de lo contrario.
	 */
	public static boolean existColumnInTable(Document doc, String column){
		boolean retorno = true;
		try{
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			Element modelElement = (Element) rootElement.getFirstChild();
			Element columnElement = (Element) modelElement.getFirstChild();
			if (columnElement.getElementsByTagName(column).getLength()==0){
				return false;
			}
		}catch(Exception e){e.printStackTrace();}
		return retorno;
	}
	
	/*
	 * Nombre: serializeArray
	 * Proposito: Serializa o guarda el arreglo de primarykey
	 * Parametro: Database actual y las primary keys
	 * Retorno: null
	 */
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
         }
	}
	
	/*
	 * Nombre: deSerializeArray
	 * Proposito: Retorna las primary keys
	 * Parametro: Database actual
	 * Retorno: HashMap con todas las primarykeys
	 */
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
	      }
		return map;
	}
	
	/*
	 * Nombre: getPrimaryKeyReferenceTable
	 * Proposito: Retorna el nombre de la tabla referenciada
	 * Parametro: Documento a revisar
	 * Retorno: String nombre de la tabla referenciada
	 */
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
	
	/*
	 * Nombre: getPrimaryKeyTable
	 * Proposito: Retorna el nombre de la primaryKey
	 * Parametro: Elemento constraint
	 * Retorno: Nombre de la primary key
	 */
	public static String getPrimaryKeyTable(Element modelElement){
		String nombre = "";
		try{
			Element constraintElement = (Element) modelElement.getLastChild();
			Element pkNameElement = (Element) constraintElement.getFirstChild();
			nombre = pkNameElement.getAttribute("Name");
		}catch(Exception e){}
		return nombre;
	}
	
	/*
	 * Nombre: getColumnsPKTable
	 * Proposito: Retorna las columnas que son primarykey
	 * Parametro: Elemento actual
	 * Retorno: Arreglo con el nombre de las columnas a ser primarykey
	 */
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
	 * Nombre: getColumnFKTable
	 * Proposito: Retorna la columna que es foreign key
	 * Parametro: Columna a revisar y el archivo de la tabla actual
	 * Retorno: El valor de la columna
	 */
	public static String getColumnFKTable(String column, File f){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(f);
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			Element modelElement = (Element) rootElement.getFirstChild();
			Element constraintElement = (Element) modelElement.getLastChild();
			Element fkElement = (Element) constraintElement.getLastChild();
			NodeList fkColumns = fkElement.getElementsByTagName("Column");
			for (int i=0; i<fkColumns.getLength(); i++){
				Element eActual = (Element) fkColumns.item(i);
				if (eActual.getAttribute("Reference").equals(column)){
					return eActual.getTextContent();
				}
			}
		}catch(Exception e){}
		return "";
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
	
	/*
	 * Nombre: existpdateDeletedData
	 * Proposito: Metodo que actualiza si se elimino alguna tabla de forma externa
	 * Parametro: null
	 * Retorno: null
	 */
	public static void updateDeletedData(){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			ArrayList<String> databases = new ArrayList<String>();
			File folder = new File("DB/");
			for (File f: folder.listFiles()){
				ArrayList<String> tables = new ArrayList<String>();
				if (f.isDirectory()){
					databases.add(f.getName());
					for (File f2: f.listFiles()){
						if (f2.isFile() && f2.getName().contains(".xml")){
							String n1 = f2.getName();
							String n2 = n1.substring(0, n1.indexOf(".xml"));
							tables.add(n2);
						}
					}
					Document doc = docBuilder.parse(new File("DB/"+f.getName()+"/Tables.md"));
					doc.getDocumentElement().normalize();
					
					Element rootElement = doc.getDocumentElement();
					NodeList nodeList = rootElement.getElementsByTagName("Name");
					for (int i=0; i<nodeList.getLength(); i++){
						if (!tables.contains(nodeList.item(i).getTextContent())){
							Element eParent = (Element) nodeList.item(i).getParentNode().getParentNode();
							eParent.removeChild(nodeList.item(i).getParentNode());
							i--;
						}
					}
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File("DB/"+f.getName()+"/Tables.md"));
					transformer.transform(source, result);
				}
			}
			Document doc = docBuilder.parse(new File("DB/Databases.md"));
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			NodeList nodeList = rootElement.getElementsByTagName("Name");
			for (int i=0; i<nodeList.getLength(); i++){
				if (!databases.contains(nodeList.item(i).getTextContent())){
					Element eParent = (Element) nodeList.item(i).getParentNode().getParentNode();
					eParent.removeChild(nodeList.item(i).getParentNode());
					i--;
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("DB/Databases.md"));
			transformer.transform(source, result);
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	/*
	 * Nombre: updateDatabases
	 * Proposito: Metodo que actualiza las bases de datos
	 * Parametro: null
	 * Retorno: null
	 */
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
				else if (fDB.getName().contains(".md")){
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
	
	/*
	 * Nombre: saveDatabase
	 * Proposito: Almacena la base de datos con todas las tablas que estan en memoria
	 * Parametro: Nombre de la database y el documento con todas las tablas en memoria
	 * Retorno: null
	 */
	public static void saveDatabase(String database, HashMap<String, Document> hm){
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
	
	/*
	 * Nombre: cargarDatabase
	 * Proposito: Metodo que carga las tablas en memoria
	 * Parametro: Nombre de la database actual
	 * Retorno: HashMap con todas las tablas en memoria
	 */
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
	 * Nombre: getNombresDatabase
	 * Proposito: Retorna todos los nombres de la databases
	 * Parametro: null
	 * Retorno: Nombres
	 */
	public static String[] getNombresDatabase(){
		String[] nombres = null;
		try{
			File fT = new File("DB/Databases.md");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			NodeList nombresList = doc.getElementsByTagName("Name");
			nombres = new String[nombresList.getLength()];
			for (int i=0; i<nombresList.getLength(); i++){
				Node nActual = nombresList.item(i);
				nombres[i] = nActual.getTextContent();
			}
		}catch(Exception e){e.printStackTrace();}
		return nombres;
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
	 * Nombre: getPrimaryKeyTable
	 * Proposito: Retorna todas las primarykeys
	 * Parametro: Database actual y tabla actual
	 * Retorno: ArrayList con los nombres de las primarykey
	 */
	public static ArrayList<String> getPrimaryKeyTable(String DBActual, String Table){
		ArrayList<String> pkColumns = new ArrayList<String>();
		try{
			File fT = new File("DB/"+DBActual+"/"+Table+".xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fT);
			doc.getDocumentElement().normalize();
			
			Element constraintElement = (Element) doc.getDocumentElement().getFirstChild().getLastChild();
			Element pkElement = (Element) constraintElement.getFirstChild();
			for (int i=0; i<pkElement.getChildNodes().getLength(); i++){
				pkColumns.add(pkElement.getChildNodes().item(i).getTextContent());
			}
			
		}catch(Exception e){e.printStackTrace();}
		return pkColumns;
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
