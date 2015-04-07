import org.w3c.dom.Element;

// Complexity: HashMap O(1)
public interface Index {
	
	public boolean add(Element v, String ... k);
	public Element get(String ... k);
	public boolean remove(String ... k);
	public boolean hasElement(String ... k);
	
}