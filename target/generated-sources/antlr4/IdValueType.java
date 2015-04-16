/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			IdValueType.java
 * Proposito:
 * 			Clase que determina el valor del id ingresado
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

public class IdValueType extends UnaryExpression{
	
	private String tableRef;
	private boolean hasRef;
	
	/*
	 * Nombre: IdValueType
	 * Proposito: Constructor que almacena variables
	 * Parametro: Nombre del id, valor del id, tipo del id, tabla referenciada (si tiene), si hay error y si tiene tabla referenciada
	 * Retorno: null
	 */
	public IdValueType(String name, String val, String type, String tableRef, boolean error, boolean hasRef) {
		super(name, val, type, error);
		this.tableRef = tableRef;
		this.hasRef = hasRef;
	}
	
	/*
	 * Nombre: getTableRef
	 * Proposito: Retorna el nombre de la tabla referenciada
	 * Parametro: null
	 * Retorno: Nombre de la tabla referenciada
	 */
	public String getTableRef(){
		return tableRef;
	}
	
	/*
	 * Nombre: hasRef
	 * Proposito: Retorna si existe la tabla referencia
	 * Parametro: null
	 * Retorno: si tiene referencia
	 */
	public boolean hasRef(){
		return hasRef;
	}
	
	/*
	 * Nombre: toString
	 * Proposito: Imprime el objeto
	 * Parametro: null
	 * Retorno: String con todas sus caracteristicas
	 */
	public String toString(){
		return super.toString();
	}
	
}
