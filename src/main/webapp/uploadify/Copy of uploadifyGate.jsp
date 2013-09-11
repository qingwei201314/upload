<%@ page import="com.kevin.util.Util"%>
<%@ page import="com.kevin.user.service.Uploadify" %>
<%@ page import="com.kevin.user.service.WidthHeight" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
	String width = request.getParameter("width");
	String height = request.getParameter("height");
	Uploadify uploadify = new Uploadify();
	WidthHeight widthHeight = new WidthHeight(width, height);
	List<WidthHeight> arguments = new ArrayList<WidthHeight>();
	arguments.add(widthHeight);
	uploadify.uplodate(request,response,arguments);
%>
