package ar.com.kafka;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

public class Consumer {

	
	public static void main(String[] args) {
	    Properties props = new Properties();
	    props.put("bootstrap.servers", "10.9.150.111:9092");
	    props.put("group.id", "0");
	    props.put("enable.auto.commit", "true");
	    props.put("auto.commit.interval.ms", "1000");
	    props.put("auto.offset.reset", "latest");
	    props.put("session.timeout.ms", "30000");
	    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	 
	    
	    KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
	    Map<String, List<PartitionInfo>> listTopics = kafkaConsumer.listTopics();
	    
	    
	    String toSubscribe ="";
	    for(String string : listTopics.keySet()) {
	    	
	    	toSubscribe+=string+"\n";
	    	
	    }
	    
	    String showInputDialog = JOptionPane.showInputDialog(toSubscribe);
	    
	    kafkaConsumer.subscribe(Arrays.asList(showInputDialog));
	    JOptionPane.showMessageDialog(null, "Subscribe to: "+showInputDialog);
	    
	    while (true) {
	    	System.out.println("pooling");
	      ConsumerRecords<String, String> records = kafkaConsumer.poll(1);
	      for (ConsumerRecord<String, String> record : records) {
	        JOptionPane.showMessageDialog(null,"offset = "+record.offset()+", value = "+record.value());
	        System.out.println();
	      }
	    }
	 
	  }
}
