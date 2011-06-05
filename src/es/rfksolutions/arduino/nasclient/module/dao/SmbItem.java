package es.rfksolutions.arduino.nasclient.module.dao;

import es.rfksolutions.arduino.nasclient.module.ServiceModuleConstants;
import es.rfksolutions.arduino.nasclient.module.common.CommonItem;

/**
 * Bean de informacion del CIFS/SMB
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class SmbItem extends CommonItem {

	private String pid;
	private String uid;
	private String denyMode;
	private String access;
	private String readWrite;
	private String oplock;
	private String sharePath;
	private String name;

	public static String RW_READ = "RDONLY";
	public static String OPLOCK_NONE = "NONE";

	public SmbItem() {
		setIdService(ServiceModuleConstants.IDSERVICE_SAMBA_VALUE);
	}

	@Override
	public String getType() {
		return ServiceModuleConstants.TYPE_SAMBA_VALUE;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDenyMode() {
		return denyMode;
	}

	public void setDenyMode(String denyMode) {
		this.denyMode = denyMode;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getReadWrite() {
		return readWrite;
	}

	public void setReadWrite(String readWrite) {
		this.readWrite = readWrite;
	}

	public String getOplock() {
		return oplock;
	}

	public void setOplock(String oplock) {
		this.oplock = oplock;
	}

	public String getSharePath() {
		return sharePath;
	}

	public void setSharePath(String sharePath) {
		this.sharePath = sharePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "[" +
			pid + ", " +
			uid + ", " +
			denyMode + ", " +
			access + ", " +
			readWrite + ", " +
			oplock + ", " +
			sharePath + ", " +
			name +
		"]";
	}

	@Override
	public String makeBufferLCD() {
		StringBuffer sb = new StringBuffer();
		String[] lineas = new String[4];
		for (int i=0; i<4; i++) {
			lineas[i]="";
		}
		if (name != null && !"".equals(name)) {
			String play = "CIFS: " + sharePath;
			int cols = 20;
			int miLinea=0;
			if (play.length()>cols) {
				lineas[miLinea++] = play.substring(0, cols);
				lineas[miLinea++] = play.substring(cols, (play.length()>2*cols) ? 2*cols : play.length());
			} else {
				lineas[miLinea++] = play;
			}
			play = "PLAY: " + name;
			if (play.length()>cols) {
				lineas[miLinea++] = play.substring(0, cols);
				lineas[miLinea++] = play.substring(cols, (play.length()>2*cols) ? 2*cols : play.length());
				if (play.length()>2*cols) {
					lineas[miLinea++] = play.substring(2*cols, (play.length()>3*cols) ? 3*cols : play.length());
				}
			} else {
				lineas[miLinea++] = play;
			}
		}
		for (int i=0; i<4; i++) {
			sb.append(getFormatLine("JUSTIFY", lineas[i],""));
		}
		return sb.toString();
	}

}
