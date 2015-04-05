import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Future;

import javax.swing.JDialog;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.Document;


public class Controller {
		
	HashMap<String, Document> hm;
	HashMap<String,ArrayList<String>> arrayListPk;
	
	public Controller(){
		hm = new HashMap<String, Document>();
		arrayListPk = new HashMap<String, ArrayList<String>>();
	}
	
	public void parse(String t){
		ANTLRInputStream input = new ANTLRInputStream(t);
		GSQLLexer lexer = new GSQLLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		GSQLParser parser = new GSQLParser(tokens);
		//Future<JDialog> tree2 = parser.database().inspect(parser);
		//parser.reset();
		ParseTree tree = parser.program();
		DBVisitor visitor = new DBVisitor();
		visitor.visit(tree);
		hm = visitor.getHMDatabase();
		arrayListPk = visitor.getArrayListPk();
		//hmPk = visitor.getHmPk();
		
		//System.out.println("Parseado exitosamente");
	}
	
	public HashMap<String, Document> getHm(){
		return hm;
	}
	
	public HashMap<String,ArrayList<String>> getArrayListPk(){
		return arrayListPk;
	}
	
	
}
