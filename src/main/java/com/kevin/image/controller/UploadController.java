package com.kevin.image.controller;

import java.io.PrintWriter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kevin.user.service.Uploadify;

@Named
@RequestScoped
public class UploadController {
	@Inject
	private Uploadify uploadify;
	
	public String getUpdateImage() throws Exception{
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		String widthXheight  = request.getParameter("widthXheight");
		String result = uploadify.uplodate(request,widthXheight);
		PrintWriter pw = response.getWriter();
		pw.append("result." + result + ".result");
		return null;
	}
}
