/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			ForeignKey.java
 * Proposito:
 * 			Clase que determina todos los valores para la constraint foreign key
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

public class ForeignKey extends Key {
	String[] references;
	String tableReference;
	boolean error;
	
	/*
	 * Nombre: ForeignKey
	 * Proposito: Constructor que almacena variables
	 * Parametro: Nombre de la clase, nombre de la fk, columnas de la fk, nombre de la tabla referenciada, las columnas referenciadas y si existe error
	 * Retorno: True null
	 */
	public ForeignKey(String name, String id, String[] columnsId, String tableReference, String[] references, boolean error){
		super(name,id,columnsId);
		this.references = references;
		this.tableReference = tableReference;
		this.error = error;
	}
	
	/*
	 * Nombre: getReference
	 * Proposito: Retorna el nombre de la columna referenciada
	 * Parametro: Posicion del nombre de la columna
	 * Retorno: Nombre de la columna
	 */
	public String getReference(int i){
		return references[i];
	}
	
	/*
	 * Nombre: getLengthReferences
	 * Proposito: Retorna el tamano de las columnas referenciadas
	 * Parametro: null
	 * Retorno: Tamano de las columnas referenciadas
	 */
	public int getLengthReferences(){
		return references.length;
	}
	
	/*
	 * Nombre: getTableReference
	 * Proposito: Retorna el nombre de la tabla referenciada
	 * Parametro: null
	 * Retorno: Nombre de la tabla referenciada
	 */
	public String getTableReference(){
		return tableReference;
	}
	
	/*
	 * Nombre: eError
	 * Proposito: Retorna si existe un error en el foreign key
	 * Parametro: null
	 * Retorno: True si existe y false de lo contrario.
	 */
	public boolean eError(){
		return error;
	}
}
