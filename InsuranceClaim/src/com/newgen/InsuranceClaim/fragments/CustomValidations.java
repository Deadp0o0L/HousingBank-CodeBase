package com.newgen.InsuranceClaim.fragments;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/*------------------------------------------------------------------------------------------------------------------------
 NEWGEN SOFTWARE TECHNOLOGIES LIMITED
 Group                                                       :Application-Projects
 Project/Product                                             :CreditClearance
 Application                                                 :CreditClearance
 Module                                                      :HBTF-CreditClearance
 File Name                                                   :DecisionRemarks 
 Author                                                      :Karanpreet
 Date (DD/MM/YYYY)                                           :31/03/2021 
 Description                                                 :DecisionRemarks
 --------------------------------------------------------------------------------------------------------------------------
 CHANGE HISTORY
 --------------------------------------------------------------------------------------------------------------------------
 Problem No/CR No  	 Change Date   		Changed By    		Change Description
 --------------------------------------------------------------------------------------------------------------------------

 --------------------------------------------------------------------------------------------------------------------------*/

import org.json.JSONArray;
import org.json.JSONObject;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.RubixLogger;

public class CustomValidations {
		
	
	// Configuration of the logs based on the WINAME | 24th Aug 2022	
	public CustomValidations()  {
				
		RubixLogger.LOGGER.info("Inside the Custom Validation Class");

	}
	
	// End of Code
	

	// Sudhanshu GIB -- Start

	@SuppressWarnings("unchecked")
	public String onChangeProductType(IFormReference formObject) {

		String prodType = formObject.getValue("Q_PRODUCT_INFO_Product_Type").toString();

		try {

			String prodQuery = "SELECT DISTINCT DOCUMENT_CATEGORY FROM RUBIX_TRADE_FINANCE WHERE PRODUCT_CLASS='"
					+ prodType + "'";

			RubixLogger.LOGGER.info("Product Type Query : " + prodQuery);

			List<List<String>> resultSet = formObject.getDataFromDB(prodQuery);
			RubixLogger.LOGGER.info("Product Type Result set: " + resultSet.toString());

			if (!resultSet.isEmpty()) {

				formObject.clearCombo("Q_PRODUCT_INFO_Document_Category");
				for (List<String> list : resultSet) {
					formObject.addItemInCombo("Q_PRODUCT_INFO_Document_Category", list.get(0), list.get(0));
				}
				formObject.setValue("Q_PRODUCT_INFO_Document_Category", "");
				formObject.setValue("Q_PRODUCT_INFO_Document_Type", "");
			}

		} catch (Exception e) {
			RubixLogger.LOGGER.info("Error in onChangeProductType Function");
			RubixLogger.LOGGER.info(e.getMessage());
		}

		return "";
	}

	@SuppressWarnings("unchecked")
	public String onChangeDocumentCategory(IFormReference formObject) {

		String prodType = formObject.getValue("Q_PRODUCT_INFO_Product_Type").toString();
		String docCateogry = formObject.getValue("Q_PRODUCT_INFO_Document_Category").toString();

		try {

			String docQuery = "SELECT DISTINCT DOCUMENT_TYPE FROM RUBIX_TRADE_FINANCE WHERE PRODUCT_CLASS='" + prodType
					+ "' AND DOCUMENT_CATEGORY='" + docCateogry + "'";

			RubixLogger.LOGGER.info("Document Category Query : " + docQuery);

			List<List<String>> resultSet = formObject.getDataFromDB(docQuery);
			RubixLogger.LOGGER.info("Document Category Result set: " + resultSet.toString());

			if (!resultSet.isEmpty()) {

				formObject.clearCombo("Q_PRODUCT_INFO_Document_Type");
				for (List<String> list : resultSet) {
					formObject.addItemInCombo("Q_PRODUCT_INFO_Document_Type", list.get(0), list.get(0));
				}
				formObject.setValue("Q_PRODUCT_INFO_Document_Type", "");
			}

		} catch (Exception e) {
			RubixLogger.LOGGER.info("Error in onChangeDocumentCategory Function");
			RubixLogger.LOGGER.info(e.getMessage());
		}

		return "";
	}

	// Sudhanshu GIB -- End

	// Bhawna - JSON Test -- Start

	@SuppressWarnings("unchecked")
	public String getJSONDataFromDB(IFormReference iformObject) {
		
		RubixLogger.LOGGER.info("JAVA | Inside the getJSONDataFromDB");
		
		try {
			
			String jsonQuery = "SELECT RESPONSE FROM RUBIX_JSON_TEST WHERE WINAME = 'Insurance-000000000057-Claim'";
			
			List<List<String>> resultSet = iformObject.getDataFromDB(jsonQuery);
			
			RubixLogger.LOGGER.info("JSON Response : " + resultSet.toString());
			
			String response = "Response Not Received!";
			
			if(!resultSet.isEmpty()) {
				for(List<String> list : resultSet) {
					response = list.get(0);
				}
			}
			
			JSONObject myObj = new JSONObject(response);
			
			JSONArray arr1 = myObj.getJSONArray("Response");
			JSONObject obj = arr1.getJSONObject(0);
			
			JSONArray arr2 = obj.getJSONArray("Signatories");
			
			
			JSONObject obj2 = arr2.getJSONObject(0);
			
			String remarks = obj2.getString("Remarks");
			RubixLogger.LOGGER.info("Remarks from Response : " + remarks);
			
			String email = obj2.getString("EmailID");
			
			RubixLogger.LOGGER.info("The JSON Response : " + email);
			
			iformObject.clearCombo("Q_JSON_SIGNER_STATUS");
			
			for(int i=0; i<arr2.length(); i++) {
				
				iformObject.addItemInCombo("Q_JSON_SIGNER_STATUS", arr2.getJSONObject(i).getString("Name") + "----" + arr2.getJSONObject(i).getString("Status"), arr2.getJSONObject(i).getString("Name") + "----" + arr2.getJSONObject(i).getString("Status"));
				RubixLogger.LOGGER.info("The " + i + "th Singnatories : " + arr2.getJSONObject(i).getString("Name") + "----" + arr2.getJSONObject(i).getString("Status"));
			}
			
			
			iformObject.setValue("Q_JSON_SIGNER_STATUS", email);
			
		} catch (Exception e) {
			RubixLogger.LOGGER.info(e.getMessage());
		}
		
		
		return "";
	}

	// Bhawna - JSON Test -- End
	
	
	// Add Multi-Language Feature in the IFrom
	@SuppressWarnings("unchecked")
	public String fetchDropdowns(IFormReference formObject) {
		RubixLogger.LOGGER.info("Inside loadDropdownInArabic function");
		
		String langSelected = String.valueOf(formObject.getValue("LANG"));
		
		RubixLogger.LOGGER.info("Language Selected : " + langSelected);
		
		String colName = "";
		if ("ARB".equalsIgnoreCase(langSelected)) {
			colName = "ARB";
		} else {
			colName = "ENG";
		}	
				
		String fetchDropdown = "SELECT ID, RELATION_" + colName + " FROM RUBIX_RELATION_MSTR";
		
		RubixLogger.LOGGER.info("Query : " + fetchDropdown);
		
		String relType = formObject.getValue("Q_HEALTH_INS_DETAILS_InsuranceType").toString();		
		RubixLogger.LOGGER.info("Relation Type : " + relType);
		
		String relQuery = "SELECT DISTINCT RELATION_" + colName + " FROM RUBIX_RELATION_MSTR WHERE ID='" + relType + "'";
		RubixLogger.LOGGER.info("Relation Query : " + relQuery);
		String relationValue="";
		@SuppressWarnings("unchecked")
		List<List<String>> resultSetDropdown = formObject.getDataFromDB(relQuery);
		RubixLogger.LOGGER.info("set relation type dropdown result set: " + resultSetDropdown.toString());
		if (!resultSetDropdown.isEmpty()) {

			for (List<String> list1 : resultSetDropdown) {
				relationValue = list1.get(0);
			}
		}
		
		
		List<List<String>> resultSet = formObject.getDataFromDB(fetchDropdown);
		RubixLogger.LOGGER.info("Result Set for  relQuery: " + resultSet.toString());
		
		if(!resultSet.isEmpty()) {
			formObject.clearCombo("Q_HEALTH_INS_DETAILS_InsuranceType");
			for (List<String> list : resultSet) {
				formObject.addItemInCombo("Q_HEALTH_INS_DETAILS_InsuranceType", list.get(1), list.get(0));
			}
			formObject.setValue("Q_HEALTH_INS_DETAILS_InsuranceType", relType);
		}
				
		
		
		return "";
	}
	
	
	//Hold Cut-off Time
	
	public String getCutOffStatus(IFormReference formObject) {
		
		String currTime = (String) formObject.getValue("CurrentDateTime");
		RubixLogger.LOGGER.info("The current time is : " + currTime);
		
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR);
		int min = now.get(Calendar.MINUTE);
		
		RubixLogger.LOGGER.info("HOUR : " + hour + " | MINUTE : " + min);
		
		String cutOffTime = formObject.getValue("CUTOFF_TIME").toString();
		RubixLogger.LOGGER.info("Cut-Off Time : " + cutOffTime);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss", Locale.ENGLISH);
		try {
			cal.setTime(sdf.parse(cutOffTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(hour <= cal.get(Calendar.HOUR) && min <= cal.get(Calendar.MINUTE) ) {		
			formObject.setValue("CUTOFF_STATUS", "N");
		}else {
			formObject.setValue("CUTOFF_STATUS", "Y");
		}
		
		
		return "";
	}
	
	//Hold Cut-off Time
	
	public String digitsToWords(IFormReference formObject) {
		
		String amount = formObject.getValue("Q_REIMB_AMT_TotReimbAmt").toString();
		
		RubixLogger.LOGGER.info("Reimbursment Amount : " + amount);
		
		try {
			
			RubixLogger.LOGGER.info("JAVA | Inside digitToWords Method");

			ProcessBuilder builder = new ProcessBuilder("py","C:\\Program Files\\IBM\\WebSphere\\AppServer\\profiles\\AppSrv02\\pythonScripts\\python_script.py",  "" + amount);
			Process process = builder.start();
						
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String result = in.readLine();
			RubixLogger.LOGGER.info("Amount In Words : " + result);
			formObject.setValue("Q_REIMB_AMT_AmountInWords", result);
			
		
			if(result == null) {
				formObject.setValue("Q_REIMB_AMT_AmountInWords", "Error in coversion!");
			}
			
			RubixLogger.LOGGER.info("Result : " + result);
			
		} catch (Exception e) {
			RubixLogger.LOGGER.info(e.getMessage());
		}
		
		
		return "";
	}
	
}
