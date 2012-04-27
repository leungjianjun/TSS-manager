package org.jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import nju.software.tss.gui.Main;
import nju.software.tss.model.Course;
import nju.software.tss.model.Section;
import nju.software.tss.util.Config;
import nju.software.tss.util.Helper;
import nju.software.tss.util.Log;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TSSClient extends TimerTask {
	
	public static Map<String,Course> coursePool = new HashMap<String,Course>();
	
	public int fileSize = 0;
	
	/**
	 * JSESSIONID是一个cookie值，用来保证登录后的连接，10分钟内不使用该值访问网站的<br>任意页面也就是10分钟内没有操作
	 * 则该值作废，这时只能重新登录。至于cookie<br>中的其他值作用不大。要注意的是，TSS使用全局登录，也就是在/global时
	 * 有一个JSESSIONID，<br>用于全局登录；还有一个/tss的JSESSIONID，用于tss的登录，所以全局登录后就能访问tss，邮箱等<br>
	 * 所有网站
	 */
	String JSESSIONID;
	
	/**
	 * 定时器，每十分钟运行一次，保持登录状态
	 */
	Timer timer;
	
	/**
	 * 真实的姓名，登录后将获取到
	 */
	String realName;
	
	/**
	 * TSS 登录
	 * 
	 * @param username
	 * 		用户名
	 * @param passwd
	 * 		密码
	 * @return
	 * 		登录成功返回true，失败返回false
	 * @throws IOException
	 * 		网站链接异常，可能链接超时，无法连接等
	 */
	public boolean login(String username,String passwd) throws IOException{
		Log.instance().info("正在登录");
		// 获取/GlobalLogin的JSESSIONID
		Connection connection = HttpConnection.connect("https://218.94.159.102/GlobalLogin/login.jsp?ReturnURL=http://218.94.159.102/tss/en/home/postSignin.html");
		try {
			Response response = connection.execute();
			this.JSESSIONID = response.cookie("JSESSIONID");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		//使用/GlobalLogin的JSESSIONID登录后获取/tss的JSESSIONID
		connection = HttpConnection.connect("https://218.94.159.102/GlobalLogin/loginservlet")
			.data("username", username)
			.data("password", passwd)
			.data("days", "90")
			.data("Submit", "Login").cookie("JSESSIONID",JSESSIONID);
		connection.request().method(Method.POST);
		Response response = connection.execute();
		this.JSESSIONID = response.cookie("JSESSIONID");
		if(checkLogin()){//登录成功后启动定时器，保持登录状态
			timer=new Timer();
			long activeTime = Config.preferenceStore().getLong(Config.ACTIVETIME);
	        timer.schedule(this,activeTime,activeTime);
	        Log.instance().info("登录成功");
	        //login success,do something
	        Main.tssWindow.sidebar.refreshRemote();
			return true;
		}
		Log.instance().error("登录失败");
		return false;
	}
	
	/**
	 * 该方法检查用户是否已经登录，如果已经登录，则设置{@link #realName}为用户的真实姓名<br>
	 * 注意不要从检查{@link #realName}来确定是否登录，因为sessionid可能已经过期
	 * @return
	 * 		已经登录返回true，否则返回false
	 * @throws IOException 
	 */
	public boolean checkLogin() throws IOException{
		if(this.JSESSIONID!=null){
			Document doc = Jsoup.connect("http://218.94.159.102/tss/en/home/mycourse.html").cookie("JSESSIONID", JSESSIONID).get();
			//匹配<td align="center" height="40"> Good morning 梁建均</td>
			String text = doc.select("td[align=center][height=40]").first().text();
			Log.instance().info(text);
			realName = text.replaceFirst("Goodnight","").replaceFirst("Good morning ","").replaceFirst("Good evening ","").replaceFirst("Good afternoon ","");
			return realName!=null;
		}else{
			return false;
		}
	}
	
	public String getRealName(){
		return this.realName;
	}
	
	/**
	 * 退出TSS
	 */
	public void exit(){
		Log.instance().info("登出TSS");
		if(timer!=null){
			timer.cancel();
		}
		this.JSESSIONID = null;
		this.realName = null;
	}
	
	/**
	 * 返回我的课程中的所有课程，但是课程里面只有ID，其他属性没有初始化
	 * @return
	 * @throws IOException 
	 */
	public List<Course> getMyCourses() throws IOException{
		List<Course> courses = new ArrayList<Course>();
		Document doc = Jsoup.connect("http://218.94.159.102/tss/en/home/mycourse.html").cookie("JSESSIONID", JSESSIONID).get();
		int i =0;
		for(Element e:doc.select("table.forum_text").first().child(0).children()){
			if(i<4){
				i++;
				continue;
			}
			Course course = Course.newCourse(e.child(0).text(),e.child(1).text().substring(3),e.child(3).text());
			setCourseTerm(course);
			courses.add(course);
		}
		
		return courses;
	}
	
	private void setCourseTerm(Course course){
		String terms[] = Config.preferenceStore().getString(Config.TERMS).split(" ");
		for(String term:terms){
			if(Config.preferenceStore().getString(term).contains(course.courseNo)){
				course.terms=term;
				return;
			}
		}
	}
	
	/**
	 * 获取所有在上的课程列表
	 * @return
	 * @throws IOException 
	 */
	public List<Section> getActiveCourse() throws IOException {
		List<Section> sections = new ArrayList<Section>();
		Section sec = null;
		Document doc = Jsoup.connect("http://218.94.159.102/tss/en/home/courselist.html").cookie("JSESSIONID", JSESSIONID).get();
		int i =0;
		for(Element e:doc.select("table.forum_text").first().child(0).children()){
			if(i<2){
				i++;
				continue;
			}
			char a =e.text().charAt(0);
			if(a>='0' && a<='9'){
				sec = new Section(e.text());
				sections.add(sec);
			}else if(a=='C'){
				continue;
			}else if(a=='c'){
				Course course = Course.newCourse(e.child(0).text(),e.child(1).text().substring(3),e.child(3).text());
				setCourseTerm(course);
				sec.courseList.add(course);
			}
		}
		return sections;
	}
	
	/**
	 * 获取所有已经结束的课程列表
	 * @return
	 * @throws IOException 
	 */
	public List<Section> getEndedCourse() throws IOException{
		List<Section> sections = new ArrayList<Section>();
		Section sec = null;
		Document doc = Jsoup.connect("http://218.94.159.102/tss/en/home/oldCourse.html").cookie("JSESSIONID", JSESSIONID).get();
		int i =0;
		for(Element e:doc.select("table.forum_text").first().child(0).children()){
			if(i<2){
				i++;
				continue;
			}
			char a =e.text().charAt(0);
			if(a>='0' && a<='9'){
				sec = new Section(e.text());
				sections.add(sec);
			}else if(a=='C'){
				continue;
			}else if(a=='c'){
				Course course = Course.newCourse(e.child(0).text(),e.child(1).text().substring(3),e.child(3).text());
				setCourseTerm(course);
				sec.courseList.add(course);
			}
		}
		return sections;
	}
	
	public File downloadFile(String path,String urlStr) throws IOException{
		URL url = new URL(urlStr);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestProperty("Cookie", "JSESSIONID="+JSESSIONID+";");
		InputStream is = con.getInputStream();
		File storageFile = new File(path+".tmp");
		File directory = new File(storageFile.getParent());
        if(!directory.isDirectory()){
        	directory.mkdirs();
        }
        fileSize = 0;
        OutputStream os = new FileOutputStream(storageFile);  
        byte[] b = new byte[1024 * 4];  
        int l = -1;  
        while ((l = is.read(b)) != -1) {
        	fileSize +=l;
        	os.write(b, 0, l);  
        }
        os.flush();  
        os.close();  
        con.disconnect();
        File file = new File(path);
        storageFile.renameTo(file);
        return file;
	}
	
	public void getCourseDLList(Course course) throws IOException{
		List<String> dwFilesURL = new ArrayList<String>();
		_getCourseDLList(dwFilesURL,"http://218.94.159.102/tss/en/"+course.courseNo+"/slide/");
		course.dwFilesURL = dwFilesURL;
	}
	
	public void getCourseUnDLList(Course course) throws IOException{
		List<String> dwFilesURL = new ArrayList<String>();
		List<String> undwFilesURL = new ArrayList<String>();
		_getCourseDLList(dwFilesURL,"http://218.94.159.102/tss/en/"+course.courseNo+"/slide/");
		
		for(String url:dwFilesURL){
			File file = new File(Helper.dlFileLocation(url, course));
			if(!file.exists()){
				undwFilesURL.add(url);
			}
		}
		course.unDWFilesURL = undwFilesURL;
	}
	
	private void _getCourseDLList(List<String> dwFilesURL,String url) throws IOException{
		Document doc = Jsoup.connect(url).cookie("JSESSIONID", JSESSIONID).get();
		for(Element e:doc.select("ul>a")){
			if(e.hasAttr("target")){
				dwFilesURL.add("http://218.94.159.102"+e.attr("href"));
			}else{
				//是文件夹，递归
				_getCourseDLList(dwFilesURL,"http://218.94.159.102"+e.attr("href"));
			}
		}
	}
	
	/*public static void main(String args[]) throws IOException{
		TSSClient tc = new TSSClient();
		System.out.println(tc.login("ljj09", "314.ljj.ec"));
		List<Section> sec = tc.getActiveCourse();
		for(Section s:sec){
			System.out.println(s.secName);
			for(Course c:s.courseList){
				System.out.println(c.courseName);
			}
		}
		//tc.downloadFile("H:/temp/a/Ch0+Course+Overview.ppt", "http://218.94.159.102/tss/en/c0705/slide/downloadSlides/Slides/Ch0+Course+Overview.ppt");
		Course course = Course.newCourse("c0705");
		tc.getCourseDLList(course);
		for(String url:course.dwFilesURL){
			System.out.println(url);
		}
		tc.getCourseUnDLList(course);
		for(String url:course.unDWFilesURL){
			System.out.println(url);
		}
		tc.downloadFile("H:/temp/a/计算机组织结构/TSS文件/作业答案/Ass4.ppt", "http://218.94.159.102/tss/en/c0520/slide/downloadSlides/%D7%F7%D2%B5%B4%F0%B0%B8/Ass4.ppt");
		System.out.println(tc.fileSize/1024.0);
	}*/

	/**
	 * 
	 */
	@Override
	public void run() {
		Log.instance().info("保持登录");
		try {
			if(this.JSESSIONID!=null){
				HttpConnection.connect("http://218.94.159.102/tss/active.html").cookie("JSESSIONID", JSESSIONID).execute();
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.instance().error("链接网站失败，检查网络连接或重新登录");
		}
	}

}
