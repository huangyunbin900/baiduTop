
public class T {
	
	
	public static void main(String[] args) {
//		String str = "ÖÜ½ÜÂ×23£»£»£»";
//		String[] tmp = str.split("//s+");
//		for (String string : tmp) {
//			System.err.println(string);
//		}
		
		String str = "word:love  property:v meaning:°®";
		  String[] strs = str.split(":\\w+\\s+");
		  for (String string : strs) {
			System.err.println(string);
		}
	}

}
