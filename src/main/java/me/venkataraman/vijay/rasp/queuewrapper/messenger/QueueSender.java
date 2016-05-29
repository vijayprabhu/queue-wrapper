package me.venkataraman.vijay.rasp.queuewrapper.messenger;

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

import me.venkataraman.vijay.rasp.queuewrapper.model.ImageMessage;

public class QueueSender {
	private ImageMessage imageMessage;
	
	public QueueSender(ImageMessage imageMessage) {
		this.imageMessage = imageMessage;
	}
	
	public void send() {
		try {
			
			System.out.println("Sending Message ["+imageMessage.getMessageID()+"/"+imageMessage.getMessageDateTime()+"]");
			
			String user = "admin";
	        String password = "password";
	        
			int port = 5672;
	        
	        String connectionURI = "amqp://192.168.1.16:" + port;
			
	        JmsConnectionFactory connectionFactory = new JmsConnectionFactory(connectionURI);
			
	        System.out.println("Connection Factory -> "+connectionFactory);
	        
			//ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://192.168.1.16");
	        Connection connection = connectionFactory.createConnection(user, password);
			String clientID = Inet4Address.getLocalHost().getHostName();
			
			System.out.println("Created Connection : "+connection +" on host "+clientID);

			connection.start();

	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
			System.out.println("Started Connection : "+connection.getMetaData().getJMSProviderName());

			Destination destination = session.createQueue("q.captured.image");
			
			MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
             
            Message message = session.createObjectMessage(imageMessage);
//            message.setObjectProperty("messageID", imageMessage.getMessageID());
//            message.setObjectProperty("messageDateTime", imageMessage.getMessageDateTime());
//            message.setObjectProperty("messageStream", imageMessage.getMessageStream());
//            message.setObjectProperty("messageStreamLength", imageMessage.getMessageStreamLength());
            
            producer.send(message);
            
            session.close();
            connection.close();
            System.out.println("Sent Message ["+imageMessage.getMessageID()+"/"+imageMessage.getMessageDateTime()+"]");
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
}
