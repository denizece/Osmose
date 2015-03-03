<%
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title></title>
</head>
<body>

<%
String[] anArrayFromMyPortlet = (String[])renderRequest.getAttribute("my-array");
String test=(String)renderRequest.getAttribute("teststr");
Long uid= (Long)renderRequest.getAttribute("userID");
String umail=(String)renderRequest.getAttribute("userMail");

%>


<ul>
<% for (String string : anArrayFromMyPortlet) { %>
<li><%= string %></li>
<% } %>
</ul>
<i>This is <%= test %> speaking:</i>

Logged in user ID <%= uid %>
Logged in user mail <%= umail %>


This is the <b>UserPortlet</b> portlet.

<script type="text/javascript">
		var uid = <%= uid %>;
		setTimeout(function(){Liferay.fire('gadget:com.piksel.osmose.userid',uid);console.log("Fired!!")},5000);
		//setTimeout(function(){Liferay.fire('gadget:com.piksel.osmose.userid',uid)},3000);
       	console.log(uid)
</script>
</body>
</html>