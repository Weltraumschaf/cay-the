package de.weltraumschaf.caythe.tools.ide.idea;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public final class CayTheLanguage extends Language {
    public static final CayTheLanguage INSTANCE = new CayTheLanguage();

    public CayTheLanguage() {
        super("Cay-The", "text/cay-the");
//        SyntaxHighlighterFactory.LANGUAGE_FACTORY.addExplicitExtension(this, new SingleLazyInstanceSyntaxHighlighterFactory() {
//            @NotNull
//            protected SyntaxHighlighter createHighlighter() {
//                return new CayTheHighlighter();
//            }
//        });
    }
}
