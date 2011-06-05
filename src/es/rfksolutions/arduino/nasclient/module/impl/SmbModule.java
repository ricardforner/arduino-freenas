package es.rfksolutions.arduino.nasclient.module.impl;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.rfksolutions.arduino.nasclient.module.common.CommonModule;
import es.rfksolutions.arduino.nasclient.module.dao.SmbItem;

/***
 * Modulo de informacion del servicio CIFS/Samba
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class SmbModule extends CommonModule {

	public String NODE_List_Open = "listopen";

	public String ATTR_Pid = "pid";
	public String ATTR_Uid = "uid";
	public String ATTR_DenyMode = "denymode";
	public String ATTR_Access = "access";
	public String ATTR_RW = "rw";
	public String ATTR_OpLock = "oplock";
	public String ATTR_SharePath = "sharepath";
	public String ATTR_Name = "name";

	private SmbItem item;
	
	public ArrayList<String> procesa() {
		lista = new ArrayList<String>();
		if (node.hasChildNodes()) {
			NodeList property = node.getChildNodes();
			for (int i = 0; i < property.getLength(); i++) {
				Node n = property.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE
						&& NODE_List_Open.equals(n.getNodeName())) {
					setObject((Element) n);
					if (!SmbItem.OPLOCK_NONE.equals(item.getOplock())
							&& SmbItem.RW_READ.equals(item.getReadWrite())) {
						lista.add(item.makeBufferLCD());
					}
				}
			}
		}
		return lista;
	}

	private void setObject(Element e) {
		item = new SmbItem();
		item.setRows(getRows());
		NodeList l = e.getChildNodes();
		Node n = null;
		for (int i=0; i<l.getLength(); i++) {
			n = l.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				if (ATTR_Pid.equals( ((Element) n).getNodeName() )) {
					item.setPid( ((Element) n).getTextContent() );
				} else if (ATTR_Uid.equals( ((Element) n).getNodeName() )) {
					item.setUid( ((Element) n).getTextContent() );
				} else if (ATTR_DenyMode.equals( ((Element) n).getNodeName() )) {
					item.setDenyMode( ((Element) n).getTextContent() );
				} else if (ATTR_Access.equals( ((Element) n).getNodeName() )) {
					item.setAccess( ((Element) n).getTextContent() );
				} else if (ATTR_RW.equals( ((Element) n).getNodeName() )) {
					item.setReadWrite( ((Element) n).getTextContent() );
				} else if (ATTR_OpLock.equals( ((Element) n).getNodeName() )) {
					item.setOplock( ((Element) n).getTextContent() );
				} else if (ATTR_SharePath.equals( ((Element) n).getNodeName() )) {
					item.setSharePath( ((Element) n).getTextContent() );
				} else if (ATTR_Name.equals( ((Element) n).getNodeName() )) {
					item.setName( ((Element) n).getTextContent() );
				}
			}
		}
		if (debug) {
			System.out.println(item.toString());
		}
	}

}
