package nju.software.tss.model;

import java.util.ArrayList;
import java.util.List;

public class Section {
	
	public String secName;
	public List<Course> courseList = new ArrayList<Course>();
	public Section(String sec){
		this.secName = sec;
	}

}
