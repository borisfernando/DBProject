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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DBVisitor extends GSQLBaseVisitor<Type>{
	private static String DBActual = "";
	private static String TableActual = "";
	private static int contNumRow = 0;
	private static HashMap<String, Document> hmDatabase;
	
	public DBVisitor(){
		Xml.createMetadataD();
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
				System.out.println("Corret. Database name "+ctx.Id(0).getText()+", changed to "+ctx.Id(1).getText()+".");
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
			}
			hmDatabase = new HashMap<String, Document>();
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
						if (!t.getName().equals("char")){
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
					if (ctx.constraint().size()==0){
						guardar = false;
					}
					else{
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
												Element fkChildNameColumn = doc.createElement("Column");
												fkChildNameColumn.setTextContent(fK.getColumnId(j));
												Attr aNameCol = doc.createAttribute("Reference");
												aNameCol.setValue(fK.getReference(j));
												fkChildNameColumn.setAttributeNode(aNameCol);
												fkChildElement.appendChild(fkChildNameColumn);
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
		try{
			File f = new File("DB/"+DBActual+"/"+TableActual+".xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(f);
			doc.getDocumentElement().normalize();
			
			Node nodoRaiz = doc.getDocumentElement();
			Node modelElement = nodoRaiz.getFirstChild();
			Node databaseElement = nodoRaiz.getLastChild();
			
			boolean bExistColumn = false;
			for (int i=0; i<modelElement.getChildNodes().getLength(); i++){
				if (modelElement.getChildNodes().item(i).getNodeName().equals(ctx.Id().getText())){
					bExistColumn = true;
				}
			}
			
			if (!bExistColumn){
				Element eNuevo = doc.createElement(ctx.Id().getText());
				Attr attr = doc.createAttribute("Type");
				attr.setValue(ctx.type().getText());
				eNuevo.setAttributeNode(attr);
				modelElement.appendChild(eNuevo);
				for (int i=0; i<databaseElement.getChildNodes().getLength(); i++){
					databaseElement.getChildNodes().item(i).appendChild(eNuevo);
				}
				
				System.out.println("Correct. Column name "+ctx.Id().getText()+" added to table "+TableActual+".");
			}
			else{
				System.out.println("Column "+ctx.Id().getText()+" already exist.");
			}
			
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer transformer = transFact.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			transformer.transform(source,result);
			
		}catch(Exception e){e.printStackTrace();}
		return null;//super.visitActionAddColumn(ctx);
	}

	@Override
	public Type visitActionDropColumn(GSQLParser.ActionDropColumnContext ctx) {
		// TODO Auto-generated method stub
		try{
			File f = new File("DB/"+DBActual+"/"+TableActual+".xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(f);
			doc.getDocumentElement().normalize();
			
			Node nodoRaiz = doc.getDocumentElement();
			Node modelElement = nodoRaiz.getFirstChild();
			Node databaseElement = nodoRaiz.getLastChild();
			
			boolean bExistColumn = false;
			for (int i=0; i<modelElement.getChildNodes().getLength(); i++){
				if (modelElement.getChildNodes().item(i).getNodeName().equals(ctx.Id().getText())){
					Node rNode = modelElement.getChildNodes().item(i);
					modelElement.removeChild(rNode);
					bExistColumn = true;
				}
			}
			
			if (bExistColumn){
				for (int i=0; i<databaseElement.getChildNodes().getLength(); i++){
					Node tActual = databaseElement.getChildNodes().item(i);
					for (int j=0; j<tActual.getChildNodes().getLength(); j++){
						Node rNode = tActual.getChildNodes().item(i);
						tActual.removeChild(rNode);
					}
				}
			}
			else{
				System.out.println("Column "+ctx.Id().getText()+" does not exist in Table "+TableActual+".");
			}
		
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer transformer = transFact.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			transformer.transform(source,result);
			
		}catch(Exception e){e.printStackTrace();}
		return null;//return super.visitActionDropColumn(ctx);
	}
	
	@Override
	public Type visitDropTable(GSQLParser.DropTableContext ctx) {
		// TODO Auto-generated method stub
		// FALTA REVISAR LAS REFERENCIAS PARA PODER ELIMINAR POR COMPLETO LA TABLA
		try{
			File f = new File("DB/"+DBActual+"/"+ctx.Id().getText()+".xml");
			if (Xml.existTable(DBActual,ctx.Id().getText())){
				Xml.deleteTable(DBActual, ctx.Id().getText());
				hmDatabase.remove(ctx.Id().getText());
				if (f.delete())
					System.out.println("Database "+ctx.Id().getText()+" deleted.");
				else
					System.out.println("Deleting database "+ctx.Id().getText()+" has found an error.");
			}
			else
				System.out.println("Table "+ctx.Id().getText()+" does not exist.");
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
		return new ValueType("date",ctx.Date().getText());
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
				if (!listOfFiles[i].getName().contains(".md")){
					nombres[contadorN] = listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf(".xml"));
					contadorN++;
				}
			}
			t.agregarDatabase(DBActual, nombres);
			for (File file : listOfFiles) {
				if (file.isFile() && !file.getName().contains(".md")){
					String name1 = file.getName();
					String n2 = name1.substring(0, name1.indexOf(".xml"));
					
					Document doc = hmDatabase.get(n2);
					doc.getDocumentElement().normalize();
					
					Node nodoRaiz = doc.getDocumentElement();
					Node modelElement = nodoRaiz.getFirstChild();
					Node columnsElement = modelElement.getFirstChild();
					Node databaseElement = nodoRaiz.getLastChild();
					
					HashMap<String, Integer> hmNumberColumn = new HashMap<String, Integer>();
					String[] columns = new String[columnsElement.getChildNodes().getLength()];
					for (int i=0; i<columnsElement.getChildNodes().getLength(); i++){
						String nombre = columnsElement.getChildNodes().item(i).getNodeName();
						columns[i] = nombre;
						hmNumberColumn.put(nombre, i);
					}
					t.agregarTabla(file.getName().substring(0, file.getName().indexOf(".xml")), columns, databaseElement.getChildNodes().getLength());
					for (int i=0; i<databaseElement.getChildNodes().getLength(); i++){
						Node tActual = databaseElement.getChildNodes().item(i);
						String[] datos = new String[tActual.getChildNodes().getLength()];
						for (int j=0; j<tActual.getChildNodes().getLength(); j++){
							String nombre = tActual.getChildNodes().item(j).getNodeName();
							int index = hmNumberColumn.get(nombre);
							datos[index] = tActual.getChildNodes().item(j).getTextContent();
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
		// TODO Auto-generated method stub
		return super.visitActionDropConstraint(ctx);
	}

	@Override
	public Type visitActionAddConstraint(GSQLParser.ActionAddConstraintContext ctx) {
		// TODO Auto-generated method stub
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
					doc.getDocumentElement().normalize();
					
					Element nodoRaiz = doc.getDocumentElement();
					Element modelElement = (Element)nodoRaiz.getFirstChild();
					
					String pkName = Xml.getPrimaryKeyTable(modelElement);
					ArrayList<String> pkColumns = Xml.getColumnsPKTable(modelElement);
					ArrayList<Attr> attributes = new ArrayList<Attr>();
					
					XPathFactory xPathfactory = XPathFactory.newInstance();
					XPath xpath = xPathfactory.newXPath();
					
					Element columnsElement = (Element)modelElement.getFirstChild();
					Element databaseElement = (Element)nodoRaiz.getLastChild();
					
					Element elementA = doc.createElement(ctx.Id(0).getText());
					if (ctx.literal().size()>0){
						for (int i=0; i<columnsElement.getChildNodes().getLength(); i++){
							Node n = doc.createElement(columnsElement.getChildNodes().item(i).getNodeName());
							elementA.appendChild(n);
						}
						boolean guardar = true;
						for (int i=1; i<ctx.Id().size(); i++){
							Attr a = null;
							Element nodosM = (Element) columnsElement.getElementsByTagName(ctx.Id(i).getText()).item(0);
							Element nodos = (Element) elementA.getElementsByTagName(ctx.Id(i).getText()).item(0);
							boolean esPK = pkColumns.contains(ctx.Id(i).getText());
							ValueType vT = (ValueType) visit(ctx.literal(i-1));
							if (vT.getName().equals(nodosM.getAttribute("Type"))){
								String value = vT.getValue();
								if(vT.getName().equals("char")){
									if (Integer.parseInt(nodosM.getAttribute("Length"))>value.length()){
										if (esPK){
											a = doc.createAttribute(ctx.Id(i).getText());
											a.setValue(value);
										}
										nodos.setTextContent(value);
									}
									else{
										guardar = false;
										System.out.println("Error in Insert. Char \""+value+"\" greater than expected.");
									}
								}
								else{
									if (esPK){
										a = doc.createAttribute(ctx.Id(i).getText());
										a.setValue(value);
									}
									nodos.setTextContent(value);
								}
							}
							else{
								System.out.println("Error in Insert. Found "+vT.getName()+" expected "+nodosM.getAttribute("Type"));
								guardar = false;
							}
							
							if (esPK){
								attributes.add(a);
							}
						}
						
						
						if (guardar){
							String cond = "[";
							for (int i=0; i<attributes.size()-1; i++){
								cond+="@"+attributes.get(i).getName()+"=\'"+attributes.get(i).getValue()+"\' and ";
							}
							cond+="@"+attributes.get(attributes.size()-1).getName()+"=\'"+attributes.get(attributes.size()-1).getValue()+"\']";
							String exp = "//"+ctx.Id(0).getText()+cond;
							XPathExpression expr = xpath.compile(exp);
							Object result = expr.evaluate(doc, XPathConstants.NODESET);
					        NodeList nodes = (NodeList) result;
					        if (nodes.getLength()==0){
								for (Attr a: attributes)
									elementA.setAttributeNode(a);
								databaseElement.appendChild(elementA);
								System.out.println("Insert("+contNumRow+").");
					        }
					        else{
					        	String ret = "";
					        	for (int i=0; i<attributes.size()-1; i++){
									ret+=""+attributes.get(i).getName()+"=\'"+attributes.get(i).getValue()+"\', ";
								}
					        	ret+=""+attributes.get(attributes.size()-1).getName()+"=\'"+attributes.get(attributes.size()-1).getValue()+"\'";
					        	System.out.println("Already exist a row with "+ret+" as Primary Key.");
					        }
					        contNumRow = Xml.modifyTable(DBActual, ctx.Id(0).getText())+1;
							Xml.modifyDatabase(DBActual, "Records");
							//System.out.println("INSERT("+contNumRow+") con exito.");
						}
					}
					
					hmDatabase.put(ctx.Id(0).getText(), doc);
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
		return new ValueType("int",ctx.Num().getText());
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
		return new ValueType("char", t);
	}

	@Override
	public Type visitFloat_literal(GSQLParser.Float_literalContext ctx) {
		return new ValueType("float",ctx.Float().getText());
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
