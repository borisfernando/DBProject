import java.util.concurrent.TimeUnit;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


public class Controller {
	DBVisitor visitor;
	
	public Controller(){
		visitor = new DBVisitor();
	}
	
	public void parse(String t){
		ANTLRInputStream input = new ANTLRInputStream(t);
		GSQLLexer lexer = new GSQLLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		GSQLParser parser = new GSQLParser(tokens);
		//parser.program().inspect(parser);
		//parser.reset();
		ParseTree tree = parser.program();
		
		long iniciale = System.currentTimeMillis();
		visitor.visit(tree);
		long finale = System.currentTimeMillis();
		System.out.println("PARSEO: "+TimeUnit.MILLISECONDS.toSeconds(finale - iniciale)+ " segundos.");
	}
	
	public void update(){
		visitor.update();
	}
	
}
