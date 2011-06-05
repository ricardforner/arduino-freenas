package es.rfksolutions.arduino.nasclient.module.dao;

import es.rfksolutions.arduino.nasclient.module.ServiceModuleConstants;
import es.rfksolutions.arduino.nasclient.module.common.CommonItem;

/**
 * Bean de informacion de espacio en disco
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class DiskSpaceItem extends CommonItem {

	private String key;
	private String filesystem;
	private String capacity;
	private String used;
	private String size;
	private String available;
	private String name;

	public DiskSpaceItem() {
		setIdService(ServiceModuleConstants.IDSERVICE_DISKSPACE_VALUE);
	}

	@Override
	public String getType() {
		return ServiceModuleConstants.TYPE_DISKSPACE_VALUE;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFilesystem() {
		return filesystem;
	}

	public void setFilesystem(String filesystem) {
		this.filesystem = filesystem;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "[" +
			key + ", " +
			filesystem + ", " +
			capacity + ", " +
			used + ", " +
			size + ", " +
			available + ", " +
			name +
		"]";
	}

	@Override
	public String makeBufferLCD() {
		StringBuffer sb = new StringBuffer();
		sb.append(getFormatLine("JUSTIFY", name, "["+filesystem.substring(5)+"]"));
		sb.append(getFormatLine("JUSTIFY", ">> Uso: " + used + " de " + size, ""));
		sb.append(getFormatLine("JUSTIFY", ">> Capacidad al " + capacity, ""));
		sb.append(getFormatLine("JUSTIFY", ">> Disponible " + available, ""));
		return sb.toString();
	}

}
