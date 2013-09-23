package com.kevin.user.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import com.kevin.util.Constant;
import com.kevin.util.Util;

/**
 * 用于处理上传文件
 * @author Kevin
 */
public class Uploadify {
	public String uplodate(HttpServletRequest request, HttpServletResponse response, String widthXheight_s) throws Exception{
		List<WidthHeight> widthHeights = new ArrayList<WidthHeight>(); //用于存各存规格
		
		String[] widthXheightArray = widthXheight_s.split("_"); //分出几种规格
		for(int i=0; i < widthXheightArray.length; i++){
			String widthXheight = widthXheightArray[i];
			String[] wXh = widthXheight.split("x");
			WidthHeight widthHeight = new WidthHeight(wXh[0], wXh[1]);
			widthHeights.add(widthHeight);
		}
		
		String resultPath = "";
		String path = "";
	 	String phone = request.getParameter(Constant.phone);
	 	int length = phone.length();
	 	int count = length%4 > 0 ? length/4 +1 : length/4;
	 	for(int i=0; i< count; i++)
	 		path += "/" + phone.substring(i*4, (i*4 + 4)>length?length:i*4 + 4);
	 	//增加年份
	 	int year = Calendar.getInstance().get(Calendar.YEAR);
	 	path += "/" + year;
	 	String ymdhms = new StringBuffer().append(Calendar.getInstance().get(Calendar.YEAR))
	 										.append("-")
	 										.append(Calendar.getInstance().get(Calendar.MONTH) + 1)
	 										.append("-")
	 										.append(Calendar.getInstance().get(Calendar.DATE))
	 										.append("-")
	 										.append(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
	 										.append("-")
	 										.append(Calendar.getInstance().get(Calendar.MINUTE))
	 										.append("-")
	 										.append(Calendar.getInstance().get(Calendar.SECOND))
	 										.toString();
	 	Util util = new Util();
	 	File filepath = new File(util.getUpload() + path);
	 	if(!filepath.exists()){
	 		filepath.mkdirs();
	 	}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletContext servletContext = request.getServletContext();
		File repository = (File) servletContext.getAttribute(Util.upload());
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(request);
		if(items != null && items.size() >0){
			for(int i=0; i< items.size(); i++){
				FileItem fileItem = items.get(i);
				if(!fileItem.isFormField()){
					File file = new File(Util.upload() + path+"/" + ymdhms + "_" + fileItem.getName());
					fileItem.write(file);
					Image srcImg = ImageIO.read(file); 
					if(widthHeights != null && widthHeights.size() >0){
						for(int j =0; j< widthHeights.size() ; j++ ){
							WidthHeight widthHeight = widthHeights.get(j);
							BufferedImage buffImg = null;
							buffImg = new BufferedImage(widthHeight.getWidth(), widthHeight.getHeight(), BufferedImage.TYPE_INT_RGB);   
							buffImg.getGraphics().drawImage(srcImg.getScaledInstance(widthHeight.getWidth(), widthHeight.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
							String postfix = StringUtils.substringAfterLast(fileItem.getName(),".");
							String newpath =  path + "/" + ymdhms + "_" + widthHeight.getWidth() + "_" + widthHeight.getHeight() + "." + postfix;
							ImageIO.write(buffImg, postfix, new File(Util.upload() + newpath));
							resultPath += newpath + ",";
						}
					}
					file.delete(); //将原始文件删除，节省磁盘空间
				}
			}
		}
		return resultPath;
	}
}
