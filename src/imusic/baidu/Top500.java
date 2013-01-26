package imusic.baidu;

import java.net.URL;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class Top500 {
	static String savaPath = "c:\\BaiduTop500Songs.dat";

	public static void sava() throws Exception {
		sava(savaPath);
	}
	
	public static void sava(String savaPath) throws Exception {
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode tagNode = cleaner.clean(new URL(
				"http://music.baidu.com/top/dayhot"), "utf8");
		Object[] musicName = tagNode
				.evaluateXPath("//span[@class='song-title ']/text()");
		Object[] singerName = tagNode
				.evaluateXPath("//span[@class='singer']/text()");
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 500; i++) {
			buffer.append(i + 1);
			buffer.append("|");
			buffer.append(musicName[i].toString().trim());
			buffer.append("|");
			String  name=singerName[i].toString().trim();
			name = handleName(name);
			buffer.append(name);
			buffer.append("\n");
		}
		Util.writeFile(buffer, savaPath);
		
	}
	
	public static String handleName(String name){
		if(name.contains("/")){
			String[] names=name.split("/");
			String manyName="";
			for (int j = 0; j < names.length; j++) {
				manyName+=names[j].trim()+"/";
			}
			manyName=manyName.substring(0,manyName.length()-1);
			return manyName;
		}
		return name;
	}
}
