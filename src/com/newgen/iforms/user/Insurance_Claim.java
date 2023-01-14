package com.newgen.iforms.user;

import com.newgen.InsuranceClaim.worksteps.Approve_Claim;
import com.newgen.InsuranceClaim.worksteps.CMO;
import com.newgen.InsuranceClaim.worksteps.Check_Claim;
import com.newgen.InsuranceClaim.worksteps.Initiate_Claim;
import com.newgen.InsuranceClaim.worksteps.Scrutiny;
import com.newgen.InsuranceClaim.worksteps.Treasury;
import com.newgen.iforms.custom.IFormListenerFactory;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;

public class Insurance_Claim implements IFormListenerFactory {
	
	public static final String CLASS_NAME = "Insurance_Claim";
	
	public Insurance_Claim() {

		RubixLogger.LOGGER.info(" ---------- Insurance Claim Process | Insurance Claim Class ----------");
		System.out.println("*************** Insurance Claim | Insurance Claim Process | Insurance Claim Class");
	}
	


	@Override
	public IFormServerEventHandler getClassInstance(IFormReference iformObj) {
		
		String processName = iformObj.getProcessName();
		String activityName = iformObj.getActivityName();
		
		RubixLogger RL = new RubixLogger();
		RL.setLogger(iformObj);
		
		if ("Insurance_Claim".equalsIgnoreCase(processName)) {
			switch (activityName) { 

			case "Initiate_Claim":
				return new Initiate_Claim();

			case "Check_Claim":
				return new Check_Claim();
				
			case "CMO":
			return new CMO();
			

			case "Treasury":
				return new Treasury();
				
			case "Approve_Claim":
				return new Approve_Claim();
				
			case "Scrutiny":
				return new Scrutiny();
			
			default:
				return null;
			}
		}
		return null;
	}

}
