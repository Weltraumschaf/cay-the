package de.weltraumschaf.caythe.tools.ide.idea;

import com.intellij.CommonBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ResourceBundle;

/**
 *
 */
public final class CayTheBundle {
    public static String message(@NotNull @PropertyKey(resourceBundle = PATH_TO_BUNDLE) String key, @NotNull Object... params) {
        return CommonBundle.message(getBundle(), key, params);
    }

    private static Reference<ResourceBundle> ourBundle;
    @NonNls
    protected static final String PATH_TO_BUNDLE = "messages.CayTheBundle";

    private CayTheBundle() {
    }

    private static ResourceBundle getBundle() {
        ResourceBundle bundle = com.intellij.reference.SoftReference.dereference(ourBundle);
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(PATH_TO_BUNDLE);
            ourBundle = new SoftReference<>(bundle);
        }
        return bundle;
    }
}
