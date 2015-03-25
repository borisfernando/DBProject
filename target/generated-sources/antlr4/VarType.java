
public class VarType implements Type{

	private String name;
	private int cantidad;
	
	public VarType(String name){
		this.name = name;
		cantidad = 0;
	}
	
	public VarType(String name, int cantidad){
		this.name = name;
		this.cantidad = cantidad;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public int getCantidad(){
		return cantidad;
	}

}
