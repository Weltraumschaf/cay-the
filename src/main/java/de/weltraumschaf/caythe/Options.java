package de.weltraumschaf.caythe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        LANGUAGE          ("l", "lang", "The language used.", true, true, Arrays.asList("name")),
        MODE              ("m", "mode", "Either compile or execute.", true, true, Arrays.asList("execute", "compile")),

        CROSS_REFERNCES   ("x", "Show variable cross reference."),
        INTERMEDIATE_CODE ("i", "Intermediate code tree."),
        LINE_NUMBERS      ("n", "Show line numbers."),
        VARIABLE_ASSIGNS  ("a", "Show variable asignemt infos."),
        VARIABLE_FETCHES  ("f", "Show variable fetch infos."),
        FUNCTION_CALLS    ("c", "Show function call infos."),
        FUNCTION_RETURNS  ("r", "SHow function return infos."),
        DEBUG             ("d", "Show debug output."),
        HELP              ("h", "Show this help.");

        private String shortOption;
        private String longOption;
        private String description;
        private boolean withRequiredArg;
        private boolean required;
        private List<String> possibleArgs;

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
            this(shortOption, longOption, description, withRequiredArg, false);
        }

        Option(String shortOption, String longOption, String description, boolean withRequiredArg, boolean required) {
            this(shortOption, longOption, description, withRequiredArg, required, new ArrayList<String>());
        }

        Option(String shortOption, String longOption, String description, boolean withRequiredArg, boolean required, List<String> possibleArgs) {
            this.shortOption     = shortOption;
            this.longOption      = longOption;
            this.description     = description;
            this.withRequiredArg = withRequiredArg;
            this.required        = required;
            this.possibleArgs    = possibleArgs;
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

        public boolean isRequired() {
            return required;
        }

        public List<String> getPossibleArgs() {
            return possibleArgs;
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

    public static String createHelpText() {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;

        for (Option opt : Option.values()) {
            if (cnt > 0) {
                sb.append('\n');
            }

            formatOption(opt, sb);
            ++cnt;
        }

        return sb.toString();
    }

    private static int ARG_DESC_PAD_SIZE = 32;

    private static void formatOption(Option option, StringBuilder sb) {
        StringBuilder line = new StringBuilder();
        line.append(' ');

        if (option.getShortOption() != null) {
            line.append('-');
            line.append(option.getShortOption());
        }

        if (option.getLongOption() != null) {
            if (option.getShortOption() != null) {
                line.append(", ");
            }

            line.append("--");
            line.append(option.getLongOption());
        }

        if (option.getPossibleArgs().size() > 0) {
            line.append(' ');
            line.append('<');

            int cnt = 0;
            for (String arg : option.getPossibleArgs()) {
                if (cnt > 0) {
                    line.append('|');
                }

                line.append(arg);
                ++cnt;
            }

            line.append('>');
        }

        // Description
        int padSize = ARG_DESC_PAD_SIZE - line.length();
        if (padSize > 0) {
            for (int i = 0; i < padSize; ++i) {
                line.append(' ');
            }
        }
        line.append(option.getDescription());

        sb.append(line);
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

    public List<String> nonOptionArguments() {
        return opts.nonOptionArguments();
    }

}
