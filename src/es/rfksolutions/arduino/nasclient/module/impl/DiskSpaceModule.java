package es.rfksolutions.arduino.nasclient.module.impl;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.rfksolutions.arduino.nasclient.module.common.CommonModule;
import es.rfksolutions.arduino.nasclient.module.dao.DiskSpaceItem;

/***
 * Modulo de informacion de espacio en disco
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class DiskSpaceModule extends CommonModule {

	public String NODE_Disc = "disc";

	public String ATTR_Key = "key";
	public String ATTR_Filesystem = "filesystem";
	public String ATTR_Capacity = "capacity";
	public String ATTR_Used = "used";
	public String ATTR_Size = "size";
	public String ATTR_Available = "available";
	public String ATTR_Name = "name";

	private DiskSpaceItem item;

	public ArrayList<String> procesa() {
		if (node.hasChildNodes()) {
			NodeList property = node.getChildNodes();
			lista = new ArrayList<String>();
			for (int i = 0; i < property.getLength(); i++) {
				Node n = property.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE
						&& NODE_Disc.equals(n.getNodeName())) {
					setObject((Element) n);
					lista.add(item.makeBufferLCD());
				}
			}
		}
		return lista;
	}
	
	private void setObject(Element e) {
		item = new DiskSpaceItem();
		item.setRows(getRows());
		item.setKey(e.getAttribute(ATTR_Key));
		NodeList l = e.getChildNodes();
		Node n = null;
		for (int i=0; i<l.getLength(); i++) {
			n = l.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				if (ATTR_Name.equals( ((Element) n).getNodeName() )) {
					item.setName( ((Element) n).getTextContent() );
				} else if (ATTR_Available.equals( ((Element) n).getNodeName() )) {
					item.setAvailable( ((Element) n).getTextContent() );
				} else if (ATTR_Capacity.equals( ((Element) n).getNodeName() )) {
					item.setCapacity( ((Element) n).getTextContent() );
				} else if (ATTR_Filesystem.equals( ((Element) n).getNodeName() )) {
					item.setFilesystem( ((Element) n).getTextContent() );
				} else if (ATTR_Size.equals( ((Element) n).getNodeName() )) {
					item.setSize( ((Element) n).getTextContent() );
				} else if (ATTR_Used.equals( ((Element) n).getNodeName() )) {
					item.setUsed( ((Element) n).getTextContent() );
				}
			}
		}
		if (debug) {
			System.out.println(item.toString());
		}
	}
	
}
