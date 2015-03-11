package com.osmose.piksel.db;

public final class DBQueries {
	
	public static final String INSERTNOTIFICATION = "INSERT INTO `Osmose`.`Notifications` (`domainid`, `userid`, `funcid`, `objid`) VALUES (?,?,?,?)";
	public static final String UPDATENOTIFICATION = "UPDATE `Osmose`.`Notifications` SET `read`='1', `read_date`=now() WHERE `idNotifications`=?";
	public static final String GETHISTORIES = "SELECT * FROM `Osmose`.`EPC_History` WHERE objectid=? ORDER BY create_date DESC";
	public static final String GETATTACHMENTS = "SELECT * FROM `Osmose`.`EPC_MediaAttachment` LEFT JOIN `Osmose`.`EPC_MediaType` ON (Osmose.EPC_MediaAttachment.media_type = Osmose.EPC_MediaType.idEPC_MediaType) WHERE objectid=? ORDER BY create_date DESC";
	public static final String GETNOTIFICATIONS = "SELECT * FROM `Osmose`.`Notifications` LEFT JOIN `Osmose`.`Functionalities` ON (Osmose.Notifications.funcid = Osmose.Functionalities.idFunc) WHERE (Notifications.userid=? AND Notifications.read=0) ORDER BY Notifications.create_date DESC";
	public static final String GETTIMELINEXML = "SELECT xml FROM `Osmose`.`Timeline` WHERE objectid=?";
	public static final String GETOBJSTATUS = "SELECT statusid FROM `Osmose`.`EPC_Objects` WHERE objectid=?";
	public static final String UPDATEOBJSTATUS = "UPDATE `Osmose`.`EPC_Objects` SET `statusid`=? WHERE `objectid`=?";
	public static final String GETFOLDERTAX_PARENTS = "SELECT * FROM `Osmose`.`FolderTaxonomy` WHERE domainID=? and parentID=-1";
	public static final String GETFOLDERTAX_CHILDREN = "SELECT * FROM `Osmose`.`FolderTaxonomy` WHERE parentID=? and domainID=?";
}
