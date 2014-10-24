package br.com.almana;

import javax.jms.*;
import javax.naming.InitialContext;

public class HornetQJMSClientSimple {
    public static void main(String[] args) {
        try {
            InitialContext ic = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");
            Queue orderQueue = (Queue) ic.lookup("/queues/OrderQueue");
            Connection connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(orderQueue);
            MessageConsumer consumer = session.createConsumer(orderQueue);
            connection.start();
            TextMessage message = session.createTextMessage("This is an order");
            producer.send(message);
            final TextMessage receivedMessage = (TextMessage) consumer.receive();
            System.out.println("Got order: " + receivedMessage.getText());
            session.close();
            connection.close();
            ic.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
