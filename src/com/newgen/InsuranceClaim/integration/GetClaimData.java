package com.newgen.InsuranceClaim.integration;

import java.util.List;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.RubixLogger;

public class GetClaimData {

	
	public String getClaimData(IFormReference formObject) {
		RubixLogger.LOGGER.info("JAVA | Inside getClaimData Method");
		
		String custID = (String) formObject.getValue("Q_PROP_DETAILS_CustIDNumber");
		RubixLogger.LOGGER.info("The customer id : " + custID);
		
		/*
		 * 1. get the cust id from form
		 * 2. fetch values on the basis of custid
		 * 3. set the values into the form
		 */
		
		String claimNo = "";
		String prodNo = "";
		String policyNo = "";
		String gender = "";
		String initDate = "";
		String hospitalName="";
		String hospitalAdd = "";
		String diagnosis = "";
		String patientName = "";
		

		String custQuery = "select CLAIMNUMBER,PRODUCTNUMBER, POLNUM_ADVRECPTNUM ,GENDER,DATEOFINTIMATION,HOSPITALNAME , HOSPITALADD, DIAGNOSIS , PATIENTNAME  "
				+ "from RUBIX_CORE WHERE CUSTIDNUMBER='"+custID+"'";
		
		@SuppressWarnings("unchecked")
		List<List<String>> resultSetClaim = formObject.getDataFromDB(custQuery);
		RubixLogger.LOGGER.info("Fetch Employee  result set: "+ resultSetClaim.toString());
		if (!resultSetClaim.isEmpty()) {

			for (List<String> list1 : resultSetClaim) {

				claimNo = list1.get(0);
				prodNo =  list1.get(1);		
				policyNo= list1.get(2);
				gender= list1.get(3);
				initDate= list1.get(4);
				hospitalName= list1.get(5);
				hospitalAdd= list1.get(6);
				diagnosis= list1.get(7);
				patientName= list1.get(8);

			}
		}
		
		formObject.setValue("Q_PROP_DETAILS_ClaimNumber", claimNo);
		formObject.setValue("Q_PROP_DETAILS_ProductNumber", prodNo);
		formObject.setValue("Q_PROP_DETAILS_PolNum_AdvRecptNum", policyNo);
		formObject.setValue("Q_PROP_DETAILS_Gender", gender);
		formObject.setValue("Q_PROP_DETAILS_DateOfIntimation", initDate);
		formObject.setValue("Q_PROP_DETAILS_HospitalName", hospitalName);
		formObject.setValue("Q_PROP_DETAILS_HospitalAdd", hospitalAdd);
		formObject.setValue("Q_PROP_DETAILS_Diagnosis", diagnosis);
		formObject.setValue("Q_PROP_DETAILS_PatientName", patientName);
		
		RubixLogger.LOGGER.info("Exited the GetClaimData Method");
		
		return "";
		
	}
	
	
}
