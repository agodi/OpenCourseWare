package com.agodi.downloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.agodi.helper.Lecture;

/**
 * Class in charge of getting the content of the course's web page
 */
public class WebContentExtractor {

	/**
	 * Gets the URLs of each lecture in the course
	 * 
	 * @param coursePage
	 *            DOM of the course's web page
	 * @return list of URLs
	 * @throws IOException
	 *             if there was an error connecting and retrieving the URLs
	 */
	public static List<String> extractLecturesUrls(Document coursePage) throws IOException {
		Element lecturesLink = coursePage.getElementsContainingOwnText("Lecture Videos").first();

		Document lecturesPage = Jsoup.connect(lecturesLink.absUrl("href")).get();
		Elements lecturesListing = lecturesPage.getElementsByClass("medialisting");

		List<String> urls = new ArrayList<String>();
		for (Element lecture : lecturesListing) {

			String url = lecture.child(0).absUrl("href");

			urls.add(url);
		}

		return urls;
	}

	/**
	 * Gets the lecture info for each URL
	 * 
	 * @param urls
	 *            list of URLs for the lectures
	 * @return list of lectures containing the lectures info
	 * @throws IOException
	 *             if there was an error connecting and retrieving the lectures
	 *             info
	 */
	public static List<Lecture> getLecturesFromUrls(List<String> urls) throws IOException {
		List<Lecture> lectures = new ArrayList<Lecture>();
		int index = 0;
		for (String url : urls) {
			Document doc = Jsoup.connect(url).get();

			String title = doc.getElementById("parent-fieldname-title").ownText();
			Element downloadLink = doc.getElementsContainingOwnText("Internet Archive").first();
			String videoUrl = downloadLink.absUrl("href");

			Lecture lecture = new Lecture(++index, title, videoUrl);
			lectures.add(lecture);
		}

		return lectures;
	}

	/**
	 * Retrieves and stores the video associated to the lecture
	 * 
	 * @param filePath
	 *            where the lecture's video will be stored
	 * @param lecture
	 *            object containing the lectures video
	 * @throws IOException
	 *             if the URL for the lecture is not valid or if a connection
	 *             can not be opened for the lecture's URL or if an input stream
	 *             can not be retrieved from the connection or if an output
	 *             stream can not be opened to store the videos content or if
	 *             there is an error reading from the input stream or if there
	 *             is an error writing to the output stream
	 */
	public static void downloadVideo(String filePath, Lecture lecture) throws IOException {
		String title = lecture.getTitle();
		String videoUrl = lecture.getVideoUrl();
		StringBuilder fileName = new StringBuilder(filePath)
				.append(title.substring(0, title.indexOf(":")))
				.append(videoUrl.substring(videoUrl.lastIndexOf(".")));

		File file = new File(fileName.toString());
		if (file.exists()) {
			return;
		}

		URL url = new URL(lecture.getVideoUrl());
		URLConnection connection = url.openConnection();
		int contentLength = connection.getContentLength();

		BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
		FileOutputStream fos = new FileOutputStream(file);

		final byte data[] = new byte[4096];
		int count;

		while ((count = bis.read(data, 0, 4096)) != -1) {
			fos.write(data, 0, count);
		}
		
		try {
			bis.close();
			fos.close();
		} catch (IOException iox) {
			// TODO
		}
		
		lecture.setFileLocation(fileName.toString());

		assert(file.length() == contentLength);
	}

	/**
	 * Creates a folder to store the course's videos and downloads each video
	 * @param lectures list of objects containing the lectures info
	 * @param filePath where to create a folder for the course's videos
	 * @param courseName name of the course
	 * @throws IOException if there is an error downloading the videos
	 */
	public static void downloadLecturesVideos(List<Lecture> lectures, String filePath, String courseName)
			throws IOException {
		StringBuilder parentFolderName = new StringBuilder(filePath).append(courseName).append(File.separator);

		File parentFolder = new File(parentFolderName.toString());
		if (!parentFolder.exists()) {
			parentFolder.mkdir();
		}

		for (Lecture lecture : lectures) {
			downloadVideo(parentFolderName.toString(), lecture);
		}
	}

	/**
	 * Gets the name of the course
	 * @param coursePage DOM of the course's web page
	 * @return the name of the course
	 */
	public static String getCourseName(Document coursePage) {
		Elements elements = coursePage.getElementsByClass("title");
		return elements.first().ownText();
	}

}
