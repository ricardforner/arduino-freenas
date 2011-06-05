package es.rfksolutions.arduino;

import java.io.InputStream;
import java.util.Properties;

import es.rfksolutions.arduino.driver.CommPortDriver;
import es.rfksolutions.arduino.nasclient.ServiceClient;
import es.rfksolutions.arduino.queue.QueueUtil;

/***
 * Monitor actividad FreeNAS y publicación de estado a Arduino
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class MonitorNAS {

	public static String CONFIGURATION_FILE = "MonitorNAS.properties";
	private String version = "0.1.0";

	private String portName;		// Nombre del puerto serie/USB
	private String hostProtocol;	// Protocolo de comunicacion con FreeNAS
	private String hostIP;			// IP del servidor FreeNAS
	private int hostPort;			// Puerto del servidor web de administracion de FreeNAS
	private String hostService;		// Pagina web del servicio de monitorizacion
	private int hostRefresh;		// Tiempo de espera entre llamadas a FreeNAS

	private boolean bDebugModule;	// Debug de los modulos
	
	private CommPortDriver driver;
	private ServiceClient client;

	private void initProperties() {
		Properties propiedades = new Properties();
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(CONFIGURATION_FILE);
			if (inputStream != null) {
				propiedades.load(inputStream);
				inputStream.close();
			} else {
				System.out.println("Fichero de propiedades " + CONFIGURATION_FILE + " no encontrado");
			}
			// Recuperacion de variables del fichero de propiedades
			portName = propiedades.getProperty("puerto_serie");
			hostProtocol = propiedades.getProperty("host_protocol");
			hostIP = propiedades.getProperty("host_ip");
			if (propiedades.getProperty("host_port") != null) {
				hostPort = Integer.valueOf(propiedades.getProperty("host_port")).intValue();
			}
			hostService = propiedades.getProperty("host_service");
			if (propiedades.getProperty("host_refresh") != null) {
				hostRefresh = Integer.valueOf(propiedades.getProperty("host_refresh")).intValue();
			}
			// Niveles de debug
			bDebugModule = false;
			if (propiedades.getProperty("debug_module") != null) {
				bDebugModule = "1".equals(propiedades.getProperty("debug_module"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initSerie() {
		System.out.println("Iniciando servicio puerto Serie");
		driver = new CommPortDriver();
		driver.init(portName);		
	}
	
	private void initLanService() {
		System.out.println("Iniciando servicio de comunicación por red");
		client = new ServiceClient(hostProtocol, hostIP, hostPort, hostService);
		client.init(hostRefresh);
		client.setDebugModule(bDebugModule);
	}
	
	private void initQueue() {
		System.out.println("Iniciando puente de comunicación.");
		QueueUtil.getInstance().init();
	}
	
	private void proceso() {
		System.out.println("Iniciando protocolo");
		if (!driver.hasPortFound()) {
			System.out.println("Puerto " + portName + " no encontrado.");
		} else {
			driver.start(QueueUtil.getInstance().getQueue());
			client.start(QueueUtil.getInstance().getQueue());
		}
	}
    
	private void inicio() {
		System.out.println("=========================================");
		System.out.println("== MonitorNAS v"+ version +" - rfksolutions.es ==");
		System.out.println("=========================================");
		// Lectura properties
		initProperties();
		// Inicio puerto serie
		initSerie();
		// Inicio acceso servicio
		initLanService();
		// Inicio puente de comunicacion
		initQueue();
	}
	
	public static void main(String[] args) {
		MonitorNAS obj = new MonitorNAS();
		obj.inicio();
		obj.proceso();
	}

}
