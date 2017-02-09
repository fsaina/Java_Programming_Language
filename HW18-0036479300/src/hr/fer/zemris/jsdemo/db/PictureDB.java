package hr.fer.zemris.jsdemo.db;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The type Pic db class implementation. All reading of the pictures is occurec here.
 * The class is modeled as a singleton class.
 *
 * @author filip
 */
public class PictureDB {

	/**
	 * The constant providin the path to the image descriptor file.
	 */
	public static final String PICTUREDESCRIPTORLOCATION = "opisnik.txt";
	/**
	 * The split sign used in the descriptor file.
	 */
	public static final String SPLIT_SIGN = "#";

	/**
	 * One-to-one relation structure describing the database
	 */
	private static Map<String, Picture> dataBase = null;

	/**
	 * Singleton, non public constructor (the database needs to be
	 * created once and accessed only from one place in the application)
	 */
	private PictureDB() {
	}

	/**
	 * Gets tags.
	 *
	 * @return the tags
	 * @throws IOException the io exception
	 */
	public static String getTags() throws IOException {
		Set<String> set = new HashSet<String>();
		databseInitialized();
		dataBase.forEach((K, V) -> {
			splitDescrptorTags(V, set);
		});
		String result = setAsString(set);
		return result;
	}

	/**
	 * Method used for checking if the databse is initialized
	 */
	private static void databseInitialized() {
		if (dataBase == null) {
			initDatabase();
		}
	}

	/**
	 * Method used for initializing the database.
	 * The descriptor file has to be correctly formatted with every picture described with
	 * three lines (name, description, tags)
	 */
	private static void initDatabase() {
		try {
			List<String> lines = Files.readAllLines(new File(PICTUREDESCRIPTORLOCATION).toPath(), StandardCharsets.UTF_8);
			dataBase = new HashMap<>();

			for (int i = 0; i < lines.size(); i = i + 3) {
				dataBase.put(lines.get(i),
						new Picture(lines.get(i).trim(), // Picture's name
								lines.get(i + 1).trim(), // Picture's description
								lines.get(i + 2).trim())); // Picture's tags
			}
		} catch (IOException e) {
			throw new RuntimeException("Illegal format of the provided descriptor file");
		}

	}

	/**
	 * Method used for splitting tags in the data set
	 *
	 * @param v   Pitcture model used
	 * @param set data set to store the new tags
	 */
	private static void splitDescrptorTags(Picture v, Set<String> set) {
		String[] tags = v.getTags().split(",");

		for (String tag : tags) {
			String tagS = tag.trim();
			set.add(tagS);
		}
	}

	/**
	 * Convert the given set as a concatinated string with all the elements
	 *
	 * @param set Set model to read data from
	 * @return String representation
	 */
	private static String setAsString(Set<String> set) {
		StringBuilder sb = new StringBuilder();

		for (String tag : set) {
			String tagString = tag.trim();
			sb.append(tagString + SPLIT_SIGN);

		}
		String result = sb.toString();

		String substring = result.substring(0, result.length() - 1);
		return substring;
	}

	/**
	 * Gets pictures by tag.
	 *
	 * @param tagName the tag name
	 * @return the pictures by tag
	 */
	public static List<Picture> getPicturesByTag(String tagName) {
		databseInitialized();
		List<Picture> pictureLIst = new ArrayList<>();

		dataBase.forEach((K, V) -> {
			if (V.containsTag(tagName)) {

				pictureLIst.add(V);

			}
		});
		return pictureLIst;
	}

	/**
	 * Method returns a picture by a given name.
	 * If no element if found -- null pointer is returned
	 *
	 * @param image the image name String to be provided
	 * @return Picture object represented by the image name
	 */
	public static Picture provideByName(String image) {
		databseInitialized();

		List<Picture> picturlist = new ArrayList<>();
		dataBase.forEach((K, V) -> {
			if (V.getName().equals(image)) {
				picturlist.add(V);
			}
		});

		if (!picturlist.isEmpty()) {
			return picturlist.get(0);
		}

		return null;
	}
}
