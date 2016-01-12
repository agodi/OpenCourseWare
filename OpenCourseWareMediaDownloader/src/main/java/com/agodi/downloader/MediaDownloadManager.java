package com.agodi.downloader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.agodi.helper.Lecture;

public class MediaDownloadManager {
	
	public static List<Lecture> downloadCourseLectures(String courseURL, String parentDirectory) {
		List<Lecture> lectures = null;
		try {
			String courseName = WebContentExtractor.getCourseName(courseURL);
			List<String> urls = WebContentExtractor.extractLecturesUrls(courseURL);
			lectures = WebContentExtractor.getLecturesFromUrls(urls);
			downloadVideos(lectures, parentDirectory, courseName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lectures;
	}
	
	/**
	 * Creates a folder to store the course's videos and downloads each video
	 * @param lectures list of objects containing the lectures info
	 * @param filePath where to create a folder for the course's videos
	 * @param courseName name of the course
	 * @throws IOException if there is an error downloading the videos
	 */
	private static void downloadVideos(List<Lecture> lectures, String filePath, String courseName)
			throws IOException {
		StringBuilder parentFolderName = new StringBuilder(filePath).append(courseName).append(File.separator);

		File parentFolder = new File(parentFolderName.toString());
		if (!parentFolder.exists()) {
			parentFolder.mkdir();
		}

		for (Lecture lecture : lectures) {
			WebContentExtractor.downloadVideo(parentFolderName.toString(), lecture);
		}
	}

}
