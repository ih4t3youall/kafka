package ar.com.kafka.dialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ar.com.kafka.helper.SessionHelper;
import ar.com.kafka.persistencia.Persistencia;
import ar.com.kafka.views.IndexView;

public class OptionsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] labels = { "bootstrap.servers", "acks", "retries", "batch.size", "linger.ms", "buffer.memory",
			"key.serializer", "value.serializer", "group.id", "enable.auto.commit", "auto.commit.interval.ms",
			"auto.offset.reset", "session.timeout.ms", "key.deserializer", "value.deserializer" };

	String[] defaultOptions = { "localhost:9092", "all", "0", "16384", "1", "102400",
			"org.apache.kafka.common.serialization.StringSerializer",
			"org.apache.kafka.common.serialization.StringSerializer", "group-1", "true", "1000", "latest", "3000",
			"org.apache.kafka.common.serialization.StringDeserializer",
			"org.apache.kafka.common.serialization.StringDeserializer" };
	Persistencia per = new Persistencia();

	public OptionsDialog(IndexView indexView) throws IOException {
		super(indexView, "algo", ModalityType.APPLICATION_MODAL);

		boolean exist = per.fileExist("map");
		int totalHeight = (20*defaultOptions.length) +20;
		setSize(900,totalHeight);
		setLocation(300, 300);
		setLayout(new GridLayout(0, 2));
		if (exist) {
			loadFile(per.loadSavedFile("map"));
		} else {
			loadDefault();
		}
		
		JButton aceptar = new JButton("Aceptar");
		add(aceptar);
		
		aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					per.save("map", SessionHelper.getOptions());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
				
			}
		});

	}

	private void loadDefault() throws IOException {

		Map<String, JTextField> map = SessionHelper.getOptions();

		for (int i = 0; i < defaultOptions.length; i++) {

			JTextField textField = new JTextField();
			textField.setText(defaultOptions[i]);
			map.put(labels[i], textField);

		}
		per.save("map", map);
		fillOptions(map);

	}

	private void loadFile(Object loadSavedFile) throws IOException {
		@SuppressWarnings("unchecked")
		Map<String,JTextField> hashmap= (Map<String,JTextField>)per.loadSavedFile("map");
		SessionHelper.setOptions(hashmap);
		fillOptions(hashmap);
	}
	
	private void fillOptions(Map<String, JTextField> map) {
		
		Set<String> keys = map.keySet();
		for (String key : keys) {
			
			add(new JLabel(" "+key));
			add(map.get(key));
			
		}
		
	}
	
	
	
}
