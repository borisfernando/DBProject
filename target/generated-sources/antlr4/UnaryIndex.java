import java.util.HashMap;

import org.w3c.dom.Element;


public class UnaryIndex implements Index{

	HashMap<String, Element> hm;
	
	public UnaryIndex(){
		hm = new HashMap<String, Element>();
	}
	
	@Override
	public boolean add(Element v, String ... k) {
		if (hasElement(k)){
			return false;
		}
		else{
			hm.put(k[0], v);
			return true;
		}
	}

	@Override
	public Element get(String... k) {
		return hm.get(k[0]);
	}

	@Override
	public boolean remove(String... k) {
		if (hasElement(k)){
			hm.remove(k[0]);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean hasElement(String... k) {
		return hm.containsKey(k[0]);
	}
	
	

}
