/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			PrimaryKey.java
 * Proposito:
 * 			Clase que almacena la primary key con todos sus atributos
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

public class PrimaryKey extends Key {
	
	/*
	 * Nombre: PrimaryKey
	 * Proposito: Constructor que almacena variables
	 * Parametro: Tipo de constraint, nombre de la primarykey y las columnas primarykey
	 * Retorno: null
	 */
	public PrimaryKey(String name, String id, String[] columnsId){
		super(name,id,columnsId);
	}
}
