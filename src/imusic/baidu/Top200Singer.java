package imusic.baidu;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class Top200Singer {
	static String savaPath = "c:\\BaiduTop200Singers.txt";
	
	public static void sava() throws Exception {
		sava(savaPath);
	}
	
	
	public static void sava(String savaPath) throws Exception {
		List<Singer> singers = getBaiduTop200Singers();
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		for (Singer singer : singers) {
			buffer.append(++i);
			buffer.append("|");
			buffer.append(singer.getName());
			buffer.append("\n");
		}
		Util.writeFile(buffer, savaPath);
	}
	
	
	public static List<Singer> getBaiduTop200Singers() throws Exception {
		List<Singer> singers = new ArrayList<Singer>();
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode tagNode = cleaner.clean(new URL(
				"http://music.baidu.com/top/artist"), "utf8");
		Object[] singerName1 = tagNode
				.evaluateXPath("//div[@class='artist-name']/text()");
		Object[] id1 = tagNode
				.evaluateXPath("//div[@class='artist-name']/a");

		Object[] singerName2 = tagNode
				.evaluateXPath("//span[@class='artist-name']/text()");
		Object[] id2 = tagNode
				.evaluateXPath("//span[@class='artist-name']/a");

		int length1 = singerName1.length;
		for (int i = 0; i < length1; i++) {
			Singer singer1 = new Singer();
			singer1.setName(singerName1[i].toString().trim());
			singer1.setId(((TagNode)id1[i]).getAttributeByName("href").toString().trim().substring(8));
			singers.add(singer1);
		}

		int length2 = singerName2.length;
		for (int i = 0; i < length2; i++) {
			Singer singer2 = new Singer();
			singer2.setName(singerName2[i].toString().trim());
			singer2.setId(((TagNode)id2[i]).getAttributeByName("href").toString().trim().substring(8));
			singers.add(singer2);
		}
		return singers;
	}

}
