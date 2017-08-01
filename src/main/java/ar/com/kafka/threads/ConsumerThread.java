package ar.com.kafka.threads;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

public class ConsumerThread extends Thread {

	private JTextArea textArea;
	private String topic;
	private int counter;

	public ConsumerThread(JTextArea textArea,String topic) {

		this.textArea = textArea;
		this.topic = topic;
		counter=0;
	}

	public void run() {

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "group-1");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("auto.offset.reset", "earliest");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
		Map<String, List<PartitionInfo>> listTopics = kafkaConsumer.listTopics();

		System.out.println("subscribed to: "+topic);

		String showInputDialog = topic;

		kafkaConsumer.subscribe(Arrays.asList(showInputDialog));
		JOptionPane.showMessageDialog(null, "Subscribe to: " + showInputDialog);

		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(2);
			for (ConsumerRecord<String, String> record : records) {
				textArea.setText(textArea.getText()+record.value()+"\n");
				counter++;
				
			}
			if(counter == 10) {
				textArea.setText("");
				counter =0;
			}
			
		}

	}

}
