package ar.com.kafka.helper;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;

public class SessionHelper {

	private static  Map<String, JTextField> options ;
	
	public Map<String,JTextField> getOptions(){
		
		if(options == null) {
			
			options = new HashMap<String,JTextField>();
			return options;
			
		}
		
		return options;
		
		
	}
	
	
	
	
	
}
