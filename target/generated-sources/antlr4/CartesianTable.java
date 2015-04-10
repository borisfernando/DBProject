import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CartesianTable implements Type {
	
	Element eGeneral; 

	public CartesianTable(ArrayList<String> ids, HashMap<String, Document> hm, Document docDoc){
		eGeneral = getTable(ids, hm, docDoc);
	}
	public Element getTable(ArrayList<String> ids, HashMap<String, Document> hm, Document docDoc) {
		try{
			docDoc.getDocumentElement().normalize();
			Element doc = docDoc.getDocumentElement();
			while (ids.size()!=0){
				String id = ids.remove(0);
				Document DocumentID =  hm.get(id);
				Node eNuevo = DocumentID.createElement("Database");
				Element rootElement = (Element) DocumentID.importNode(DocumentID.getDocumentElement().cloneNode(true),true);
				Element table = (Element) rootElement.getLastChild();
				NodeList hmTableList = table.getChildNodes();
				if (!doc.hasChildNodes()){
					NodeList nListTable = table.getElementsByTagName(id+"_Row");
					for (int i=0; i<nListTable.getLength(); i++){
						Element eTable = (Element) nListTable.item(i);
						for (int j=0; j<eTable.getChildNodes().getLength(); j++){
							Element eHijo = (Element) eTable.getChildNodes().item(j);
							eHijo.setAttribute("Reference", id);
						}
						eTable.removeAttribute(eTable.getAttributes().item(0).getNodeName());
						eNuevo.appendChild(DocumentID.importNode(DocumentID.renameNode(eTable,null,"row"),true));
						i--;
					}
				}
				else{
					Element eGeneral = docDoc.createElement("Database");
					if (hmTableList.getLength()!=0){
						for (int i=0; i<hmTableList.getLength(); i++){
							Element eActual = (Element) hmTableList.item(i);
							NodeList docTableList = doc.getChildNodes();  
								for (int j=0; j<docTableList.getLength(); j++){
									Element eNew = docDoc.createElement("row");
									for (int k=0; k<eActual.getChildNodes().getLength(); k++){
										Element eHijo = (Element) eActual.getChildNodes().item(k);
										eHijo.setAttribute("Reference", id);
										eNew.appendChild(docDoc.importNode(eHijo,true));
									}
									Element eDoc = (Element) docTableList.item(j);
									for (int k=0; k<eDoc.getChildNodes().getLength(); k++){
										eNew.appendChild(docDoc.importNode(eDoc.getChildNodes().item(k),true));
									}
									eGeneral.appendChild(eNew);
								}
							}
							
						}
					else{
						eGeneral = (Element) docDoc.importNode(doc,true);
					}
					eNuevo = (Element) docDoc.importNode(eGeneral,true);
				}
				docDoc.replaceChild(docDoc.importNode(eNuevo,true), doc);
				return getTable(ids, hm, docDoc);
			}
			return doc;
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
	
	@Override
	public String getName() {
		return "From";
	}
	
	public Element getGeneral(){
		return eGeneral;
	}

}
