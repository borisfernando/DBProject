/*
 * Universidad del Valle de Guatemala
 * Bases de Datos
 * Proyecto I
 * Autores: 
 * 			Oscar Gil,		12358
 * 			Boris Becerra,	12461
 * Nombre del Archivo:
 * 			MultipleExpression.java
 * Proposito:
 * 			Clase que determina una expresion multiple con operaciones
 * Fecha de Creacion:
 * 15/04/2015
 * 
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;


public class MultipleExpression extends Expression{

	private Expression e1;
	private Expression e2;
	
	/*
	 * Nombre: MultipleExpression
	 * Proposito: Constructor que almacena variables
	 * Parametro: Tipo de expresion, expresion 1, operacion realizada, expresion 2, y si tiene error la expresion
	 * Retorno: null
	 */
	public MultipleExpression(String name, Expression e1, String op, Expression e2, boolean eError) {
		super(name,op,eError);
		this.e1 = e1;
		this.e2 = e2;
	}
	
	/*
	 * Nombre: getE1
	 * Proposito: Retorna la primara expresion
	 * Parametro: null
	 * Retorno: Primera expresion
	 */
	public Expression getE1(){
		return e1;
	}
	
	/*
	 * Nombre: getE2
	 * Proposito: Retorna la segunda expresion
	 * Parametro: Null
	 * Retorno: Expresion 2
	 */
	public Expression getE2(){
		return e2;
	}
	
	/*
	 * Nombre: toString
	 * Proposito: Imprime el objeto
	 * Parametro: null
	 * Retorno: String con todas sus caracteristicas
	 */
	public String toString(){
		return e1.toString()+" "+getValue()+" "+e2.toString();
	}
	
	/*
	 * Nombre: getUnaries
	 * Proposito: Metodo que crea el xpath expression
	 * Parametro: Texto a almacenar, la expresion actual y las columnas de expresiones
	 * Retorno: columnas de expresiones
	 */
	public HashMap<String,String> getUnaries(String t,Expression e, HashMap<String,String> columns){
		if (e.getName().equals("MExpression")){
			MultipleExpression exp = (MultipleExpression) e;
			columns = getUnaries(t,exp.getE1(),columns);
			columns = getUnaries(t,exp.getE2(),columns);
			HashMap<String, String> columnas = (HashMap)columns.clone();
			TreeMap<String,String> columnasSorted = new TreeMap<String, String>();
			columnasSorted.putAll(columnas);
			for (String s: columnasSorted.keySet()){
				if (!columns.get(s).equals("Expression") && !s.equals("oConstant") && !s.equals("noReference")){
					t+="./"+columns.get(s)+"[@Reference=\'"+s+"\']/text() "+exp.getValue()+" ";
					columnas.remove(s);
				}
				else if(!columns.get(s).equals("Expression") && s.equals("oConstant") && !s.equals("noReference")){
					t+=""+columns.get(s)+" "+exp.getValue()+" ";
					columnas.remove(s);
				}
				else if(!columns.get(s).equals("Expression") && !s.equals("oConstant") && s.equals("noReference")){
					t+="./"+columns.get(s)+"/text() "+exp.getValue()+" ";
					columnas.remove(s);
				}
				else{
					if (columns.keySet().size()==2){
						t+=s+" "+exp.getValue()+" ";
						columnas.remove(s);
					}
				}
			}
			if (!t.equals("")){
				t = t.substring(0, (t.length()-exp.getValue().length())-2);				
				columns = columnas;
				columns.put(t, "Expression");
			}
			
		}
		else if (e.getName().equals("Unary")){
			UnaryExpression uExp = (UnaryExpression) e;
			if (uExp.getType().equals("ID")){
				ValueType val = (ValueType) uExp;
				IdValueType idVal = (IdValueType) val;
				if (idVal.hasRef()){
					columns.put(idVal.getTableRef(),idVal.getValue());
				}
				else{
					columns.put("noReference",idVal.getValue());
				}
			}
			else if (uExp.getType().equals("CHAR")){
				columns.put("oConstant", uExp.getValue());
			}
			else{
				columns.put("oConstant", uExp.getValue());
			}
		}
		return columns;
	}
	
	

}
