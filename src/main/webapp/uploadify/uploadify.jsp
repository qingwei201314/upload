<%@ page import="com.kevin.util.Util"%>
<%@ page import="com.kevin.user.service.Uploadify" %>
<%@ page import="com.kevin.user.service.WidthHeight" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
	Uploadify uploadify = new Uploadify();
	WidthHeight widthHeight_220 = new WidthHeight(220, 165);
	List<WidthHeight> arguments = new ArrayList<WidthHeight>();
	arguments.add(widthHeight_220);
	WidthHeight widthHeight_400 = new WidthHeight(400, 300);
	arguments.add(widthHeight_400);
	String path = uploadify.uplodate(request,arguments);
	out.clear();
	out.print(Util.repository() + path);
	out.flush();
	out.clear();
%>
