package com.agodi.downloader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.agodi.helper.Lecture;

public class MediaDownloadManagerTest {

	@Test
	public void downloadCourseLecturesTest() throws IOException {
		String directory = System.getProperty("user.dir") + File.separator;
		ContentExtractor contentExtractor = new MockContentExtractor();
		List<Lecture> lectures = MediaDownloadManager.downloadCourseLectures(contentExtractor, directory);
		int index = 0;
		for (Lecture lecture : lectures) {
			assertEquals(++index, lecture.getIndex());
			assertTrue(lecture.getTitle() != null);
			assertTrue(lecture.getFileLocation().startsWith(directory + contentExtractor.getCourseName() + File.separator));
		}
	}

}
