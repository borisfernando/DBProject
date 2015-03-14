import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class DBVisitor extends GSQLBaseVisitor<String>{
	public static String DBActual = null;
	
	/*
	 * Method: existDB
	 * Parameter: File f
	 * Return: boolean bExisteArchivo
	 * Use: returns true if theres an existing database, else otherwise
	 */
	public boolean existDB(File f){
		boolean bExisteArchivo = false;
		File folder = new File("DB/");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        if (file.compareTo(f)==0){
		        	bExisteArchivo = true;
		        }
		    }
		}
		return bExisteArchivo;
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
	
	@Override
	public String visitCreateTable(GSQLParser.CreateTableContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCreateTable(ctx);
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
	public String visitAlterDatabase(GSQLParser.AlterDatabaseContext ctx) {
		try{
			File f = new File("DB/"+ctx.Id(0).getText()+".xml");
			File fNew = new File("DB/"+ctx.Id(1).getText()+".xml");
			boolean bExisteArchivo = existDB(f);
			boolean bExisteArchivo2 = existDB(fNew);
			
			if (bExisteArchivo && !bExisteArchivo2){
				f.renameTo(fNew);
				f.getParentFile().mkdirs();
				System.out.println("Corret. Database name "+ctx.Id(0).getText()+", changed to "+ctx.Id(1).getText()+".");
			}
		}catch(Exception e){}
		return null;//super.visitAlterDatabase(ctx);
	}

	@Override
	public String visitAlterTable(GSQLParser.AlterTableContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAlterTable(ctx);
	}

	@Override
	public String visitConstraint(GSQLParser.ConstraintContext ctx) {
		// TODO Auto-generated method stub
		return super.visitConstraint(ctx);
	}

	@Override
	public String visitDropDatabase(GSQLParser.DropDatabaseContext ctx) {
		try{
			File f = new File("DB/"+ctx.Id().getText()+".xml");
			boolean bExisteArchivo = existDB(f);
			if (bExisteArchivo){
				if (f.delete())
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
	public String visitCreateDatabase(GSQLParser.CreateDatabaseContext ctx) {
		try{
			File fNew = new File("DB/"+ctx.Id().getText()+".xml");
			boolean bExisteArchivo = existDB(fNew);
			
			if (!bExisteArchivo){
				// Root elements
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				/*Element rootElement = doc.createElement(ctx.Id().getText());
				doc.appendChild(rootElement);*/
				
				// Write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				fNew.getParentFile().mkdirs(); 
				fNew.createNewFile();
				
				StreamResult result = new StreamResult(fNew);
				transformer.transform(source, result);
				System.out.println("File saved!");
			}
			else{
				System.out.println("File error! Already a Database named : "+ctx.Id().getText());
			}
		}catch(Exception e){System.out.println("Error File!");}
		
		return super.visitCreateDatabase(ctx);
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
	public String visitAction(GSQLParser.ActionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAction(ctx);
	}

	@Override
	public String visitCondExpression(GSQLParser.CondExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCondExpression(ctx);
	}

	@Override
	public String visitInsertInto(GSQLParser.InsertIntoContext ctx) {
		// TODO Auto-generated method stub
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
	public String visitDropTable(GSQLParser.DropTableContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDropTable(ctx);
	}

	@Override
	public String visitUseDatabase(GSQLParser.UseDatabaseContext ctx) {
		File f = new File("DB/"+ctx.Id().getText()+".xml");
		boolean bExisteArchivo = existDB(f);
		if (bExisteArchivo){
			DBActual = ctx.Id().getText();
		}
		else{
			System.out.println("Database "+ctx.Id().getText()+" does not exist.");
		}
		return null;//super.visitUseDatabase(ctx);
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
