package ar.com.kafka.dialog;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ar.com.kafka.views.IndexView;

public class OptionsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] labels = { "bootstrap.servers", "acks", "retries", "batch.size", "linger.ms", "buffer.memory",
			"key.serializer", "value.serializer", "group.id", "enable.auto.commit", "auto.commit.interval.ms",
			"auto.offset.reset", "session.timeout.ms", "key.deserializer", "value.deserializer" };

	public OptionsDialog(IndexView indexView) {
		super(indexView, "algo", ModalityType.APPLICATION_MODAL);
		setLayout(new GridLayout(0, 2));
		Map<String, JTextField> map = new HashMap<String, JTextField>();
		for (int i = 0; i < labels.length; i++) {
			JTextField text = new JTextField();
			add(new JLabel(labels[i]));
			add(text);
			map.put(labels[i], text);
		}
	}
}
