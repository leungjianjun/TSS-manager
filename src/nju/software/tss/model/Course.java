package nju.software.tss.model;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.TSSClient;

public class Course {
	
	public String courseNo;//唯一
	public String courseName;
	public List<Instructor> instructors;
	public String lastUpdate;
	public String terms;//
	public List<String> dwFilesURL;
	public List<String> unDWFilesURL;
	
	private Course(String cn){
		this.courseNo = cn;
	}
	
	private Course(String cn,String name,String lu){
		this.courseNo = cn;
		this.courseName = name;
		this.lastUpdate = lu;
	}
	
	public static Course newCourse(String cn){
		Course course = TSSClient.coursePool.get(cn);
		if(course==null){
			course = new Course(cn);
			TSSClient.coursePool.put(cn, course);
		}
		return course;
	}
	
	public static Course newCourse(String cn,String name,String lu){
		Course course = TSSClient.coursePool.get(cn);
		if(course==null){
			course = new Course(cn,name,lu);
			TSSClient.coursePool.put(cn, course);
		}
		return course;
	}
	
	public boolean addDWFileURL(String url){
		if(dwFilesURL==null) dwFilesURL= new ArrayList<String>();
		return dwFilesURL.add(url);
	}
	
	public boolean equals(Course course){
		return this.courseNo.endsWith(course.courseNo);
	}
	
	public static void main(String args[]){
		String url = "/tss/adaf/adfdasfdsa/asdfas.pgf";
		System.out.println(url.substring(url.lastIndexOf('/')+1));
	}

}
