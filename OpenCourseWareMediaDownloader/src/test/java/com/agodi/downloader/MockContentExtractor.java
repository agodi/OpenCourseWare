package com.agodi.downloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.agodi.helper.Lecture;

public class MockContentExtractor implements ContentExtractor {

	public List<String> extractLecturesUrls() throws IOException {
		String[] urls = {"Mock Lecture 1", "Mock Lecture 2"};
		return Arrays.asList(urls);
	}

	public List<Lecture> getLecturesFromUrls(List<String> urls) throws IOException {
		List<Lecture> lectures = new ArrayList<Lecture>();
		int index = 0;
		for(String url : urls) {
			Lecture lecture = new Lecture(++index, url, "http:\\" + url);
			lectures.add(lecture);
		}
		return lectures;
	}

	public void downloadVideo(String directory, Lecture lecture) throws IOException {
		String filePath = new StringBuilder(directory)
				.append(lecture.getTitle())
				.append(".mp4")
				.toString();
		lecture.setFileLocation(filePath);
	}

	public String getCourseName() throws IOException {
		return "MockCourse";
	}

}
