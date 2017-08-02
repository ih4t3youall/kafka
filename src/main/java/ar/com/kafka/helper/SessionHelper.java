package ar.com.kafka.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JTextField;

public class SessionHelper {

	private static Map<String, JTextField> options;
	
	private static String[] consumerOptions = { "bootstrap.servers", "group.id", "enable.auto.commit", "auto.commit.interval.ms",
			"auto.offset.reset", "session.timeout.ms", "key.deserializer", "value.deserializer","heartbeat.interval.ms","max.poll.records" };
	private static String[] producerOptions = { "bootstrap.servers", "acks", "retries", "batch.size", "linger.ms", "buffer.memory",
			"key.serializer", "value.serializer" };
	
	
	
	public static Map<String, JTextField> getOptions() {

		if (options == null) {

			options = new HashMap<String, JTextField>();
			return options;

		}

		return options;

	}
	
	
	public static int getMaxNumberOfConsumptions() {
		
		return Integer.parseInt(options.get("maxNumberOfConsumptions").getText());
		
	}

	public static Properties getProducerProperties() {

		Map<String, JTextField> map = getOptions();
		
		Properties pro = new Properties();
		
		for (int i = 0; i < producerOptions.length; i++) {
			
			pro.put(producerOptions[i], map.get(producerOptions[i]).getText());
			
		}
		
		return pro;
		
	}



	public static Properties getConsumerProperties() {

		Map<String, JTextField> map = getOptions();
		
		Properties pro = new Properties();
		
		for (int i = 0; i < consumerOptions.length; i++) {
			
			pro.put(consumerOptions[i], map.get(consumerOptions[i]).getText());
			
		}
		
		return pro;
		
	}

	
	public static void setOptions(Object loadSavedFile) {
		Map<String, JTextField> map = (Map<String, JTextField>) loadSavedFile;
		options = map;
	}

}
