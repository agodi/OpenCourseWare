package com.agodi.downloader;

import java.io.IOException;
import java.util.List;

import com.agodi.helper.Lecture;

public interface ContentExtractor {

	/**
	 * Gets the URLs of each lecture in the course
	 * 
	 * @return list of URLs
	 * @throws IOException
	 *             if there was an error connecting and retrieving the URLs
	 */
	List<String> extractLecturesUrls() throws IOException;
	
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
	List<Lecture> getLecturesFromUrls(List<String> urls) throws IOException;

	/**
	 * Retrieves and stores the video associated to the lecture
	 * 
	 * @param directory
	 *            directory where the lecture's video will be stored
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
	void downloadVideo(String directory, Lecture lecture) throws IOException;
	
	/**
	 * Gets the name of the course
	 * @return the name of the course
	 * @throws IOException
	 *             if there was an error connecting and retrieving the URLs
	 */
	String getCourseName() throws IOException;

}
