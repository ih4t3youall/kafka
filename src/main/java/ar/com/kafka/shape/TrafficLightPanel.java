package ar.com.kafka.shape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TrafficLightPanel extends JPanel{
//	private JLabel changeColor;
	private JButton red, green, yellow;
	private Circle light;
	
	public TrafficLightPanel(){
		light = new Circle(20, Color.red, 0, 0);
//		changeColor = new JLabel("Change color to: ");
		
//		add(changeColor);
//		add(red);
//		add(yellow);
//		add(green);
		
		setPreferredSize (new Dimension(20, 20));
		setBackground (Color.gray);
	}
	public void paintComponent(Graphics page){
		super.paintComponent(page);
		
//		page.setColor(Color.black);
//		page.fillRect(150, 50, 20, 40);
		
		/*page.setColor(Color.red);
		page.fillOval(150, 60, 20, 20);*/
		
		light.draw(page);
		
		
	}
	public void changeColorRed(){
			light.setColor(Color.red);
			repaint();
	}
	public void changeColorGreen(){
			light.setColor(Color.green);
			repaint();
	}
}