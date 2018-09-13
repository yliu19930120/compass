package com.core.request;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.utils.HttpConnectionManager;
import com.google.gson.Gson;



public class Request {
	
	 private static Logger logger = LoggerFactory.getLogger(Request.class);  
	
	 private static HttpConnectionManager manager = HttpConnectionManager.getInstance();
	 	 

	/* 发送请求 */
	public  static String get(String url) {
		
		CloseableHttpClient client = manager.getHttpClient();
		
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response;
		get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		String body=null;
		try {
			response = client.execute(get);
            body = EntityUtils.toString(response.getEntity(),"GBK");
			
			response.close();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return body;
	}
	
	/* 发送请求 */
	public  static String get(String url,String path) {
		
		CloseableHttpClient client = manager.getHttpClient();
		
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response;
		get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		String body=null;
		try {
			response = client.execute(get);
            body = EntityUtils.toString(response.getEntity(),"UTF-8");
			
			response.close();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writeToFile(body,path);
		return body;
	}
	
    //获取图片响应体
	public static CloseableHttpResponse getImage(String url){
		
        CloseableHttpClient client = manager.getHttpClient();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = null;
		get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		try {
			response = client.execute(get);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
		}
	
	//POST请求
	public static boolean post(String url,Map<String,Object> parms){
		 boolean isSuccess = false;
		    
		    HttpPost post = null;
		    try {
		    	CloseableHttpClient httpClient = manager.getHttpClient();

		        // 设置超时时间
		        post = new HttpPost(url);
		        // 构造消息头
		        post.setHeader("Content-type", "application/json; charset=utf-8");
		        post.setHeader("Connection", "Close");
		        String sessionId = getSessionId();
		        post.setHeader("SessionId", sessionId);
		        post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		                    
		        // 构建消息实体
		        String parm = new Gson().toJson(parms);
		        System.out.println(parms);
		        StringEntity entity = new StringEntity(parm, Charset.forName("UTF-8"));
		        entity.setContentEncoding("UTF-8");
		        // 发送Json格式的数据请求
		        entity.setContentType("application/json");
		        post.setEntity(entity);
		            
		        HttpResponse response = httpClient.execute(post);
		            
		        // 检验返回码
		        int statusCode = response.getStatusLine().getStatusCode();
		        if(statusCode != HttpStatus.SC_OK){
		        	logger.info("请求出错: "+statusCode);
		            isSuccess = false;
		        }else{
		            int retCode = 0;
		            String sessendId = "";
		            // 返回码中包含retCode及会话Id
		            for(Header header : response.getAllHeaders()){
		                if(header.getName().equals("retcode")){
		                    retCode = Integer.parseInt(header.getValue());
		                }
		                if(header.getName().equals("SessionId")){
		                    sessendId = header.getValue();
		                }
		            }
		            
		            if(200 != retCode ){
		                // 日志打印
		            	logger.info("error return code,  sessionId: "+sessendId+"\t"+"retCode: "+retCode);
		                isSuccess = false;
		            }else{
		                isSuccess = true;
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        isSuccess = false;
		    }finally{
		        if(post != null){
		            try {
		                post.releaseConnection();
		                Thread.sleep(500);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		    return isSuccess;
	}
	
	// 构建唯一会话Id
	public static String getSessionId(){
	    UUID uuid = UUID.randomUUID();
	    String str = uuid.toString();
	    return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
	}
	
	public static void writeToFile(String input,String path){
		String str=input;
        FileWriter writer;
        try {
            writer = new FileWriter(path);
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	

}
