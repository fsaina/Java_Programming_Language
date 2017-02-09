package hr.fer.zemris.jsdemo.db;

import java.util.Arrays;
import java.util.List;

/**
 * Picture model abstraction representing a single picture element in the
 * descriptor file
 *
 * @author filip
 */
public class Picture {

    /**
     * Picture tags
     */
    private String tags;

    /**
     * Picture name
     */
    private String name;

    /**
     * Picture description
     */
    private String description;

    /**
     * Instantiates a new Picture.
     *
     * @param name        the name
     * @param description the description
     * @param tags        the tags
     */
    public Picture(String name, String description, String tags) {
        this.name = name.trim();
        this.tags = tags.trim();
        this.description = description.trim();
    }

    /**
     * Instantiates a new Picture.
     *
     * @param pic the pic
     */
    public Picture(Picture pic) {
        if (pic == null) {
            throw new IllegalArgumentException("Given picture cannot be null!");
        }
        this.name = pic.getName();
        this.description = pic.getDescription();
        this.tags = pic.getTags();
    }

    /**
     * Contains tag boolean.
     *
     * @param tag the tag
     * @return the boolean
     */
    public boolean containsTag(String tag) {
        List<String> tagsList = Arrays.asList(this.tags.split(","));
        for (int i = 0; i < tagsList.size(); i++) {
            tagsList.set(i, tagsList.get(i).trim());
        }
        if (tagsList.contains(tag)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets tags.
     *
     * @return the tags
     */
    public String getTags() {
        return tags;
    }


}
