/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			ValueType.java
 * Proposito:
 * 			Clase que almacena los valores de ingreso de columnas
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

public class ValueType extends DataType {
	String value;
	
	/*
	 * Nombre: ValueType
	 * Proposito: Constructor que almacena variables
	 * Parametro: Nombre de la clase y el valor
	 * Retorno: null
	 */
	public ValueType(String name,String value){
		super(name);
		this.value = value;
	}

	/*
	 * Nombre: getValue
	 * Proposito: Metodo que retorna el valor del elemento
	 * Parametro: null
	 * Retorno: Valor del elemento
	 */
	public String getValue(){
		return value;
	}
	
	/*
	 * Nombre: toString
	 * Proposito: Metodo que imprime el objeto
	 * Parametro: null
	 * Retorno: String con todos sus atributos
	 */
	public String toString(){
		return value;
	}
	
}

