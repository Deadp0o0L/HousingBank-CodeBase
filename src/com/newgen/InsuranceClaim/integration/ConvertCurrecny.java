package com.newgen.InsuranceClaim.integration;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.RubixLogger;

public class ConvertCurrecny {
	
	
	public void convertToCUrrency() {
		
	}

	public String convertToCurrecny(IFormReference iformObj) {
		RubixLogger RL = new RubixLogger();
		RL.setLogger(iformObj);
		
		String toCurr = "USD";
		String fromCurr = "INR";
		
		String amount = "10000";						
		String convertedAmount = conversion(toCurr,fromCurr, amount,iformObj);
		
		RubixLogger.LOGGER.info("Converted Amount Response: " + convertedAmount);
					
		return null;
	}
	
	
	public String conversion(String to, String from, String amount, IFormReference iformObj) {
		RubixLogger RL = new RubixLogger();
		RL.setLogger(iformObj);	
		
		RubixLogger.LOGGER.info("Inside the Currecny Conversion Method");
		
		String convertedAmount="456123";
		
		try {
			
			String currURL = "https://api.apilayer.com/exchangerates_data/convert?to=USD&from=INR&amount=1000000";
			URL url = new URL(currURL);			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("apikey", "P58XL0xLGg4qNrZ26gmfwBV5F6lRhUj8");			
			
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				BufferedReader inn = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				String inputLine = inn.readLine();
				StringBuffer response = new StringBuffer();
				response.append(inputLine);
				
				RubixLogger.LOGGER.info("Response of the Currecny Convertor API : " + response);
				
				System.out.println("Response of the Currecny Conversion : " + response);
				
			}
			
			
		} catch (Exception e) {
			
		}
		
		return convertedAmount;
	}
	
	
	
	
	
}
