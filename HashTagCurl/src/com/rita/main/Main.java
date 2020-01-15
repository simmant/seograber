package com.rita.main;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

	static List<String> paresedList = new ArrayList<>();

	static String webUrl = "https://www.xyz.com";

	static int count = 0;

	static String keyword = "xyz";

	static List<StringBuilder> pagesData = new ArrayList<>();

	public static void main(String[] ar) throws Exception {
		MetaParseThread metaParseThread = new MetaParseThread();
		Scanner sc = new Scanner(System.in);

		System.out.println("Please enter website url: ");
		webUrl = sc.next();
		keyword = webUrl.split("\\.")[1];

		metaParseThread.start();
		parsePage(webUrl);

	}

	private static void parsePage(String siteUrl) {
		if (!paresedList.contains(siteUrl)) {
			// System.out.println("url :"+siteUrl);
			paresedList.add(siteUrl);
			List<String> pageLinks = new ArrayList<>();
			StringBuilder str = new StringBuilder();
			try {
				URL url = new URL(siteUrl);
				InputStream io = url.openStream();
				Scanner sc = new Scanner(io);
				while (sc.hasNext()) {
					String s = sc.nextLine();
					str.append(s);
					pageLinks.addAll(getLinks(s));
				}
				pagesData.add(str);
				count++;
				// System.out.println("pageLinks :"+pageLinks.size());
				for (String link : pageLinks) {
					if (!link.equals("")) {
						parsePage(link);
					}

				}

			} catch (Exception e) {
				System.out.println(siteUrl + " could'nt be parsed.");
			}
		}
	}

	private static List<String> getLinks(String line) {
		String data[] = line.split(">");
		List<String> urlList = new ArrayList<>();
		for (String word : data) {
			if (word.contains("href")) {
				String link = getLink(word);
				if (!link.equals("") && link.contains(keyword)) {
					link = link.replace("href=", "");
					link = link.replace("\"", "");
					urlList.add(link);
				}
			}
		}
		return urlList;
	}

	private static String getLink(String hrefData) {

		String[] details = hrefData.split(" ");
		String url = "";
		for (String data : details) {
			if (data.startsWith("href=")) {
				url = data;
				break;
			}

		}
		return url;
	}

}
