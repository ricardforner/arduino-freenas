package es.rfksolutions.arduino.driver;

import java.io.InputStream;

/***
 * Clase Reader para puerto serie.
 * 
 * @see CommPortDriver
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
public class SerialReaderThread implements Runnable {

    InputStream in;
    
    /**
     * @param in Stream de entrada
     */
    public SerialReaderThread(InputStream in) {
        this.in = in;
    }

	@Override
	public void run() {
		System.out.println("Thread SerialReader: Iniciado.");
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while((len = this.in.read(buffer)) > -1) {
                System.out.print(new String(buffer,0,len));
            }
			Thread.sleep(100);
        } catch (Exception e) {
			System.err.println("SerialReaderThread error: " + e);
        }            
	}

}
