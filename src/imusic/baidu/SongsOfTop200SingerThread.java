package imusic.baidu;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SongsOfTop200SingerThread extends Thread {
	private static Log log=LogFactory.getLog(SongsOfTop200SingerThread.class);
	private List<Singer> singers;
	
 @Override
public void run() {
	 
	 log.info(Thread.currentThread()+"---start");
	 log.info("list size---"+singers.size());
		try {
			for (Singer singer : singers) {
				SongsOfTop200Singer.CompleteSongNum(singer);
				StringBuffer buffer = SongsOfTop200Singer.getSongsBySinger(singer);
				Util.addFile(buffer, SongsOfTop200Singer.savaPath);
//				SongsOfTop200Singer.addBuffer(buffer);
				log.info(singer.getSongNum()+"---id---"+singer.getId()+"---name--"+singer.getName()+"---has over.---Thread---"+Thread.currentThread());
//				sleep(1000);
			}
			SongsOfTop200Singer.reduseThreadNum();
			if(SongsOfTop200Singer.threadNum<=0){
//				Util.writeFile(SongsOfTop200Singer.songbuffer, SongsOfTop200Singer.savaPath);
				Util.closeHttpclient();
				}
			
			
			log.info(Thread.currentThread()+"---end");
		} catch (Exception e) {
			e.printStackTrace();
		}
}

public List<Singer> getSingers() {
	return singers;
}

public void setSingers(List<Singer> singers) {
	this.singers = singers;
}
}
