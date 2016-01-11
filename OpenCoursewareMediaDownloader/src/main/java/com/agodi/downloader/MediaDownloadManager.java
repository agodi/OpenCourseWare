package com.agodi.downloader;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.agodi.helper.Lecture;

public class MediaDownloadManager {
	
	public static List<Lecture> downloadCourseLectures(String courseURL, String parentDirectory) {
		List<Lecture> lectures = null;
		try {
			Document coursePage = Jsoup.connect(courseURL).get();
			String courseName = WebContentExtractor.getCourseName(coursePage);
			List<String> urls = WebContentExtractor.extractLecturesUrls(coursePage);
			lectures = WebContentExtractor.getLecturesFromUrls(urls);
			WebContentExtractor.downloadLecturesVideos(lectures, parentDirectory, courseName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lectures;
	}

	public static void main(String args[]) {
		downloadCourseLectures(
				"http://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-006-introduction-to-algorithms-fall-2011/",
				"C:\\Users\\Arturo\\Downloads");
	}

}
