
public class CharType extends DataType{
	private int cantidad;
	
	public CharType(String name){
		super(name);
	}
	public CharType(String name, int cantidad){
		this.name = name;
		this.cantidad = cantidad;
	}

	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}	
}
