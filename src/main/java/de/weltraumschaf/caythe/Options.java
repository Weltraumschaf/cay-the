package de.weltraumschaf.caythe;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpecBuilder;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Options {

    public enum Option {
        LANGUAGE          ("l", "lang", "The language used.", true),
        MODE              ("m", "mode", "Either compile or execute.", true),

        DEBUG             ("d", "Show debug output."),
        HELP              ("h", "Show this help."),
        CROSS_REFERNCES   ("x", "Show variable cross reference."),
        INTERMEDIATE_CODE ("i", "Intermediate code tree."),
        LINE_NUMBERS      ("n", "Show line numbers."),
        VARIABLE_ASSIGNS  ("a", "Show variable asignemt infos."),
        VARIABLE_FETCHES  ("f", "Show variable fetch infos."),
        FUNCTION_CALLS    ("c", "Show function call infos."),
        FUNCTION_RETURNS  ("r", "SHow function return infos.");

        private String shortOption;
        private String longOption;
        private String description;
        private boolean withRequiredArg;

        Option(String shortOption) {
            this(shortOption, "");
        }

        Option(String shortOption, String description) {
            this(shortOption, null, description);
        }

        Option(String shortOption, String longOption, String description) {
            this(shortOption, longOption, description, false);
        }

        Option(String shortOption, String longOption, String description, boolean withRequiredArg) {
            this.shortOption     = shortOption;
            this.longOption      = longOption;
            this.description     = description;
            this.withRequiredArg = withRequiredArg;
        }

        public String getDescription() {
            return description;
        }

        public String getShortOption() {
            return shortOption;
        }

        public String getLongOption() {
            return longOption;
        }

        public boolean isWithRequiredArg() {
            return withRequiredArg;
        }

    }

    private OptionSet opts;

    public Options(OptionSet opts) {
        this.opts = opts;
    }

    public static OptionParser createParser() {
        OptionParser p = new OptionParser();

        for (Options.Option option : Options.Option.values()) {
            OptionSpecBuilder builder;

            if (null != option.getShortOption()) {
                builder = p.accepts(option.getShortOption());

                if (option.isWithRequiredArg()) {
                    builder.withRequiredArg();
                }
            }

            if (null != option.getLongOption()) {
                builder = p.accepts(option.getLongOption());

                if (option.isWithRequiredArg()) {
                    builder.withRequiredArg();
                }
            }
        }

        return p;
    }

    private boolean isEnabled(Options.Option o) {
        return opts.has(o.getShortOption()) || opts.has(o.getLongOption());
    }

    public boolean isDebugEnabled() {
        return isEnabled(Option.DEBUG);
    }

    public boolean isHelpEnabled() {
        return isEnabled(Option.HELP);
    }

    public boolean isCrossRefernecesEnabled() {
        return isEnabled(Option.CROSS_REFERNCES);
    }

    public boolean isIntermediateCodeEnabled() {
        return isEnabled(Option.INTERMEDIATE_CODE);
    }

    public boolean isLineNumbersEnabled() {
        return isEnabled(Option.LINE_NUMBERS);
    }

    public boolean isVarAssignsEnabled() {
        return isEnabled(Option.VARIABLE_ASSIGNS);
    }

    public boolean isVarFetchesEnabled() {
        return isEnabled(Option.VARIABLE_FETCHES);
    }

    public boolean isFunctionCallsEnabled() {
        return isEnabled(Option.FUNCTION_CALLS);
    }

    public boolean isFunctionReturnsEnabled() {
        return isEnabled(Option.FUNCTION_RETURNS);
    }

    public String getLanguage() {
        if (opts.has(Option.LANGUAGE.getShortOption())) {
            return (String) opts.valueOf(Option.LANGUAGE.getShortOption());
        }

        if (opts.has(Option.LANGUAGE.getLongOption())) {
            return (String) opts.valueOf(Option.LANGUAGE.getLongOption());
        }

        return "";
    }

    public String getMode() {
        if (opts.has(Option.MODE.getShortOption())) {
            return (String) opts.valueOf(Option.MODE.getShortOption());
        }

        if (opts.has(Option.MODE.getLongOption())) {
            return (String) opts.valueOf(Option.MODE.getLongOption());
        }

        return "";
    }
}
