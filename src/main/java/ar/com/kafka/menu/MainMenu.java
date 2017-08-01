package ar.com.kafka.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import ar.com.kafka.dialog.OptionsDialog;
import ar.com.kafka.views.IndexView;

public class MainMenu  extends JMenuBar{

	
	//Where the GUI is created:
	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;

	public MainMenu(IndexView indexView) {
	
	
		menu = new JMenu("archivo");
		
		menuItem = new JMenuItem("Opciones");
		menu.add(menuItem);
		add(menu);
		OptionsDialog dialog = new OptionsDialog(indexView);
		
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

//				dialog.setContentPane(indexView);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				
			}
		});
		
	
	
	}
	
	
	
	
}