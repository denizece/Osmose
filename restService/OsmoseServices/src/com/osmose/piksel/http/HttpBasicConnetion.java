package com.osmose.piksel.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;












import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class HttpBasicConnetion {

	final String user = "465ea716cebb2476fa0d8eca90c3d4f594e64b51";
	final String pwd = "ccbdb91f75d61b726800313b2aa9f50f562bad66";
	
	final String baseUrl = "http://demos.polymedia.it/tellme/learninglocker/data/xAPI/";
	Client client = null;
	
	public HttpBasicConnetion()
	{
		client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(user, pwd));
	}

	public String whenMixHasDone(String query) throws Exception, JSONException {
		
		String whenMix = "-1";
		if (query==null)
		{
			query = "{'Mix':'12','user':'gianluigi.divito@piksel.com'}";
		}
		JSONObject jobj = new JSONObject(query);
		
		JSONObject param = new JSONObject();
		addActor(param,jobj.getString("user"));
		addObjectId(param,jobj.getString("Mix"));
		
		// aggiungere il filtraggio per object->id 
		// http://www.tellme-ip.eu/expapi/activities/learningMix/MixId
		// dovrebbe essere un solo statements (altrimenti filtrare anche per il verbo)
		// recuperare il timestamp e fare la differenza in giorni con now();
		
		//WebResource webResource = client.resource(baseUrl).path("statements").queryParam("filter", param.toString());
		WebResource webResource = client.resource(baseUrl + "statements?filter=" + URLEncoder.encode(param.toString(), "UTF-8"));
		webResource.accept("application/json");
		JSONObject job = new JSONObject(webResource.get(String.class));
		JSONArray statements = job.getJSONArray("statements");
		for (int i = 0; i < statements.length(); i++) {
			JSONObject row = statements.getJSONObject(i);
			
			// tutta questa parte andrebbe eliminata se funzionasse la parte di filtraggio sui servizi del LearningLocker.
			
			JSONObject actor = row.getJSONObject("actor");
			if (actor.getString("mbox").equals("mailto:"+ jobj.getString("user")))
			{
				JSONObject obj = row.getJSONObject("object");
				if (obj.getString("id").equals(LLocker.lMix + jobj.getString("Mix")))
				{
					SimpleDateFormat fromjson = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date timestamp = fromjson.parse(row.getString("timestamp"));
					Date today = new Date();
					whenMix = howManyDays(today,timestamp);
				}
			}
		}
		client.destroy();
		return whenMix.replace(".0", "");
	}
	
	public String WhenHasDone(String query) throws JSONException, UnsupportedEncodingException, ParseException {

		String whenMix = "-1";
		if (query==null)
		{
			query = "{'tag':'aw#Object#Helicopter Model#Commercial#Parapublic#Medium#AW139','user':'gianluigi.divito@piksel.com'}";
		}
		System.out.println("query = " + query);
		JSONObject jobj = new JSONObject(query);
		
		JSONObject param = new JSONObject();
		addActor(param,jobj.getString("user"));
		// non è previsto un filtro per le extension
		
		
		WebResource webResource = client.resource(baseUrl + "statements?filter=" + URLEncoder.encode(param.toString(), "UTF-8"));
		webResource.accept("application/json");
		JSONObject job = new JSONObject(webResource.get(String.class));
		JSONArray statements = job.getJSONArray("statements");
		for (int i = 0; i < statements.length(); i++) {
			JSONObject row = statements.getJSONObject(i);
			
			// tutta questa parte andrebbe eliminata se funzionasse la parte di filtraggio sui servizi del LearningLocker.
			// aggiungere il filtro dipendente dal verbo "done".
			
			JSONObject actor = row.getJSONObject("actor");
			if (actor.getString("mbox").equals("mailto:"+ jobj.getString("user")))
			{
				JSONObject obj = row.getJSONObject("object");
				//System.out.println(obj.toString());
				try
				{
					JSONObject joexts = obj.getJSONObject("definition").getJSONObject("extensions");
					if (joexts.has("http://"+jobj.getString("tag").replace("#","/")))
					{
						SimpleDateFormat fromjson = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date timestamp = fromjson.parse(row.getString("timestamp"));
						Date today = new Date();
						whenMix = howManyDays(today,timestamp);
					}	
				}
				catch (Exception ex)
				{}
			}
		}
		client.destroy();
		return whenMix.replace(".0", "");
	}
	

	public String HowMuchHasDone(String query) throws JSONException, UnsupportedEncodingException, ParseException {

		int howmuch= 0;
		SimpleDateFormat fromjson = new SimpleDateFormat("yyyy-MM-dd");
		
		if (query==null)
		{
			query = "{'tag':'aw#Object#Helicopter Model#Commercial#Parapublic#Medium#AW139','user':'gianluigi.divito@piksel.com','startDate':'2014-02-14','endDate':'2014-08-14'}";
		}
		JSONObject jobj = new JSONObject(query);
		
		JSONObject param = new JSONObject();
		addActor(param,jobj.getString("user"));
		
		Date startDate = fromjson.parse(jobj.getString("startDate"));
		Date endDate = fromjson.parse(jobj.getString("endDate"));
		// non è previsto un filtro per le extension
		
		WebResource webResource = client.resource(baseUrl + "statements?filter=" + URLEncoder.encode(param.toString(), "UTF-8"));
		webResource.accept("application/json");
		JSONObject job = new JSONObject(webResource.get(String.class));
		JSONArray statements = job.getJSONArray("statements");
		for (int i = 0; i < statements.length(); i++) {
			JSONObject row = statements.getJSONObject(i);
			
			// tutta questa parte andrebbe eliminata se funzionasse la parte di filtraggio sui servizi del LearningLocker.
			// aggiungere il filtro dipendente dal verbo "done".
			
			JSONObject actor = row.getJSONObject("actor");
			if (actor.getString("mbox").equals("mailto:"+ jobj.getString("user")))
			{
				JSONObject obj = row.getJSONObject("object");
				//System.out.println(obj.toString());
				try
				{
					JSONObject joexts = obj.getJSONObject("definition").getJSONObject("extensions");
					if (joexts.has("http://"+jobj.getString("tag").replace("#","/")))
					{
						Date timestamp = fromjson.parse(row.getString("timestamp"));
						if ((timestamp.after(startDate)) && (timestamp.before(endDate)))
							howmuch++;
					}	
				}
				catch (Exception ex)
				{}
			}
		}
		client.destroy();
		return String.valueOf(howmuch);
	}
	
	private String howManyDays(Date today, Date timestamp) {
		long millisDiff = today.getTime() - timestamp.getTime();
		double differenza = Math.round( millisDiff / 86400000.0 );
		return String.valueOf(differenza);
	}

	private static void addActor(JSONObject jobb, String email) throws JSONException
	{
		jobb.put(Statements.email, LLocker.email + email);
	}
	
	private static void addObjectId(JSONObject jobb, String objId) throws JSONException
	{
		jobb.put(Statements.objid, LLocker.lMix + objId);
	}
	
}
