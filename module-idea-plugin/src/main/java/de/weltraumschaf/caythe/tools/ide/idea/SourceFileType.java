package de.weltraumschaf.caythe.tools.ide.idea;

import com.intellij.icons.AllIcons;
import com.intellij.lang.properties.charset.Native2AsciiCharset;
import com.intellij.openapi.fileEditor.impl.LoadTextUtil;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.Trinity;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.encoding.EncodingRegistry;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.nio.charset.Charset;

/**
 *
 */
public final class SourceFileType extends LanguageFileType {
    public static final LanguageFileType INSTANCE = new SourceFileType();
    @NonNls public static final String DEFAULT_EXTENSION = "ct";
    @NonNls public static final String DOT_DEFAULT_EXTENSION = "."+DEFAULT_EXTENSION;

    private SourceFileType() {
        super(CayTheLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Properties";
    }

    @NotNull
    @Override
    public String getDescription() {
        return CayTheBundle.message("caythe.files.file.type.description");
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Override
    public Icon getIcon() {
        return AllIcons.FileTypes.Custom;
    }

    @Override
    public String getCharset(@NotNull final VirtualFile file, @NotNull final byte[] content) {
        final Trinity<Charset, CharsetToolkit.GuessedEncoding, byte[]> guessed =
            LoadTextUtil.guessFromContent(file, content, content.length);
        Charset charset = guessed == null || guessed.first == null
            ? EncodingRegistry.getInstance().getDefaultCharsetForPropertiesFiles(file)
            : guessed.first;

        if (charset == null) {
            charset = CharsetToolkit.getDefaultSystemCharset();
        }

        if (EncodingRegistry.getInstance().isNative2Ascii(file)) {
            charset = Native2AsciiCharset.wrap(charset);
        }

        return charset.name();
    }
}
