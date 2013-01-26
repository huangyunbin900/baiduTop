package imusic.baidu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class Baidu {
	private static Log log=LogFactory.getLog(Baidu.class);

	static {
		initLog();
		init();
	}
	
	

	public static void main(String[] args) throws Exception {
		long time1=System.currentTimeMillis();
		Top500.sava();
		log.info("top500 use time---"+(System.currentTimeMillis()-time1)/1000);
		long time2=System.currentTimeMillis();
		Top200Singer.sava();
		log.info("Top200Singer use  time---"+(System.currentTimeMillis()-time2)/1000);
		long time3=System.currentTimeMillis();
		SongsOfTop200Singer.sava();
		log.info("SongsOfTop200Singer use  time---"+(System.currentTimeMillis()-time3)/1000);
	}

	public static void init() {
		
		  Properties config=initConfig();
				try {
			Top500.savaPath = config.getProperty("top500SavaPath");
			Top200Singer.savaPath = config.getProperty("top200SingerSavaPath");
			SongsOfTop200Singer.savaPath = config.getProperty("songsOfTop200SingerSavaPath");
			SongsOfTop200Singer.threadNum=Integer.valueOf(config.getProperty("threadNum"));
			
			log.info("Top500.savaPath---"+config.getProperty("top500SavaPath"));
			log.info("threadNum---"+Integer.valueOf(config.getProperty("threadNum")));
				}catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	
	public static void initLog(){
		InputStream in = null;
		try {
			in = new FileInputStream("/home/music/baidu/log4jServer.properties");
			PropertyConfigurator.configure("/home/music/baidu/log4jServer.properties");
			if(log.isInfoEnabled())
				log.info("server log about info is ok");
			log.info("server log is ok");
		} catch (FileNotFoundException e) {
		}
		if(in==null){
			try {
				in = new FileInputStream("c:\\baidu\\log4jLocal.properties");
				PropertyConfigurator.configure("c:\\baidu\\log4jLocal.properties");
				log.info("local log is ok");
			} catch (FileNotFoundException e) {
			}
		}
		try {
			in.close();
		} catch (Exception e2) {
			log.error("close log config file fail");
		}
		
		
	}
	
	public static Properties initConfig() {
		Properties config = new Properties();
		InputStream in = null;
		try {
		in = new FileInputStream("/home/music/baidu/baiduServer.properties");
		log.info("config path---/home/imusic/baidu/baidu.properties");
		log.info("server config ok");
		}catch (Exception e) {
			log.info(e);
			log.error("server config fail");
		}
		
		if (in == null) {
			try {
			in = new FileInputStream("c:\\baidu\\baiduLocal.properties");
			log.info("config path---c:\\baidu\\baidu.properties");
			log.info("local config ok");
			}catch (Exception e) {
				e.printStackTrace();
				log.error("local config fail");
			}
		}
		try {
			config.load(in);
		} catch (Exception e) {
			log.error("load config fail");
		}finally{
			try {
				in.close();
			} catch (Exception e2) {
				log.error("close config file fail");
			}
		}
		return config;
	}
	

}
