package ar.com.kafka.views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;

import ar.com.kafka.helper.SessionHelper;
import ar.com.kafka.menu.MainMenu;
import ar.com.kafka.persistencia.Persistencia;
import ar.com.kafka.shape.TrafficLightPanel;
import ar.com.kafka.threads.ConsumerThread;

public class IndexView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8090714351624196916L;
	private ConsumerThread consumerThread;
	private boolean threadRuning = false;
	private JList<String> queues = new JList<String>();
	private JTextField data = new JTextField(30);

	public IndexView() throws IOException {

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		setJMenuBar(new MainMenu(this));
		// panel things
		// panel.setSize(390,390);
		
		JButton send = new JButton("send");
		JButton batch = new JButton("BatchSend");

		JTextArea response = new JTextArea("", 10, 30);

		JScrollPane scroll = new JScrollPane(response);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		response.setEditable(false);
		JButton consume = new JButton("consume");

		JButton showQueues = new JButton("show Queues");
		DefaultListModel<String> model = new DefaultListModel<String>();
		model.addElement("nothing to display");
		queues.setModel(model);

		// add panel
		JPanel panelSend = new JPanel();
		panelSend.setLayout(new FlowLayout());

		panelSend.add(data);
		panelSend.add(send);
		panelSend.add(batch);

		panel.add(panelSend);

		panel.add(queues);
		panel.add(showQueues);

		panel.add(scroll);
		panel.add(consume);

		// frame things
		add(panel);
		setVisible(true);
		// width height
		setSize(570, 400);
		setLocation(400, 400);

		JButton kill = new JButton("kill it!");
		JButton clean = new JButton("Clean");
		
		data.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if ( 10 == e.getKeyCode()) {
					sendAMessage();
				}
				
			}
		});
		
		

		TrafficLightPanel color = new TrafficLightPanel();
		JPanel panel2 = new JPanel();
		panel.add(panel2);
		panel2.add(color);
		panel2.add(kill);
		panel2.add(clean);
		
		
		clean.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				response.setText("");
			}
		});

		kill.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				color.changeColorRed();
				if (threadRuning) {
					consumerThread.interrupt();
					threadRuning = false;
				}

			}
		});

		consume.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Properties props = SessionHelper.getConsumerProperties();
				KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
				String topic = queues.getSelectedValue();

				if (topic.equals("") || topic == null)
					throw new RuntimeException();

				if (threadRuning) {
					JOptionPane.showMessageDialog(null, "Thread already running!");
				} else {
					consumerThread = new ConsumerThread(response, topic);
					color.changeColorGreen();
					consumerThread.start();
					threadRuning = true;
				}

			}
		});

		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendAMessage();
			}
		});

		batch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "gif", "jpg");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					Persistencia per = new Persistencia();
					List<String> messages = per.readLine(chooser.getSelectedFile().getAbsolutePath());

					String topic = queues.getSelectedValue();
					Properties props = SessionHelper.getProducerProperties();
					Producer<String, String> producer = new KafkaProducer<>(props);

					for (String message : messages) {
						System.out.println("send to: " + topic + " value: " + message);
						ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, "dfg",
								message);
						producer.send(producerRecord);
					}

					producer.close();

				} 

			}
		});

		showQueues.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Properties props = SessionHelper.getConsumerProperties();
				KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
				Map<String, List<PartitionInfo>> listTopics = kafkaConsumer.listTopics();

				model.clear();

				for (String string : listTopics.keySet()) {

					model.addElement(string);

				}

				kafkaConsumer.close();

			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public void sendAMessage() {
		String topic = queues.getSelectedValue();
		String message = data.getText();

		if (message == "") {
			JOptionPane.showMessageDialog(null, "message can not be empty");
		} else {
			Properties props = SessionHelper.getProducerProperties();
			Producer<String, String> producer = new KafkaProducer<>(props);
			System.out.println("send to: " + topic + " value: " + message);
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, "clave",
					message);
			producer.send(producerRecord);
			producer.close();
			data.setText("");
		}
		
	}

}
