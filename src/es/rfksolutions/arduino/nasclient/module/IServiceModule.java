package es.rfksolutions.arduino.nasclient.module;

import java.util.ArrayList;

import org.w3c.dom.Node;

/***
 * Interficie de los módulos del servicio cliente web
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public interface IServiceModule {

	public void setDebug(boolean bDebugModule);
	public void setNode(Node e);
	public void setLCD(String lcd);
	public ArrayList<String> procesa();

}
