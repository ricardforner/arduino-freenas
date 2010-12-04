/**
 * MonitorNAS por Ricard Forner
 */
package es.rfksolutions.arduino;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

@SuppressWarnings("restriction")
public class MonitorNAS {

	public static String CONFIGURATION_FILE = "MonitorNAS.properties";

    private CommPortIdentifier portId;
	private boolean portFound = false;
	private String portName;

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

			portName = propiedades.getProperty("puerto_serie");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void initSerie() {
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portId = portList.nextElement();
			if (CommPortIdentifier.PORT_SERIAL == portId.getPortType()) {
				if (portId.getName().equals(portName)) {
					portFound = true;
					break;
				}
			}
		}
	}
	
	private void proceso() {
		if (!portFound) {
			System.out.println("Puerto " + portName + " no encontrado.");
			return;
		}
		
		CommPortIdentifier portIdentifier = null;
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			if (portIdentifier.isCurrentlyOwned()) {
				System.out.println("Puerto " + portName + " esta en uso.");
	        } else {
	        	// Abrimos el puerto
	        	CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
	        	if (commPort instanceof SerialPort) {
	                SerialPort serialPort = (SerialPort) commPort;
	                serialPort.setSerialPortParams(
	                		9600,
	                		SerialPort.DATABITS_8,
	                		SerialPort.STOPBITS_1,
	                		SerialPort.PARITY_NONE);
	                serialPort.notifyOnOutputEmpty(true);
	                
	                InputStream in = serialPort.getInputStream();
	                OutputStream out = serialPort.getOutputStream();

	                (new Thread(new SerialReader(in))).start();
	                (new Thread(new SerialWriter(out))).start();
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static class SerialReader implements Runnable {
        InputStream in;
        
        public SerialReader(InputStream in) {
            this.in = in;
        }
        
        public void run() {
            byte[] buffer = new byte[1024];
            int len = -1;
            try {
                while((len = this.in.read(buffer)) > -1) {
                    System.out.print(new String(buffer,0,len));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }            
        }
    }

    public static class SerialWriter implements Runnable {
        OutputStream out;
        
        public SerialWriter (OutputStream out) {
            this.out = out;
        }
        
        public void run() {
            try {                
                int c = 0;
                while ((c = System.in.read()) > -1) {
                    //this.out.write(c);
                }                
            }
            catch (IOException e) {
                e.printStackTrace();
            }            
        }
    }
    

	private void inicio() {
		// Lectura properties
		initProperties();
		// Inicio puerto serie
		initSerie();
	}
	
	public static void main(String[] args) {
		System.out.println("MonitorNAS v0.1 (C) Ricard Forner");
		MonitorNAS obj = new MonitorNAS();
		obj.inicio();
		obj.proceso();
	}

}
