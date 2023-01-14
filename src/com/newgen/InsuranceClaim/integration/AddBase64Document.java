package com.newgen.InsuranceClaim.integration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.RubixLogger;

public class AddBase64Document {

	public String addDocument() {

		String fileName = "CustomerSignature";
		String fileExtns = "pdf";
		String filePath = "D:\\CustomerSignature.pdf";
		String TARGET_URL = "http://192.168.153.216:9081/" + "OmniDocsRestWS/rest/services/addDocument";
		
		nGOAddDocumentService(TARGET_URL,"hbtfdevnew","1","14634",filePath,
	            fileName,fileExtns,"supervisor","supervisor351","Testing NGOAddDocument Call");

		try {

		} catch (Exception e) {
			RubixLogger.LOGGER.info("Error : " + e.getMessage());
		}

		return "";
	}

	public static String nGOAddDocumentService(String url, String cabName, String volId, String folderIndex,
			String documentPath, String documentName, String fileAppType, String userName, String passWord,
			String comments) {
		String returnMessage = "";
		try {
			String inputXML = "<NGOAddDocumentBDO>" + "<cabinetName>" + cabName + "</cabinetName>" + "<folderIndex>"
					+ folderIndex + "</folderIndex>" + "<documentName>" + documentName + "</documentName>"
					+ "<userDBId></userDBId>" + "<volumeId>" + volId + "</volumeId>" + "<accessType>S</accessType>"
					+ "<createdByAppName>" + fileAppType + "</createdByAppName>" + "<enableLog>Y</enableLog>"
					+ "<FTSFlag>PP</FTSFlag>" + "<userName>" + userName + "</userName>" + "<userPassword>" + passWord
					+ "</userPassword>" + "<comment>" + comments + "</comment>" + "</NGOAddDocumentBDO>";
			
			RubixLogger.LOGGER.info(inputXML);
			
			String response = sendAddDocuemntRequest(inputXML, documentPath, url, documentName, false);
			RubixLogger.LOGGER.info("Got Response:" + response);
			
		} catch (Exception ex) {
			System.err.println("Error occurred while sending REST Request to Server");
			returnMessage = "-9999~" + ex.toString();
			System.out.println("nGOAddDocumentService exception " + ex.toString());
			RubixLogger.LOGGER.info("Exception in nGOAddDocumentService" + ex);
		}
		return returnMessage;
	}

	public static String sendAddDocuemntRequest(String request, String documentPath, String TARGET_URL,
			String documentName, boolean isBase64Upload) {
		
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost uploadFile = new HttpPost(TARGET_URL);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("NGOAddDocumentBDO", request, ContentType.TEXT_PLAIN);
		
		try {
			File file = new File(documentPath);
			builder.addBinaryBody("file", new FileInputStream(file), ContentType.APPLICATION_OCTET_STREAM,
					documentName);
			HttpEntity multipart = builder.build();
			uploadFile.setEntity(multipart);
			CloseableHttpResponse response = httpClient.execute(uploadFile);
			String responseStr = EntityUtils.toString(response.getEntity());
			return responseStr;
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getClaimData(IFormReference formObject) {
		RubixLogger.LOGGER.info("JAVA | Inside ConvertToBase64 Method");

		// Call the Base64 Method
		File f = new File("C:\\Users\\dheerajkumar\\Pictures\\Logo.png");
		String encodstring = encodeFileToBase64Binary(f);
		System.out.println(encodstring);

		RubixLogger.LOGGER.info("Exited the ConvertToBase64 Method");

		return "";

	}

	public String attachWI(IFormReference formObject) {
		RubixLogger.LOGGER.info("JAVA | Inside AttachWI Method");

		String base64code = "";
		String base64Query = "";

		@SuppressWarnings("unchecked")
		List<List<String>> resultSet64 = formObject.getDataFromDB(base64Query);
		RubixLogger.LOGGER.info("Fetch Employee  result set: " + resultSet64.toString());
		if (!resultSet64.isEmpty()) {

			for (List<String> list1 : resultSet64) {
				base64code = list1.get(0);
			}
		}

		return "";
	}

	private static String encodeFileToBase64Binary(File file) {
		String encodedFile = null;
		try {

			@SuppressWarnings("resource")
			FileInputStream fileInputStreamReader = new FileInputStream(file);

			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);

			encodedFile = Base64.getEncoder().encodeToString(bytes);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return encodedFile;
	}

}
