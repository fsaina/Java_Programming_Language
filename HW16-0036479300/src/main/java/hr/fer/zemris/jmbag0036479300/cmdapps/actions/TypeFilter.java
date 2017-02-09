package hr.fer.zemris.jmbag0036479300.cmdapps.actions;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * The type Type filter.
 */
public class TypeFilter extends FileFilter {

    private String extension;
    private String description;

    /**
     * Instantiates a new Type filter.
     *
     * @param extension   the extension
     * @param description the description
     */
    public TypeFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return file.getName().endsWith(extension);
    }

    @Override
    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }

    @Override
    public String toString() {

        return extension;
    }
}