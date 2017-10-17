package de.weltraumschaf.caythe.cli.run;

import de.weltraumschaf.caythe.intermediate.model.Module;

/**
 * Validates the given model semantically.
 */
final class ModuleValidator {
    void validate(final Module module) {
        /*
            TODO module.manifest validation:
            - missing: group, artifact, namespace, version.
            - version must not be 0.0.0
            - imports
                - no duplicates
                - versions > 0.0.0
         */
    }
}
