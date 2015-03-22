import java.util.concurrent.Future;

import javax.swing.JDialog;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


public class Controller {
		
	public void parse(String t){
		ANTLRInputStream input = new ANTLRInputStream(t);
		GSQLLexer lexer = new GSQLLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		GSQLParser parser = new GSQLParser(tokens);
		//Future<JDialog> tree2 = parser.database().inspect(parser);
		ParseTree tree = parser.database();
		DBVisitor visitor = new DBVisitor();
		visitor.visit(tree);
		System.out.println("Parseado exitosamente");
	}
}
