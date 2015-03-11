package com.osmose.piksel.em;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mysql.jdbc.PreparedStatement;

public class ExecJson {
	
	private String emuserid;
	private String emid;
	private String emtype;
	private String emstatus;
	private String emtimestamp;
	private String emprogessId;
	private String emtags;
	private String emparentId;
	private String emurl;
	private String emjobid;
	private String emsource;
	private String emassigneddate;
	
	public ExecJson(JSONObject emJSON) throws JSONException
	{
		emuserid = emJSON.getString("userId");
		emid = emJSON.getString("id");
		emtype = emJSON.getString("type");
		emstatus = emJSON.getString("status");
		//emtimestamp = emJSON.getString("timestamp");
		emprogessId = emJSON.getString("progressId");
		emtags = emJSON.getString("tags");
		emparentId = emJSON.getString("parentId");
		emurl = emJSON.getString("url");
		emjobid = emJSON.getString("jobid");
		emsource = emJSON.getString("source");
		emassigneddate =  emJSON.getString("assignedate");
		
	}
	
	public ExecJson(ResultSet emRs) throws SQLException
	{
		emuserid =emRs.getString("userId");
		emid = emRs.getString("id");
		emtype = emRs.getString("type");
		emstatus = emRs.getString("status");
		emtimestamp = emRs.getString("timestamp");
		emprogessId = emRs.getString("progressId");
		emtags = emRs.getString("tags");
		emparentId = emRs.getString("parentId");
		emurl = emRs.getString("url");
		emjobid = emRs.getString("jobId");
		emsource = emRs.getString("source");
		emassigneddate =  emRs.getString("Assignedate");
	}
	
	public JSONObject toJson() throws JSONException
	{
		JSONObject resp = new JSONObject();
		resp.put("userId",emuserid);
		resp.put("id",emid);
		resp.put("type",emtype);
		resp.put("status",emstatus);
		resp.put("timestamp",emtimestamp);
		resp.put("progressId",emprogessId);
		resp.put("tags",emtags);
		resp.put("parentId",emparentId);
		resp.put("url",emurl);
		resp.put("jobid",emjobid);
		resp.put("source",emsource);
		resp.put("assignedate",emassigneddate);
		return resp;
	}
	
	public String getUserId()
	{
		return this.emuserid;
	}
	
	public String getId()
	{
		return this.emid;
	}
	
	public String getType()
	{
		return this.emtype;
	}
	
	public String getStatus()
	{
		return this.emstatus;
	}
	
	public String getProgressId()
	{
		return this.emprogessId;
	}
	
	public String getTagsId()
	{
		return this.emtags;
	}
	
	public String getParentId()
	{
		return this.emparentId;
	}
	
	public String getUrl()
	{
		return this.emurl;
	}
	
	public Integer getJobId()
	{
		return Integer.decode(this.emjobid);
	}
	
	public String getSource()
	{
		return this.emsource;
	}
	
	public Timestamp getAssignedDate()
	{
		return Timestamp.valueOf(this.emassigneddate);
	}
}