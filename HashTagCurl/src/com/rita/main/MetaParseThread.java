package com.rita.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class MetaParseThread extends Thread {

	public void run() {

		try {
			Thread.sleep(10000);

			//System.out.println("INIT SIZE: " + Main.pagesData.size());
			for (int i = 0; i < Main.pagesData.size(); i++) {
				//System.out.println("IN THE LOOP: " + Main.pagesData.size());
				Thread.sleep(10000);

				String data = Main.pagesData.get(i).toString();
				//System.out.println("data :" + data);
				parseMetaAndTitle(data);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void parseMetaAndTitle(String line) {
		String data[] = line.split(">");
		for (String word : data) {
			if (word.toUpperCase().contains("TITLE")) {
				ArrayList<String> arrayList = new ArrayList<>();
				arrayList.add("<");
				arrayList.add("TITLE");
				arrayList.add("title");
				arrayList.add("/");
				System.out.println(getActualContent(word, arrayList));
			}
			
			if (word.toUpperCase().contains("META")) {
				ArrayList<String> arrayList = new ArrayList<>();
				arrayList.add("<");
				arrayList.add("META");
				arrayList.add("meta");
				arrayList.add("/");
				System.out.println(getActualContent(word, arrayList));
			}
			
		}
	}

	private String getActualContent(String dataString, List<String> garbage) {

		for (String garb : garbage) {
			dataString = dataString.replace(garb, "");
		}
		return dataString.trim();
	}

}
