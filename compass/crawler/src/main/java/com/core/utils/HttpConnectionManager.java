package com.core.utils;

import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Http连接池
 * 
 * @author Yonghua Liu
 * @email liuyonghua@bly100.com
 * @date 2018年1月16日下午4:34:42
 */
public class HttpConnectionManager {
	
	private static Logger logger = LoggerFactory.getLogger(HttpConnectionManager.class);  
	
	PoolingHttpClientConnectionManager cm = null;
    
	private static HttpConnectionManager single= new HttpConnectionManager();
	
	/*单例*/
	public static HttpConnectionManager getInstance(){
		return single;
	}
	
	/*构造方法私有化*/
    private HttpConnectionManager() {
		super();
		init();
	}

	public void init() {
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm =new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
        logger.debug("建立HttpClient连接池------------------------------------------------------------------");
        logger.debug("###########################################################################");
    }

    public CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        /*CloseableHttpClient httpClient = HttpClients.createDefault();//如果不采用连接池就是这种方式获取连接*/
        return httpClient;
    }
}
