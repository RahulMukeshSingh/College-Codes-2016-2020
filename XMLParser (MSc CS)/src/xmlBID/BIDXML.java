package xmlBID;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BIDXML {

	public ArrayList<String> xmlTraversal(String tagData, String tagNames, ArrayList<String> result, int index) {
		if (tagNames.endsWith("/")) {
			tagNames = tagNames.substring(0, (tagNames.length() - 1));
		}
		String[] tags = tagNames.split("/");
		String tagStart = "<" + tags[index].trim() + ">";
		String tagEnd = "</" + tags[index].trim() + ">";
		int sLen = tagStart.length(), eLen = tagEnd.length();
		int startIndex = tagData.indexOf(tagStart);
		int endIndex = tagData.indexOf(tagEnd);
		while (startIndex != -1 || endIndex != -1) {
			if (index == (tags.length - 1)) {
				result.add(tagData.substring(startIndex + sLen, endIndex));
			} else if (index < (tags.length - 1)) {
				xmlTraversal(tagData.substring(startIndex + sLen, endIndex), tagNames, result, (index + 1));
			}
			tagData = tagData.substring(0, startIndex) + tagData.substring(endIndex + eLen, tagData.length());
			startIndex = tagData.indexOf(tagStart);
			endIndex = tagData.indexOf(tagEnd);
		}
		return result;
	}
	
	public static void main(String[] args) {
		BIDXML bXML = new BIDXML();
		String xmlData = "<?xml version='1.0' encoding='UTF-8'?>" + "<notes>"
				+ "<note><to>Tove1</to><from>Jani1</from><heading>Reminder1</heading><body>Don't forget me this weekend!1</body></note>"
				+ "<note><to>Tove2</to><from>Jani2</from><heading>Reminder2</heading><body>Don't forget me this weekend!2</body></note>"
				+ "<note><to>Tove3</to><from>Jani3</from><heading>Reminder3</heading><body>Don't forget me this weekend!3</body></note>"
				+ "<note><to>Tove4</to><from>Jani4</from><heading>Reminder4</heading><body>Don't forget me this weekend!4</body></note>"
				+ "</notes>";
		ArrayList<String> result = new ArrayList<String>();
		bXML.xmlTraversal("", "notes/note/", result, 0);
		for (String r : result) {
			System.out.println(r);
		}
	}

}
