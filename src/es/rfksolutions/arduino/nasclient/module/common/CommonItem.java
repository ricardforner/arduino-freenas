package es.rfksolutions.arduino.nasclient.module.common;

import es.rfksolutions.util.StringUtil;

/**
 * Bean de informacion comun
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public abstract class CommonItem {

	// LCD variables
	private int maxRows;
	protected String idService;
	protected int rows = 0;
	protected int cols = 0;
	protected int screen = 0;

	public abstract String getType();
	public abstract String makeBufferLCD();

	public CommonItem() {
		this.maxRows = 2;
		this.cols = 20;
	}

	public void setIdService(String idService) {
		this.idService = idService;
	}

	public void setRows(int maxRows) {
		this.maxRows = maxRows;
	}

	protected String pos() {
		StringBuffer sb = new StringBuffer();
		sb.append("S");
		sb.append(idService);
		screen = (rows%maxRows==0) ? screen+1 : screen;
		sb.append("P");
		sb.append(screen);
		sb.append("L");
		sb.append(rows%maxRows);
		rows++;
		return sb.toString();
	}

	protected String getFormatLine(String type, String p1, String p2) {
		StringBuffer s = new StringBuffer();
		s.append(pos());
		if ("JUSTIFY".equals(type)) {
			s.append(StringUtil.rightPadding(p1, ' ', 20-p2.length()));
			s.append(p2);
		}
		s.append("|");
		return s.toString();
	}

}
