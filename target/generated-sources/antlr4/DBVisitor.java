import java.io.File;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
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

import sun.org.mozilla.javascript.internal.ast.NewExpression;

public class DBVisitor extends GSQLBaseVisitor<Type>{
	private static String DBActual = "";
	private static String TableActual = "";
	private static int contNumRow = 0;
	private static HashMap<String, Document> hmDatabase;
	private static HashMap<String, Index> hmPrimaryKeyDatabase; 
	private static HashMap<String,ArrayList<String>> PrimaryKeys = new HashMap<String, ArrayList<String>>();
	
	public DBVisitor(){
		Xml.createMetadataD();
	}
	
	public HashMap<String,ArrayList<String>> getArrayListPk(){
		return PrimaryKeys;
	}
	
	/*
	 * Method: returnDBActual
	 * Parameter: none
	 * Return: String DBActual
	 * Use: returns the actual database to be working at
	 */
	public static String returnDBActual(){
		return DBActual;
	}
	
	/*
	 * Method deleteDirectory
	 * Parameter File directory
	 * Return boolean delete
	 * Use: returns true if the directory was deleted, false otherwise
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
	
	@Override
	public Type visitProgram(GSQLParser.ProgramContext ctx) {
		// TODO Auto-generated method stub
		return super.visitProgram(ctx);
	}

	@Override
	public Type visitCreateDatabase(GSQLParser.CreateDatabaseContext ctx) {
		try{
			File fNew = new File("DB/"+ctx.Id().getText());
			if (!Xml.existDB(ctx.Id().getText())){
				fNew.mkdir();
				Xml.addMetadataD(ctx.Id().getText());
				Xml.createMetadataT(ctx.Id().getText());
				HashMap<String, ArrayList<String>> temp = new HashMap<String, ArrayList<String>>();
				Xml.serializeArray(ctx.Id().getText(), temp);
				System.out.println("Database "+ctx.Id().getText()+" Created!");
			}
			else{
				System.out.println("Already a Database named : "+ctx.Id().getText());
			}
		}catch(Exception e){System.out.println("Error File!");}
		
		return super.visitCreateDatabase(ctx);
	}
	
	@Override
	public Type visitAlterDatabase(GSQLParser.AlterDatabaseContext ctx) {
		try{
			File f = new File("DB/"+ctx.Id(0).getText());
			File fNew = new File("DB/"+ctx.Id(1).getText());
			boolean bExiste1 = Xml.existDB(ctx.Id(0).getText());
			boolean bExiste2 = Xml.existDB(ctx.Id(1).getText());
			
			if (bExiste1 && !bExiste2){
				Xml.changeNameD(ctx.Id(0).getText(), ctx.Id(1).getText());
				f.renameTo(fNew);
				f.getParentFile().mkdirs();
				System.out.println("Correct. Database name "+ctx.Id(0).getText()+", changed to "+ctx.Id(1).getText()+".");
			}
			else{
				if (!bExiste1){
					System.out.println("Database "+ctx.Id(0).getText()+" does not exist.");
				}
				if (bExiste2){
					System.out.println("Database "+ctx.Id(1).getText()+" already exist.");
				}
			}
		}catch(Exception e){}
		return null;//super.visitAlterDatabase(ctx);
	}
	
	@Override
	public Type visitUseDatabase(GSQLParser.UseDatabaseContext ctx) {
		if (Xml.existDB(ctx.Id().getText())){
			if (DBActual != ""){
				Xml.guardarDatabase(DBActual, hmDatabase);	
				if (Xml.existDB(DBActual)){
					Xml.serializeArray(DBActual, PrimaryKeys);
				}
				PrimaryKeys = Xml.deSerializeArray(ctx.Id().getText());
			}
			hmDatabase = new HashMap<String, Document>();
			hmPrimaryKeyDatabase = new HashMap<String, Index>();
			PrimaryKeys = Xml.deSerializeArray(ctx.Id().getText());
			File folder = new File("DB/"+ctx.Id().getText()+"/");
			File[] filesInFolder = folder.listFiles();
			
			for (int i=0; i<filesInFolder.length; i++){
				if (filesInFolder[i].isFile() && filesInFolder[i].getName().contains(".xml")){
					String nombre = filesInFolder[i].getName();
					String nombreC = nombre.substring(0, filesInFolder[i].getName().indexOf(".xml"));
					hmDatabase.put(nombreC, Xml.getElementByName(filesInFolder[i]));
				}
			}
			
			DBActual = ctx.Id().getText();
			contNumRow = 0;
			System.out.println("ACTUALDATABASE: "+DBActual);
		}
		else{
			System.out.println("Database "+ctx.Id().getText()+" does not exist.");
		}
		return null;//super.visitUseDatabase(ctx);
	}
	
	@Override
	public Type visitDropDatabase(GSQLParser.DropDatabaseContext ctx) {
		try{
			File f = new File("DB/"+ctx.Id().getText());
			if (Xml.existDB(ctx.Id().getText())){
				int[] valores = Xml.getCant(ctx.Id().getText());
				String op = JOptionPane.showInputDialog(null, "¿Borrar base de datos "+ctx.Id().getText()+" con "+valores[0]+" tablas y "+valores[1]+" registros? (S/N)");
				if (op.equals("S") || op.equals("s")){
					Xml.deleteDatabase(ctx.Id().getText());
					hmDatabase = new HashMap<String, Document>();
					if (deleteDirectory(f))
						System.out.println("Database "+ctx.Id().getText()+" deleted.");
					else
						System.out.println("Deleting database "+ctx.Id().getText()+" has found an error.");
				}
			}
			else{
				System.out.println("Database "+ctx.Id().getText()+" not exist.");
			}
		}catch(Exception e){}
		return null;//super.visitDropDatabase(ctx);
	}
	
	@Override
	public Type visitCreateTable(GSQLParser.CreateTableContext ctx) {
		try{
			if (Xml.existDB(DBActual)){
				File fT = new File("DB/"+DBActual+"/"+ctx.Id(0).getText()+".xml");
				if (!Xml.existTable(DBActual,ctx.Id(0).getText())){
					boolean guardar = true;
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.newDocument();
					
					Element rootElement = doc.createElement("Table");
					
					Element databaseElement = doc.createElement("Database");
					Element modelElement = doc.createElement("Model");
					
					Element columnElement = doc.createElement("Column");
					Element constraintElement = doc.createElement("Constraint");
					Element pkElement = doc.createElement("PrimaryKey");
					Element fkElement = doc.createElement("ForeignKey");
					
					for (int i=1; i<ctx.Id().size(); i++){
						Element eNuevo = doc.createElement(ctx.Id(i).getText());
						Attr attr = doc.createAttribute("Type");
						Type t = visit(ctx.type(i-1));
						if (!t.getName().equals("CHAR")){
							attr.setValue(t.getName());
							eNuevo.setAttributeNode(attr);
						}
						else{
							CharType cT = (CharType) t;
							Attr attrL = doc.createAttribute("Length");
							attr.setValue(cT.getName());
							attrL.setValue(""+cT.getCantidad());
							eNuevo.setAttributeNode(attrL);
							eNuevo.setAttributeNode(attr);
						}
						columnElement.appendChild(eNuevo);
					}
					if (ctx.constraint()!= null){
						//Constraint
						for (int i=0; i<ctx.constraint().size(); i++){
							Type t = visit(ctx.constraint(i));
							if (t.getName().equals("PK")){
								PrimaryKey pK = (PrimaryKey) t;
								String id = pK.getId();
								
								Attr attr = doc.createAttribute("Name");
								attr.setValue(id);
								pkElement.setAttributeNode(attr);
								for (int j=0; j<pK.getLengthColumnsId(); j++){
									Element eHijo = doc.createElement("Column");
									String columnC = pK.getColumnId(j);
									if (columnElement.getElementsByTagName(columnC).getLength()!=0){
										eHijo.setTextContent(columnC);
									}
									else{
										guardar = false;
										System.out.println("Column name "+columnC+" does not exist in Table "+ctx.Id(0).getText()+".");
									}
									pkElement.appendChild(eHijo);
								}
							}
							else if(t.getName().equals("FK")){
								ForeignKey fK = (ForeignKey) t;
								String id = fK.getId();
								String tableRef = fK.getTableReference();
								
								Element fkChildElement = doc.createElement("FK");
								Attr aNameFK = doc.createAttribute("Name");
								aNameFK.setValue(id);
								fkChildElement.setAttributeNode(aNameFK);
								
								if (!Xml.existTable(DBActual, tableRef)){
									guardar = false;
									System.out.println("Table "+tableRef+" as a reference, does not exist.");
								}
								else{
									Attr aTableFK = doc.createAttribute("Table");
									aTableFK.setValue(tableRef);
									fkChildElement.setAttributeNode(aTableFK);
									
									File f = new File("DB/"+DBActual+"/"+tableRef+".xml");
									if (fK.getLengthColumnsId()==fK.getLengthReferences()){
										for (int j=0; j<fK.getLengthColumnsId(); j++){
											if (Xml.existColumnInTable(f, fK.getReference(j))){
												if (columnElement.getElementsByTagName(fK.getColumnId(j)).getLength()==0){
													//NO estoy muy seguro de esto
													NamedNodeMap nNodeM = Xml.getTypeColumn(f, fK.getReference(j));
													Element nElement = doc.createElement(fK.getColumnId(j));
													for (int k = 0; k<nNodeM.getLength(); k++){
														String name = nNodeM.item(k).getNodeName();
														String value = nNodeM.item(k).getTextContent();
														nElement.setAttribute(name, value);
													}
													columnElement.appendChild(nElement);
													
													Element fkChildNameColumn = doc.createElement("Column");
													fkChildNameColumn.setTextContent(fK.getColumnId(j));
													Attr aNameCol = doc.createAttribute("Reference");
													aNameCol.setValue(fK.getReference(j));
													fkChildNameColumn.setAttributeNode(aNameCol);
													fkChildElement.appendChild(fkChildNameColumn);
												}
												else{
													System.out.println("Column "+fK.getColumnId(j)+" already exist in table "+ctx.Id(0).getText());
													guardar = false;
												}
											}
											else{
												guardar = false;
												System.out.println("Column "+fK.getReference(j)+" does not exist in Table Reference "+fK.getTableReference()+".");
											}
										}
									}
									else{
										guardar = false;
									}
								}
								fkElement.appendChild(fkChildElement);
							}
							else if(t.getName().equals("CK")){
							
							}
						}
					}
					if (guardar){
						PrimaryKeys.put(ctx.Id(0).getText(), new ArrayList<String>());
						
						constraintElement.appendChild(pkElement);
						constraintElement.appendChild(fkElement);
						modelElement.appendChild(columnElement);
						modelElement.appendChild(constraintElement);
						
						rootElement.appendChild(modelElement);
						rootElement.appendChild(databaseElement);
						doc.appendChild(rootElement);
						
						hmDatabase.put(ctx.Id(0).getText(),doc);
					
						// Write the content into xml file
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						DOMSource source = new DOMSource(doc);
						StreamResult result = new StreamResult(fT);
						transformer.transform(source, result);
						fT.getParentFile().mkdirs(); 
						fT.createNewFile();
					
					
						Xml.addMetadataT(DBActual,ctx.Id(0).getText());
						Xml.modifyDatabase(DBActual, "Tables");
						System.out.println("Correct. Table name "+ctx.Id(0).getText()+" added to database "+DBActual+".");
					}
					else{
						System.out.println("False. Table name "+ctx.Id(0).getText()+" was not added to database "+DBActual+".");
					}
				}
				else{
					System.out.println("Table already exist in database "+DBActual);
				}
			}
			else{
				System.out.println("Database does not exist ");
			}
		}catch(Exception e){e.printStackTrace();}
		return super.visitCreateTable(ctx);
	}
	
	@Override
	public Type visitRenameAlterTable(GSQLParser.RenameAlterTableContext ctx) {
		try{
			File f = new File("DB/"+DBActual+"/"+ctx.Id(0).getText()+".xml");
			if (Xml.existDB(DBActual)){
				if (Xml.existTable(DBActual,ctx.Id(0).getText())){
					File fN = new File("DB/"+DBActual+"/"+ctx.Id(1).getText()+".xml");
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.parse(f);
					doc.getDocumentElement().normalize();
					
					Node rootElement = doc.getDocumentElement();
					Node databaseElement = rootElement.getLastChild();
					
					for (int i=0; i<databaseElement.getChildNodes().getLength(); i++){
						doc.renameNode(databaseElement.getChildNodes().item(i), ctx.Id(1).getText(), ctx.Id(1).getText());
					}
					
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(f);
					transformer.transform(source, result);
					f.renameTo(fN);
					f.getParentFile().mkdirs();
					DBActual = Xml.changeNameT(DBActual,ctx.Id(0).getText(),ctx.Id(1).getText());
					System.out.println("Corret. Table name "+ctx.Id(0).getText()+", changed to "+ctx.Id(1).getText()+".");
				}
				else{
					System.out.println("Table "+ctx.Id(0).getText()+" does not exist.");
				}
			}
		}catch(Exception e){e.printStackTrace();}
		return null;//super.visitRenameAlterTable(ctx);
	}
	
	@Override
	public Type visitActionAlterTable(GSQLParser.ActionAlterTableContext ctx) {
		if (Xml.existDB(DBActual)){
			if (Xml.existTable(DBActual,ctx.Id().getText())){
				TableActual = ctx.Id().getText();
				super.visitActionAlterTable(ctx);
			}
			else{
				System.out.println("Table "+ctx.Id().getText()+" does not exist.");
			}
		}
		return null;
	}
	
	@Override
	public Type visitActionAddColumn(GSQLParser.ActionAddColumnContext ctx) {
		boolean paso = true; 
		try{
			//Document doc = (Document) hmDatabase.get(TableActual);
			Document doc = (Document) hmDatabase.get(TableActual).cloneNode(true);
			doc.getDocumentElement().normalize();
			Element rootElement = doc.getDocumentElement();
			Element modelElement = (Element) rootElement.getFirstChild();
			Element columnElement = (Element) modelElement.getFirstChild();
			if (columnElement.getElementsByTagName(ctx.Id().getText()).getLength()!=0){
				paso = false;
				System.out.println("Column "+ctx.Id().getText()+" already exist in table "+TableActual);
			}
			else{
				Type t = visit(ctx.type());
				Element eNuevo = doc.createElement(ctx.Id().getText());
				Attr attr = doc.createAttribute("Type");
				if (!t.getName().equals("CHAR")){
					attr.setValue(t.getName());
					eNuevo.setAttributeNode(attr);
				}
				else{
					CharType cT = (CharType) t;
					Attr attrL = doc.createAttribute("Length");
					attr.setValue(cT.getName());
					attrL.setValue(""+cT.getCantidad());
					eNuevo.setAttributeNode(attrL);
					eNuevo.setAttributeNode(attr);
				}
				columnElement.appendChild(eNuevo);
			}
			
			Element constraintElement = (Element) modelElement.getLastChild();
			Element pkElement = (Element) constraintElement.getFirstChild();
			Element fkElement = (Element) constraintElement.getLastChild();
			if (paso && ctx.constraint()!=null){
				Type t = visit(ctx.constraint());
				if (t.getName().equals("PK") && !pkElement.hasChildNodes()){
					PrimaryKey pK = (PrimaryKey) t;
					String id = pK.getId();
					
					Attr attr = doc.createAttribute("Name");
					attr.setValue(id);
					pkElement.setAttributeNode(attr);
					for (int j=0; j<pK.getLengthColumnsId(); j++){
						Element eHijo = doc.createElement("Column");
						String columnC = pK.getColumnId(j);
						if (columnElement.getElementsByTagName(columnC).getLength()!=0){
							eHijo.setTextContent(columnC);
							pkElement.appendChild(eHijo);
						}
						else{
							paso = false;
							System.out.println("Column name "+columnC+" does not exist in Table "+TableActual+".");
						}
					}
				}
				else if(t.getName().equals("PK") && pkElement.hasChildNodes()){
					paso = false;
					System.out.println("False. Column "+ctx.Id().getText()+" was not added to table "+TableActual+". Theres already a PK");
				}
				else if(t.getName().equals("FK")){
					ForeignKey fK = (ForeignKey) t;
					String id = fK.getId();
					String tableRef = fK.getTableReference();
					
					Element fkChildElement = doc.createElement("FK");
					Attr aNameFK = doc.createAttribute("Name");
					aNameFK.setValue(id);
					fkChildElement.setAttributeNode(aNameFK);
					
					if (!Xml.existTable(DBActual, tableRef)){
						paso = false;
						System.out.println("Table "+tableRef+" as a reference, does not exist.");
					}
					else{
						Attr aTableFK = doc.createAttribute("Table");
						aTableFK.setValue(tableRef);
						fkChildElement.setAttributeNode(aTableFK);
						
						File fDB = new File("DB/"+DBActual+"/"+tableRef+".xml");
						if (fK.getLengthColumnsId()==fK.getLengthReferences()){
							for (int j=0; j<fK.getLengthColumnsId(); j++){
								if (Xml.existColumnInTable(fDB, fK.getReference(j))){
									Element fkChildNameColumn = doc.createElement("Column");
									fkChildNameColumn.setTextContent(fK.getColumnId(j));
									Attr aNameCol = doc.createAttribute("Reference");
									aNameCol.setValue(fK.getReference(j));
									fkChildNameColumn.setAttributeNode(aNameCol);
									fkChildElement.appendChild(fkChildNameColumn);
								}
								else{
									paso = false;
									System.out.println("Column "+fK.getReference(j)+" does not exist in Table Reference "+fK.getTableReference()+".");
								}
							}
						}
						else{
							paso = false;
						}
					}
					fkElement.appendChild(fkChildElement);
				}
				else{
					paso = false;
				}
			}
			
			Element databaseElement = (Element) rootElement.getLastChild();
			for (int i=0; i<databaseElement.getChildNodes().getLength(); i++){
				Element dataActual = (Element) databaseElement.getChildNodes().item(i);
				Element nColumn = doc.createElement(ctx.Id().getText());
				dataActual.appendChild(nColumn);
			}
			
			if (paso){
				hmDatabase.put(TableActual, doc);
				System.out.println("True. Column "+ctx.Id().getText()+" was added to table "+TableActual);
			}
			else{
				System.out.println("False. Column "+ctx.Id().getText()+" was not added to the table "+TableActual);
			}
			
		}catch(Exception e){e.printStackTrace();}
		return null;//super.visitActionAddColumn(ctx);
	}

	@Override
	public Type visitActionDropColumn(GSQLParser.ActionDropColumnContext ctx) {
		// TODO Auto-generated method stub
		try{
			boolean borrar = true;
			Document doc = hmDatabase.get(TableActual);
			doc.getDocumentElement().normalize();
			
			Element nodoRaiz = doc.getDocumentElement();
			Element modelElement = (Element)nodoRaiz.getFirstChild();
			Element columnElement = (Element) modelElement.getFirstChild();
			Element constraintElement = (Element) modelElement.getLastChild();
			Element pkElement = (Element) constraintElement.getFirstChild();
			
			NodeList tagElements = nodoRaiz.getElementsByTagName(ctx.Id().getText());
			if (tagElements.getLength()!=0){
				File folder = new File("DB/"+DBActual+"/");
				for (File fT: folder.listFiles()){
					if (fT.isFile() && fT.getName().contains(".xml")){
						String n1 = fT.getName();
						String n2 = n1.substring(0, n1.indexOf(".xml"));
						if (!n2.equals(TableActual) && Xml.existReferenceColumn(TableActual, ctx.Id().getText(), fT)){
							borrar = false;
							System.out.println("Column "+ctx.Id().getText()+" references the table "+n2);
						}
					}
				}
			}
			else{
				borrar = false;
				System.out.println("Column "+ctx.Id().getText()+" does not exist in table "+TableActual);
			}
			
			if (borrar){
				boolean esPK = false;
				for (int i=0; i<pkElement.getChildNodes().getLength(); i++){
					Element e = (Element) pkElement.getChildNodes().item(i);
					if (e.getTextContent().equals(ctx.Id().getText())){
						esPK = true;
					}
				}
				if (!esPK){
					for (int i=0; i<tagElements.getLength(); i++){
						Element e = (Element) tagElements.item(i);
						e.getParentNode().removeChild(e);
					}
					hmDatabase.put(TableActual, doc);
					TransformerFactory transFact = TransformerFactory.newInstance();
					Transformer transformer = transFact.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File("DB/"+DBActual+"/"+TableActual+".xml"));
					transformer.transform(source,result);
					System.out.println("True. Column "+ctx.Id().getText()+" deleted from table "+TableActual);
				}
				else{
					System.out.println("False. Column "+ctx.Id().getText()+" is a PK in table "+TableActual);
				}
			}
		}catch(Exception e){e.printStackTrace();}
		return null;//return super.visitActionDropColumn(ctx);
	}
	
	@Override
	public Type visitDropTable(GSQLParser.DropTableContext ctx) {
		// TODO Auto-generated method stub
		try{
			boolean pasar = true;
			File folder = new File("DB/"+DBActual+"/");
			for (File f: folder.listFiles()){
				if (f.isFile() && f.getName().contains(".xml") && !f.getName().equals(ctx.Id().getText()+".xml")){
					if (Xml.existReferenceTable(ctx.Id().getText(), f)){
						String n1 = f.getName();
						String n2 = n1.substring(0, n1.indexOf(".xml"));
						System.out.println("Table "+n2+" has a reference in table "+ctx.Id().getText());
						pasar = false;
					}
				}
			}
			if (pasar){
				File f = new File("DB/"+DBActual+"/"+ctx.Id().getText()+".xml");
				if (Xml.existTable(DBActual,ctx.Id().getText())){
					Xml.deleteTable(DBActual, ctx.Id().getText());
					hmDatabase.remove(ctx.Id().getText());
					PrimaryKeys.remove(ctx.Id().getText());
					if (f.delete()){
						System.out.println("True. Table "+ctx.Id().getText()+" deleted.");
					}
					else{
						System.out.println("Deleting table "+ctx.Id().getText()+" has found an error.");
					}
				}
				else{
					System.out.println("Table "+ctx.Id().getText()+" does not exist.");
				}
			}
			else{
				System.out.println("False. Table "+ctx.Id().getText()+" was not deleted.");
			}
		}catch(Exception e){}
		return null;//super.visitDropTable(ctx);
	}
	
	@Override
	public Type visitEqOpExpression(GSQLParser.EqOpExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEqOpExpression(ctx);
	}

	@Override
	public Type visitSelectFrom(GSQLParser.SelectFromContext ctx) {
		// TODO Auto-generated method stub
		return super.visitSelectFrom(ctx);
	}	

	@Override
	public Type visitConstraint(GSQLParser.ConstraintContext ctx) {
		// TODO Auto-generated method stub
		return super.visitConstraint(ctx);
	}

	@Override
	public Type visitForeignKey(GSQLParser.ForeignKeyContext ctx) {
		String id = ctx.Id(0).getText();
		String[] columnsId = new String[ctx.Id().size()-1];
		for (int i=1; i<ctx.Id().size(); i++){
			columnsId[i-1] = ctx.Id(i).getText();
		}
		Key t = (Key) visit(ctx.reference());
		String referenceTable = t.getId();
		ForeignKey fK = new ForeignKey("FK", id, columnsId, referenceTable, t.getColumnsId());
		return fK;
	}
	
	@Override
	public Type visitReference(GSQLParser.ReferenceContext ctx) {
		String id = ctx.Id(0).getText();
		String[] references = new String[ctx.Id().size()-1];
		for (int i=1 ; i<ctx.Id().size(); i++){
			references[i-1] = ctx.Id(i).getText();
		}
		Key k = new Key("REF",id,references);
		return k;
	}

	@Override
	public Type visitCheck(GSQLParser.CheckContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCheck(ctx);
	}

	@Override
	public Type visitPrimaryKey(GSQLParser.PrimaryKeyContext ctx) {		 
		String id = ctx.Id(0).getText();
		String[] columnsId = new String[ctx.Id().size()-1];
		
		for (int i=1; i<ctx.Id().size(); i++){
			columnsId[i-1] = ctx.Id(i).getText();
		}
		PrimaryKey pK = new PrimaryKey("PK", id, columnsId);
		return pK;
	}

	@Override
	public Type visitDeleteFrom(GSQLParser.DeleteFromContext ctx) {
		// TODO Auto-generated method stub
		try{
			if (Xml.existDB(DBActual)){
				if (Xml.existTable(DBActual,ctx.Id().getText())){
					Document doc = hmDatabase.get(ctx.Id().getText());
					doc.getDocumentElement().normalize();
					int cont = 0;
					if (ctx.WHERE() != null){
						// ACA IRIA POR SI QUISIERAMOS SABER UN VALOR DE UN NODO
						NodeList nodos = doc.getElementsByTagName("ACA");
						for (int i=0; i<nodos.getLength(); i++){
							Node nodoActual = nodos.item(i);
							//ACA IRIA COMPARAR EL VALOR DE UN NODO
							if (nodoActual.getTextContent().equals("ACA2")){
								if (nodoActual.getNodeType() == Node.ELEMENT_NODE) {
									Element elActual = (Element) nodoActual;
									
								}
							}
						}
					}
					else{
						Node rootNode = doc.getDocumentElement();
						cont = rootNode.getLastChild().getChildNodes().getLength();
						rootNode.removeChild(rootNode.getLastChild());
						Element databaseElement = doc.createElement("Database");
						rootNode.appendChild(databaseElement);
						PrimaryKeys.remove(ctx.Id().getText());
						PrimaryKeys.put(ctx.Id().getText(), new ArrayList<String>());
						System.out.println("DELETE ("+cont+").");
					}
				}
				else{
					System.out.println("Table "+ctx.Id().getText()+" does not exist");
				}
			}
			else{
				System.out.println("Database "+DBActual+" does not exist.");
			}
		}catch(Exception e){e.printStackTrace();}
		
		return super.visitDeleteFrom(ctx);
	}

	@Override
	public Type visitShowColumns(GSQLParser.ShowColumnsContext ctx) {
		// TODO Auto-generated method stub
		return super.visitShowColumns(ctx);
	}

	@Override
	public Type visitFloatType(GSQLParser.FloatTypeContext ctx) {
		return new DataType(ctx.getText());
	}

	@Override
	public Type visitIntType(GSQLParser.IntTypeContext ctx) {
		return new DataType(ctx.getText());
	}

	@Override
	public Type visitCharType(GSQLParser.CharTypeContext ctx) {
		return new CharType(ctx.CHAR().getText(),Integer.parseInt(ctx.Num().getText()));
	}

	@Override
	public Type visitDateType(GSQLParser.DateTypeContext ctx) {
		return new DataType(ctx.getText());
	}

	@Override
	public Type visitDate_literal(GSQLParser.Date_literalContext ctx) {
		return new ValueType("DATE",ctx.Date().getText());
	}

	@Override
	public Type visitUnExpression(GSQLParser.UnExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitUnExpression(ctx);
	}

	@Override
	public Type visitShowTables(GSQLParser.ShowTablesContext ctx) {
		// TODO Auto-generated method stub
		try{
			Table t = new Table();
			File folder = new File("DB/"+DBActual+"/");
			File[] listOfFiles = folder.listFiles();
			String[] nombres = new String[listOfFiles.length-1];
			int contadorN = 0;
			for (int i=0 ; i<listOfFiles.length; i++) {
				if (listOfFiles[i].getName().contains(".xml")){
					nombres[contadorN] = listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf(".xml"));
					contadorN++;
				}
			}
			t.agregarDatabase(DBActual, nombres);
			for (File file : listOfFiles) {
				if (file.isFile() && file.getName().contains(".xml")){
					String name1 = file.getName();
					String n2 = name1.substring(0, name1.indexOf(".xml"));
					
					Document doc = hmDatabase.get(n2);
					doc.getDocumentElement().normalize();
					
					Element nodoRaiz = doc.getDocumentElement();
					Element modelElement = (Element) nodoRaiz.getFirstChild();
					Element columnsElement = (Element) modelElement.getFirstChild();
					Element databaseElement = (Element) nodoRaiz.getLastChild();
					
					HashMap<String, Integer> hmNumberColumn = new HashMap<String, Integer>();
					String[] columns = new String[columnsElement.getChildNodes().getLength()];
					for (int i=0; i<columnsElement.getChildNodes().getLength(); i++){
						String nombre = columnsElement.getChildNodes().item(i).getNodeName();
						columns[i] = nombre;
						hmNumberColumn.put(nombre, i);
					}
					t.agregarTabla(file.getName().substring(0, file.getName().indexOf(".xml")), columns, databaseElement.getChildNodes().getLength());
					for (int i=0; i<databaseElement.getChildNodes().getLength(); i++){
						Element tActual = (Element) databaseElement.getChildNodes().item(i);
						String[] datos = new String[tActual.getChildNodes().getLength()];
						for (int j=0; j<columns.length; j++){
							String dato = tActual.getElementsByTagName(columns[j]).item(0).getTextContent();
							datos[j] = dato;
						}
						t.agregarDatosTabla(datos);
					}
				}
			}
			t.mostrarTabla();
		}catch(Exception e){e.printStackTrace();}
		return super.visitShowTables(ctx);
	}

	@Override
	public Type visitExpAndExpression(GSQLParser.ExpAndExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitExpAndExpression(ctx);
	}

	@Override
	public Type visitCondOrExpression(GSQLParser.CondOrExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCondOrExpression(ctx);
	}
	
	@Override
	public Type visitActionDropConstraint(GSQLParser.ActionDropConstraintContext ctx) {
		try{
			boolean existe = false;
			Document doc = hmDatabase.get(TableActual);
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			Element modelElement = (Element) rootElement.getFirstChild();
			Element constraintElement = (Element) modelElement.getLastChild();
			Element pkElement = (Element) constraintElement.getFirstChild();
			Element fkElement = (Element) constraintElement.getLastChild();
			Element databaseElement = (Element) rootElement.getLastChild();	
			
			if (pkElement.getAttribute("Name").equals(ctx.Id().getText())){
				Element pkNewElement = doc.createElement("PrimaryKey");
				
				for (int i=0; i<databaseElement.getChildNodes().getLength(); i++){
					Element eActual = (Element) databaseElement.getChildNodes().item(i);
					NamedNodeMap attributes = eActual.getAttributes();
					for (int j=0; j<attributes.getLength(); j++){
						attributes.removeNamedItem(attributes.item(j).getNodeName());
						j-=1;
					}
				}
				PrimaryKeys.remove(TableActual);
				PrimaryKeys.put(TableActual, new ArrayList<String>());
				constraintElement.replaceChild(pkNewElement, pkElement);
				existe = true;
			}
			for (int i=0; i<fkElement.getChildNodes().getLength(); i++){
				ArrayList<String> columnsArrayList = new ArrayList<String>();
				Element eActual = (Element) fkElement.getChildNodes().item(i);
				if (eActual.getAttribute("Name").equals(ctx.Id().getText())){
					existe = true;
					for (int j=0; j<eActual.getChildNodes().getLength(); j++){
						Element childElement = (Element) eActual.getChildNodes().item(j);
						columnsArrayList.add(childElement.getTextContent());
					}
					System.out.println(columnsArrayList);
					for (int j=0; j<columnsArrayList.size(); j++){
						NodeList nodosList = rootElement.getElementsByTagName(columnsArrayList.get(j));
						for (int k=0; k<nodosList.getLength(); k++){
							Element pElement = (Element) nodosList.item(k).getParentNode();
							pElement.removeChild(nodosList.item(k));
						}
					}
					fkElement.removeChild(eActual);
				}
			}
			
			if (!existe){
				System.out.println("Constraint "+ctx.Id().getText()+" does not exist in table "+TableActual);				
			}
		}catch(Exception e){}
		return null;
	}

	@Override
	public Type visitActionAddConstraint(GSQLParser.ActionAddConstraintContext ctx) {
		try{
			boolean paso = true;
			
			Document doc = (Document)hmDatabase.get(TableActual);
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			Element modelElement = (Element) rootElement.getFirstChild();
			Element columnElement = (Element) modelElement.getFirstChild();
			Element constraintElement = (Element) modelElement.getLastChild();
			Element pkElement = (Element) constraintElement.getFirstChild();
			Element fkElement = (Element) constraintElement.getLastChild();
			Element databaseElement = (Element) rootElement.getLastChild();
			
			Type t = visit(ctx.constraint());
			if (t.getName().equals("PK") && !pkElement.hasChildNodes()){
				System.out.println("The rows with the same PK, will be deleted, only the first one will stay.");
				System.out.println("Same goes to null PK's");
				PrimaryKey pK = (PrimaryKey) t;
				String id = pK.getId();
				
				Attr attr = doc.createAttribute("Name");
				attr.setValue(id);
				pkElement.setAttributeNode(attr);
				ArrayList<String> columnsPK = new ArrayList<String>();
				for (int i=0; i<pK.getLengthColumnsId(); i++){
					Element eHijo = doc.createElement("Column");
					String columnC = pK.getColumnId(i);
					if (columnElement.getElementsByTagName(columnC).getLength()!=0){
						eHijo.setTextContent(columnC);
						columnsPK.add(columnC);
						NodeList nList = databaseElement.getElementsByTagName(columnC);
						for (int j=0; j<nList.getLength(); j++){
							Element e = (Element) nList.item(j);
							Element eParent = (Element) e.getParentNode();
							eParent.setAttribute(columnC, e.getTextContent());
						}
						pkElement.appendChild(eHijo);
					}
					else{
						paso = false;
						pkElement.removeAttribute("Name");
						System.out.println("Column name "+columnC+" does not exist in Table "+TableActual+".");
					}
				}
				
				for (int i=0; i<databaseElement.getChildNodes().getLength(); i++){
					String text = "";
					Element eActual = (Element) databaseElement.getChildNodes().item(i);
					NamedNodeMap attributes = eActual.getAttributes();
					for (int j=0; j<attributes.getLength()-1; j++){
						text += attributes.item(j).getTextContent()+"_";
					}
					text+=attributes.item(attributes.getLength()-1).getTextContent();
			
					if (!text.equals("") && !PrimaryKeys.get(TableActual).contains(text)){
						PrimaryKeys.get(TableActual).add(text);
					}
					else{
						databaseElement.removeChild(eActual);
						i--;
					}
				}
			}
			else if(t.getName().equals("PK") && pkElement.hasChildNodes()){
				System.out.println("False. Theres already a PK");
			}
			else if(t.getName().equals("FK")){
				ForeignKey fK = (ForeignKey) t;
				String id = fK.getId();
				String tableRef = fK.getTableReference();
				
				Element fkChildElement = doc.createElement("FK");
				Attr aNameFK = doc.createAttribute("Name");
				aNameFK.setValue(id);
				fkChildElement.setAttributeNode(aNameFK);
				
				if (!Xml.existTable(DBActual, tableRef)){
					paso = false;
					System.out.println("Table "+tableRef+" as a reference, does not exist.");
				}
				else{
					Attr aTableFK = doc.createAttribute("Table");
					aTableFK.setValue(tableRef);
					fkChildElement.setAttributeNode(aTableFK);
					
					File fDB = new File("DB/"+DBActual+"/"+tableRef+".xml");
					if (fK.getLengthColumnsId()==fK.getLengthReferences()){
						for (int j=0; j<fK.getLengthColumnsId(); j++){
							if (Xml.existColumnInTable(fDB, fK.getReference(j))){
								Element fkChildNameColumn = doc.createElement("Column");
								fkChildNameColumn.setTextContent(fK.getColumnId(j));
								Attr aNameCol = doc.createAttribute("Reference");
								aNameCol.setValue(fK.getReference(j));
								fkChildNameColumn.setAttributeNode(aNameCol);
								fkChildElement.appendChild(fkChildNameColumn);
							}
							else{
								paso = false;
								System.out.println("Column "+fK.getReference(j)+" does not exist in Table Reference "+fK.getTableReference()+".");
							}
						}
					}
					else{
						paso = false;
					}
				}
				fkElement.appendChild(fkChildElement);
			}
			else{
				paso = false;
			}
			
			if (paso){
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("DB/"+DBActual+"/"+TableActual+".xml"));
				transformer.transform(source, result);
				System.out.println("True. Constraint added to table "+TableActual);
			}
			else{
				System.out.println("False. Constraint was not added to the table "+TableActual);
			}
			
		}catch(Exception e){}	
		return super.visitActionAddConstraint(ctx);
	}

	@Override
	public Type visitCondExpression(GSQLParser.CondExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCondExpression(ctx);
	}

	@Override
	public Type visitInsertInto(GSQLParser.InsertIntoContext ctx) {
		// TODO Auto-generated method stub
		try{
			if (Xml.existDB(DBActual)){
				if (Xml.existTable(DBActual,ctx.Id(0).getText())){
					Document doc = hmDatabase.get(ctx.Id(0).getText());
					//Document doc =(Document) hmDatabase.get(ctx.Id(0).getText()).cloneNode(true);
					doc.getDocumentElement().normalize();
					
					Element nodoRaiz = doc.getDocumentElement();
					
					Element modelElement = (Element)nodoRaiz.getFirstChild();
					Element columnsElement = (Element)modelElement.getFirstChild();
					Element constraintElement = (Element)modelElement.getLastChild();
					Element pkElement = (Element)constraintElement.getFirstChild();
					
					ArrayList<String> pkColumns = Xml.getColumnsPKTable(modelElement);
					ArrayList<Attr> attributes = new ArrayList<Attr>();
					
					ArrayList<String> columnAgregadas = new ArrayList<String>();
					//String pkName = Xml.getPrimaryKeyTable(modelElement);
										
					XPathFactory xPathfactory = XPathFactory.newInstance();
					XPath xpath = xPathfactory.newXPath();
					
					if (pkElement.hasChildNodes()){
						for (int i=0; i<pkElement.getChildNodes().getLength(); i++){
							Element e = (Element) pkElement.getChildNodes().item(i);
							pkColumns.add(e.getTextContent());
						}
						
						Element databaseElement = (Element)nodoRaiz.getLastChild();
						Element newDataElement = doc.createElement(ctx.Id(0).getText()+"_Row");
						
						boolean save = true;
						if (ctx.Id().size()>1){
							for (int i=1; i<ctx.Id().size(); i++){
								NodeList columnList = modelElement.getElementsByTagName(ctx.Id(i).getText());
								if (columnList.getLength()==1){
									Element columnElement = (Element) columnList.item(0);
									Element newElement = doc.createElement(columnElement.getNodeName());
									columnAgregadas.add(columnElement.getNodeName());
									boolean esPK = pkColumns.contains(columnElement.getNodeName());
									ValueType vT = (ValueType) visit(ctx.literal(i-1));
									
									if (vT.getName().equals(columnElement.getAttribute("Type"))){
										String value = vT.getValue();
										if(vT.getName().equals("CHAR")){
											if (Integer.parseInt(columnElement.getAttribute("Length"))>value.length()){
												if (esPK){
													Attr a = doc.createAttribute(columnElement.getNodeName());
													a.setValue(value);
													attributes.add(a);
												}
												newElement.setTextContent(value);
											}
											else{
												save = false;
												System.out.println("Error in Insert. Char \""+value+"\" greater than expected.");
											}
										}
										else{
											if (esPK){
												Attr a = doc.createAttribute(columnElement.getNodeName());
												a.setValue(value);
												attributes.add(a);
											}
											newElement.setTextContent(value);
										}
									}
									else{
										System.out.println("Error in Insert. Found "+vT.getName()+" expected "+columnElement.getAttribute("Type"));
										save = false;
									}
									newDataElement.appendChild(newElement);
								}
								else{
									System.out.println("Column "+ctx.Id(i).getText()+" does not exist in table "+ctx.Id(0).getText());
								}
							}
							
							for (int i=0; i<columnsElement.getChildNodes().getLength(); i++){
								Element e = (Element) columnsElement.getChildNodes().item(i);
								if (!columnAgregadas.contains(e.getNodeName())){
									Element newElement = doc.createElement(e.getNodeName());
									newDataElement.appendChild(newElement);
								}
							}
						}
						else{
							for (int i=0; i<columnsElement.getChildNodes().getLength(); i++){
								Element e = (Element) columnsElement.getChildNodes().item(i);
								Element newElement = doc.createElement(e.getNodeName());
								boolean esPK = pkColumns.contains(e.getNodeName());
								if (ctx.literal(i)!=null){
									ValueType vT = (ValueType) visit(ctx.literal(i));
									
									if (vT.getName().equals(e.getAttribute("Type"))){
										String value = vT.getValue();
										if(vT.getName().equals("CHAR")){
											if (Integer.parseInt(e.getAttribute("Length"))>value.length()){
												if (esPK){
													Attr a = doc.createAttribute(e.getNodeName());
													a.setValue(value);
													attributes.add(a);
												}
												newElement.setTextContent(value);
											}
											else{
												save = false;
												System.out.println("Error in Insert. Char \""+value+"\" greater than expected.");
											}
										}
										else{
											if (esPK){
												Attr a = doc.createAttribute(e.getNodeName());
												a.setValue(value);
												attributes.add(a);
											}
											newElement.setTextContent(value);
										}
									}
									else{
										System.out.println("Error in Insert. Found "+vT.getName()+" expected "+e.getAttribute("Type"));
										save = false;
									}
									newDataElement.appendChild(newElement);
								}
								else{
									newDataElement.appendChild(newElement);
								}
							}
						}
						if (save){
							String t = "";
							if (attributes.size()>0){
								for (int i=0; i<attributes.size()-1; i++){
									t += attributes.get(i).getValue()+"_";
								}
								t+= attributes.get(attributes.size()-1).getValue();
							}
							/*String cond = "[";
							for (int i=0; i<attributes.size()-1; i++){
								cond+="@"+attributes.get(i).getName()+"=\'"+attributes.get(i).getValue()+"\' and ";
							}
							cond+="@"+attributes.get(attributes.size()-1).getName()+"=\'"+attributes.get(attributes.size()-1).getValue()+"\']";
							String exp = "//"+ctx.Id(0).getText()+cond;
							XPathExpression expr = xpath.compile(exp);
							Object result = expr.evaluate(doc, XPathConstants.NODESET);
					        NodeList nodes = (NodeList) result;
					        */
					        //if (nodes.getLength()==0){
							if (!PrimaryKeys.get(ctx.Id(0).getText()).contains(t) && t!=""){
								PrimaryKeys.get(ctx.Id(0).getText()).add(t);
								for (Attr a: attributes)
									newDataElement.setAttributeNode(a);
								databaseElement.appendChild(newDataElement);
								//hmDatabase.put(TableActual, doc);
								//System.out.println("Insert("+contNumRow+").");
								contNumRow = Xml.modifyTable(DBActual, ctx.Id(0).getText())+1;
								Xml.modifyDatabase(DBActual, "Records");
					        }
					        else{
					        	String ret = "";
					        	if (attributes.size()>0){
						        	for (int i=0; i<attributes.size()-1; i++){
										ret+=""+attributes.get(i).getName()+"=\'"+attributes.get(i).getValue()+"\', ";
									}
						        	ret+=""+attributes.get(attributes.size()-1).getName()+"=\'"+attributes.get(attributes.size()-1).getValue()+"\'";
						        	System.out.println("Already exist a row with "+ret+" as Primary Key.");
					        	}else{
					        		System.out.println("False. No primary key given");
					        	}
					        }
						}
						//hmDatabase.put(ctx.Id(0).getText(), doc);
					}
					else{
						System.out.println("Table "+ctx.Id(0).getText()+" does not have primary key.");
					}
				}
				else{
					System.out.println("Table "+ctx.Id(0).getText()+" does not exist in database "+DBActual+".");
				}
			}
			else{
				System.out.println("Database "+DBActual+" does not exist.");
			}
		}catch(Exception e){e.printStackTrace();}			
	
		return super.visitInsertInto(ctx);
	}

	@Override
	public Type visitShowDatabase(GSQLParser.ShowDatabaseContext ctx) {
		// TODO Auto-generated method stub
		return super.visitShowDatabase(ctx);
	}

	@Override
	public Type visitRelOp(GSQLParser.RelOpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRelOp(ctx);
	}

	@Override
	public Type visitInt_literal(GSQLParser.Int_literalContext ctx) {
		return new ValueType("INT",ctx.Num().getText());
	}

	@Override
	public Type visitEqAndExpression(GSQLParser.EqAndExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEqAndExpression(ctx);
	}

	@Override
	public Type visitRelOpExpression(GSQLParser.RelOpExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRelOpExpression(ctx);
	}

	@Override
	public Type visitAndOp(GSQLParser.AndOpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAndOp(ctx);
	}

	@Override
	public Type visitDatabase(GSQLParser.DatabaseContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDatabase(ctx);
	}

	@Override
	public Type visitChar_literal(GSQLParser.Char_literalContext ctx) {	
		String t = ctx.Char().getText().substring(1, ctx.Char().getText().length()-1);
		return new ValueType("CHAR", t);
	}

	@Override
	public Type visitFloat_literal(GSQLParser.Float_literalContext ctx) {
		return new ValueType("FLOAT",ctx.Float().getText());
	}

	@Override
	public Type visitEqRelExpression(GSQLParser.EqRelExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEqRelExpression(ctx);
	}

	@Override
	public Type visitEqOp(GSQLParser.EqOpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEqOp(ctx);
	}

	@Override
	public Type visitOrOp(GSQLParser.OrOpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitOrOp(ctx);
	}

	@Override
	public Type visitRelSumExpression(GSQLParser.RelSumExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRelSumExpression(ctx);
	}

	@Override
	public Type visitUpdateSet(GSQLParser.UpdateSetContext ctx) {
		// TODO Auto-generated method stub
		try{
			if (Xml.existDB(DBActual)){
				if (Xml.existTable(DBActual,ctx.Id(0).getText())){
					Document doc = hmDatabase.get(ctx.Id(0).getText());
					doc.getDocumentElement().normalize();
					int cont = 0;
					if (ctx.WHERE() != null){
						// ACA IRIA POR SI QUISIERAMOS SABER UN VALOR DE UN NODO
						NodeList nodos = doc.getElementsByTagName("ACA");
						for (int i=0; i<nodos.getLength(); i++){
							Node nodoActual = nodos.item(i);
							//ACA IRIA COMPARAR EL VALOR DE UN NODO
							if (nodoActual.getTextContent().equals("ACA2")){
								if (nodoActual.getNodeType() == Node.ELEMENT_NODE) {
									Element elActual = (Element) nodoActual;
									for (int j=1; j<ctx.Id().size(); j++){
										NodeList n = elActual.getElementsByTagName(ctx.Id(j).getText());
										n.item(0).setTextContent(ctx.Char(j-1).getText());
										cont++;
									}
								}
							}
						}
						System.out.println("UPDATE("+cont+") con exito.");
					}
					else{
						for (int i=1; i<ctx.Id().size(); i++){
							NodeList nodos = doc.getElementsByTagName(ctx.Id(i).getText());
							if (nodos.getLength()!=0){
								for (int j=1; j<nodos.getLength(); j++){
									nodos.item(j).setTextContent(ctx.Char(i-1).getText());
									cont++;
								}
							}
							else{
								System.out.println("Id "+ctx.Id(i).getText()+" does not exist in table "+ctx.Id(0).getText());
							}
						}
						System.out.println("UPDATE("+cont+") con exito.");
					}
				}
				else{
					System.out.println("Table "+ctx.Id(0).getText()+" does not exist");
				}
			}
			else{
				System.out.println("Database "+DBActual+" does not exist.");
			}
		}catch(Exception e){e.printStackTrace();}
		return super.visitUpdateSet(ctx);
	}

	@Override
	public Type visitTableInstruction(GSQLParser.TableInstructionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitTableInstruction(ctx);
	}

	@Override
	public Type visitLiteral(GSQLParser.LiteralContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLiteral(ctx);
	}
	
	public HashMap<String, Document> getHMDatabase(){
		return hmDatabase;
	}
	
	public void setHMDatabase(HashMap<String, Document> hmDatabase){
		this.hmDatabase = hmDatabase;
	}
	
}
