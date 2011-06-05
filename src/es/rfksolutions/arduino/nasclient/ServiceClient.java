package es.rfksolutions.arduino.nasclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.BlockingQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.rfksolutions.arduino.nasclient.module.IServiceModule;
import es.rfksolutions.arduino.nasclient.module.ServiceModuleConstants;
import es.rfksolutions.arduino.nasclient.module.ServiceModuleFactory;

/***
 * Servicio de acceso por red a FreeNAS.
 * 
 * @see ServiceReaderThread
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class ServiceClient {

	// XML
	public String SERVICE_CONFIG = "config";
	public String SERVICE_DETAIL = "detail";
	public String CONFIG_ATTR_REFRESH = "refresh";
	public String CONFIG_ATTR_NUMCHAR = "numchar";

	private String KEY_PARAM_DELAY = "DELAY";
	private String KEY_PARAM_TYPELCD = "TYPELCD";
	
	private String protocol;
	private String host;
	private int port;
	private String service;
	private int sleep;
	
	private Hashtable<String, String> arduinoParams;
	
	// Lista de objetos devueltos por los Modulos
	private ArrayList<String> detailItemList; 
	
	// Variables de debug
	private boolean bDebugModule;
	
	// Variable para test local
	private boolean modeTest = false; // Set TRUE for TEST
	private String fileTest = "C:\\Users\\rforner\\Documents\\JavaProjects\\ArduinoUtils\\test.xml";
	
	public ServiceClient(String protocol, String host, int port, String service) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.service = service;
		this.arduinoParams = new Hashtable<String, String>();
		this.sleep = 1;
		this.detailItemList = new ArrayList<String>();
	}
	
	public void init(int sleep) {
		this.sleep = sleep;
	}

	public void setDebugModule(boolean bDebugModule) {
		this.bDebugModule = bDebugModule;
	}

	protected int getSleep() {
		return sleep;
	}
	
	protected String getURL(String param) {
		StringBuffer sburl = new StringBuffer();
		sburl.append(protocol);
		sburl.append("://");
		sburl.append(host);
		sburl.append(":");
		sburl.append(port);
		sburl.append("/");
		sburl.append(service);
		sburl.append("?pg=");
		sburl.append(param);
		return sburl.toString();
	}

	public void start(BlockingQueue<String> q) {
		Thread p1 = new Thread(new ServiceReaderThread(this, q));
		p1.start();
	}
	
	protected boolean readConfig() {
		if (bDebugModule) {
			System.out.println("Llamada ServiceClient->readConfig");
		}
		boolean isOk = false;
		InputStream is = null;
		try {
			URL xmlUrl = new URL(getURL(SERVICE_CONFIG));
			if (modeTest) {
				is = new FileInputStream(fileTest);
			} else {
				is = xmlUrl.openStream();
			}
			Document document = parse(is);
			if (document == null)
				return isOk;

			// Recuperamos los elementos del configuracion
			NodeList propertyNodes = document.getElementsByTagName(ServiceModuleConstants.ROOT_NODE_TAG_NAME);
			for (int i = 0; i < propertyNodes.getLength(); i++) {
				Node nodo = propertyNodes.item(i);
				if (nodo.getNodeType()==Node.ELEMENT_NODE) {
					Element rootNode = (Element) nodo;
					if (rootNode.getAttribute(CONFIG_ATTR_REFRESH) != null) {
						arduinoParams.put(KEY_PARAM_DELAY, rootNode.getAttribute(CONFIG_ATTR_REFRESH));
					}
					if (rootNode.getAttribute(CONFIG_ATTR_NUMCHAR) != null) {
						arduinoParams.put(KEY_PARAM_TYPELCD, rootNode.getAttribute(CONFIG_ATTR_NUMCHAR));
					}
				}
				isOk = true;
			}
		} catch (Exception ex) {
			System.err.println("ServiceClient->readConfig: No puedo leer el XML de configuración: " + ex);
		}
		return isOk;
	}
	
	private String getParam(String key) {
		return arduinoParams.get(key);
	}

	public String getParamDelay() {
		return getParam(KEY_PARAM_DELAY);
	}
	
	public String getParamTypeLCD() {
		return getParam(KEY_PARAM_TYPELCD);
	}
	
	protected boolean readDetail() {
		if (bDebugModule) {
			System.out.println("Llamada ServiceClient->readDetail");
		}
		boolean isOk = false;
		InputStream is = null;
		try {
			URL xmlUrl = new URL(getURL(SERVICE_DETAIL));
			if (modeTest) {
				is = new FileInputStream(fileTest);
			} else {
				is = xmlUrl.openStream();
			}
			Document document = parse(is);
			if (document == null)
				return isOk;

			IServiceModule module = null;
			detailItemList.clear();
			// Recuperamos los elementos del tipo item
			NodeList propertyNodes = document.getElementsByTagName(ServiceModuleConstants.ITEM_TAG_NAME);
			for (int i = 0; i < propertyNodes.getLength(); i++) {
				Node nodo = propertyNodes.item(i);
				if (nodo.getNodeType()==Node.ELEMENT_NODE) {
					Element itemNode = (Element) nodo;
					module = ServiceModuleFactory.create(itemNode.getAttribute(ServiceModuleConstants.ITEM_TYPE_ATTRIBUTE_NAME));
					module.setDebug(bDebugModule);
					module.setLCD(getParam(KEY_PARAM_TYPELCD));
					module.setNode(itemNode);	
					detailItemList.addAll(module.procesa());
				}
				isOk = true;
			}
			module = null;

		} catch (IOException ex) {
			System.err.println("ServiceClient->readDetail: No puedo leer el XML de datos: " + ex);
		}
		return isOk;
	}
	
	public ArrayList<String> getDetail() {
		return detailItemList;
	}

	private Document parse(InputStream is) {
		Document ret = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder db = dbf.newDocumentBuilder();
			if (is != null) {
				ret = db.parse(is);
				is.close();
			}
		} catch (Exception ex) {
			System.err.println("No puedo cargar el XML: " + ex);
		}
		return ret;
	}
	
	public static void main(String[] args) {
		ServiceClient sc = new ServiceClient("http", "naspc", 80, "services_monitornas_public.php");
		sc.readDetail();
	}

}
