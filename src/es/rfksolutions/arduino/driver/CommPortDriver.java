package es.rfksolutions.arduino.driver;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;

/***
 * Servicio de acceso puerto serie a arduino.
 * 
 * @see SerialReaderThread
 * @see SerialWriterThread
 * @author ricardforner.feedback@gmail.com
 * @version 1.0
 */
@SuppressWarnings("restriction")
public class CommPortDriver {

	private CommPortIdentifier portId;
    private String portName;
	private boolean portFound;
	private SerialPort serialPort;

	public CommPortDriver() {
		portFound = false;
	}
	
	@SuppressWarnings("unchecked")
	public void init(String pPortName) {
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portId = portList.nextElement();
			if (CommPortIdentifier.PORT_SERIAL == portId.getPortType()) {
				if (portId.getName().equals(pPortName)) {
					portName = pPortName;
					portFound = true;
					break;
				}
			}
			portId = null;
		}
	}
	
	public boolean hasPortFound() {
		return portFound;
	}
	
	public CommPortIdentifier getPort() {
		return portId;
	}
	
	public void start(BlockingQueue<String> q) {
		CommPortIdentifier portIdentifier = null;
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			if (portIdentifier.isCurrentlyOwned()) {
				System.out.println("Puerto " + portName + " esta en uso.");
			} else {
				// Abrimos el puerto
				CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
				if (commPort instanceof SerialPort) {
					serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(
							38400,
							SerialPort.DATABITS_8,
							SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
					serialPort.notifyOnOutputEmpty(true);
					read();
					write(q);
				}
			}
		} catch (Exception ex) {
			System.err.println("CommPortDriver: Error al conectar al puerto serie: " + ex);
		}
	}

	public void read() throws IOException {
		if (serialPort != null) {
			InputStream in = serialPort.getInputStream();
			(new Thread(new SerialReaderThread(in))).start();
		}
	}
	
	public void write(BlockingQueue<String> q) throws IOException {
		if (serialPort != null) {
			OutputStream out = serialPort.getOutputStream();
			Thread tw = new Thread(new SerialWriterThread(out, q));
			tw.start();
		}
	}

}
