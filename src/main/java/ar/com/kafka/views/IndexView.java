package ar.com.kafka.views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;

import ar.com.kafka.shape.TrafficLightPanel;
import ar.com.kafka.threads.ConsumerThread;

public class IndexView extends JFrame {

	private ConsumerThread consumerThread;
	
	
	public IndexView() {

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		
		
		
		// panel things
		// panel.setSize(390,390);
		JTextField data = new JTextField(30);
		JButton send = new JButton("send");

		JTextArea response = new JTextArea("", 10, 30);
		response.setEditable(false);
		JButton consume = new JButton("consume");

		JList<String> queues = new JList<String>();
		response.setEditable(false);
		JButton showQueues = new JButton("show Queues");
		DefaultListModel<String> model = new DefaultListModel<String>();
		queues.setModel(model);

		// add panel
		panel.add(data);
		panel.add(send);

		panel.add(queues);
		panel.add(showQueues);

		panel.add(response);
		panel.add(consume);

		
		// frame things
		add(panel);
		setVisible(true);
		// width height
		setSize(481, 400);
		setLocation(400, 400);

		// listeners

		JButton kill = new JButton("kill it!");

//		Round round = new Round();
		TrafficLightPanel color = new TrafficLightPanel();
		JPanel panel2 = new JPanel();
		panel.add(panel2);
		panel2.add(color);
		panel2.add(kill);
		
		
		
		kill.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				color.changeColorRed();
				consumerThread.interrupt();
				
			}
		});
		
		consume.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Properties props = consumerProperties();

				KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
				
				String topic = queues.getSelectedValue();
				
				if(topic.equals("") || topic == null)
						throw new RuntimeException();

				 consumerThread = new ConsumerThread(response, topic);
				 color.changeColorGreen();
				consumerThread.start();
				
				
			}
		});

		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String topic = queues.getSelectedValue();
				String message = data.getText();
				Properties props = getProducerProperties();
				Producer<String, String> producer = new KafkaProducer<>(props);
				System.out.println("send to: " + topic + " value: " + message);
				ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, "dfg",
						message);
				producer.send(producerRecord);
				producer.close();

			}
		});

		showQueues.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Properties props = getProperties();
				KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
				Map<String, List<PartitionInfo>> listTopics = kafkaConsumer.listTopics();
				String toPost = "";

				for (String string : listTopics.keySet()) {

					model.addElement(string);

				}

				kafkaConsumer.close();

			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public Properties getProperties() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "group-1");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("auto.offset.reset", "earliest");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		return props;
	}

	
	public Properties getProducerProperties() {
		
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 102400);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		return props;
		
		
	}
	public Properties consumerProperties() {

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "group-1");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("auto.offset.reset", "latest");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		return props;

	}

}
