package nju.software.tss.util;

import java.io.File;
import java.io.UnsupportedEncodingException;

import nju.software.tss.model.Course;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Helper {
	
	public static String encode(String raw){
		return Base64.encode(raw.getBytes());
	}
	
	public static String decode(String encode){
		try {
			return new String(Base64.decode(encode));
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String args[]){
		String text = "";
		System.out.println(encode(text));
		System.out.println(decode(encode(text)));
	}
	
	public static String dlFileLocation(String url,Course course){
		int l = (new String("http://218.94.159.102/tss/en/"+course.courseNo+"/slide/downloadSlides")).length();
		try {
			return Config.preferenceStore().getString(Config.WORKSPACE)+"/"+course.terms+"/"+course.courseName+"/"+Config.preferenceStore().getString(Config.TSS_FILE)+java.net.URLDecoder.decode(url,"GBK").substring(l);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static String getFileExt(File file){
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');
		if(i<0){
			return null;
		}
		return fileName.substring(i);
	}
	
	public static String getFileExt(String fileName){
		int i = fileName.lastIndexOf('.');
		if(i<0){
			return null;
		}
		return fileName.substring(i);
	}

}
