import java.util.HashMap;

import org.w3c.dom.Element;


public class MultipleIndex implements Index{

	HashMap hm;
	
	public MultipleIndex() {
		hm = new HashMap(); 
	}
	
	@Override
	public boolean add(Element v, String... k) {
		HashMap hmTemporal = null;
		boolean flag = true;
		int index=0;
		
		for (int i=0; i<k.length; i++){
			if (hm.containsKey(k[i])){
				hmTemporal = (HashMap) hm.get(k[i]);
			}
			else{
				flag = false;
				index = i;
				break;
			}
		}
		if (!flag){
			HashMap<String, HashMap> hmTemporal2 = null;
			
			if (index == k.length-1){
				hmTemporal.put(k[index], v);
			}
			if(index + 1 == k.length-1){
				HashMap<String, Element> hmFinale = new HashMap();
				hmFinale.put(k[k.length-1], v);
				hmTemporal.put(k[index], hmFinale);
			}
			if (index + 2 <= k.length-1){
				HashMap<String, Element> hmFinale = new HashMap();
				hmFinale.put(k[k.length-1], v);
				hmTemporal2 = new HashMap();
				hmTemporal2.put(k[index], hmFinale);
			
				for (int i=k.length-1; i>index+2; i--){
					HashMap hmTemporal3 = new HashMap();
					hmTemporal3.put(k[i], hmTemporal2);
					hmTemporal2 = hmTemporal3;
				}
				hmTemporal.put(k[index], hmTemporal2);
			}
			return true;
		}
		return false;
	}

	@Override
	public Element get(String... k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(String... k) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasElement(String... k) {
		HashMap hmTemporal; 
		for (String col: k){
			if (hm.containsKey(col)){
				hmTemporal = (HashMap) hm.get(col);
			}
			else{
				return false;
			}
		}
		return true;
	}

}
