package tss.test;

public class testUDecode {
	
	public static void main(String args[]) {  
	    String str = "\u5927\u4e09\u4e0a \u5927\u4e09\u4e0b";  
	    for(char ch : "作业和答案".toCharArray()) {  
	        if(ch > 128) {  
	            System.out.print("\\u"+Integer.toHexString(ch));  
	        } else {  
	            System.out.print(ch);  
	        }  
	    }  
	    System.out.println();  
	    System.out.println("2--"+str);  
	    str = "make in \\u4e2d\\u56fd";  
	  
	    String v = "'"+str+"'";  
	    System.out.println("3--"+v);  
	  
	    /*try {  
	        //System.out.println(new JSONTokener(v).nextValue().toString());  
	    } catch (JSONException e) {  
	        e.printStackTrace();  
	    } */ 
	}  
}
