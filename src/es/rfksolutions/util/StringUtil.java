/*
 * Created on 20-jul-2005
 *
 */
package es.rfksolutions.util;

/**
 * Utilidad de tratamiento sobre Strings
 * <li> v1.1 - Añadida funcion cutandAdd de recorte y adicion de cadena "..."
 * 
 * @author Ricard Forner
 * @version 1.1
 */
public class StringUtil {

	public static String leftPadding(String cadena, char caracter, int longitud) {
		String temporal = new String(cadena);
		while (temporal.length() < longitud)
			temporal = caracter + temporal;
		return temporal;
	}

	public static String replaceCadena(String cadena, String oldCadena,
			String newCadena) {
		int index = 0;
		while ((index = cadena.indexOf(oldCadena, index)) >= 0) {
			cadena = cadena.substring(0, index) + newCadena
					+ cadena.substring(index + oldCadena.length());
			index += newCadena.length();
		}
		return cadena;
	}

	public static String rightPadding(String cadena, char caracter, int longitud) {
		String temporal = new String(cadena);
		while (temporal.length() < longitud)
			temporal = temporal + caracter;
		return temporal;
	}
	
	public static String cutAndAdd(String cadena, int longitud) {
	    String temporal = new String();
	    if (cadena.length() > longitud)
	        temporal = cadena.substring(0, longitud) + "...";
	    else
	        temporal = cadena;
	    return temporal;
	}

}
