package com.agodi.downloader;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.agodi.helper.Lecture;

public class MediaDownloadManagerTest {

	@Test
	public void downloadCourseLecturesTest() {
		String directory = "/usr/local/";
		List<Lecture> lectures = MediaDownloadManager.downloadCourseLectures(new MockContentExtractor(), directory);
		int index = 0;
		for (Lecture lecture : lectures) {
			assertEquals(++index, lecture.getIndex());
			assertTrue(lecture.getTitle() != null);
			assertTrue(lecture.getFileLocation().startsWith(directory));
		}
	}

}
