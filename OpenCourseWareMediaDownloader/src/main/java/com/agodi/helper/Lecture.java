package com.agodi.helper;

/**
 * Value object to hold the name of the lecture and its video URL.
 */
public class Lecture {

	private int index;
	private String title;
	private String videoUrl;
	private String fileLocation;

	public Lecture(int index, String title, String videoUrl) {
		this.index = index;
		this.title = title;
		this.videoUrl = videoUrl;
	}

	public int getIndex() {
		return index;
	}

	public String getTitle() {
		return title;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

}
