package me.venkataraman.vijay.rasp.queuewrapper.app;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.qpid.jms.JmsConnectionFactory;


/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		for(int i=0;i<5;i++) {
			MessageSender sender = new MessageSender("This is message # "+(i+1));
			sender.send();
		}
		
		for(int i=0;i<5;i++) {
			MessageSender sender = new MessageSender("This is message # "+(i+1));
			sender.send();
		}
	}

	public static class MessageSender {

		private String sMessage;	
		
		public MessageSender(String message) {
			this.sMessage = message;
		}
		
		public void send() {

			try {
				
				System.out.println("Sending Message ["+sMessage+"]");
				
				String user = "admin";
		        String password = "password";
		        
				int port = 5672;
		        
		        String connectionURI = "amqp://localhost:" + port;
				
		        JmsConnectionFactory connectionFactory = new JmsConnectionFactory(connectionURI);
				
		        System.out.println("Connection Factory -> "+connectionFactory);
		        
				//ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://192.168.1.16");
		        Connection connection = connectionFactory.createConnection(user, password);
				String clientID = Inet4Address.getLocalHost().getHostName();
				
				System.out.println("Created Connection : "+connection +" on host "+clientID);

				connection.start();

		        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//		        Connection connection = connectionFactory.createConnection("admin","password");
				
				
//				connection.setExceptionListener(this);
				
//				connection.setClientID(clientID);				
//				connection.start();
//				
				System.out.println("Started Connection : "+connection.getMetaData().getJMSProviderName());

				// Create a Session
//				Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

				// Create the destination (Topic or Queue)
				Destination destination = session.createQueue("q.captured.image");
				
				MessageProducer producer = session.createProducer(destination);
	            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	             
	            Message message = session.createObjectMessage();
	            message.setObjectProperty("imageTime", "124");
	            message.setObjectProperty("imageMessage", sMessage);
	            
	            producer.send(message);
	            
	            session.close();
                connection.close();
                System.out.println("Sent Message ["+message+"]");
				
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		}

	}
}
