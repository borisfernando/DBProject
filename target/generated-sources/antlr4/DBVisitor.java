
import java.io.File;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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

import com.sun.jmx.interceptor.DefaultMBeanServerInterceptor;

//import sun.org.mozilla.javascript.internal.ast.NewExpression;

public class DBVisitor extends GSQLBaseVisitor<Type>{
	public static String DBActual = "";
	private static String TableActual = "";
	private static int contNumRow = 0;
	private static HashMap<String, Document> hmDatabase;
	private static HashMap<String, Index> hmPrimaryKeyDatabase; 
	private static ArrayList<String> columnsFromTable;
	private static HashMap<String,ArrayList<String>> PrimaryKeys = new HashMap<String,ArrayList<String>>();;
	private Tables table;
	
	public DBVisitor(){
		//PrimaryKeys = new HashMap<String, ArrayList<String>>();
		//hmDatabase = new HashMap<String, Document>();
		//hmPrimaryKeyDatabase = new HashMap<String, Index>();
		columnsFromTable = new ArrayList<String>();
		table = new Tables();
		DBM.createMetadataD();
	}
	
	public void update(){
		DBM.saveDatabase(DBActual, hmDatabase);
		if (DBActual!=""){
			DBM.serializeArray(DBActual, PrimaryKeys);
		}
		DBM.updateDatabases();
		DBM.updateDeletedData();
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
			if (!DBM.existDB(ctx.Id().getText())){
				fNew.mkdir();
				DBM.addMetadataD(ctx.Id().getText());
				DBM.createMetadataT(ctx.Id().getText());
				HashMap<String, ArrayList<String>> temp = new HashMap<String, ArrayList<String>>();
				DBM.serializeArray(ctx.Id().getText(), temp);
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
			boolean bExiste1 = DBM.existDB(ctx.Id(0).getText());
			boolean bExiste2 = DBM.existDB(ctx.Id(1).getText());
			
			if (bExiste1 && !bExiste2){
				DBM.changeNameD(ctx.Id(0).getText(), ctx.Id(1).getText());
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
		return null;
	}
	
	@Override
	public Type visitUseDatabase(GSQLParser.UseDatabaseContext ctx) {
		if (DBM.existDB(ctx.Id().getText())){
			if (DBActual != ""){
				DBM.saveDatabase(DBActual, hmDatabase);	
				if (DBM.existDB(DBActual)){
					DBM.serializeArray(DBActual, PrimaryKeys);
				}
				PrimaryKeys = DBM.deSerializeArray(ctx.Id().getText());
			}
			hmDatabase = new HashMap<String, Document>();
			hmPrimaryKeyDatabase = new HashMap<String, Index>();
			PrimaryKeys = DBM.deSerializeArray(ctx.Id().getText());
			File folder = new File("DB/"+ctx.Id().getText()+"/");
			File[] filesInFolder = folder.listFiles();
			
			for (int i=0; i<filesInFolder.length; i++){
				if (filesInFolder[i].isFile() && filesInFolder[i].getName().contains(".xml")){
					String nombre = filesInFolder[i].getName();
					String nombreC = nombre.substring(0, filesInFolder[i].getName().indexOf(".xml"));
					hmDatabase.put(nombreC, DBM.getElementByName(filesInFolder[i]));
				}
			}
			
			DBActual = ctx.Id().getText();
			contNumRow = 0;
			System.out.println("ACTUALDATABASE: "+DBActual);
		}
		else{
			System.out.println("Database "+ctx.Id().getText()+" does not exist.");
		}
		return null;
	}
	
	@Override
	public Type visitDropDatabase(GSQLParser.DropDatabaseContext ctx) {
		try{
			String database = ctx.Id().getText();
			File f = new File("DB/"+database);
			if (DBM.existDB(database)){
				int[] valores = DBM.getCant(database);
				String op = JOptionPane.showInputDialog(null, "¿Borrar base de datos "+database+" con "+valores[0]+" tablas y "+valores[1]+" registros? (S/N)");
				if (op.equals("S") || op.equals("s")){
					DBM.deleteDatabase(database);
					hmDatabase = new HashMap<String, Document>();
					if (DBM.deleteDirectory(f))
						System.out.println("Database "+database+" deleted.");
					else
						System.out.println("Deleting database "+database+" has found an error.");
				}
			}
			else{
				System.out.println("Database "+database+" does not exist.");
			}
		}catch(Exception e){}
		return null;
	}
	
	@Override
	public Type visitCreateTable(GSQLParser.CreateTableContext ctx) {
		try{
			if (DBM.existDB(DBActual)){
				File fT = new File("DB/"+DBActual+"/"+ctx.Id(0).getText()+".xml");
				if (!DBM.existTable(DBActual,ctx.Id(0).getText())){
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
						if (!t.getName().toUpperCase().equals("CHAR")){
							attr.setValue(t.getName().toUpperCase());
							eNuevo.setAttributeNode(attr);
						}
						else{
							CharType cT = (CharType) t;
							Attr attrL = doc.createAttribute("Length");
							attr.setValue(cT.getName().toUpperCase());
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
								if (!fK.eError()){
									String id = fK.getId();
									String tableRef = fK.getTableReference();
									
									Element fkChildElement = doc.createElement("FK");
									Attr aNameFK = doc.createAttribute("Name");
									aNameFK.setValue(id);
									fkChildElement.setAttributeNode(aNameFK);
									
									if (!DBM.existTable(DBActual, tableRef)){
										guardar = false;
										System.out.println("Table "+tableRef+" as a reference, does not exist.");
									}
									else{
										Attr aTableFK = doc.createAttribute("Table");
										aTableFK.setValue(tableRef);
										fkChildElement.setAttributeNode(aTableFK);
										
										File f = new File("DB/"+DBActual+"/"+tableRef+".xml");
										ArrayList<String> pkColumns = DBM.getPrimaryKeyTable(DBActual,tableRef);
										if (fK.getLengthColumnsId()==fK.getLengthReferences() && fK.getLengthColumnsId()==pkColumns.size()){
											for (int j=0; j<fK.getLengthColumnsId(); j++){
												if (DBM.existColumnInTable(f, fK.getReference(j))){
													if (columnElement.getElementsByTagName(fK.getColumnId(j)).getLength()==0){
														if (pkColumns.contains(fK.getReference(j))){
															NamedNodeMap nNodeM = DBM.getTypeColumn(f, fK.getReference(j));
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
															guardar = false;
															System.out.println("The FK references does not match as a PK in the referenced table.");
														}
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
										else if (fK.getLengthColumnsId()!=pkColumns.size()){
											System.out.println("The FK references does not match the amount of columns in the PK of the table referenced.");
											guardar = false;
										}
										else{
											guardar = false;
										}
									}
									fkElement.appendChild(fkChildElement);
								}
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
					
					
						DBM.addMetadataT(DBActual,ctx.Id(0).getText());
						DBM.modifyDatabase(DBActual, "Tables");
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
			if (DBM.existDB(DBActual)){
				if (DBM.existTable(DBActual,ctx.Id(0).getText())){
					String idNew = ctx.Id(1).getText();
					String idOld = ctx.Id(0).getText();
					File fN = new File("DB/"+DBActual+"/"+idNew+".xml");
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.parse(f);
					doc.getDocumentElement().normalize();
					
					Node rootElement = doc.getDocumentElement();
					Node databaseElement = rootElement.getLastChild();
					
					for (int i=0; i<databaseElement.getChildNodes().getLength(); i++){
						doc.renameNode(databaseElement.getChildNodes().item(i), null, idNew);
					}
					
					File[] folder = new File("DB/"+DBActual+"/").listFiles();
					for (File fFolder: folder){
						if (fFolder.isFile() && fFolder.getName().contains(".xml")){
							Document doc2 = docBuilder.parse(fFolder);
							doc2.getDocumentElement().normalize();
							Element rootElement2 = doc2.getDocumentElement();
							NodeList listFK = rootElement2.getElementsByTagName("FK");
							for (int i=0; i<listFK.getLength(); i++){
								Element eFK = (Element) listFK.item(i);
								if (eFK.getAttribute("Table").equals(idOld)){
									eFK.setAttribute("Table", idNew);
								}
							}
							TransformerFactory transformerFactory = TransformerFactory.newInstance();
							Transformer transformer = transformerFactory.newTransformer();
							DOMSource source = new DOMSource(doc2);
							StreamResult result = new StreamResult(fFolder);
							transformer.transform(source, result);
						}
					}
					
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(f);
					transformer.transform(source, result);
					f.renameTo(fN);
					f.getParentFile().mkdirs();
					//DBActual = DBM.changeNameT(DBActual,idOld,idNew);
					Document docAnterior = hmDatabase.remove(idOld);
					hmDatabase.put(idNew, docAnterior);
					DBM.changeNameT(DBActual,idOld,idNew);
					System.out.println("Correct. Table name "+idOld+", changed to "+idNew+".");
				}
				else{
					System.out.println("Table "+ctx.Id(0).getText()+" does not exist.");
				}
			}
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
	
	@Override
	public Type visitActionAlterTable(GSQLParser.ActionAlterTableContext ctx) {
		if (DBM.existDB(DBActual)){
			if (DBM.existTable(DBActual,ctx.Id().getText())){
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
					
					if (!DBM.existTable(DBActual, tableRef)){
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
								if (DBM.existColumnInTable(fDB, fK.getReference(j))){
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
		return null;
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
			Element constraintElement = (Element) modelElement.getLastChild();
			Element pkElement = (Element) constraintElement.getFirstChild();
			
			NodeList tagElements = nodoRaiz.getElementsByTagName(ctx.Id().getText());
			if (tagElements.getLength()!=0){
				File folder = new File("DB/"+DBActual+"/");
				for (File fT: folder.listFiles()){
					if (fT.isFile() && fT.getName().contains(".xml")){
						String n1 = fT.getName();
						String n2 = n1.substring(0, n1.indexOf(".xml"));
						if (!n2.equals(TableActual) && DBM.existReferenceColumn(TableActual, ctx.Id().getText(), fT)){
							borrar = false;
							System.out.println("Column "+ctx.Id().getText()+" references the table "+n2);
						}
					}
				}
				if (DBM.isFk(ctx.Id().getText(), constraintElement)){
					borrar = false;
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
			else{
				System.out.println("False. Column " +ctx.Id().getText()+" was not deleted from table "+TableActual);
			}
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
	
	@Override
	public Type visitDropTable(GSQLParser.DropTableContext ctx) {
		// TODO Auto-generated method stub
		try{
			boolean pasar = true;
			File folder = new File("DB/"+DBActual+"/");
			for (File f: folder.listFiles()){
				if (f.isFile() && f.getName().contains(".xml") && !f.getName().equals(ctx.Id().getText()+".xml")){
					if (DBM.existReferenceTable(ctx.Id().getText(), f)){
						String n1 = f.getName();
						String n2 = n1.substring(0, n1.indexOf(".xml"));
						System.out.println("Table "+n2+" has a reference in table "+ctx.Id().getText());
						pasar = false;
					}
				}
			}
			if (pasar){
				File f = new File("DB/"+DBActual+"/"+ctx.Id().getText()+".xml");
				if (DBM.existTable(DBActual,ctx.Id().getText())){
					DBM.deleteTable(DBActual, ctx.Id().getText());
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
		return null;
	}
	
	@Override
	public Type visitEqOpExpression(GSQLParser.EqOpExpressionContext ctx) {
		Expression e1 = (Expression) visit(ctx.eqExpression());
		String opString = ctx.eqOp().getText();
		if (opString.equals("<>")){
			opString = "!=";
		}
		Expression e2 = (Expression) visit(ctx.relExpression());
		
		return new MultipleExpression("MExpression",e1,opString,e2,(e1.eError() || e2.eError()));
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
		Key t = (Key) visit(ctx.reference());
		String referenceTable = t.getId();
		boolean error = false;
		for (int i=1; i<ctx.Id().size(); i++){
			error = !DBM.existColumnInTable(new File("DB/"+DBActual+"/"+referenceTable+".xml"), ctx.Id(i).getText()); 
			if (!error){
				columnsId[i-1] = ctx.Id(i).getText();
			}
			else{
				System.out.println("Column "+ctx.Id(i).getText()+" already exist in table "+TableActual);
			}
		}
		ForeignKey fK = new ForeignKey("FK", id, columnsId, referenceTable, t.getColumnsId(),error);
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
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		MultipleExpression e = (MultipleExpression) visit(ctx.expression());
		String t = "";
		HashMap<String,String> referencias = e.getUnaries(t,e, new HashMap<String,String>());
		System.out.println(referencias);
		
		
		
		return null;//super.visitCheck(ctx);
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
			if (DBM.existDB(DBActual)){
				if (DBM.existTable(DBActual,ctx.Id().getText())){
					String id = ctx.Id().getText();
					TableActual = ctx.Id().getText();
					Document doc = hmDatabase.get(ctx.Id().getText());
					doc.getDocumentElement().normalize();
					Element rootElement = doc.getDocumentElement();
					
					int cont = 0;
					if (ctx.where() != null){
						Expression eType = (Expression) visit(ctx.where());
						if (!eType.eError()){
							String cond = "["+eType.toString()+"]";
							XPathFactory xPathfactory = XPathFactory.newInstance();
							XPath xpath = xPathfactory.newXPath();
							
							String exp = "/Table/Database/"+TableActual+"_Row"+cond;
									
							XPathExpression expr = xpath.compile(exp);
							Object result = expr.evaluate(doc, XPathConstants.NODESET);
					        NodeList nodes = (NodeList) result;
					        Element modelElement = (Element) rootElement.getFirstChild();
					        Element databaseElement = (Element) rootElement.getLastChild();
					        
					        ArrayList<String> pkColumns = DBM.getColumnsPKTable(modelElement);
					        
					        for (int i=0; i<nodes.getLength(); i++){
					        	Element e = (Element) nodes.item(i);
					        	
					        	String pkVal = e.getAttributes().item(0).getTextContent();
					        	PrimaryKeys.get(ctx.Id().getText()).remove(pkVal);
					        	
					        	String condR = "[";
						        for (int j=1; j<pkColumns.size(); j++){
						        	String s = pkColumns.get(j);
						        	String v = e.getElementsByTagName(s).item(0).getTextContent();
						        	condR+=s+"="+v+" and ";
						        }
						        String v1 = e.getElementsByTagName(pkColumns.get(pkColumns.size()-1)).item(0).getTextContent();
						        condR+=pkColumns.get(pkColumns.size()-1)+"="+v1+"]";
						        File folder = new File("DB/"+DBActual+"/");
			        			for (File f: folder.listFiles()){
			        				if (f.getName().contains(".xml") && !f.getName().contains(id) && DBM.existReferenceTable(ctx.Id().getText(), f)){
			        					String n1 = f.getName();
			        					String n2 = n1.substring(0, n1.indexOf(".xml"));
			        					Document docR = hmDatabase.get(n2);
			        					Document docN = DBM.deleteColumnValue(n2, condR, docR);
			        					hmDatabase.remove(n2);
			        					hmDatabase.put(n2, docN);
			        				}
			        			}
						        
					        	PrimaryKeys.get(TableActual).remove(e.getAttributes().item(0));
					        	databaseElement.removeChild(nodes.item(i));
					        	cont++;
					        }
					        System.out.println("DELETE ("+cont+").");
						}else{
							System.out.println("Error in expression. ");
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
		
		return null;
	}

	@Override
	public Type visitShowColumns(GSQLParser.ShowColumnsContext ctx) {
		try{
			table.reset();
			String tableName = ctx.Id().getText();
			File f = new File("DB/"+DBActual+"/"+tableName+".xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(f);
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			Element modelElement = (Element) rootElement.getFirstChild();
			Element columnElement = (Element) modelElement.getFirstChild();
			Element constraintElement = (Element) modelElement.getLastChild();
			
			String[] heading = {"Column Name","Type","Length","Restriction","Constraint"};
			table.setColumnSelect(heading);
			for (int i=0; i<columnElement.getChildNodes().getLength(); i++){
				String[] row = new String[5];
				Element eActual = (Element) columnElement.getChildNodes().item(i);
				row[0] = eActual.getNodeName();
				row[1] = eActual.getAttribute("Type");
				if (row[1].equals("CHAR")){
					row[2] = eActual.getAttribute("Length");
				}
				else{
					row[2] = "";
				}
				row[3] = "RESTRICTIONS...";
				
				if (DBM.isFk(eActual.getNodeName(), constraintElement)){
					row[4] = "Foreign Key";
				}
				else if(DBM.isPk(eActual.getNodeName(), constraintElement)){
					row[4] = "Primary Key";
				}
				else{
					row[4] = "";
				}
				
				table.addTable(row);
			}
			table.show();
			
		}catch(Exception e){
			
		}
		
		return super.visitShowColumns(ctx);
	}

	@Override
	public Type visitFloatType(GSQLParser.FloatTypeContext ctx) {
		return new DataType(ctx.getText().toUpperCase());
	}

	@Override
	public Type visitIntType(GSQLParser.IntTypeContext ctx) {
		return new DataType(ctx.getText().toUpperCase());
	}

	@Override
	public Type visitCharType(GSQLParser.CharTypeContext ctx) {
		return new CharType(ctx.CHAR().getText().toUpperCase(),Integer.parseInt(ctx.Num().getText()));
	}

	@Override
	public Type visitDateType(GSQLParser.DateTypeContext ctx) {
		return new DataType(ctx.getText().toUpperCase());
	}
	
	@Override
	public Type visitUnExpression(GSQLParser.UnExpressionContext ctx) {
		if (ctx.NOT()!=null){
			return null;
		}
		else{
			return visit(ctx.selectLiteral());
		}
	}

	@Override
	public Type visitShowTables(GSQLParser.ShowTablesContext ctx) {
		// TODO Auto-generated method stub
		try{
			table.reset();
			DBM.saveDatabase(DBActual, hmDatabase);	
			DBM.serializeArray(DBActual, PrimaryKeys);
			
			Tables t = new Tables();
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
			t.addDatabase(DBActual, nombres);
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
					t.addTables(file.getName().substring(0, file.getName().indexOf(".xml")), columns, databaseElement.getChildNodes().getLength());
					for (int i=0; i<databaseElement.getChildNodes().getLength(); i++){
						Element tActual = (Element) databaseElement.getChildNodes().item(i);
						String[] datos = new String[tActual.getChildNodes().getLength()];
						for (int j=0; j<columns.length; j++){
							String dato = tActual.getElementsByTagName(columns[j]).item(0).getTextContent();
							datos[j] = dato;
						}
						t.addData(datos);
					}
				}
			}
			t.showTables();
		}catch(Exception e){e.printStackTrace();}
		return super.visitShowTables(ctx);
	}

	@Override
	public Type visitExpAndExpression(GSQLParser.ExpAndExpressionContext ctx) {
		return visit(ctx.andExpression());
	}

	@Override
	public Type visitCondOrExpression(GSQLParser.CondOrExpressionContext ctx) {
		Expression e1 = (Expression) visit(ctx.expression());
		String opExpString = ctx.orOp().getText();
		Expression e2 = (Expression) visit(ctx.andExpression());
		
		return new MultipleExpression("MExpression",e1,opExpString,e2,(e1.eError() || e2.eError()));
	}
	
	@Override
	public Type visitActionDropConstraint(GSQLParser.ActionDropConstraintContext ctx) {
		try{
			boolean existe = false;
			Document doc = hmDatabase.get(TableActual);
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement();
			Element modelElement = (Element) rootElement.getFirstChild();
			Element columnElement = (Element) modelElement.getFirstChild();
			Element constraintElement = (Element) modelElement.getLastChild();
			Element pkElement = (Element) constraintElement.getFirstChild();
			Element fkElement = (Element) constraintElement.getLastChild();
			Element databaseElement = (Element) rootElement.getLastChild();	
			
			String id = ctx.Id().getText();
			boolean error = false;
			
			ArrayList<String> columnsPK = DBM.getColumnsPKTable(modelElement);
			for (String s: columnsPK){
				File folder = new File("DB/"+DBActual+"/");
				for (File f: folder.listFiles()){
					if (f.isFile() && f.getName().contains(".xml")){
						String n1 = f.getName();
						String n2 = n1.substring(0, n1.indexOf(".xml"));
						if (f.isFile() && !n2.equals(id) && DBM.existReferenceColumn(TableActual, s, f)){
							error = true;
						}
					}
				}
			}
			if (!error){
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
						for (int j=0; j<columnsArrayList.size(); j++){
							NodeList nodosList = columnElement.getElementsByTagName(columnsArrayList.get(j));
							for (int k=0; k<nodosList.getLength(); k++){
								Element pElement = (Element) nodosList.item(k).getParentNode();
								pElement.removeChild(nodosList.item(k));
							}
						}
						for (int j=0; j<columnsArrayList.size(); j++){
							NodeList nodosList = databaseElement.getElementsByTagName(columnsArrayList.get(j));
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
				else{
					System.out.println("Constraint "+ctx.Id().getText()+" deleted from table "+TableActual);
				}
			}else{
				System.out.println("Constraint was not deleted. The constraint "+id+" has a reference in other table.");
			}
		}catch(Exception e){e.printStackTrace();}
		return null;
	}

	@Override
	public Type visitActionAddConstraint(GSQLParser.ActionAddConstraintContext ctx) {
		try{
			boolean paso = true;
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			
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
			
			ArrayList<String> namesConstraint = DBM.getNamesConstraint(constraintElement);
			
			if (t.getName().equals("PK") && !pkElement.hasChildNodes()){
				System.out.println("The rows with the same PK, will be deleted, only the first one will stay.");
				System.out.println("Same goes to null PK's");
				PrimaryKey pK = (PrimaryKey) t;
				String id = pK.getId();
				if (!namesConstraint.contains(id)){			
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
				else{
					System.out.println("The constraint name "+id+" already exist as constraint.");
				}
			}
			else if(t.getName().equals("PK") && pkElement.hasChildNodes()){
				paso = false;
				System.out.println("False. Theres already a PK");
			}
			else if(t.getName().equals("FK")){
				String cond = "[";
				ForeignKey fK = (ForeignKey) t;
				if (!fK.eError()){
					String id = fK.getId();
					if (!columnsFromTable.contains(id)){
						String tableRef = fK.getTableReference();
						boolean existe = false;
						Element fkChildElement = doc.createElement("FK");
						NodeList nodos = constraintElement.getElementsByTagName("FK");
						for (int i=0; i<nodos.getLength(); i++){
							Element eActual = (Element) nodos.item(i);
							if (eActual.getAttribute("Name").equals(id)){
								existe = true;
								paso = false;
							}
						}
						cond+="@Name !=\'"+id+"\' and ";
						
						if (!existe){
							
							Attr aNameFK = doc.createAttribute("Name");
							aNameFK.setValue(id);
							fkChildElement.setAttributeNode(aNameFK);
							
							if (!DBM.existTable(DBActual, tableRef)){
								paso = false;
								System.out.println("Table "+tableRef+" as a reference, does not exist.");
							}
							else{
								Attr aTableFK = doc.createAttribute("Table");
								cond+="@Table=\'"+tableRef+"\']/Column";
								aTableFK.setValue(tableRef);
								fkChildElement.setAttributeNode(aTableFK);
								File fDB = new File("DB/"+DBActual+"/"+tableRef+".xml");
								if (fK.getLengthColumnsId()==fK.getLengthReferences()){
									ArrayList<String> pkTableRef = DBM.getPrimaryKeyTable(DBActual, tableRef);
									if (pkTableRef.size()==fK.getLengthColumnsId()){
										for (int j=0; j<fK.getLengthColumnsId(); j++){
											System.out.println(pkTableRef);
											if (DBM.existColumnInTable(fDB, fK.getReference(j)) && pkTableRef.contains(fK.getReference(j))){
												cond+="[@Reference=\'"+fK.getReference(j)+"\' and text()=\'"+fK.getColumnId(j)+"\']";
											}
											else if(!DBM.getPrimaryKeyTable(DBActual, tableRef).contains(fK.getReference(j))){
												System.out.println("Column "+fK.getReference(j)+" is not a primary key.");
												paso = false;
											}
											else{
												paso = false;
												System.out.println("Column "+fK.getReference(j)+" does not exist in Table Reference "+fK.getTableReference()+".");
											}
										}
										
										String exp = "//FK"+cond;
										XPathExpression expr = xpath.compile(exp);
										Object result = expr.evaluate(rootElement, XPathConstants.NODESET);
								        NodeList nodes = (NodeList) result;
								        if (nodes.getLength()!=0){
								        	paso = false;
								        }
								        else if (nodes.getLength()!=0 && !paso){
								        	System.out.println("Already a Foreign Key with that columns.");
								        }
										
								        if (paso){
											for (int j=0; j<fK.getLengthColumnsId(); j++){
												Element fkChildNameColumn = doc.createElement("Column");
												fkChildNameColumn.setTextContent(fK.getColumnId(j));
												Attr aNameCol = doc.createAttribute("Reference");
												cond+="[@Reference=\'"+fK.getReference(j)+"\' and text()=\'"+fK.getColumnId(j)+"\']";
												aNameCol.setValue(fK.getReference(j));
												fkChildNameColumn.setAttributeNode(aNameCol);
												fkChildElement.appendChild(fkChildNameColumn);
												Element newChild = doc.createElement(fK.getColumnId(j));
												for (int i=0; i<databaseElement.getChildNodes().getLength(); i++){
													Element aHijo = (Element) databaseElement.getChildNodes().item(i);
													aHijo.appendChild(newChild);
												}
												NamedNodeMap map = DBM.getTypeColumn(fDB,fK.getReference(j));
												for (int i=0; i<map.getLength(); i++){
													newChild.setAttributeNode((Attr)doc.importNode(map.item(i),true));
												}
												columnElement.appendChild(newChild);
												
											}
								        }
									}
									else{
										System.out.println("Primary Keys length does not match to be a Foreign Key.");
										paso = false;
									}
								}
								else{
									paso = false;
								}
							}
						}
						else{
							System.out.println("Foreign Key named "+id+" already exist.");
						}
						if (paso){
							fkElement.appendChild(fkChildElement);
						}
					}
					else{
						System.out.println("The constraint name "+id+" already exist as constraint.");
					}
				}
				else{
					paso = false;
				}
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
			
		}catch(Exception e){e.printStackTrace();}	
		return null;
	}

	@Override
	public Type visitCondExpression(GSQLParser.CondExpressionContext ctx) {
		Expression e1 = (Expression) visit(ctx.andExpression());
		String opString = ctx.andOp().getText();
		Expression e2 = (Expression) visit(ctx.eqExpression());
		
		return new MultipleExpression("MExpression",e1,opString,e2,(e1.eError() || e2.eError()));
	}

	@Override
	public Type visitInsertInto(GSQLParser.InsertIntoContext ctx) {
		// TODO Auto-generated method stub
		try{
			if (DBM.existDB(DBActual)){
				if (DBM.existTable(DBActual,ctx.Id(0).getText())){
					Document doc = hmDatabase.get(ctx.Id(0).getText());
					//Document doc =(Document) hmDatabase.get(ctx.Id(0).getText()).cloneNode(true);
					doc.getDocumentElement().normalize();
					
					Element nodoRaiz = doc.getDocumentElement();
					
					Element modelElement = (Element)nodoRaiz.getFirstChild();
					Element columnsElement = (Element)modelElement.getFirstChild();
					Element constraintElement = (Element)modelElement.getLastChild();
					Element pkElement = (Element)constraintElement.getFirstChild();
					Element fkElement = (Element)constraintElement.getLastChild();
					
					ArrayList<String> pkColumns = DBM.getColumnsPKTable(modelElement);
					/* ACA VA FOREIGN KEY */
					ArrayList<String> columnAgregadas = new ArrayList<String>();
					HashMap<String, ArrayList<String>> fkColumns = new HashMap<String, ArrayList<String>>();
					
					for (int i=0; i<fkElement.getChildNodes().getLength(); i++){
						Element fkActual = (Element) fkElement.getChildNodes().item(i);
						String nombre = fkActual.getAttribute("Table");
						ArrayList<String> arrayActual = new ArrayList<String>();
						for (int j=0; j<fkActual.getChildNodes().getLength(); j++){
							Element fkColumnActual = (Element) fkActual.getChildNodes().item(j);
							//arrayActual.add(fkColumnActual.getAttribute("Reference"));
							arrayActual.add(fkColumnActual.getTextContent());
						}
						fkColumns.put(nombre, arrayActual);
					}
					
					
					if (pkElement.hasChildNodes()){
						for (int i=0; i<pkElement.getChildNodes().getLength(); i++){
							Element e = (Element) pkElement.getChildNodes().item(i);
							pkColumns.add(e.getTextContent());
						}
						
						Element databaseElement = (Element)nodoRaiz.getLastChild();
						Element newDataElement = doc.createElement(ctx.Id(0).getText()+"_Row");
						
						boolean save = true;
						String pKeyString = "";
						//Por si te dan las columnas.
						if (ctx.Id().size()>1 && ctx.Id().size()-1==ctx.literal().size()){
							for (int i=1; i<ctx.Id().size(); i++){
								NodeList columnList = modelElement.getElementsByTagName(ctx.Id(i).getText());
								if (columnList.getLength()==1){
									Element columnElement = (Element) columnList.item(0);
									Element newElement = doc.createElement(columnElement.getNodeName());
									columnAgregadas.add(columnElement.getNodeName());
									boolean esPK = pkColumns.contains(columnElement.getNodeName());
									ValueType vT = (ValueType) visit(ctx.literal(i-1));
									
									String valType = vT.getName().toUpperCase();
					        		String valColumn = columnElement.getAttribute("Type").toUpperCase();
									if (valType.equals(valColumn)){
										String value = vT.getValue();
										if(vT.getName().equals("CHAR")){
											if (Integer.parseInt(columnElement.getAttribute("Length"))>=value.length()){
												if (esPK){
													pKeyString += value+"_";
												}
												newElement.setTextContent(value);
											}
											else{
												save = false;
												System.out.println("Error in Insert. Char \""+value+"\" greater than expected.");
											}
										}
										else if(vT.getName().equals("INT")){
											int val = Integer.parseInt(value);
											value = ""+val;
											if (esPK){
												pKeyString += value+"_";
											}
											newElement.setTextContent(value);
										}
										else if(vT.getName().equals("FLOAT")){
											float val = Float.parseFloat(value);
											value = ""+val;
											if (esPK){
												pKeyString += value+"_";
											}
											newElement.setTextContent(value);
										}
										else{
											if (esPK){
												pKeyString += value+"_";
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
								boolean esPK = pkColumns.contains(e.getNodeName()) && columnAgregadas.contains(e.getNodeName());
								boolean esFK = false;
								for (ArrayList<String> t: fkColumns.values()){
									if (t.contains(e.getNodeName()) && columnAgregadas.contains(e.getNodeName())){
										esFK = true;
									}
								}
								
								if (!columnAgregadas.contains(e.getNodeName()) && (!esPK || !esFK)){
									Element newElement = doc.createElement(e.getNodeName());
									newDataElement.appendChild(newElement);
								}
								else if (!columnAgregadas.contains(e.getNodeName()) && esPK){
									save = false;
									System.out.println("Must give a key in PK and FK");
								}
							}
						}
						//Por si no te dan las columnas.
						else if (ctx.Id().size()<2){
							for (int i=0; i<columnsElement.getChildNodes().getLength(); i++){
								Element e = (Element) columnsElement.getChildNodes().item(i);
								Element newElement = doc.createElement(e.getNodeName());
								boolean esPK = pkColumns.contains(e.getNodeName());
								if (ctx.literal(i)!=null){
									ValueType vT = (ValueType) visit(ctx.literal(i));
									String valType = vT.getName().toUpperCase();
					        		String valColumn = e.getAttribute("Type").toUpperCase();
									
									if (valType.equals(valColumn)){
										String value = vT.getValue();
										if(vT.getName().equals("CHAR")){
											if (Integer.parseInt(e.getAttribute("Length"))>=value.length()){
												if (esPK && !value.equals("")){
													pKeyString += value+"_";
												}
												else if (esPK && value.equals("")){
													save = false;
													System.out.println("Must give a key in PK");
												}
												newElement.setTextContent(value);
											}
											else{
												save = false;
												System.out.println("Error in Insert. Char \""+value+"\" greater than expected.");
											}
										}
										else if(vT.getName().equals("INT")){
											int val = Integer.parseInt(value);
											value = ""+val;
											if (esPK && !value.equals("")){
												pKeyString += value+"_";
											}
											else if (esPK && value.equals("")){
												save = false;
												System.out.println("Must give a key in PK");
											}
											newElement.setTextContent(value);
										}
										else if(vT.getName().equals("FLOAT")){
											float val = Float.parseFloat(value);
											value = ""+val;
											if (esPK && !value.equals("")){
												pKeyString += value+"_";
											}
											else if (esPK && value.equals("")){
												save = false;
												System.out.println("Must give a key in PK");
											}
											newElement.setTextContent(value);
										}
										else{
											if (esPK && !value.equals("")){
												pKeyString += value+"_";
											}
											else if (esPK && value.equals("")){
												save = false;
												System.out.println("Must give a key in PK");
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
									if (pkColumns.contains(newElement.getNodeName())){
										System.out.println("Must give a key in PK");
										save = false;
									}
									else{
										newDataElement.appendChild(newElement);
									}
									
								}
							}
						}
						else{
							System.out.println("Error. The length of values and columns is not the same.");
							fkColumns = new HashMap<String, ArrayList<String>>();
							pKeyString = ".";
						}
						
						for (String fkColum: fkColumns.keySet()){
							String text = "";
							for (String col: fkColumns.get(fkColum)){
								Element e = (Element) newDataElement.getElementsByTagName(col).item(0);
								text+=e.getTextContent()+"_";
							}
							text = text.substring(0, text.length()-1);
							if (!PrimaryKeys.containsKey(fkColum) || !PrimaryKeys.get(fkColum).contains(text)){
								if (text.equals(""))
									text = "NULL";
								System.out.println("PK "+text+" does not exist in table "+fkColum);
								save = false;
							}
						}
						
						if (save){
							if (pKeyString.equals("")){
								pKeyString = ".";
							}
							String t = pKeyString.substring(0, pKeyString.length()-1);
							if (!PrimaryKeys.get(ctx.Id(0).getText()).contains(t) && !t.equals("")){
								PrimaryKeys.get(ctx.Id(0).getText()).add(t);
								String nombrePK = pkElement.getAttribute("Name");
								Attr a = doc.createAttribute(nombrePK);
								a.setValue(t);
								newDataElement.setAttributeNode(a);
								databaseElement.appendChild(newDataElement);
								//hmDatabase.put(TableActual, doc);
								//System.out.println("Insert("+contNumRow+").");
								contNumRow = DBM.modifyTable(DBActual, ctx.Id(0).getText())+1;
								DBM.modifyDatabase(DBActual, "Records");
					        }
					        else{
					        	if (!t.equals("")){
						        	System.out.println("Already exist a row with "+t+" as Primary Key.");
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
		String[] encab = {"Name","Tables","Records"};
		table.setColumnSelect(encab);
		String[] row = new String[3];
		String[] nombres = DBM.getNombresDatabase();
		for (int i=0; i<nombres.length; i++){
			row[0] = nombres[i];
			int[] cant = DBM.getCant(nombres[i]);
			row[1] = ""+cant[0];
			row[2] = ""+cant[1];
			table.addTable(row);
		}
		table.show();
		return null;
	}

	@Override
	public Type visitRelOp(GSQLParser.RelOpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRelOp(ctx);
	}

	@Override
	public Type visitEqAndExpression(GSQLParser.EqAndExpressionContext ctx) {
		return visit(ctx.eqExpression());
	}

	@Override
	public Type visitRelOpExpression(GSQLParser.RelOpExpressionContext ctx) {
		String newConditionString = "";
		Expression e1 = (Expression) visit(ctx.relExpression());
		String opString = ctx.relOp().getText();
		Expression e2 = (Expression) visit(ctx.unExpression());
		boolean error = false;
		
		/*if (eR.getName().equals("Unary")){
			String eRValue = eR.getCondition();
			String eUValue = eU.getCondition();
			
			Document doc = hmDatabase.get(TableActual);
			doc.getDocumentElement().normalize();
			
			Element modelElement = (Element) doc.getDocumentElement().getFirstChild().getFirstChild();
			if (modelElement.getElementsByTagName(eRValue).getLength()!=0){
				newConditionString+=eRValue+relation+eUValue+"";
			}else if (modelElement.getElementsByTagName(eUValue).getLength()!=0){
				newConditionString+=eRValue+relation+eUValue+"";
			}
			else{
				error = true;
				System.out.println("Error. Id not declarated.");
			}
		}
		else{
			//Falta ver este
			// TODO Auto-generated method stub
		}
		// por el momento false*/
		return new MultipleExpression("MExpression",e1,opString,e2,(e1.eError() || e2.eError()));
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
	public Type visitInt_literal(GSQLParser.Int_literalContext ctx) {
		return new ValueType("INT", ctx.getText());
	}

	@Override
	public Type visitChar_literal(GSQLParser.Char_literalContext ctx) {
		String val = ctx.getText();
		val = val.substring(1, val.length()-1);
		return new ValueType("CHAR", val);
	}

	@Override
	public Type visitFloat_literal(GSQLParser.Float_literalContext ctx) {
		return new ValueType("FLOAT", ctx.getText());
	}

	@Override
	public Type visitDate_literal(GSQLParser.Date_literalContext ctx) {
		return new ValueType("DATE", ctx.getText());
	}

	@Override
	public Type visitSelectLiteral(GSQLParser.SelectLiteralContext ctx) {
		return super.visitSelectLiteral(ctx);
	}

	@Override
	public Type visitSelect_int(GSQLParser.Select_intContext ctx) {
		return new UnaryExpression("Unary", ctx.Num().getText(),"INT", false);
	}

	@Override
	public Type visitSelect_char(GSQLParser.Select_charContext ctx) {	
		String t = ctx.Char().getText();
		return new UnaryExpression("Unary", t, "CHAR", false);
	}

	@Override
	public Type visitSelect_float(GSQLParser.Select_floatContext ctx) {
		return new UnaryExpression("Unary", ctx.Float().getText(), "FLOAT", false);
	}
	
	@Override
	public Type visitSelect_date(GSQLParser.Select_dateContext ctx) {
		return new UnaryExpression("Unary", ctx.Date().getText(), "DATE", false);
	}

	@Override
	public Type visitDoubleId_literal(GSQLParser.DoubleId_literalContext ctx) {
		if (DBM.existTable(DBActual, ctx.Id(0).getText())){
			boolean error = !(DBM.existColumnInTable(new File("DB/"+DBActual+"/"+ctx.Id(0).getText()+".xml"), ctx.Id(1).getText()));
			if (error)
				System.out.println("Column "+ctx.Id(1).getText()+" does not exist in table "+ctx.Id(0).getText());
			return new IdValueType("Unary", ctx.Id(1).getText(), "ID", ctx.Id(0).getText(), error, true);
		}
		else{
			System.out.println("Table "+ctx.Id(0).getText()+" does not exist in database "+DBActual);
			return new IdValueType("Unary", ctx.Id(1).getText(), "ID", ctx.Id(0).getText(), true, true);
		}
	}

	@Override
	public Type visitSimpId_literal(GSQLParser.SimpId_literalContext ctx) {
		String id = ctx.Id().getText();
		boolean error = false;
		if (!TableActual.equals("")){
			error = !(DBM.existColumnInTable(new File("DB/"+DBActual+"/"+TableActual+".xml"), id));
			if (error){
				System.out.println("Column "+id+" does not exist in table "+TableActual);
			}
		}
		else if(columnsFromTable.indexOf(id) != columnsFromTable.lastIndexOf(id)){
			System.out.println("There is no reference for duplicated "+id);
			error = true;
		}
		else if(!columnsFromTable.contains(id)){
			System.out.println("Column name "+id+" does not exist");
			error = true;
		}
		return new IdValueType("Unary", id, "ID", "", error, false);
	}
	

	@Override
	public Type visitEqRelExpression(GSQLParser.EqRelExpressionContext ctx) {
		return visit(ctx.relExpression());
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
		return visit(ctx.unExpression());
	}

	@Override
	public Type visitUpdateSet(GSQLParser.UpdateSetContext ctx) {
		// TODO Auto-generated method stub
		try{
			if (DBM.existDB(DBActual)){
				if (DBM.existTable(DBActual,ctx.Id(0).getText())){
					TableActual = ctx.Id(0).getText();
					Document doc = hmDatabase.get(ctx.Id(0).getText());
					doc.getDocumentElement().normalize();
					
					Element rootElement = doc.getDocumentElement();
					Element modelElement = (Element) rootElement.getFirstChild();
					Element columnElement = (Element) modelElement.getFirstChild();
					Element databaseElement = (Element) rootElement.getLastChild();
					
					int cont = 0;
					boolean save = true;
					if (ctx.where() != null){
						Expression eType = (Expression) visit(ctx.where());
						if (!eType.eError()){
							String cond = "["+eType.toString()+"]";
							
							XPathFactory xPathfactory = XPathFactory.newInstance();
							XPath xpath = xPathfactory.newXPath();
							
							String exp = "/Table/Database/"+TableActual+"_Row"+cond;
							
							XPathExpression expr = xpath.compile(exp);
							Object result = expr.evaluate(doc, XPathConstants.NODESET);
					        NodeList nodes = (NodeList) result;
					        
					        ArrayList<String> pkColumns = DBM.getColumnsPKTable(modelElement);
					        
					        for (int i=0; i<nodes.getLength(); i++){
					        	for (int j=1; j<ctx.Id().size(); j++){
					        		String columnString = ctx.Id(j).getText();
					        		NodeList nodos = columnElement.getElementsByTagName(columnString);
					        		if (nodos.getLength()!=0){
						        		Element columnValueElement = (Element) nodos.item(0);
						        		ValueType vT = (ValueType) visit(ctx.literal(j-1));;
						        		String valType = vT.getName().toUpperCase();
						        		String valColumn = columnValueElement.getAttribute("Type").toUpperCase();
						        		boolean paso = false;
						        		if (valType.equals(valColumn)){
											String value = vT.getValue();
											if(vT.getName().equals("CHAR")){
												if (Integer.parseInt(columnValueElement.getAttribute("Length"))>=value.length()){
													Element nElement = (Element) nodes.item(i);
													if (pkColumns.contains(columnString)){
														Element eCambio = (Element) nodes.item(i);
														String valueOld = eCambio.getElementsByTagName(columnString).item(0).getTextContent();
									        			File folder = new File("DB/"+DBActual+"/");
									        			for (File f: folder.listFiles()){
									        				if (f.getName().contains(".xml") && DBM.existReferenceColumn(ctx.Id(0).getText(), columnString, f)){
									        					String n1 = f.getName();
									        					String n2 = n1.substring(0, n1.indexOf(".xml"));
									        					Document docR = hmDatabase.get(n2);
									        					String columnFK = DBM.getColumnFKTable(columnString, f);
									        					DBM.changeColumnValue(n2,columnFK,valueOld, value, docR);
									        				}
									        			}
									        			String pkNew = "";
									        			String pkOld = eCambio.getAttributes().item(0).getTextContent();
									        			for (String pk: pkColumns){
									        				if (!pk.equals(columnString)){
									        					pkNew+=eCambio.getElementsByTagName(pk).item(0).getTextContent()+"_";
									        				}
									        				else{
									        					pkNew+=value+"_";								        					
									        				}
									        			}
									        			pkNew = pkNew.substring(0, pkNew.length()-1);
									        			nElement.getAttributes().item(0).setTextContent(pkNew);
									        			if (!PrimaryKeys.get(ctx.Id(0).getText()).contains(pkNew)){
									        				PrimaryKeys.get(ctx.Id(0).getText()).remove(pkOld);
										        			PrimaryKeys.get(ctx.Id(0).getText()).add(pkNew);
									        			}
									        			else{
									        				save = false;
									        				System.out.println("Already a row with PK "+pkNew+".");
									        			}
									        		}
													if (save){
														Element valueElement = (Element) nElement.getElementsByTagName(ctx.Id(j).getText()).item(0);
														valueElement.setTextContent(value);
														cont++;
													}
												}
												else{
													System.out.println("Error in Update. Char \""+value+"\" greater than expected.");
												}
												paso = true;
											}
											if (valType.equals("INT")){
												int val = Integer.parseInt(value);
												value = ""+val;
											}
											else if(valType.equals("FLOAT")){
												float val = Float.parseFloat(value);
												value = ""+val;
											}
											if (!paso){
												Element nElement = (Element) nodes.item(i);
												if (pkColumns.contains(columnString)){
													
													Element eCambio = (Element) nodes.item(i);
													String valueOld = eCambio.getElementsByTagName(columnString).item(0).getTextContent();
								        			File folder = new File("DB/"+DBActual+"/");
								        			for (File f: folder.listFiles()){
								        				if (f.getName().contains(".xml") && DBM.existReferenceColumn(ctx.Id(0).getText(), columnString, f)){
								        					String n1 = f.getName();
								        					String n2 = n1.substring(0, n1.indexOf(".xml"));
								        					Document docR = hmDatabase.get(n2);
								        					String columnFK = DBM.getColumnFKTable(columnString, f);
								        					DBM.changeColumnValue(n2,columnFK, valueOld, value, docR);
								        				}
								        			}
								        			String pkNew = "";
								        			String pkOld = eCambio.getAttributes().item(0).getTextContent();
								        			for (String pk: pkColumns){
								        				if (!pk.equals(columnString)){
								        					pkNew+=eCambio.getElementsByTagName(pk).item(0).getTextContent()+"_";
								        				}
								        				else{
								        					pkNew+=value+"_";								        					
								        				}
								        			}
								        			pkNew = pkNew.substring(0, pkNew.length()-1);
								        			nElement.getAttributes().item(0).setTextContent(pkNew);
								        			if (!PrimaryKeys.get(ctx.Id(0).getText()).contains(pkNew)){
								        				PrimaryKeys.get(ctx.Id(0).getText()).remove(pkOld);
									        			PrimaryKeys.get(ctx.Id(0).getText()).add(pkNew);
								        			}
								        			else{
								        				save = false;
								        				System.out.println("Already a row with PK "+pkNew);
								        			}
								        		}
												if (save){
													Element valueElement = (Element) nElement.getElementsByTagName(ctx.Id(j).getText()).item(0);
													valueElement.setTextContent(value);
													cont++;
												}
											}
										}
										else{
											System.out.println("Error in Update. Found "+vT.getName()+" expected "+columnElement.getAttribute("Type"));
										}
					        		}else{
					        			System.out.println("Column "+columnString+" does not exist in table "+TableActual);
					        		}
					        	}
					        }
							System.out.println("UPDATE("+cont+")");
						}
				        else{
				        	System.out.println("Error in expression");
				        }
					}
					else{
						for (int i=1; i<ctx.Id().size(); i++){
							String columnString = ctx.Id(i).getText();
							
							Element columnValueElement = (Element) columnElement.getElementsByTagName(columnString).item(0);

							ValueType vT = (ValueType) visit(ctx.literal(i-1));
							
							String valType = vT.getName().toUpperCase();
			        		String valColumn = columnValueElement.getAttribute("Type").toUpperCase();
							
							if (valType.equals(valColumn)){
								String value = vT.getValue();
								if(vT.getName().equals("CHAR")){
									if (Integer.parseInt(columnValueElement.getAttribute("Length"))>=value.length()){
										NodeList nList = databaseElement.getElementsByTagName(columnString);
										for (int j=0; j<nList.getLength(); j++){
											nList.item(j).setTextContent(value);
											cont++;
										}
									}
									else{
										System.out.println("Error in Update. Char \""+value+"\" greater than expected.");
									}
								}
								else{
									NodeList nList = databaseElement.getElementsByTagName(columnString);
									for (int j=0; j<nList.getLength(); j++){
										nList.item(j).setTextContent(value);
										cont++;
									}
								}
							}
							else{
								System.out.println("Error in Update. Found "+vT.getName()+" expected "+columnElement.getAttribute("Type"));
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
	
	@Override
	public Type visitOrderId(GSQLParser.OrderIdContext ctx) {
		UnaryExpression ids = (UnaryExpression) visit(ctx.select_id());
		IdValueType idVal = (IdValueType) ids;
		if (!idVal.eError()){
			String order = "asc";
			if (ctx.orderType()!=null){
				ValueType typeOrder = (ValueType) visit(ctx.orderType());
				order = typeOrder.getValue();
			}
			if (idVal.hasRef()){
				return new ValueType(idVal.getTableRef()+"."+idVal.getValue(), order);
			}
			else{
				return new ValueType(idVal.getValue(), order);
			}
		}
		else{
			return null;
		}
	}

	@Override
	public Type visitOrderBy(GSQLParser.OrderByContext ctx) {
		String[][] order = new String[ctx.orderId().size()][ctx.orderId().size()];
		boolean error = false;
		for (int i=0; i<ctx.orderId().size(); i++){
			String[] orderS = new String[2];
			ValueType valueId = (ValueType) visit(ctx.orderId(i));
			if (valueId!=null){
				String id = valueId.getName();
				String orderTable = valueId.getValue();
				
				if (table.getIndexOfColumnSelect(id)!=-1){
					orderS[0] = id;
					orderS[1] = orderTable;
					order[i] = orderS;
				}
				else if(table.getIndexOfColumnSelect("."+id)!=-1){
					orderS[0] = id;
					orderS[1] = orderTable;
					order[i] = orderS;
				}
				else{
					System.out.println("Column "+id+" is not in select field.");
					error = true;
				}
			}else{
				error = true;
			}
		}
		if (!error)
			table.orderTable(order);
		return null;
	}

	@Override
	public Type visitOrderType(GSQLParser.OrderTypeContext ctx) {
		return new ValueType("ORDER", ctx.getText());
	}

	@Override
	public Type visitWhere(GSQLParser.WhereContext ctx) {
		return visit(ctx.expression());
	}

	@Override
	public Type visitSelect(GSQLParser.SelectContext ctx) {
		try{
			boolean error = false;
			table.reset();
			CartesianTable cT = (CartesianTable) visit(ctx.from());
			if (cT!=null){
				Element rows = cT.getGeneral();
				if (ctx.where()!=null){
					MultipleExpression where = (MultipleExpression) visit(ctx.where());
					if (!where.eError()){
						String t = "";
						HashMap<String,String> referencias = where.getUnaries(t,where, new HashMap<String,String>());
										
						String exp = "/Database/row[";
						for (String r: referencias.keySet()){
							exp+=r+"]";
						}
						
						//use database db; select * from persons, fechas where persons.personid=10;
						//exp = "/Database/row[./personid[@Reference = \'persons\']/text() = ./fechaid[@Reference=\'fechas\']/text()]";
						
						XPathFactory xPathfactory = XPathFactory.newInstance();
						XPath xpath = xPathfactory.newXPath();
						XPathExpression expr = xpath.compile(exp);
						Object result = expr.evaluate(rows, XPathConstants.NODESET);
				        NodeList nodes = (NodeList) result;
				        
				        String[] columnHead = new String[nodes.item(0).getChildNodes().getLength()];
				        for (int i=0; i<nodes.item(0).getChildNodes().getLength(); i++){
				        	Element eActual = (Element) nodes.item(0).getChildNodes().item(i);
				        	columnHead[i] = eActual.getAttribute("Reference")+"."+eActual.getNodeName();
				        }
				        table.setTableHeader(columnHead);
				        for (int i=0; i<nodes.getLength(); i++){		        	
			        		Element eActual = (Element) nodes.item(i);
			        		ValueType[] column = new ValueType[eActual.getChildNodes().getLength()];
			        		for (int j=0; j<eActual.getChildNodes().getLength(); j++){
			        			Element eHijo = (Element) eActual.getChildNodes().item(j);
			        			int index = table.getIndexOfColumn(eHijo.getAttribute("Reference")+"."+eHijo.getNodeName());
			        			column[index] = new ValueType(eHijo.getAttribute("Type"), eHijo.getTextContent());
			        			table.addTableSelect(column);
			        		}
				        }
					}
					else{
						System.out.println("Error in expression.");
						error = true;
					}
				}
				else{
					
					/*File fT = new File("DB/PRUEBASSSSS.md");
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(rows);
					StreamResult results = new StreamResult(fT);
					transformer.transform(source, results);
					fT.getParentFile().mkdirs(); 
					fT.createNewFile();*/
					
					String[] heading = new String[rows.getChildNodes().item(0).getChildNodes().getLength()];
					for (int i=0; i<rows.getChildNodes().item(0).getChildNodes().getLength(); i++){
						Element e = (Element) rows.getChildNodes().item(0).getChildNodes().item(i);
						heading[i] = e.getAttribute("Reference")+"."+e.getNodeName();
					}
					table.setTableHeader(heading);
					for (int i=0; i<rows.getChildNodes().getLength(); i++){
						Element e = (Element) rows.getChildNodes().item(i);
						ValueType[] val = new ValueType[heading.length];
						for (int j=0; j<e.getChildNodes().getLength(); j++){
							Element eHijo = (Element) e.getChildNodes().item(j);
							int index = table.getIndexOfColumn(eHijo.getAttribute("Reference")+"."+eHijo.getNodeName());
							val[index] = new ValueType(eHijo.getAttribute("Type"), eHijo.getTextContent());
						}
						table.addTableSelect(val);
					}
				}
				if (ctx.select_id().size()!=0){
			        String[] columnsSelect = new String[ctx.select_id().size()];
			        for (int i=0; i<ctx.select_id().size(); i++){
			        	UnaryExpression ids = (UnaryExpression) visit(ctx.select_id(i));
						IdValueType idVal = (IdValueType) ids;
						if (idVal.eError()){
							error = true;
						}
						String id = idVal.getValue();
						String ref = "";
						if (!idVal.hasRef()){
							Element e = (Element) rows.getChildNodes().item(0);
							Element n = (Element) e.getElementsByTagName(id).item(0);
							ref = n.getAttribute("Reference");
						}
						else{
							ref = idVal.getTableRef();
						}
						
						columnsSelect[i] = ref+"."+id;
			        }
			        table.setColumnSelect(columnsSelect);
		        }
		        else{
		        	table.setColumnSelectAll();
		        }
		        
		        if (!error && ctx.orderBy()==null){
		        	table.showTable();
		        }
		        else if (!error && ctx.orderBy()!=null){
		        	visit(ctx.orderBy());
		        }
		        else{
		        	System.out.println("No results");
		        }
			}
		}catch(Exception e){e.printStackTrace();}
        
		return null;
	}

	@Override
	public Type visitFrom(GSQLParser.FromContext ctx) {
		try{
			long iniciale = System.currentTimeMillis();
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element d = doc.createElement("Database");
			doc.appendChild(d);
			
			ArrayList<String> ids = new ArrayList<String>();
			for (int i=0; i<ctx.Id().size(); i++){
				if (DBM.existTable(DBActual, ctx.Id(i).getText())){
					ids.add(ctx.Id(i).getText());
					ArrayList<String> col = DBM.getColumnsByTable(ctx.Id(i).getText(), DBActual);
					for (String c: col){
						columnsFromTable.add(c);
					}
				}
				else{
					System.out.println("Table "+ctx.Id(i).getText()+" does not exist in database "+DBActual+".");
					return null;
				}
			}
			
			CartesianTable cT = new CartesianTable(ids, (HashMap<String,Document>) hmDatabase.clone(), doc);
			
			long finale = System.currentTimeMillis();
			System.out.println("FROM "+TimeUnit.MILLISECONDS.toSeconds(finale - iniciale)+ " segundos.");
			return cT;
		}catch(Exception e){e.printStackTrace();}			
		return null;
	}
	
}
