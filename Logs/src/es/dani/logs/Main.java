package es.dani.logs;

public class Main {

	public static void main(String[] args) {
		Log log= new Log("logDani.html", Log.WARNING);
		log.debug("debug");
		log.info("info");
		log.warning("warning");
		log.error("error");

	}

}
