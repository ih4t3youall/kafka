package ar.com.kafka.threads;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


import ar.com.kafka.helper.SessionHelper;

public class ConsumerThread extends Thread {

	private JTextArea textArea;
	private String topic;
	private int counter;
	boolean flag ;

	public ConsumerThread(JTextArea textArea,String topic) {

		this.textArea = textArea;
		this.topic = topic;
		counter=0;
		flag = false;
	}

	public void run() {

		Properties props = new Properties();
		props = SessionHelper.getConsumerProperties();
	
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);

		System.out.println("subscribed to: "+topic);

		String showInputDialog = topic;

		kafkaConsumer.subscribe(Arrays.asList(showInputDialog));

		new Thread(()->{
			JOptionPane.showMessageDialog(null, "Subscribe to: " + showInputDialog);
		}).start();

		int maxNumberOfConsumptions = SessionHelper.getMaxNumberOfConsumptions();
		
		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(1);
			
			
			for (ConsumerRecord<String, String> record : records) {
				textArea.setText(textArea.getText()+"key: "+record.key()+" value: "+record.value()+"\n");
				counter++;
			}
			flag = needToBreak(maxNumberOfConsumptions, counter);
			if(flag) {
				System.out.println("thread is dying");
				kafkaConsumer.close();
				break;
			}
			
		}
		
		System.out.println("thread is dead.");

	}
	
	public boolean needToBreak(int maxNumberOfConsumptions, int count) {
		
		if(maxNumberOfConsumptions != 0 ) {
			
			if(maxNumberOfConsumptions <= count) {
				return true;
			}
			
		}
		
		return false;
		
		
		
	}

}
