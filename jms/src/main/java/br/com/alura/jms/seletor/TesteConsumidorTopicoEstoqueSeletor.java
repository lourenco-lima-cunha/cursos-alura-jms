package br.com.alura.jms.seletor;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class TesteConsumidorTopicoEstoqueSeletor {

	public static void main(String[] args) throws Exception{

        InitialContext context = new InitialContext(); 

        //imports do package javax.jms
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.setClientID("estoque-seletor");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination topico = (Destination) context.lookup("loja");
        
        String selectorStr = "ebook = false";
        
        MessageConsumer consumer = session.createDurableSubscriber( (Topic) topico, "sign selector", selectorStr, false);
        
        consumer.setMessageListener(new MessageListener(){

            @Override
            public void onMessage(Message message){
            	TextMessage textMessage  = (TextMessage) message;
                try{
                    System.out.println(textMessage.getText());
                } catch(JMSException e){
                    e.printStackTrace();
                }
            }

        });
        
        new Scanner(System.in).nextLine(); //parar o programa para testar a conexao

        session.close();
        
        connection.close();
        context.close();
	}
	
}
