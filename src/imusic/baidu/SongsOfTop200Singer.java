package imusic.baidu;

import java.net.URL;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class SongsOfTop200Singer {
	private static Log log=LogFactory.getLog(SongsOfTop200Singer.class);
	static String savaPath = "c:\\BaiduTop200SingerSongs.txt";
	static StringBuffer songbuffer =new StringBuffer();
	static int threadNum=1;
	
	public static void sava() throws Exception {
		sava(savaPath);
	}
	
	
	
	public  static void sava(String savaPath)
			throws Exception {
		Util.deleteOldFile(savaPath);
		List<Singer> singers = Top200Singer.getBaiduTop200Singers();
//		singers=singers.subList(0, 10);
		int threadnum=threadNum;
		List<Singer> threadSingers=null;
		int  oneThreadDoNum=singers.size()/threadnum;
		for (int i = 0; i < threadNum; i++) {
			if(threadNum-1==i){
				 threadSingers=singers.subList(i*oneThreadDoNum, singers.size());	
			}
			else{
			    threadSingers=singers.subList(i*oneThreadDoNum, (i+1)*oneThreadDoNum);
			}
			SongsOfTop200SingerThread thead=new SongsOfTop200SingerThread();
			thead.setSingers(threadSingers);
			thead.start();
			 
		}
		
	}

	

	


	public static  void CompleteSongNum(Singer singer) throws Exception {
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode tagNode = cleaner.clean(new URL(
				"http://music.baidu.com/artist/" + singer.getId()), "utf8");
		Object[] singerIds = tagNode
				.evaluateXPath("//li[@class='ui-tab-active  ']/a/text()");
		String songNum = singerIds[0].toString().trim().replaceAll("歌曲", "")
				.replaceAll("\\(", "").replaceAll("\\)", "");
		singer.setSongNum(songNum);
	}

	public static  StringBuffer getSongsBySinger(Singer singer) throws Exception {
		StringBuffer buffer = new StringBuffer();
		HtmlCleaner cleaner = new HtmlCleaner();
		int songNum = Integer.valueOf(singer.getSongNum());
		int page = songNum / 20 + 1;
		for (int i = 0; i < page; i++) {
			String url = "http://music.baidu.com/data/user/getsongs?start=" + i
					* 20 + "&ting_uid=" + singer.getId() + "&order=hot";
			
			long time3=System.currentTimeMillis();
			String songs = Util.getUrlContent(url);
			log.info("get detail 20 song use  time---"+(System.currentTimeMillis()-time3)+"---"+Thread.currentThread());
			
			String songshtml = Util.getHtmlFromJson(songs);
			
			TagNode tagNode = cleaner.clean(songshtml);
			Object[] songName = tagNode
					.evaluateXPath("//span[@class='song-title ']/text()");
			Object[] albumName = tagNode
					.evaluateXPath("//span[@class='album-title']/text()");
			for (int j = 0; j < albumName.length; j++) {
				buffer.append(singer.getName());
				buffer.append("|");
				buffer.append(i*20+j + 1);
				buffer.append("|");
				buffer.append(songName[j].toString().trim());
				buffer.append("|");
				buffer.append(albumName[j].toString().trim());
				buffer.append("\n");
			}
		}

		return buffer;

	}
	
	
	public synchronized  static  void  reduseThreadNum(){
		threadNum--;
	}
	
	public static void  addBuffer(StringBuffer buffer){
		songbuffer.append(buffer);
	}

}
