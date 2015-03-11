package com.osmose.piksel.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import org.codehaus.jettison.json.JSONException;

import com.tellme.polymedia.TXT.SupportService;

public class RESTClient {

	public static void callInsertLC(String json) throws ParseException, JSONException, SQLException {
		
		SupportService sService = new SupportService();
		sService.InsertLC(json);
		
		/*
		HttpClient httpClient = new DefaultHttpClient();
		//HttpPost postRequest = new HttpPost("http://demos.polymedia.it/tellme/TellMeSupportServices/insertLC");
		HttpPost postRequest = new HttpPost("http://pc-divito7:8080/TellMeSupportServices/supportservices/insertLC");
		
		//parameters setting
		ArrayList<NameValuePair> postParameters;
		postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("lcJsonString", URLEncoder.encode(json, "UTF-8")));
		postRequest.setEntity(new UrlEncodedFormEntity(postParameters));	 		
		HttpResponse response = httpClient.execute(postRequest);
		//System.out.println(response.getStatusLine());
		
		httpClient.getConnectionManager().shutdown();
		*/
	}
	
	

}
