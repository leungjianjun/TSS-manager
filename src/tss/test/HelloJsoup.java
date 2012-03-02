package tss.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HelloJsoup {
	
	public static void main(String args[]) throws IOException{
		Document doc1 = Jsoup.connect("https://218.94.159.102/GlobalLogin/loginservlet")
				  .data("username", "ljj09").data("password", "314.ljj.ec").data("days", "90").data("Submit", "Login")
				  .post();
		System.out.println(doc1.html());
		//Document doc =  Jsoup.connects("https://218.94.159.102/GlobalLogin/login.jsp").get();
		//Document doc =  Jsoup.connect("https://chrome.google.com/webstore/category/home").get();
		//System.out.println(doc.html());
	}

}


























