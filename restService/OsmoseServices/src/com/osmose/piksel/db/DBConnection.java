package com.osmose.piksel.db;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;





import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Produces;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.jpeg.JpegDescriptor;
import com.drew.metadata.jpeg.JpegDirectory;
import com.osmose.piksel.em.ExecJson;
import com.osmose.piksel.http.RESTClient;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class DBConnection {

	private Connection mysqlCon = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public DBConnection()
	{
		try {	
			String userName = "crs";
			String password = "crs";
			String url = "jdbc:mysql://172.23.2.119:3306/Osmose";
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			mysqlCon = DriverManager.getConnection (url, userName, password);
			//System.out.println ("Database connection established");

			if(!mysqlCon.isClosed())
				System.out.println("Successfully connected to " +
						"MySQL server using TCP/IP...");

		} catch(Exception e) {
			//System.err.println("Exception: " + e.getMessage());
			e.printStackTrace();
		} 
	}


	public void CloseDb()
	{
		try {
			if(mysqlCon != null)
				mysqlCon.close();
		} catch(SQLException e) {}
	}

	public JSONObject addARNotification(int userId, int domainId, String objId,int functionId) throws JSONException, SQLException {
		
		JSONObject resp = new JSONObject();
		try
		{
			mysqlCon.setAutoCommit(false);
			preparedStatement = mysqlCon.prepareStatement(DBQueries.INSERTNOTIFICATION);
			preparedStatement.setInt(1, domainId);
			preparedStatement.setInt(2, userId);
			preparedStatement.setInt(3, functionId);
			preparedStatement.setString(4, objId);
			preparedStatement.executeUpdate();
			mysqlCon.commit();
			resp.put("message", "OK");
		}
		catch (SQLException e)
		{
			resp.put("error", ErrorMessages.DBCONERROR);
			resp.put("exception", e.getMessage());
			mysqlCon.rollback(); 
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
		finally
		{
			mysqlCon.close();
			preparedStatement.close();
		}
		return resp;
	}

	public JSONObject consumeNotification(int idNotifications) throws JSONException, SQLException {
		JSONObject resp = new JSONObject();
		try
		{
			mysqlCon.setAutoCommit(false);
			preparedStatement = mysqlCon.prepareStatement(DBQueries.UPDATENOTIFICATION);
			preparedStatement.setInt(1, idNotifications);
			preparedStatement.executeUpdate();
			mysqlCon.commit();
			resp.put("message", "OK");
		}
		catch (SQLException e)
		{
			resp.put("error", ErrorMessages.DBCONERROR);
			resp.put("exception", e.getMessage());
			mysqlCon.rollback(); 
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
		finally
		{
			mysqlCon.close();
			preparedStatement.close();
		}
		return resp;
	}


	public JSONArray retriveObjHistory(String objId) throws JSONException, SQLException {
		
		JSONArray resp = new JSONArray();
		try
		{
			mysqlCon.setAutoCommit(false);
			preparedStatement = mysqlCon.prepareStatement(DBQueries.GETHISTORIES);
			preparedStatement.setString(1, objId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				JSONObject temp = new JSONObject();
				temp.put("idHistory",resultSet.getInt("idEPC_History"));
				temp.put("description",resultSet.getString("description"));
				temp.put("timestamp",resultSet.getTimestamp("create_date"));
				resp.put(temp);
			}
			
			mysqlCon.commit();
		}
		catch (SQLException e)
		{
			JSONObject error = new JSONObject();
			error.put("error", ErrorMessages.DBCONERROR);
			error.put("exception", e.getMessage());
			resp.put(error);
			mysqlCon.rollback(); 
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
		finally
		{
			mysqlCon.close();
			preparedStatement.close();
		}
		return resp;
	}


	public String retriveObjTimeline(String objId)  throws JSONException, SQLException {
		String resp= "";
		try
		{
			mysqlCon.setAutoCommit(false);
			preparedStatement = mysqlCon.prepareStatement(DBQueries.GETTIMELINEXML);
			preparedStatement.setString(1, objId);
			//System.out.println(preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				resp = resultSet.getString("xml");
			}
			
			mysqlCon.commit();
		}
		catch (SQLException e)
		{
			mysqlCon.rollback(); 
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
		finally
		{
			mysqlCon.close();
			preparedStatement.close();
		}
		return resp;
	}


	public JSONArray retriveObjMediaAttach(String objId)  throws JSONException, SQLException {
		JSONArray resp = new JSONArray();
		try
		{
			mysqlCon.setAutoCommit(false);
			preparedStatement = mysqlCon.prepareStatement(DBQueries.GETATTACHMENTS);
			preparedStatement.setString(1, objId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				JSONObject temp = new JSONObject();
				temp.put("id",resultSet.getInt("idEPC_Docs"));
				temp.put("description",resultSet.getString("description"));
				temp.put("timestamp",resultSet.getTimestamp("create_date"));
				temp.put("url",resultSet.getString("url"));
				temp.put("mediatype",resultSet.getString("name"));
				temp.put("filename",resultSet.getString("filename"));
				resp.put(temp);
			}
			
			mysqlCon.commit();
		}
		catch (SQLException e)
		{
			JSONObject error = new JSONObject();
			error.put("error", ErrorMessages.DBCONERROR);
			error.put("exception", e.getMessage());
			resp.put(error);
			mysqlCon.rollback(); 
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
		finally
		{
			mysqlCon.close();
			preparedStatement.close();
		}
		return resp;
	}


	public JSONArray retriveUserNotications(int userId)  throws JSONException, SQLException {
		JSONArray resp = new JSONArray();
		try
		{
			mysqlCon.setAutoCommit(false);
			preparedStatement = mysqlCon.prepareStatement(DBQueries.GETNOTIFICATIONS);
			preparedStatement.setInt(1, userId);
			//System.out.println(preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				JSONObject temp = new JSONObject();
				temp.put("id",resultSet.getInt("idNotifications"));
				temp.put("functionid",resultSet.getInt("funcid"));
				temp.put("name",resultSet.getString("funcid"));
				temp.put("objectid",resultSet.getString("objid"));
				temp.put("timestamp",resultSet.getTimestamp("create_date"));
				resp.put(temp);
			}
			
			mysqlCon.commit();
		}
		catch (SQLException e)
		{
			JSONObject error = new JSONObject();
			error.put("error", ErrorMessages.DBCONERROR);
			error.put("exception", e.getMessage());
			resp.put(error);
			mysqlCon.rollback(); 
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
		finally
		{
			mysqlCon.close();
			preparedStatement.close();
		}
		return resp;
	}


	public JSONObject retriveObjStatus(String objId) throws JSONException, SQLException {
		JSONObject resp= new JSONObject();
		try
		{
			mysqlCon.setAutoCommit(false);
			preparedStatement = mysqlCon.prepareStatement(DBQueries.GETOBJSTATUS);
			preparedStatement.setString(1, objId);
			//System.out.println(preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				resp.put("status",resultSet.getInt("statusid"));
			}
			
			mysqlCon.commit();
		}
		catch (SQLException e)
		{
			mysqlCon.rollback(); 
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
			resp.put("error",e.getMessage());
		}
		finally
		{
			mysqlCon.close();
			preparedStatement.close();
		}
		return resp;
	}


	public JSONObject setObjStatus(String objId, int statusId) throws JSONException, SQLException {
		JSONObject resp = new JSONObject();
		try
		{
			mysqlCon.setAutoCommit(false);
			preparedStatement = mysqlCon.prepareStatement(DBQueries.UPDATEOBJSTATUS);
			preparedStatement.setInt(1, statusId);
			preparedStatement.setString(2, objId);
			preparedStatement.executeUpdate();
			mysqlCon.commit();
			resp.put("message","Object status correctly changed");
		}
		catch (SQLException e)
		{
			resp.put("error", ErrorMessages.DBCONERROR);
			resp.put("exception", e.getMessage());
			mysqlCon.rollback(); 
			Logger lgr = Logger.getLogger(DBConnection.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
		finally
		{
			mysqlCon.close();
			preparedStatement.close();
		}
		return resp;
	}


	public JSONObject retriveFolderTaxonomy(String domId) throws SQLException, JSONException {
		JSONObject resp = new JSONObject();
		
		PreparedStatement preparedStatementChildren=null;
		
		//{ data: [
		           //         { text: "Item 1" },
		           //         { text: "Item 2", items: [
		           //             { text: "SubItem 2.1" },
		          //              { text: "SubItem 2.2" }
		          //          ] },
		          //          { text: "Item 3" }
		         //           ]},
		
		
		
		
		try
		{
			mysqlCon.setAutoCommit(false);
			preparedStatement = mysqlCon.prepareStatement(DBQueries.GETFOLDERTAX_PARENTS);
			preparedStatement.setString(1, domId);
			//System.out.println(preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();
			JSONArray jarrtemps=new JSONArray();
			while (resultSet.next()) {
				JSONObject temp = new JSONObject();
				int parid=resultSet.getInt("ID");
				//temp.put("ID",parid);
				temp.put("text",resultSet.getString("Name"));
				//temp.put("DomainID",resultSet.getInt("DomainID"));
				
				 preparedStatementChildren = mysqlCon.prepareStatement(DBQueries.GETFOLDERTAX_CHILDREN);
				preparedStatementChildren.setInt(1, parid);
				preparedStatementChildren.setString(2, domId);

				ResultSet resultSetChildren=preparedStatementChildren.executeQuery();
				JSONArray tempChildren = new JSONArray();
				int childIndex=0;
				while (resultSetChildren.next()){
					JSONObject tempChild = new JSONObject();
					//int childid=resultSetChildren.getInt("ID");
					//tempChild.put("id",childid);
					tempChild.put("text",resultSetChildren.getString("Name"));
					//tempChild.put("DomainID",resultSetChildren.getInt("DomainID"));
					
					tempChildren.put(tempChild);
					childIndex++;
				}
				if(childIndex!=0)
					temp.put("items", tempChildren);
				jarrtemps.put(temp);
			}
			resp.put("data", jarrtemps);
			

			mysqlCon.commit();
			}
		catch(SQLException e){
		mysqlCon.rollback(); 
		Logger lgr = Logger.getLogger(DBConnection.class.getName());
		lgr.log(Level.WARNING, e.getMessage(), e);
		resp.put("error",e.getMessage());}
		finally{
		mysqlCon.close();
		preparedStatementChildren.close();
		preparedStatement.close();
		}
		// TODO Auto-generated method stub
		return resp;
	}
}
