package es.dani.logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	
	public static final int DEBUG = 0;
	public static final int INFO = 1;
	public static final int WARNING = 2;
	public static final int ERROR = 3;
	private int nivell;
	private String path = System.getProperty("user.home")+"//log";
	private File directori = new File(path);
	private File fitxerLog;
	private String escritura;
	private String textLog;
	private String fitxerDefecte = "//log";
	private String fitxerU = "";
	private String extensio = ".txt";
	private SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy hh:mm:ss"); 
	private boolean writeDebug = false;
	private boolean writeInfo = false;
	private boolean writeWarning = false;
	private boolean writeError = false;
		
	public Log() {
		crearDirectori();
		creacioFitxer();
		nivell=WARNING;
		escriureFitxer();
	}
	
	public Log(String fitxerUsuari) {
		crearDirectori();
		creacioFitxer(fitxerUsuari);
		nivell=WARNING;
		escriureFitxer();
	}
	
	public Log(String fitxerUsuari,int nivel) {
		crearDirectori();
		creacioFitxer(fitxerUsuari);
		if(nivel<0 || nivel>3) 
			nivell= 2;
		else 
			nivell = nivel;
	}
	
	public void escriureSegonsNivell(){
		switch (nivell) {
	            case 0:  writeDebug =true;writeInfo = true;writeWarning=true;writeError=true;
	                     break;
	            case 1:  writeInfo = true;writeWarning=true;writeError=true;
	                     break;
	            case 2:  writeWarning=true;writeError=true;
	                     break;
	            case 3:  writeError=true;
	                     break;
	        }	
	}
	
	public void debug(String missatge) {
		nivell = 0;
		escriureFitxer();
	}
	public void info(String missatge) {
		nivell = 1;
		escriureFitxer();
	}
	public void warning(String missatge) {
		nivell = 2;
		escriureFitxer();
	}
	public void error(String missatge) {
		nivell = 3;
		escriureFitxer();
	}
	
	public void setNivell(int nivel) {
		if(nivel<0 || nivel>3) 
			nivell= 2;
		else 
			nivell = nivel;
	}
	
	private void escriureFitxer() {
		Charset charset = Charset.forName("UTF-8");
		Path pEscritura = Paths.get(escritura);
		Date fecha = new Date();
		try (BufferedWriter writer = Files.newBufferedWriter(pEscritura, charset,StandardOpenOption.APPEND)) {
	        switch (nivell) {
	            case 0:  textLog = "[DEBUG] ";
	                     break;
	            case 1:  textLog = "[INFO] ";
	                     break;
	            case 2:  textLog = "[WARNING] ";
	                     break;
	            case 3:  textLog = "[ERROR] ";
	                     break;
	        }
	        writer.write(textLog+df.format(fecha)+"\n");
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		
	}
	
	private void creacioFitxer() {
		if(fitxerU.trim().isEmpty()) {
			fitxerLog = new File(path+fitxerDefecte+extensio);
			escritura = path+fitxerDefecte+extensio;
		}else {
			fitxerU = "//"+fitxerU.trim();
			if(fitxerU.indexOf('.')!=-1) {
				fitxerU = fitxerU.substring(fitxerU.indexOf('.'));
				fitxerLog = new File(path+fitxerU+extensio);
				escritura = path+fitxerU+extensio;
			}else {
				fitxerLog = new File(path+fitxerU+extensio);
				escritura = path+fitxerU+extensio;
			}
		}
		try {
			if(fitxerLog.createNewFile())
				System.out.println("S'ha creat el fitxer amb exit");
			else
				System.out.println("El fitxer ja existeix");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void creacioFitxer(String fitxerU) {
		if(fitxerU.trim().isEmpty()) {
			fitxerLog = new File(path+fitxerDefecte+extensio);
			escritura = path+fitxerDefecte+extensio;
		}else {
			fitxerU = "//"+fitxerU.trim();
			if(fitxerU.indexOf('.')!=-1) {
				fitxerU = fitxerU.substring(0,fitxerU.indexOf('.'));
				fitxerLog = new File(path+fitxerU+extensio);
				escritura = path+fitxerU+extensio;
			}else {
				fitxerLog = new File(path+fitxerU+extensio);
				escritura = path+fitxerU+extensio;
			}
		}
		try {
			if(fitxerLog.createNewFile())
				System.out.println("S'ha creat el fitxer amb exit");
			else
				System.out.println("El fitxer ja existeix");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void crearDirectori() {
		boolean creacio = false;
		if(comprovarExistenciaDirectori()) {
			try {
				directori.mkdir();
				creacio = true;
			}catch(SecurityException se) {
				System.out.println(se.getMessage());
			}
			if(creacio)
				System.out.println("S'ha creat el directori log");
			
		}else {
			System.out.println("El directori ja existeix");
		}
	}
	
	private boolean comprovarExistenciaDirectori() {
		
		if(!directori.exists()) {
			return true;
		}else {
			return false;
		}
	}	

}

//nivell + missatge +data hora
