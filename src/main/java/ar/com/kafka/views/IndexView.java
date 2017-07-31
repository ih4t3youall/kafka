package ar.com.kafka.views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

public class IndexView extends JFrame {

	
	
	public IndexView() {
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		//panel things
//		panel.setSize(390,390);
		JTextField data =  new JTextField(30);
		JButton send = new JButton("send");

		JTextArea response = new JTextArea("",30,30);
		response.setEditable(false);
		JButton clear = new JButton("clear");
		
		
		JTextArea queues = new JTextArea("",30,30);
		response.setEditable(false);
		JButton showQueues = new JButton("clear");
		
		
		//add panel
		panel.add(data);
		panel.add(send);
		panel.add(response);
		panel.add(clear);
		
		panel.add(queues);
		panel.add(showQueues);
		
		
		
		
		
		//frame things
		add(panel);
		setVisible(true);
		setSize(481,944);
		setLocation(400,400);
		
		//listeners
		
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				response.setText("");
				int height2 = getHeight();
				int width2 = getWidth();
				response.setText(height2+"\n"+width2);
				
			}
		});
		
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				
			}
		});
		
		
		showQueues.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				
				
			    Properties props = new Properties();
			    props.put("bootstrap.servers", "10.9.150.111:9092");
			    props.put("group.id", "group-1");
			    props.put("enable.auto.commit", "true");
			    props.put("auto.commit.interval.ms", "1000");
			    props.put("auto.offset.reset", "earliest");
			    props.put("session.timeout.ms", "30000");
			    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			 
			    KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
			    Map<String, List<PartitionInfo>> listTopics = kafkaConsumer.listTopics();
			    String toPost = "";
			    for(String string : listTopics.keySet()) {
			    	toPost+= string+"\n";
			    	
			    }
			    
			    queues.setText(toPost);
				kafkaConsumer.close();
				
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	}
	
}
