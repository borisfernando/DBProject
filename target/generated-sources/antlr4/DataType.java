/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			DataType.java
 * Proposito:
 * 			Clase que determina el tipo de columna
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

public class DataType implements Type{

	protected String name;	
	
	/*
	 * Nombre: DataType
	 * Proposito: Constructor que almacena variables
	 * Parametro: Nombre del tipo de la columna
	 * Retorno: null
	 */
	public DataType(String name){
		this.name = name;		
	}
	
	/*
	 * Nombre: getName
	 * Proposito: Retorna el nombre de la clase
	 * Parametro: null
	 * Retorno: Nombre de la clase
	 */
	@Override
	public String getName() {
		return name;
	}
}
