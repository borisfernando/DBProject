/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			UnaryExpression.java
 * Proposito:
 * 			Clase que determina una expresion que solo tiene valores ingresados
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

public class UnaryExpression extends Expression {
	
	String type;
	
	/*
	 * Nombre: UnaryExpression
	 * Proposito: Constructor que almacena variables
	 * Parametro: Nombre de la clase, el valor, el tipo almacenado y si contiene error
	 * Retorno: null
	 */
	public UnaryExpression(String name, String val, String type, boolean eError) {
		super(name,val,eError);
		this.type = type;
	}
	
	/*
	 * Nombre: getType
	 * Proposito: Metodo que retorna el nombre de la clase
	 * Parametro: null
	 * Retorno: Nombre de la clase
	 */
	public String getType(){
		return type;
	}
	
	/*
	 * Nombre: toString
	 * Proposito: Metodo que imprime el objeto
	 * Parametro: null
	 * Retorno: String con todas sus caracteristicas
	 */
	public String toString(){
		return super.toString();
	}
	
}
