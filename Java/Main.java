package sangjin;

import org.apache.log4j.Logger;

public class Main {
	
	public static Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		System.out.println(Main.class.getName());
		System.out.println("Server On!");
		
		logger.fatal("log4j:logger.fatal()");
		logger.error("log4j:logger.error()");
		logger.warn("log4j:logger.warn()");
		logger.info("log4j:logger.info()");
		logger.debug("log4j:logger.debug()");
		logger.trace("log4j:logger.trace()");  
		
		 

	}

}
