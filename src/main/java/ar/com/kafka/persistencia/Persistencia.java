package ar.com.kafka.persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Persistencia {

	private String userHome;

	public Persistencia() {
		userHome = System.getProperty("user.home") + "/" + "kafkaApp" + "/";

	}

	public void createCategory(String filename) throws IOException {

		File file = new File(userHome + filename);
		if (!fileExist(filename)) {
			file.createNewFile();
		}
	}
	
	
	public List<String> readLine(String fullpath) {
		
			List<String> toSend = new ArrayList<String>();
			System.out.println("reading line to line fullpath: "+fullpath);
		    try {
		      FileReader fr = new FileReader(fullpath);
		      BufferedReader br = new BufferedReader(fr);
		 
		      String linea;
		      while((linea = br.readLine()) != null)
		        toSend.add(linea);
		 
		      fr.close();
		    }
		    catch(Exception e) {
		      System.out.println("Exception on file: "+ fullpath + ": " + e);
		    }
		    System.out.println("read ok.");
		    return toSend;
		
	}

	public boolean fileExist(String filename) {
		System.out.println("absolute path: " + userHome + filename);
		return new File(userHome + filename).exists();

	}

	public String save(String fileName,Object toSave) throws IOException {

		System.out.println("Saving to: " + userHome +"/"+fileName);
		FileOutputStream fileOut;
		ObjectOutputStream obj_out = null;
		
		
		boolean exist = existHomeFolder();
		
		if(!exist)
			createFolder("");
		
		try {
			fileOut = new FileOutputStream(userHome + fileName);
			obj_out = new ObjectOutputStream(fileOut);
			obj_out.writeObject(toSave);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			obj_out.close();

		}

		return "200ok";
	}

	public Object loadSavedFile(String fileName) throws IOException {
		File file = new File(userHome + fileName);
		FileInputStream fin = new FileInputStream(file.getAbsolutePath());
		ObjectInputStream ois = new ObjectInputStream(fin);
		try {
			return  ois.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("TIRE EXCEPTION!!!");
			e.printStackTrace();
		} finally {
			ois.close();
		}

		return "";

	}

	public String[] listDirectory(String username) {
		System.out.println(userHome + "/" + username);
		return new File(userHome + "/" + username).list();

	}
	
	public boolean existHomeFolder() {
		
		return new File(userHome).exists();
		
	}

	public void deleteAndCreateFile(String filename) throws IOException {
		File file = new File(userHome + filename);
		file.delete();
		file.createNewFile();
	}

	/**
	 * 
	 * Delete a file using as base the userHome property
	 * 
	 * @param filename
	 *            the file path after the userHome
	 */
	public void deleteFile(String filename) {
		File file = new File(userHome + filename);
		file.delete();
	}

	public void createFolder(String path) {
		System.out.println("create folder: " + userHome + path);
		new File(userHome + path).mkdir();

	}

}