/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			ErrorListener.java
 * Proposito:
 * 			Clase que agrega un error a la gramatica
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;


public class ErrorListener extends BaseErrorListener{
	private StringBuilder buf = new StringBuilder();
	
	/*
	 * Nombre: syntaxError
	 * Proposito: Metodo que agrega un error sintactico
	 * Parametro: 
	 * Retorno: null
	 */
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer,
			Object offendingSymbol,
			int line, int charPositionInLine,
			String msg,
			RecognitionException e)
	{
	List<String> stack = (( Parser)recognizer).getRuleInvocationStack();
	Collections.reverse(stack);
	buf.append( "\nError found in: " +stack+"" );
	buf.append( "\nline " + line + " char " + charPositionInLine + ": " + msg + "\n");
	}
	
	/*
	 * Nombre: errorMessage
	 * Proposito: Metodo que retorna cada uno de los errores en la gramatica.
	 * Parametro: null
	 * Retorno: Cadena de texto con cada error
	 */
	public String errorMessage(){
		if (buf.toString().isEmpty()){
			return "";
		}
		return buf.toString();
	}
}
