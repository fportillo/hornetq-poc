package br.com.almana;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.*;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;

public class HornetQCoreClientSimple {
    public static void main(String[] args) {
        try {

            ServerLocator locator = HornetQClient.createServerLocatorWithoutHA(new TransportConfiguration(
                    NettyConnectorFactory.class.getName()));

            ClientSessionFactory factory = locator.createSessionFactory();

            ClientSession session = factory.createSession();

            ClientProducer producer = session.createProducer("jms.queue.OrderQueue");

            ClientMessage message = session.createMessage(true);

            message.getBodyBuffer().writeString("Hello");

            producer.send(message);

            session.start();

            ClientConsumer consumer = session.createConsumer("jms.queue.OrderQueue");

            ClientMessage msgReceived = consumer.receive();

            System.out.println("message = " + msgReceived.getBodyBuffer().readString());

            session.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
