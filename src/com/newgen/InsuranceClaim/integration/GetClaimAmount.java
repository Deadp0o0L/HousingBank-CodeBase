package com.newgen.InsuranceClaim.integration;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.RubixLogger;

public class GetClaimAmount {
	

	public String calculateAmount(IFormReference formObject) {
		RubixLogger.LOGGER.info("JAVA | Inside calculateAmount Method");
		
		
		String cash = (String) formObject.getValue("Q_REIMB_AMT_TotCashAmt");
		String card = (String) formObject.getValue("Q_REIMB_AMT_TotCrdDebAmt");
		String upi = (String) formObject.getValue("Q_REIMB_AMT_TotNetBankUPIAmt");
		
		//Intitalize empty fields to zero in order to avoid NumberFormatException
		if("".equalsIgnoreCase(upi) || upi.isEmpty()) {
			upi = "0";
		}
		if("".equalsIgnoreCase(card) || card.isEmpty()) {
			card = "0";
		}
		if("".equalsIgnoreCase(cash) || cash.isEmpty()) {
			cash = "0";
		}
		
		RubixLogger.LOGGER.info("Cash : " + cash + " | " + "Card : " + card + " | " + "UPI : " + upi);
		
		int totalAmount = 0;
		try {
			totalAmount = Integer.parseInt(card+"") + Integer.parseInt(cash+"") + Integer.parseInt(upi+"");
		}catch (NumberFormatException  nfe) {
			RubixLogger.LOGGER.info("Number Format Exception !");
		}
		
		RubixLogger.LOGGER.info("Total Amount : " + totalAmount);
		
		// Check needed to be done on basis of relation type
		String relation = formObject.getValue("Q_HEALTH_INS_DETAILS_InsuranceType").toString();
		double claimPercent = 0.0;
		switch (relation){
			
			case "Myself" : claimPercent = 0.90;
			break;
			case "Parents" : claimPercent = 0.80;
				break;
			case"Child" : claimPercent = 0.70;
				break;
			case "Spouse" : claimPercent = 0.70;
				break;
		}
		
		formObject.setValue("Q_REIMB_AMT_RelAndClaim", relation +" | " + claimPercent*100 + "%");
		
		if(claimPercent == 0.0) {
			formObject.setStyle("ERRORMSG", "visible", "true");
			formObject.setValue("ERRORMSG", "Please select the relation type..." );
		}
		
		int RemAmount = (int) (totalAmount * claimPercent) ;
		
		formObject.setValue("Q_REIMB_AMT_TotClaimAmt", totalAmount+"");
		formObject.setValue("Q_REIMB_AMT_TotReimbAmt", RemAmount+"");
		
		return "";
	}
	
		
}
