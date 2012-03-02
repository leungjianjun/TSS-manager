package org.jsoup;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import nju.software.tss.util.Config;
import nju.software.tss.util.Log;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

public class TSSClient extends TimerTask {
	
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
	
	public static void main(String args[]) throws IOException{
		TSSClient tc = new TSSClient();
		System.out.println(tc.login("ljj09", "314.ljj.ec"));
	}

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
