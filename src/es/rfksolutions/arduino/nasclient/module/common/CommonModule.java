package es.rfksolutions.arduino.nasclient.module.common;

import java.util.ArrayList;

import org.w3c.dom.Node;

import es.rfksolutions.arduino.nasclient.module.IServiceModule;

public abstract class CommonModule implements IServiceModule {

	protected boolean debug;
	protected Node node;
	private int maxRows;

	protected ArrayList<String> lista;

	public void setNode(Node e) {
		this.node = e;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public void setLCD(String LCD) {
		if (LCD != null && !"".equals(LCD) && (LCD.indexOf(",") != -1)) {
			maxRows = (new Integer(LCD.substring(1+LCD.indexOf(",")))).intValue();
		}
	}
	
	public int getRows() {
		return maxRows;
	}
	
}
