package com.newgen.InsuranceClaim.worksteps;
/*------------------------------------------------------------------------------------------------------------------------
									NEWGEN SOFTWARE TECHNOLOGIES LIMITED
					Group                                                       :Application-Projects
					Project/Product                                             :RUBIX_Insurnace_Claim
					Application                                                 :RUBIX_Insurnace_Claim
					File Name                                                   :Check_Claim 
					Author                                                      :Dheeraj Kumar
					Date (DD/MM/YYYY)                                           :06/03/2022 
					Description                                                 :Introduction workstep
--------------------------------------------------------------------------------------------------------------------------
												CHANGE HISTORY
--------------------------------------------------------------------------------------------------------------------------
Problem No/CR No  	 				Change Date   					Changed By    					Change Description
--------------------------------------------------------------------------------------------------------------------------


--------------------------------------------------------------------------------------------------------------------------*/


import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.newgen.InsuranceClaim.fragments.DecisionRemarks;
import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.RubixLogger;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class Treasury implements IFormServerEventHandler {

	public static final String CLASS_NAME = "Check_Claim";

	public Treasury() {
		//consoleLogger = new CustomLogger();
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference formObject) {
		
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1, String arg2, String arg3, String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject, String operation, String data, String fragment) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(data);
			RubixLogger.LOGGER.info("fragment: "+fragment);
			RubixLogger.LOGGER.info("operation: "+operation);
				
			DecisionRemarks decRem = new DecisionRemarks();
			
			if("Decision Remarks".equalsIgnoreCase(fragment)){
				 if("setRemarksHistData".equalsIgnoreCase(operation)){
						return decRem.setRemarksHistData(formObject);
					}
				 if("loadDecisions".equalsIgnoreCase(operation)){
						return decRem.loadDecisions(formObject);
					}
			}

		} catch (ParseException e) {
			RubixLogger.LOGGER.info(e.getMessage());
		} 
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setMaskedValue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return arg1;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateHTML(EControl arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
