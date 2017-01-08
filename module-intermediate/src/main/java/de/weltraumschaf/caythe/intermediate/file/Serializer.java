package de.weltraumschaf.caythe.intermediate.file;

import com.google.gson.Gson;
import de.weltraumschaf.caythe.intermediate.model.Manifest;
import de.weltraumschaf.caythe.intermediate.model.Module;

/**
 * Serializes a given semantic model from or to a character sequence.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class Serializer {

    private final Gson gson = new Gson();

    /**
     * Serialize a ggiven model to a string.
     * <p>
     * Returns empty string if {@code null} was passed in
     * </p>
     * 
     * @param module
     *        maybe {@code null}
     * @return never {@code null}, maybe empty
     */
    String serialize(final Module module) {
        if (module == null) {
            return "";
        }

        return gson.toJson(module);
    }

    /**
     * Deserialize a given string to a model.
     * <p>
     * If {@code null} or empty string is given then an {@link Module#NULL null object} will be returned.
     * </p>
     * 
     * @param serializedModel
     *        maybe {@code null} or empty
     * @return never {@code null}, maybe {@link Module#NULL empty}
     */
    Module deserialize(final String serializedModel) {
        if (serializedModel == null || serializedModel.isEmpty()) {
            return Module.NULL;
        }

        return gson.fromJson(serializedModel, Module.class);
    }
}
