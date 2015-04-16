/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			Controller.java
 * Proposito:
 * 			Clase que hace la llamada a la logica del programa
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

import java.util.concurrent.TimeUnit;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


public class Controller {
	DBVisitor visitor;
	String dbactual;
	
	/*
	 * Nombre: Controller
	 * Proposito: Constructor que inicializa variables
	 * Parametro: null
	 * Retorno: null
	 */
	public Controller(){
		visitor = new DBVisitor();
	}
	
	/*
	 * Nombre: parse
	 * Proposito: Realiza la logica principal con el texto t ingresado
	 * Parametro: Texto ingresado por el usuario
	 * Retorno: null
	 */
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
	
	/*
	 * Nombre: update
	 * Proposito: Actualiza la base de datos con todas sus caracteristicas
	 * Parametro: null
	 * Retorno: null
	 */
	public void update(){
		visitor.update();
	}
	
	/*
	 * Nombre: dbactual
	 * Proposito: Retorna la database actual
	 * Parametro: null
	 * Retorno: Database actual
	 */
	public String dbactual(){
		return visitor.DBActual;
	}
	
}
