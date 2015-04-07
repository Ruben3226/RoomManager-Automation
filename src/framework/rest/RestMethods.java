package framework.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;

import framework.common.AppConfigConstants;

public class RestMethods {
	private BufferedReader br;
	private String lineReader;
	private String output = "";
	private String urlpath =  AppConfigConstants.URL_REST;

	/** [J.C]
	 * @param type:  Type of method, can be GET or DELETE without Authentication
	 * @param request: the path of the url used for the request to REST API
	 * @return This method is used for DELETE or GET methods to obtain or delete some information
	 */
	public String getOrDeleteMethods(String type, String request)throws IOException, MalformedURLException {
		try {
			URL url = new URL(urlpath + request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(type);
			conn.setRequestProperty("Accept",  "application/json");
			if (conn.getResponseCode() !=  200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+  conn.getResponseCode());
			}
			br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			while ((lineReader = br.readLine()) !=  null) {
				output = output + lineReader;
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	/** [J.C]
	 * @param type:  Type of method, can be GET or DELETE without Authentication
	 * @param request: the path of the url used for the request to REST API
	 * @return This method is used for DELETE or GET methods to obtain or delete some information
	 */
	public String deleteAuthenticatedMethod(String request, String userCredentials) throws IOException, 
	MalformedURLException {
		try {
			URL url = new URL(urlpath + request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
			conn.setRequestProperty ("Authorization", basicAuth);
			conn.setRequestProperty("Accept",  "application/json");
			conn.setRequestMethod("DELETE");
			if (conn.getResponseCode() !=  200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+  conn.getResponseCode());
			}
			br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			while ((lineReader = br.readLine()) !=  null) {
				output = output + lineReader;
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	/** [J.C]
	 * @param type:  Type of method, can be POST or PUT
	 * @param request: the path of the url used for the request to REST API
	 * @return This method can be used for POST or PUT methods to create or update some information
	 * @throws IOException 
	 */
	public void postMethod(String request, String filePath, String userCredentials) throws IOException,
	MalformedURLException {
		File jsonFile = null;
		BufferedReader br = null;
		String fileJSONString = "";
		String line;
		jsonFile= new File (filePath);
		br = new BufferedReader(new FileReader (jsonFile));
		while((line=br.readLine())!=null){
			fileJSONString = fileJSONString + line;
		}
		br.close();
		try {
			URL url = new URL(urlpath + request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
			conn.setRequestProperty ("Authorization", basicAuth);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(fileJSONString.getBytes());
			os.flush();
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/** [J.C]
	 * @param request: the path of the url used for the request to REST API
	 * @param condition : the parameter for search elements in an JSONArray 
	 * @param dataType : the dataType of parameter searched, can be String,Int or Boolean 
	 * @return This method return a LinkedList <String> of elements that have the condition
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public LinkedList <String> response(String request,  String condition, String dataType) 
			throws JSONException, MalformedURLException, IOException{
		LinkedList <String> list = new LinkedList <String>();
		JSONArray jsonResponse = new JSONArray(getOrDeleteMethods("GET", request));
		int iterator = 0;
		if(dataType=="String"){
			while(iterator < jsonResponse.length()){
				list.add(jsonResponse.getJSONObject(iterator).getString(condition));
				iterator ++ ;
			}
		}
		if(dataType=="Int"){
			while(iterator < jsonResponse.length()){
				list.add("" + jsonResponse.getJSONObject(iterator).getInt(condition) + "");
				iterator ++ ;
			}
		}
		if(dataType=="Boolean"){
			while(iterator < jsonResponse.length()){
				list.add("" + jsonResponse.getJSONObject(iterator).getBoolean(condition) + "");
				iterator ++ ;
			}
		}

		return list; 
	}

	/** [J.C]
	 * @param feature : Refers to the feature (i.e. rooms, resources, etc.) 
	 * @param attributeName : Refers to the attribute of feature 
	 * (i.e. If feature is rooms, attribute could be _id)
	 * @param attributeValue : Refers to the value of attribute 
	 * (i.e. If attribute is displayName,attributeValue could be Room 1)
	 * @param attributeSearched : Refers to the attribute of feature that you search
	 * (i.e. If feature is rooms, attribute could be _id)
	 * @return return the value of attributeSearched
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public String findAttributeValue(String feature, String dataType, String attributeName,
			String attributeValue, String attributeSearched) throws JSONException, MalformedURLException, IOException{
		LinkedList <String> listNames = null;
		LinkedList<String> listCondition = null;;
		try {
			listNames = response(feature, attributeName, dataType);
			listCondition = response(feature, attributeSearched,dataType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int iterator = 0;iterator < listNames.size();iterator ++ ){
			if(listNames.get(iterator).equals(attributeValue)){

				return listCondition.get(iterator);
			}
		}
		return null;
	}

}