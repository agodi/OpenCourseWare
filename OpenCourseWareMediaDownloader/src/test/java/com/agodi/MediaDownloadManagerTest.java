package com.agodi;

import java.io.File;

import org.junit.Test;

import com.agodi.downloader.MediaDownloadManager;

public class MediaDownloadManagerTest {
	
	@Test
	public void downloadCourseLecturesTest() {
		File root = File.listRoots()[0];
		String tempDir = root.getAbsolutePath() + File.separator + "temp" + File.separator;
System.out.println(root.getAbsolutePath());
		MediaDownloadManager.downloadCourseLectures(
				"http://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-006-introduction-to-algorithms-fall-2011/", 
				"C:\\Users\\Arturo\\Downloads\\");
	}

}
