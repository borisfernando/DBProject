import java.io.File;

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

//import GSQLParser.DatabaseContext;

public class DBVisitor extends GSQLBaseVisitor<String>{
	public static String DBActual = null;
	public static String TableActual = null;
	
	public DBVisitor(){}
	
	/*
	 * Method: existDB
	 * Parameter: File f
	 * Return: boolean bExisteArchivo
	 * Use: returns true if theres an existing database, else otherwise
	 */
	public static boolean existDB(File f){
		boolean bExist = false;
		File folder = new File("DB/");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
	        if (file.compareTo(f)==0){
	        	bExist = true;
	        }
		}
		return bExist;
	}
	
	/*
	 * Method: existTable
	 * Parameter: String table
	 * Return: boolean bExisteTable
	 * Use: returns true if there is an existing table, else otherwise
	 */
	public static boolean existTable(File table){
		boolean bExist = false; 
		File f = new File("DB/"+DBActual+"/");
		File[] listOfFiles = f.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()){
				if (file.compareTo(table)==0){
					bExist = true;
				}
			}
		}
		return bExist;
	}
	
	/*
	 * Method: returnDBActual
	 * Parameter: none
	 * Return: String DBActual
	 * Use: returns the actual database to be working at
	 */
	public String returnDBActual(){
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
	public String visitCreateDatabase(GSQLParser.CreateDatabaseContext ctx) {
		try{
			File fNew = new File("DB/"+ctx.Id().getText());
			if (!existDB(fNew)){
				fNew.mkdir();
				System.out.println("Database "+ctx.Id().getText()+" Created!");
			}
			else{
				System.out.println("Already a Database named : "+ctx.Id().getText());
			}
		}catch(Exception e){System.out.println("Error File!");}
		
		return super.visitCreateDatabase(ctx);
	}
	
	@Override
	public String visitAlterDatabase(GSQLParser.AlterDatabaseContext ctx) {
		try{
			File f = new File("DB/"+ctx.Id(0).getText());
			File fNew = new File("DB/"+ctx.Id(1).getText());
			boolean bExiste1 = existDB(f);
			boolean bExiste2 = existDB(fNew);
			
			if (bExiste1 && !bExiste2){
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
	public String visitUseDatabase(GSQLParser.UseDatabaseContext ctx) {
		if (existDB(new File("DB/"+ctx.Id().getText()))){
			DBActual = ctx.Id().getText();
			System.out.println("ACTUALDATABASE: "+DBActual);
		}
		else{
			System.out.println("Database "+ctx.Id().getText()+" does not exist.");
		}
		return null;//super.visitUseDatabase(ctx);
	}
	
	@Override
	public String visitDropDatabase(GSQLParser.DropDatabaseContext ctx) {
		try{
			File f = new File("DB/"+ctx.Id().getText());
			if (existDB(f)){
				if (deleteDirectory(f))
					System.out.println("Database "+ctx.Id().getText()+" deleted.");
				else
					System.out.println("Deleting database "+ctx.Id().getText()+" has found an error.");
			}
			else{
				System.out.println("Database "+ctx.Id().getText()+" not exist.");
			}
		}catch(Exception e){}
		return null;//super.visitDropDatabase(ctx);
	}
	
	@Override
	public String visitCreateTable(GSQLParser.CreateTableContext ctx) {
		// TODO Auto-generated method stub
		try{
			File f = new File("DB/"+DBActual+"/");
			if (existDB(f)){
				File fT = new File("DB/"+DBActual+"/"+ctx.Id(0).getText()+".xml");
				if (!existTable(fT)){
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.newDocument();
					
					Element rootElement = doc.createElement("Table");
					
					Element databaseElement = doc.createElement("Database");
					Element modelElement = doc.createElement("Model");
					
					for (int i=1; i<ctx.Id().size(); i++){
						Element eNuevo = doc.createElement(ctx.Id(i).getText());
						//FALTA CONSTRAINT
						if (ctx.constraint() != null){				
							if (visit(ctx.constraint()).equals(ctx.Id(i).getText())){
								Attr attrC = doc.createAttribute("Constraint_FALTA");
								attrC.setValue("PRIMARY_KEY");
								eNuevo.setAttributeNode(attrC);
							}
						}
						Attr attr = doc.createAttribute("Type");
						attr.setValue(ctx.type(i-1).getText());
						eNuevo.setAttributeNode(attr);
						//FALTA LENGTH CHAR
						if (ctx.type(i-1).getText().contains("char")){
							Attr attrCh = doc.createAttribute("Length");
							attrCh.setValue("10_ejemplo");
							eNuevo.setAttributeNode(attrCh);
						}
						modelElement.appendChild(eNuevo);
					}
					
					rootElement.appendChild(modelElement);
					rootElement.appendChild(databaseElement);
					doc.appendChild(rootElement);
					
					// Write the content into xml file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(fT);
					transformer.transform(source, result);
					fT.getParentFile().mkdirs(); 
					fT.createNewFile();
					System.out.println("Correct. Table name "+ctx.Id(0).getText()+" added to database "+DBActual+".");
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
	public String visitRenameAlterTable(GSQLParser.RenameAlterTableContext ctx) {
		try{
			File fD = new File("DB/"+DBActual);
			File f = new File("DB/"+DBActual+"/"+ctx.Id(0).getText()+".xml");
			if (existDB(fD)){
				if (existTable(f)){
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
	public String visitActionAlterTable(GSQLParser.ActionAlterTableContext ctx) {
		if (existDB(new File("DB/"+DBActual))){
			if (existTable(new File("DB/"+DBActual+"/"+ctx.Id().getText()+".xml"))){
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
	public String visitActionAddColumn(GSQLParser.ActionAddColumnContext ctx) {
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
	public String visitActionDropColumn(GSQLParser.ActionDropColumnContext ctx) {
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
	public String visitDropTable(GSQLParser.DropTableContext ctx) {
		try{
			File f = new File("DB/"+DBActual+"/"+ctx.Id().getText()+".xml");
			if (existTable(f)){
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
	public String visitEqOpExpression(GSQLParser.EqOpExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEqOpExpression(ctx);
	}

	@Override
	public String visitSelectFrom(GSQLParser.SelectFromContext ctx) {
		// TODO Auto-generated method stub
		return super.visitSelectFrom(ctx);
	}	

	@Override
	public String visitConstraint(GSQLParser.ConstraintContext ctx) {
		// TODO Auto-generated method stub
		//return super.visitConstraint(ctx);
		return ctx.Id(0).getText();
	}

	@Override
	public String visitDeleteFrom(GSQLParser.DeleteFromContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDeleteFrom(ctx);
	}

	@Override
	public String visitShowColumns(GSQLParser.ShowColumnsContext ctx) {
		// TODO Auto-generated method stub
		return super.visitShowColumns(ctx);
	}

	@Override
	public String visitDate(GSQLParser.DateContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDate(ctx);
	}

	@Override
	public String visitType(GSQLParser.TypeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitType(ctx);
	}

	@Override
	public String visitUnExpression(GSQLParser.UnExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitUnExpression(ctx);
	}

	@Override
	public String visitShowTables(GSQLParser.ShowTablesContext ctx) {
		// TODO Auto-generated method stub
		return super.visitShowTables(ctx);
	}

	@Override
	public String visitExpAndExpression(GSQLParser.ExpAndExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitExpAndExpression(ctx);
	}

	@Override
	public String visitCondOrExpression(GSQLParser.CondOrExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCondOrExpression(ctx);
	}
	
	@Override
	public String visitActionDropConstraint(GSQLParser.ActionDropConstraintContext ctx) {
		// TODO Auto-generated method stub
		return super.visitActionDropConstraint(ctx);
	}

	@Override
	public String visitActionAddConstraint(GSQLParser.ActionAddConstraintContext ctx) {
		// TODO Auto-generated method stub
		return super.visitActionAddConstraint(ctx);
	}

	@Override
	public String visitCondExpression(GSQLParser.CondExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCondExpression(ctx);
	}

	@Override
	public String visitInsertInto(GSQLParser.InsertIntoContext ctx) {
		// TODO Auto-generated method stub
		try{
			if (existDB(new File("DB/"+DBActual))){
				File fT = new File("DB/"+DBActual+"/"+ctx.Id(0).getText()+".xml");
				if (existTable(fT)){
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.parse(fT);
					doc.getDocumentElement().normalize();
					
					Node nodoRaiz = doc.getDocumentElement();
					Node modelElement = nodoRaiz.getFirstChild();
					Node databaseElement = nodoRaiz.getLastChild();
					
					Node nElement = doc.createElement(ctx.Id(0).getText());
					if (ctx.literal().size()>0){
						for (int i=0; i<modelElement.getChildNodes().getLength(); i++){
							Node meActual = modelElement.getChildNodes().item(i).cloneNode(true);
							//String t = modelElement.getChildNodes().item(i).getNodeName();
							//Element meActual = doc.createElement(t);
							for (int j=1; j<ctx.Id().size();j++){
								if (ctx.Id(j).getText().equals(meActual.getNodeName())){
									meActual.setTextContent(ctx.literal(i).getText());							
									nElement.appendChild(meActual);
									break;
								}
							}
						}
						databaseElement.appendChild(nElement);
						TransformerFactory transFact = TransformerFactory.newInstance();
						Transformer transformer = transFact.newTransformer();
						DOMSource source = new DOMSource(doc);
						StreamResult result = new StreamResult(fT);
						transformer.transform(source,result);
						
						System.out.println("Correct. Data saved in "+ctx.Id(0).getText());
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
	public String visitShowDatabase(GSQLParser.ShowDatabaseContext ctx) {
		// TODO Auto-generated method stub
		return super.visitShowDatabase(ctx);
	}

	@Override
	public String visitRelOp(GSQLParser.RelOpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRelOp(ctx);
	}

	@Override
	public String visitInt_literal(GSQLParser.Int_literalContext ctx) {
		// TODO Auto-generated method stub
		return super.visitInt_literal(ctx);
	}

	@Override
	public String visitEqAndExpression(GSQLParser.EqAndExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEqAndExpression(ctx);
	}

	@Override
	public String visitRelOpExpression(GSQLParser.RelOpExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRelOpExpression(ctx);
	}

	@Override
	public String visitAndOp(GSQLParser.AndOpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAndOp(ctx);
	}

	@Override
	public String visitDatabase(GSQLParser.DatabaseContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDatabase(ctx);
	}

	@Override
	public String visitChar_literal(GSQLParser.Char_literalContext ctx) {
		// TODO Auto-generated method stub
		return super.visitChar_literal(ctx);
	}

	@Override
	public String visitFloat_literal(GSQLParser.Float_literalContext ctx) {
		// TODO Auto-generated method stub
		return super.visitFloat_literal(ctx);
	}

	@Override
	public String visitEqRelExpression(GSQLParser.EqRelExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEqRelExpression(ctx);
	}

	@Override
	public String visitEqOp(GSQLParser.EqOpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEqOp(ctx);
	}

	@Override
	public String visitOrOp(GSQLParser.OrOpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitOrOp(ctx);
	}

	@Override
	public String visitRelSumExpression(GSQLParser.RelSumExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRelSumExpression(ctx);
	}

	@Override
	public String visitUpdateSet(GSQLParser.UpdateSetContext ctx) {
		// TODO Auto-generated method stub
		return super.visitUpdateSet(ctx);
	}

	@Override
	public String visitTableInstruction(GSQLParser.TableInstructionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitTableInstruction(ctx);
	}

	@Override
	public String visitLiteral(GSQLParser.LiteralContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLiteral(ctx);
	}

}
