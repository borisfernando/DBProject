/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			Key.java
 * Proposito:
 * 			Clase que determina si es una pk o fk
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

public class Key extends DataType{
	protected String id;
	protected String[] columnsId;
	
	/*
	 * Nombre: Key
	 * Proposito: Constructor que almacena variables
	 * Parametro: Nombre de la constraint, nombre de la constraint y las columnas que hagan referencia
	 * Retorno: null
	 */
	public Key(String name, String id, String[] columnsId){
		super(name);
		this.id = id;
		this.columnsId = columnsId;
	}
	
	/*
	 * Nombre: getId
	 * Proposito: Retorna el nombre de la constraint
	 * Parametro: null
	 * Retorno: Nombre de la constraint
	 */
	public String getId(){
		return id;
	}
	
	/*
	 * Nombre: getColumnId
	 * Proposito: Retorna la columna i referenciada
	 * Parametro: El indice de la columna
	 * Retorno: Nombre de la columna
	 */
	public String getColumnId(int i){
		return columnsId[i];
	}
	
	/*
	 * Nombre: getColumnsId
	 * Proposito: Retorna las columnas que son primary key
	 * Parametro: null
	 * Retorno: columnas
	 */
	public String[] getColumnsId(){
		return columnsId;
	}
	
	/*
	 * Nombre: getLengthColumnsId
	 * Proposito: Retorna el tamano de las columnas del constraint
	 * Parametro: null
	 * Retorno: cantidad de elementos de columna
	 */
	public int getLengthColumnsId(){
		return columnsId.length;
	}
}
