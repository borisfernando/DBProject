import java.util.HashMap;


public class MultipleExpression extends Expression{

	private Expression e1;
	private Expression e2;
	
	public MultipleExpression(String name, Expression e1, String op, Expression e2, boolean eError) {
		super(name,op,eError);
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public Expression getE1(){
		return e1;
	}
	public Expression getE2(){
		return e2;
	}
	
	public String toString(){
		return e1.toString()+" "+getValue()+" "+e2.toString();
	}
	
	public HashMap<String,String> getUnaries(String t,Expression e, HashMap<String,String> columns){
		if (e.getName().equals("MExpression")){
			MultipleExpression exp = (MultipleExpression) e;
			columns = getUnaries(t,exp.getE1(),columns);
			columns = getUnaries(t,exp.getE2(),columns);
			HashMap<String, String> columnas = (HashMap)columns.clone();
			for (String s: columns.keySet()){
				if (!columns.get(s).equals("Expression") && !s.equals("Constant") && !columns.get(s).equals("noReference")){
					t+="./"+s+"[@Reference=\'"+columns.get(s)+"\']/text() "+exp.getValue()+" ";
					columnas.remove(s);
				}
				else if(!columns.get(s).equals("Expression") && s.equals("Constant") && !columns.get(s).equals("noReference")){
					t+=""+columns.get(s)+" "+exp.getValue()+" ";
					columnas.remove(s);
				}
				else if(!columns.get(s).equals("Expression") && !s.equals("Constant") && columns.get(s).equals("noReference")){
					t+="./"+s+"/text() "+exp.getValue()+" ";
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
					columns.put(idVal.getValue(), idVal.getTableRef());
				}
				else{
					columns.put(idVal.getValue(), "noReference");
				}
			}
			else if (uExp.getType().equals("CHAR")){
				columns.put("Constant", "\'"+uExp.getValue()+"\'");
			}
			else{
				columns.put("Constant", uExp.getValue());
			}
		}
		return columns;
	}
	
	

}
