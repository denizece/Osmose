package com.test;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class UserInfoPortlet
 */
public class UserInfoPortlet extends MVCPortlet {
	
	@Override
	public void doView(RenderRequest request,
			RenderResponse response) throws IOException, PortletException {
		// TODO Auto-generated method stub
		  PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher("/html/userinfo/view.jsp");
		  //rd.include(request,response);
			System.out.println("sgsfgsfgsfgsfgsdgsfgsfgs");
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);

			User user = themeDisplay.getRealUser(); // it gives you the actual Logged in User
			long userId = user.getUserId();
			request.setAttribute("userID", userId);
			String userMail = user.getEmailAddress();
			request.setAttribute("userMail", userMail);
			 
		System.out.println("id is here="+userId);
		
		 String[] array = new String[] { "foo", "bar", "baz" };
	     request.setAttribute("my-array", array);
	     String d="deniz";
	     request.setAttribute("teststr", d);
		super.doView(request, response);
	}
	
	
	
	
/*	@Override
	protected void include(String path, PortletRequest portletRequest,
			PortletResponse portletResponse, String lifecycle)
			throws IOException, PortletException {
		// TODO Auto-generated method stub
		System.out.println("sgsfgsfgsfgsfgsdgsfgsfgs");
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getRealUser(); // it gives you the actual Logged in User
		//you can also use
		// User user = themeDisplay.getUser(); // this would fetch the User you are impersonating 

		long userId = user.getUserId();
		String userMail = user.getEmailAddress();
		 System.out.println("id is here="+userId);
		
		
		super.include(path, portletRequest, portletResponse, lifecycle);
	}
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getRealUser(); // it gives you the actual Logged in User
		//you can also use
		// User user = themeDisplay.getUser(); // this would fetch the User you are impersonating 

		long userId = user.getUserId();
		String userMail = user.getEmailAddress();
		 System.out.println("id is here="+userId);
		long userId2 = themeDisplay.getRealUserId(); // themeDisplay.getUserId();
		try {
			User user2 = UserLocalServiceUtil.getUser(userId);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			System.out.println("qweqweqwe");
			e.printStackTrace();
		} catch (SystemException e) {
			System.out.println("qweqweqwe");
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
		
	}
public void writeUserScreenName(PortletRequest pr) {
	System.out.println("sfjskjdhfksjhdkfhskjdhfkjk");

	ThemeDisplay themeDisplay = (ThemeDisplay)pr.getAttribute(WebKeys.THEME_DISPLAY);

	User user = themeDisplay.getRealUser(); // it gives you the actual Logged in User
	System.out.println("sfjskjdhfksjhdkfhskjdhfkjk");
	//you can also use
	// User user = themeDisplay.getUser(); // this would fetch the User you are impersonating 

	long userId = user.getUserId();
	String userMail = user.getEmailAddress();
	long userId2 = themeDisplay.getRealUserId(); // themeDisplay.getUserId();
	try {
		User user2 = UserLocalServiceUtil.getUser(userId);
	} catch (PortalException | SystemException e) {
		// TODO Auto-generated catch block
		System.out.println("sfjskjdhfksjhdkfhskjdhfkjk");

		e.printStackTrace();
	}	
	
}*/
}
