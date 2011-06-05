package es.rfksolutions.arduino.nasclient.module.dao;

import es.rfksolutions.arduino.nasclient.module.ServiceModuleConstants;
import es.rfksolutions.arduino.nasclient.module.common.CommonItem;

/**
 * Bean de informacion del sistema
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class SystemItem extends CommonItem {

	private String hostname;
	private String version;
	private String memory_percentage;
	private String memory_total;

	public SystemItem() {
		setIdService(ServiceModuleConstants.IDSERVICE_SYSTEM_VALUE);
	}
	
	@Override
	public String getType() {
		return ServiceModuleConstants.TYPE_SYSTEM_VALUE;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMemoryPercentage() {
		return memory_percentage;
	}

	public void setMemoryPercentage(String memoryPercentage) {
		memory_percentage = memoryPercentage;
	}

	public String getMemoryTotal() {
		return memory_total;
	}

	public void setMemoryTotal(String memoryTotal) {
		memory_total = memoryTotal;
	}

	public String toString() {
		return "[" +
			hostname + ", " +
			version + ", " +
			memory_percentage + ", " +
			memory_total +
		"]";
	}

	@Override
	public String makeBufferLCD() {
		StringBuffer sb = new StringBuffer();
		sb.append(pos());
		sb.append("System information");
		sb.append("|");
		sb.append(pos());
		sb.append("--------------------");
		sb.append("|");
		sb.append(pos());
		sb.append(hostname + " (v" + version + ")");
		sb.append("|");
		sb.append(pos());
		sb.append("Mem: " + memory_percentage + " de " + memory_total + "Mb");
		sb.append("|");
		return sb.toString();
	}

}
