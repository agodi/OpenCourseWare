package com.agodi.downloader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.agodi.helper.Lecture;

public class MediaDownloadManager {
	
	/**
	 * Downloads the course lectures into the given directory
	 * @param extractor used to get the lectures
	 * @param parentDirectory folder where the videos will be stored
	 * @return
	 */
	public static List<Lecture> downloadCourseLectures(ContentExtractor extractor, String parentDirectory) {
		List<Lecture> lectures = null;
		try {
			String courseName = extractor.getCourseName();
			List<String> urls = extractor.extractLecturesUrls();
			lectures = extractor.getLecturesFromUrls(urls);
			downloadVideos(extractor, lectures, parentDirectory, courseName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lectures;
	}
	
	/**
	 * Creates a folder to store the course's videos and downloads each video
	 * @param lectures list of objects containing the lectures info
	 * @param directory where to create a folder for the course's videos
	 * @param courseName name of the course
	 * @throws IOException if there is an error downloading the videos
	 */
	private static void downloadVideos(ContentExtractor extractor, List<Lecture> lectures, String directory, String courseName)
			throws IOException {
		StringBuilder parentFolderName = new StringBuilder(directory).append(courseName).append(File.separator);

		File parentFolder = new File(parentFolderName.toString());
		if (!parentFolder.exists()) {
			parentFolder.mkdir();
		}

		for (Lecture lecture : lectures) {
			extractor.downloadVideo(parentFolderName.toString(), lecture);
		}
	}

}
