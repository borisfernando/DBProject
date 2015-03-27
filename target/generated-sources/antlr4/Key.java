
public class Key extends DataType{
	protected String id;
	protected String[] columnsId;
	public Key(){}
	public Key(String name, String id, String[] columnsId){
		super(name);
		this.id = id;
		this.columnsId = columnsId;
	}
	public String getId(){
		return id;
	}
	public String getColumnId(int i){
		return columnsId[i];
	}
	public String[] getColumnsId(){
		return columnsId;
	}
	public int getLengthColumnsId(){
		return columnsId.length;
	}
}
