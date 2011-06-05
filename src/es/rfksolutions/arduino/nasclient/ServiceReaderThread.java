package es.rfksolutions.arduino.nasclient;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/***
 * Clase Reader para acceso red a FreeNAS.
 * 
 * @see ServiceClient
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class ServiceReaderThread implements Runnable {

	private int sleep;
	private ServiceClient client;
	private final BlockingQueue<String> queue;
	
	/**
	 * @param client ServiceClient
	 * @param queue Cola de mensajes
	 * @see ServiceClient
	 */
	public ServiceReaderThread(Object client, BlockingQueue<String> queue) {
		this.client = (ServiceClient) client;
		this.queue = queue;
		// variables particulares
		this.sleep = this.client.getSleep();
	}
	
	public void run() {
		System.out.println("Thread ServiceReader: Iniciado.");
		boolean configReaded = false;
		ArrayList<String> lista = null;
		String[] listaArr = null;
		while (true) {
			try {
				if (!configReaded) {
					// Lectura de configuracion
					configReaded = client.readConfig();
					queue.put("");
//					queue.put("Tipo LCD: " + client.getParamTypeLCD());
//					queue.put("Tiempo espera: " + client.getParamDelay());
				} else {
					// Lectura de valores
					boolean hasValue = client.readDetail();
					if (hasValue) {
						lista = client.getDetail();
						for (int i=0; lista != null && i < lista.size(); i++) {
							//listaArr = lista.get(i).split("\\|");
							//for (int j=0; listaArr != null && j < listaArr.length; j++) {
							//	System.out.println(listaArr[j]);	
							//	queue.put(listaArr[i]);
							//}
							queue.put(lista.get(i));
						}
					}
				}
				Thread.sleep(sleep * 1000);
			} catch (Exception e) {
				System.err.println("ServiceReaderThread error: " + e);
			}
		}
	}

}
