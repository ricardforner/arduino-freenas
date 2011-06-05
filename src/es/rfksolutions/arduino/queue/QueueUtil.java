package es.rfksolutions.arduino.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/***
 * Cola de intercambio de informacion entre red y puerto serie.
 * 
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class QueueUtil {

	public static final String END_QUEUE = "*EOF*";
	
	private static QueueUtil instance = null;
	private BlockingQueue<String> queue;
	
	public static QueueUtil getInstance() {
		if (instance == null)
			synchronized (QueueUtil.class) {
				if (instance == null)
					instance = new QueueUtil();
			}
		return instance;
	}
	
	public void init() {
		this.queue = new LinkedBlockingQueue<String>();
	}
	
	public BlockingQueue<String> getQueue() {
		return queue;
	}

}
