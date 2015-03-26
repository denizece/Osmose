package com.osmose.piksel.restws;

import java.sql.SQLException;
import java.text.ParseException;

import javax.activation.MimeType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.osmose.piksel.db.DBConnection;
import com.sun.jersey.api.json.JSONWithPadding;

// importante ricordarsi di settare il file web.xml per la servlet!
// path per test: 

@Path("")
@Produces("application/x-javascript")
public class wstest {
	
	@Path("/addARNotification")
	@POST
	@Produces("application/json")
	public JSONWithPadding addARNotification(@QueryParam("callback") String callback, @FormParam("userid") int userId, @FormParam("domainid") int domainId, @FormParam("objid") String objId, @FormParam("functionId") int functionId) throws JSONException, SQLException, ParseException
	{
		DBConnection dbconn = new DBConnection();
		JSONObject resp = dbconn.addARNotification(userId, domainId, objId, functionId);
		JSONWithPadding jsonpRet = new JSONWithPadding(resp, callback);
		return (jsonpRet);
	}
	
	@Path("/consumeNotification")
	@POST
	@Produces("application/json")
	public JSONWithPadding consumeNotification(@QueryParam("callback") String callback, @FormParam("idNotifications") int idNotifications) throws JSONException, SQLException, ParseException
	{
		DBConnection dbconn = new DBConnection();
		JSONObject resp = dbconn.consumeNotification(idNotifications);
        return (new JSONWithPadding(resp, callback));
	}
	
	@Path("/retriveObjHistory/{objId: [A-Za-z0-9]*}")
	@GET
	public JSONWithPadding retriveObjHistory(@QueryParam("callback") String callback, @PathParam("objId") String objId) throws JSONException, SQLException, ParseException
	{
		DBConnection dbconn = new DBConnection();
		JSONArray resp = dbconn.retriveObjHistory(objId);
        return (new JSONWithPadding(resp, callback));
	}
	
	@Path("/retriveObjTimeline/{objId: [A-Za-z0-9]*}")
	@GET
	@Produces({"application/xml", "application/json"})
	public String retriveObjTimeline(@QueryParam("callback") String callback, @PathParam("objId") String objId) throws JSONException, SQLException, ParseException
	{
		
		DBConnection dbconn = new DBConnection();
		System.out.println("--> " + objId);
		String resp = dbconn.retriveObjTimeline(objId);
        return (resp);
	}
	
	@Path("/retriveObjMediaAttach/{objId: [#A-Za-z0-9]*}")
	@GET
	public JSONWithPadding retriveObjMediaAttach(@QueryParam("callback") String callback, @PathParam("objId") String objId) throws JSONException, SQLException, ParseException
	{
		DBConnection dbconn = new DBConnection();
		JSONArray resp = dbconn.retriveObjMediaAttach(objId);
        return (new JSONWithPadding(resp, callback));
	}
	
	@Path("/retriveUserNotications/{userId: [0-9]*}")
	@GET
	public JSONWithPadding retriveUserNotications(@QueryParam("callback") String callback, @PathParam("userId") int userId) throws JSONException, SQLException, ParseException
	{
		DBConnection dbconn = new DBConnection();
		JSONArray resp = dbconn.retriveUserNotications(userId);
        return (new JSONWithPadding(resp, callback));
	}
	
	@Path("/retriveObjStatus/{objId: [#A-Za-z0-9]*}")
	@GET
	public JSONWithPadding retriveObjStatus(@QueryParam("callback") String callback, @PathParam("objId") String objId) throws JSONException, SQLException, ParseException
	{
		DBConnection dbconn = new DBConnection();
		JSONObject resp = dbconn.retriveObjStatus(objId);
        return (new JSONWithPadding(resp, callback));
	}
	@Path("/retriveFolderTaxonomy/{domId: [#A-Za-z0-9]*}")
	@GET
	public JSONWithPadding retriveFolderTaxonomy(@QueryParam("callback") String callback, @PathParam("domId") String domId) throws JSONException, SQLException, ParseException
	{
		DBConnection dbconn = new DBConnection();
		JSONObject resp = dbconn.retriveFolderTaxonomy(domId);
        return (new JSONWithPadding(resp, callback));
	}
	
	@Path("/setObjStatus/{objId}/{statusId}")
	@POST
	@Produces("application/json")
	public JSONWithPadding setObjStatus(@QueryParam("callback") String callback,  @PathParam("objId") String objId,  @PathParam("statusId") int statusId) throws JSONException, SQLException, ParseException
	{
		DBConnection dbconn = new DBConnection();
		JSONObject resp = dbconn.setObjStatus(objId,statusId);
        return (null);
	}
}
