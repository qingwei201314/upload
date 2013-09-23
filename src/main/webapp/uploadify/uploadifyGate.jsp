<%@ page import="com.kevin.util.Util"%>
<%@ page import="com.kevin.user.service.Uploadify" %>
<%@ page import="com.kevin.user.service.WidthHeight" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
	String widthXheight = request.getParameter("widthXheight");
	Uploadify uploadify = new Uploadify();
	String result = uploadify.uplodate(request,response,widthXheight);
	out.clear();
	out.print(result);
	out.flush();
	out.clear();
%>
