package es.rfksolutions.arduino.nasclient.module.impl;

import java.util.ArrayList;

import es.rfksolutions.arduino.nasclient.module.common.CommonModule;

/**
 * Modulo por defecto
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class EmptyModule extends CommonModule {

	public ArrayList<String> procesa() {
		return new ArrayList<String>();
	}

}
