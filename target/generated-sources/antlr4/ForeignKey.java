
public class ForeignKey extends Key {
	String[] references;
	String tableReference;
	
	public ForeignKey(String name, String id, String[] columnsId, String tableReference, String[] references){
		super(name,id,columnsId);
		this.references = references;
		this.tableReference = tableReference;
	}
	
	public String getReference(int i){
		return references[i];
	}
	
	public int getLengthReferences(){
		return references.length;
	}
	
	public String getTableReference(){
		return tableReference;
	}
}
