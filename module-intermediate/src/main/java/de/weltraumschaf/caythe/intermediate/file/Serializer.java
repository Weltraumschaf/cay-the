package de.weltraumschaf.caythe.intermediate.file;

import com.google.gson.Gson;
import de.weltraumschaf.caythe.intermediate.model.Manifest;
import de.weltraumschaf.caythe.intermediate.model.Module;

/**
 * Serializes a given semantic model from or to a byte array.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class Serializer {

    private final Gson gson = new Gson();

    byte[] serialize(final Module module) {
        if (module == null) {
            return new byte[0];
        }

        return gson.toJson(module).getBytes();
    }

    Module deserialize(final byte[] serializedModel) {
        if (serializedModel == null || serializedModel.length == 0) {
            new Module(Manifest.NULL);
        }

        return gson.fromJson(new String(serializedModel), Module.class);
    }
}
