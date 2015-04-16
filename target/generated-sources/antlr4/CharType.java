/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			CharType.java
 * Proposito:
 * 			Clase que determina un valor char y cuantos caracteres puede tener
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

public class CharType extends DataType{
	private int cantidad;
	
	/*
	 * Nombre: CharType
	 * Proposito: Constructor que inicializa y settea variables
	 * Parametro: Nombre de la clase y la cantidad de chars aceptados
	 * Retorno: null
	 */
	public CharType(String name, int cantidad){
		super(name);
		this.cantidad = cantidad;
	}

	/*
	 * Nombre: getCantidad
	 * Proposito: Retorna la cantidad de chars admitidos en la columna
	 * Parametro: null
	 * Retorno: Cantidad de char admitidos en la columna
	 */
	public int getCantidad() {
		return cantidad;
	}
	
	/*
	 * Nombre: setCantidad
	 * Proposito: Almacena la cantidad de chars aceptados por la columna
	 * Parametro: Int cantidad de chars
	 * Retorno: null
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}	
}
