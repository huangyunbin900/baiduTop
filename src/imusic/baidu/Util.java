package imusic.baidu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class Util {

	static PoolingClientConnectionManager cm;

	public static HttpClient getHttpClient() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory
				.getSocketFactory()));

		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(
				schemeRegistry);
		// 设置连接最大数
		cm.setMaxTotal(200);
		// 设置每个Route的连接最大数
		cm.setDefaultMaxPerRoute(20);
		// 设置指定域的连接最大数
		HttpHost localhost = new HttpHost("locahost", 80);
		cm.setMaxPerRoute(new HttpRoute(localhost), 50);
		HttpClient client = new DefaultHttpClient(cm);
		return client;
	}

	public static void closeHttpclient() {
		if (cm != null) {
			cm.shutdown();
		}
	}

	public static String getHtmlFromJson(String json) throws Exception {
		JSONObject all = JSONObject.parseObject(json);
		JSONObject data = all.getJSONObject("data");
		String result = data.get("html").toString();
		return result;
	}

	public static String getUrlContentByJava(String urlString) throws Exception {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		String result = convertStreamToString(inputStream);
		return result;
	}

	public static String getUrlContent(String urlString) throws Exception {

		HttpGet get = new HttpGet(urlString);
		HttpClient client = getHttpClient();
		HttpResponse res = client.execute(get);
		String result=EntityUtils.toString(res.getEntity());
		try {
		} finally {
			try {
				res.getEntity().getContent().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String convertStreamToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		return sb.toString();
	}

	public static void writeFile(StringBuffer buffer, String fileAddres)
			throws Exception {
		FileWriter  writer = new FileWriter(fileAddres, false);
		writer.write(buffer.toString());
		writer.close();
	}
	/*
	 * 追加内容到文件
	 */
	public static synchronized void addFile(StringBuffer buffer, String fileAddres)
			throws Exception {
		FileWriter  writer = new FileWriter(fileAddres, true);
		writer.write(buffer.toString());
		writer.close();
	}
	
	public static void deleteOldFile(String savaPath) {
		File file = new File(savaPath);         
		 if (file.isFile() && file.exists()) {   
			 file.delete(); 
		 }
	}


}
