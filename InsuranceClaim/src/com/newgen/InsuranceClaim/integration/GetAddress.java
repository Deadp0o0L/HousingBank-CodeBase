package com.newgen.InsuranceClaim.integration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.RubixLogger;

public class GetAddress {

	/*
	 * Method for getting Address from External API
	 */
	public String getAddress(IFormReference formObject) {
		
		RubixLogger.LOGGER.info("JAVA | Inside getAddress Method...");
		
		String pin = (String) formObject.getValue("Q_ADDRESS_ZIPCode");
		RubixLogger.LOGGER.info("ZIPCode : " + pin);

		try {

		
			String url = "https://api.postalpincode.in/pincode/" + pin;
			RubixLogger.LOGGER.info("URL : " + url);
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
		
			RubixLogger.LOGGER.info("Connection Created");
			
			con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			con.setRequestProperty("Content-Type", "application/json");
			
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			
			con.connect();
			RubixLogger.LOGGER.info("Response Status : " + con.getResponseCode());
			
			if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {

				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));			
			
				String inputLine = in.readLine();
				StringBuffer response = new StringBuffer();
				response.append(inputLine);
			
				
				RubixLogger.LOGGER.info("The resposne : " + response.toString());
				
				//Parsing the response
				JSONArray array1 = new JSONArray(response.toString());			
				JSONObject object1 = array1.getJSONObject(0);
				JSONArray object2 = object1.getJSONArray("PostOffice");
				JSONObject object3 = object2.getJSONObject(0);
				
				RubixLogger.LOGGER.info("Country Name : " + (String) object3.get("Country"));
				RubixLogger.LOGGER.info("Region Name :" + (String) object3.get("Region"));
				RubixLogger.LOGGER.info("State Name : " + (String) object3.get("State"));			
				RubixLogger.LOGGER.info("District Name : " + (String) object3.get("District"));
				
				formObject.setValue("Q_ADDRESS_District", (String) object3.get("District"));
				formObject.setValue("Q_ADDRESS_Region", (String) object3.get("Region"));
			    formObject.setValue("Q_ADDRESS_State", (String) object3.get("State"));
				formObject.setValue("Q_ADDRESS_Country", (String) object3.get("Country"));
				
				in.close();
			}else {
				RubixLogger.LOGGER.info("Error in connecting the API");
			}
			

		

		} catch (Exception e) {
			
			RubixLogger.LOGGER.info(e.getMessage());
			RubixLogger.LOGGER.error("Error in the API - ", e);
			
		}
		
		return "";

	}
	
	
	
}
