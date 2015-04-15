import java.util.concurrent.TimeUnit;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


public class Controller {
	DBVisitor visitor;
	String dbactual;
	
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
		ErrorListener errorListener = new ErrorListener();
		parser.removeErrorListeners(); // remove ConsoleErrorListener
		parser.addErrorListener(errorListener); // add ours
		ParseTree tree = parser.program();
		if (errorListener.errorMessage().equals("")){
			System.out.println("Parseo Correcto");
			long iniciale = System.currentTimeMillis();
			visitor.visit(tree);
			long finale = System.currentTimeMillis();
			System.out.println("EJECUCIÓN: "+TimeUnit.MILLISECONDS.toSeconds(finale - iniciale)+ " segundos.");
		} else{
			System.out.println(errorListener.errorMessage());
		}
	}
	
	public void update(){
		visitor.update();
	}
	
	public String dbactual(){
		return visitor.DBActual;
	}
	
}
