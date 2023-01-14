package com.newgen.iforms.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.newgen.iforms.custom.IFormListenerFactory;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;

public class RubixLogger implements IFormListenerFactory {
	
	private static String loggerName = "Insurance";
	public static final Logger LOGGER = Logger.getLogger(loggerName);

	@Override
	public IFormServerEventHandler getClassInstance(IFormReference iformObj) {

		setLogger(iformObj);

		return null;
	}

	public void setLogger(IFormReference iformObject) {

		try {

			String winame = iformObject.getObjGeneralData().getM_strProcessInstanceId();

			// Specifiying the folder and filename for the loggers			
			String logfolderPath = System.getProperty("user.dir") + File.separator + "logs" + File.separator
					+ "RubiX_CustomLogs";
			String logFileName = winame + ".log";

			String finalLogPath = logfolderPath + File.separator + logFileName;
			
			System.out.println("*************** Insurance Claim | Final Log Path : " + finalLogPath);

			// Reading the external Log4j Properties file
			Properties prop = new Properties();
			prop.load(new FileInputStream(System.getProperty("user.dir") + File.separator + "bin" + File.separator
					+ "InsurcanceConfig" + File.separator + "Insurance_Logger.properties"));
			
			System.out.println("*************** Insurance Claim | Property file path : " + System.getProperty("user.dir") + File.separator + "bin" + File.separator
					+ "InsurcanceConfig" + File.separator + "Insurance_Logger.properties");
			
			File logFile = new File(logfolderPath);
			logFile.mkdirs();
			
			File file = new File(finalLogPath);
			
			if(file.createNewFile()) {
				LOGGER.info("*****Log file created successfully");
			}else {
				LOGGER.info("*****Updating Log File");
			}
			
			prop.put("log4j.appender."+loggerName+".File", finalLogPath);
			
			//Read Log4j from external source
			BasicConfigurator.resetConfiguration();
			PropertyConfigurator.configure(prop);
			
		} catch (Exception e) {
			printException(e);
		}

	}
	
	
	public static void printException(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exception = sw.toString();
		System.out.println("Exception is " + exception);
	}
}
