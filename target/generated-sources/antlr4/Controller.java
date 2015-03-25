import java.util.HashMap;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.Document;


public class Controller {
		
	HashMap<String, Document> hm;
	
	public Controller(){
		hm = new HashMap<String, Document>();
	}
	
	public void parse(String t){
		ANTLRInputStream input = new ANTLRInputStream(t);
		GSQLLexer lexer = new GSQLLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		GSQLParser parser = new GSQLParser(tokens);
		//Future<JDialog> tree2 = parser.database().inspect(parser);
		ParseTree tree = parser.program();
		DBVisitor visitor = new DBVisitor();
		visitor.visit(tree);
		hm = visitor.getHMDatabase();
		//System.out.println("Parseado exitosamente");
	}
	
	public HashMap<String, Document> getHm(){
		return hm;
	}
	
	
}
