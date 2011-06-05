package es.rfksolutions.arduino.nasclient.module.impl;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.rfksolutions.arduino.nasclient.module.common.CommonModule;
import es.rfksolutions.arduino.nasclient.module.dao.SystemItem;

/***
 * Modulo de informacion del sistema
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class SystemModule extends CommonModule {

	public String ATTR_Hostname = "hostname";
	public String ATTR_Version = "version";
	public String ATTR_Memory_Percentage = "memory_percentage";
	public String ATTR_Memory_Total = "memory_total";

	private SystemItem item;
	
	public ArrayList<String> procesa() {
		setObject((Element) node);
		lista = new ArrayList<String>(1);
		lista.add(item.makeBufferLCD());
		return lista;
	}

	private void setObject(Element e) {
		item = new SystemItem();
		item.setRows(getRows());
		NodeList l = e.getChildNodes();
		Node n = null;
		for (int i=0; i<l.getLength(); i++) {
			n = l.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				if (ATTR_Hostname.equals( ((Element) n).getNodeName() )) {
					item.setHostname( ((Element) n).getTextContent() );
				} else if (ATTR_Version.equals( ((Element) n).getNodeName() )) {
					item.setVersion( ((Element) n).getTextContent() );
				} else if (ATTR_Memory_Percentage.equals( ((Element) n).getNodeName() )) {
					item.setMemoryPercentage( ((Element) n).getTextContent() );
				} else if (ATTR_Memory_Total.equals( ((Element) n).getNodeName() )) {
					item.setMemoryTotal( ((Element) n).getTextContent() );
				}
			}
		}
		if (debug) {
			System.out.println(item.toString());
		}
	}

}
