/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			Expression.java
 * Proposito:
 * 			Clase que determina el tipo de expression para la clausula WHERE
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

public class Expression extends ValueType{
	
	private boolean eError;
	
	/*
	 * Nombre: Expression
	 * Proposito: Constructor que almacena variables
	 * Parametro: Nombre de la clase, el valor y si contiene un error en la expresion
	 * Retorno: null
	 */
	public Expression(String name, String value, boolean eError) {
		super(name,value);
		this.eError = eError;
	}

	/*
	 * Nombre: getName
	 * Proposito: Retorna el nombre de la clase
	 * Parametro: null
	 * Retorno: Nombre de la clase
	 */
	@Override
	public String getName() {
		return super.getName();
	} 
	
	/*
	 * Nombre: eError
	 * Proposito: Retorna si existe un error en la expresion
	 * Parametro: null
	 * Retorno: True si existe error y false de lo contrario.
	 */
	public boolean eError(){
		return eError;
	}
}
