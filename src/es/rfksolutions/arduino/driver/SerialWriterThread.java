package es.rfksolutions.arduino.driver;

import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

import es.rfksolutions.arduino.queue.QueueUtil;

/***
 * Clase Writer para puerto serie.
 * 
 * @see CommPortDriver
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class SerialWriterThread implements Runnable {

	private OutputStream out;
	private final BlockingQueue<String> queue;

	/**
	 * @param out Stream de salida
	 * @param queue Cola de mensajes
	 */
	public SerialWriterThread(OutputStream out, BlockingQueue<String> queue) {
        this.out = out;
        this.queue = queue;
    }
    
	@Override
	public void run() {
		System.out.println("Thread SerialWriter: Iniciado.");
		String value;
		try {
			value = queue.take();
			while (!QueueUtil.END_QUEUE.equals(value)) {
				out.write(value.getBytes(), 0, value.length());
				Thread.sleep(2000);
				value = queue.take();
			}
		} catch (Exception e) {
			System.err.println("SerialWriterThread error: " + e);
		}
	}

}
