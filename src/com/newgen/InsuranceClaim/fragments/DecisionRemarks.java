package com.newgen.InsuranceClaim.fragments;

import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.RubixLogger;

public class DecisionRemarks {

	public DecisionRemarks() {
		RubixLogger.LOGGER.info("Inside the Decision Remarks Class");
	}
	

	public String loadDecisions(IFormReference formObject) {
		
		RubixLogger.LOGGER.info("JAVA | Inside loadDecisions method");
		
		try {
			String activityName = formObject.getActivityName();
			RubixLogger.LOGGER.info("activity name: " + activityName);

			// Clear the Decsion dropdown and Remarks fields on form load
			formObject.setValue("DECISION", "");
			formObject.setValue("REMARKS", "");

			// Populate the decision dropdown on the basis of Worksteps
			fetchDecisions(formObject, activityName);

		} catch (Exception e) {
			RubixLogger.LOGGER.info("exception : " + e.getMessage());
		}
		return "";
	}

	public void fetchDecisions(IFormReference formObject, String activityName) {
		
		RubixLogger.LOGGER.info("JAVA | Inside fetchDecisions method");
		
		
		try {
			String fetchDecisionQuery = "SELECT ACTIONS FROM RUBIX_DECISION_MSTR WHERE WORKSTEP_NAME='" + activityName
					+ "'";
			RubixLogger.LOGGER.info("fetch decision query: " + fetchDecisionQuery);

			@SuppressWarnings("unchecked")
			List<List<String>> resultSet = formObject.getDataFromDB(fetchDecisionQuery);
			RubixLogger.LOGGER.info("fetch decision result set: " + resultSet.toString());

			if (!resultSet.isEmpty()) {
				formObject.clearCombo("DECISION");
				for (List<String> list : resultSet) {
					formObject.addItemInCombo("DECISION", list.get(0), list.get(0));
				}
				formObject.setValue("DECISION", "");
			}

		} catch (Exception e) {
			RubixLogger.LOGGER.info("exception : " + e.getMessage());
		}
	}

	public String saveVariables(IFormReference formObject) {
		try {
			RubixLogger.LOGGER.info("JAVA | Inside saveVariables method");

			String custID = (String) formObject.getValue("Q_PROP_DETAILS_CustIDNumber");
			String claimNum = (String) formObject.getValue("Q_PROP_DETAILS_ClaimNumber");
			String winame = formObject.getObjGeneralData().getM_strProcessInstanceId();
			String username = formObject.getUserName();
			String relation_type = (String) formObject.getValue("Q_HEALTH_INS_DETAILS_InsuranceType");

			RubixLogger.LOGGER.info("User Name is" + username);
			RubixLogger.LOGGER.info("Winame : " + winame);

			String saveQuery = "update wfinstrumenttable set VAR_STR1='" + claimNum + "',  VAR_STR2='" + custID
					+ "', VAR_STR3='"+ username +"', VAR_STR4='"+ relation_type  +"'  where PROCESSINSTANCEID='" + winame + "'";

			RubixLogger.LOGGER.info("Save Query: " + saveQuery);
			formObject.saveDataInDB(saveQuery);
		} catch (Exception e) {
			RubixLogger.LOGGER.info("Exception while saving branch code:" + e.getMessage());
		}

		return "";
	}

	@SuppressWarnings("unchecked")
	public String setRemarksHistData(IFormReference formObject) {
		RubixLogger.LOGGER.info("JAVA | Inside setRemarksHistData");
		
		try {

			String activityName = formObject.getActivityName();

			// Save the VAR_STR variables
			if ("Initiate_Claim".equalsIgnoreCase(activityName) || "Edit_Request".equalsIgnoreCase(activityName)) {
				saveVariables(formObject);
			}

			String decision = String.valueOf(formObject.getValue("DECISION"));
			String remarks = String.valueOf(formObject.getValue("REMARKS"));
			String claimNum = String.valueOf(formObject.getValue("Q_PROP_DETAILS_ClaimNumber"));
			
			String userName = formObject.getUserName();

			RubixLogger.LOGGER.info("[DecisionRemarks] [setRemarksHistData] activityName : " + activityName);
			RubixLogger.LOGGER.info("[DecisionRemarks] [setRemarksHistData] decision : " + decision);
			RubixLogger.LOGGER.info("[DecisionRemarks] [setRemarksHistData] remarks : " + remarks);
			RubixLogger.LOGGER.info("[DecisionRemarks] [setRemarksHistData] userName : " + userName);

			JSONArray array = new JSONArray();
			JSONObject row = new JSONObject();
			row.put("Workstep Name", activityName);
			row.put("User Name", userName);
			row.put("Claim Number", claimNum);
			row.put("Decision", decision);
			row.put("Remarks", remarks);

			array.add(row);
			formObject.addDataToGrid("AUDIT_GRID", array);

		} catch (Exception e) {
			RubixLogger.LOGGER.info("[DecisionRemarks] [fetchDecisions] error : " + e.getMessage());
		}
		return "";
	}

}
