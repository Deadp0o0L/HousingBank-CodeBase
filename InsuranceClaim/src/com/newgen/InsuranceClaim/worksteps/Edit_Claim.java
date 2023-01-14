package com.newgen.InsuranceClaim.worksteps;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;

import com.newgen.InsuranceClaim.fragments.CustomValidations;
import com.newgen.InsuranceClaim.fragments.DecisionRemarks;
import com.newgen.InsuranceClaim.integration.GetAddress;
import com.newgen.InsuranceClaim.integration.GetClaimAmount;
import com.newgen.InsuranceClaim.integration.GetClaimData;
import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.RubixLogger;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class Edit_Claim implements IFormServerEventHandler {

	public Edit_Claim() {
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
//			JSONParser parser = new JSONParser();
//			JSONObject jsonObject = (JSONObject) parser.parse(data);
			RubixLogger.LOGGER.info("fragment: "+fragment);
			RubixLogger.LOGGER.info("operation: "+operation);
			
			
			
			RubixLogger.LOGGER.info("Inside Service Executor Function");
			
			GetAddress address = new GetAddress();
			GetClaimData claim = new GetClaimData();
			GetClaimAmount amt = new GetClaimAmount();
			DecisionRemarks decRem = new DecisionRemarks();
			CustomValidations cv = new CustomValidations();
			
			if("Decision Remarks".equalsIgnoreCase(fragment)){
				 if("setRemarksHistData".equalsIgnoreCase(operation)){
						return decRem.setRemarksHistData(formObject);
					}
				 if("loadDecisions".equalsIgnoreCase(operation)){
						return decRem.loadDecisions(formObject);
					}
			}
			
			if("ServiceExecutor".equalsIgnoreCase(fragment)) {				
				if("getAddress".equalsIgnoreCase(operation)) {
					return address.getAddress(formObject);
				}
				if("getClaimData".equalsIgnoreCase(operation)) {
					return claim.getClaimData(formObject);
				}
				if("calculateAmount".equalsIgnoreCase(operation)) {
					return amt.calculateAmount(formObject);
				}
				
				
				
			}
			
			if("CustomValidations".equalsIgnoreCase(fragment)) {				
				if("onChangeDocumentCategory".equalsIgnoreCase(operation)) {
					return cv.onChangeDocumentCategory(formObject);
				}
				if("onChangeProductType".equalsIgnoreCase(operation)) {
					return cv.onChangeProductType(formObject);
				}
				if("getJSONDataFromDB".equalsIgnoreCase(operation)) {
					return cv.getJSONDataFromDB(formObject);
				}
				if("getCutOffStatus".equalsIgnoreCase(operation)) {
					return cv.getCutOffStatus(formObject);
				}
				if("digitsToWords".equalsIgnoreCase(operation)) {
					return cv.digitsToWords(formObject);
				}
				
			}
						

		} catch (Exception e) {
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
