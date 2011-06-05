package es.rfksolutions.arduino.nasclient.module;

import es.rfksolutions.arduino.nasclient.module.impl.DiskSpaceModule;
import es.rfksolutions.arduino.nasclient.module.impl.EmptyModule;
import es.rfksolutions.arduino.nasclient.module.impl.SmbModule;
import es.rfksolutions.arduino.nasclient.module.impl.SystemModule;

/**
 * Crea una implementación de modulo de servicio
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class ServiceModuleFactory {

	public static IServiceModule create(String key) {
		if (ServiceModuleConstants.TYPE_SYSTEM_VALUE.equals(key)) {
			return new SystemModule();
		} else if (ServiceModuleConstants.TYPE_DISKSPACE_VALUE.equals(key)) {
			return new DiskSpaceModule();
		} else if (ServiceModuleConstants.TYPE_SAMBA_VALUE.equals(key)) {
			return new SmbModule();
		} 
		return new EmptyModule();
	}
}
