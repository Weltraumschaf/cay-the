package de.weltraumschaf.caythe.cli.source;

import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Holds all found files in a module directory.
 * <p>
 * This class is by design immutable.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString
public final class ModuleFiles {
    @Getter
    private final Path manifestFile;
    @Getter
    private final Collection<Path> sourceFiles;
    @Getter
    private final Collection<Path> otherFiles;

    /**
     * Dedicated constructor.
     *
     * @param manifestFile must not be {@code null}
     * @param sourceFiles  must not be {@code null}
     * @param otherFiles   must not be {@code null}
     */
    public ModuleFiles(final Path manifestFile, final Collection<Path> sourceFiles, final Collection<Path> otherFiles) {
        super();
        this.manifestFile = Validate.notNull(manifestFile, "manifestFile");
        this.sourceFiles = Collections.unmodifiableCollection(Validate.notNull(sourceFiles, "sourceFiles"));
        this.otherFiles = Collections.unmodifiableCollection(Validate.notNull(otherFiles, "otherFiles"));
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ModuleFiles)) {
            return false;
        }

        final ModuleFiles that = (ModuleFiles) o;
        return Objects.equals(manifestFile, that.manifestFile) &&
            Objects.equals(sourceFiles, that.sourceFiles) &&
            Objects.equals(otherFiles, that.otherFiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manifestFile, sourceFiles, otherFiles);
    }

}
