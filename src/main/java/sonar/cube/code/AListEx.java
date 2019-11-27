package sonar.cube.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AListEx {

	private static final  Logger logger = LoggerFactory.getLogger(AListEx.class);
	
	private static void staticAccess() {
		logger.info("Static access");
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String s = null;
		if(s!=null) {
			int i=s.length();
			logger.info("Length of string"  +  i);
			}
		else {
			logger.info("Length of string is null");
		}
			
		
		
		AListEx.staticAccess();

	}

}
