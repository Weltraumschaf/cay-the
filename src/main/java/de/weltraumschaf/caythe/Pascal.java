package de.weltraumschaf.caythe;

import de.weltraumschaf.caythe.backend.Backend;
import de.weltraumschaf.caythe.backend.BackendFactory;
import de.weltraumschaf.caythe.frontend.FrontendFactory;
import de.weltraumschaf.caythe.frontend.Parser;
import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.intermediate.IntermediateCode;
import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageListener;
import de.weltraumschaf.caythe.message.MessageType;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Pascal {

    private BackendFactory.Operation operation;
    private String filePath;
    private String flags;
    private Parser parser;
    private Source source;
    private IntermediateCode iCode;
    private SymbolTable symTab;
    private Backend backend;

    public Pascal(BackendFactory.Operation operation, String filePath, String flags) {
        this.operation = operation;
        this.filePath  = filePath;
        this.flags     = flags;
    }


    public void execute() throws Exception {
        boolean intermediate = flags.indexOf('i') > -1;
        boolean xref         = flags.indexOf('x') > -1;

        source = new Source(new BufferedReader(new FileReader(filePath)));
        source.addMessageListener(new SourceMessageListener());

        parser = FrontendFactory.createParser(
            FrontendFactory.Language.PASCAL,
            FrontendFactory.Type.TOP_DOWN,
            source
        );
        parser.addMessageListener(new ParserMessageListener());

        backend = BackendFactory.createBackend(operation);
        backend.addMessageListener(new BackendMessageListener());

        parser.parse();
        source.close();

        iCode  = parser.getIntermediateCode();
        symTab = parser.getSymbolTable();

        backend.process(iCode, symTab);
    }

    private static final String SOURCE_LINE_FORMAT = "%03d %s";

    private class SourceMessageListener implements MessageListener {
        @Override
        public void messageReceived(Message message) {
            MessageType type = message.getType();
            Object body[]    = (Object[])message.getBody();

            switch (type) {
                case SOURCE_LINE: {
                    int lineNumber  = (Integer) body[0];
                    String lineText = (String) body[1];

                    System.out.println(String.format(SOURCE_LINE_FORMAT, lineNumber, lineText));
                    break;
                }
            }
        }
    }

    private static final String PARSER_SUMMARY_FORMAT = "\n%,20d source lines." +
                                                        "\n%,20d syntax errors." +
                                                        "\n%,20.2f seconds total parsing time.\n";

    private class ParserMessageListener implements MessageListener {
        @Override
        public void messageReceived(Message message) {
            MessageType type = message.getType();

            switch (type) {
                case PARSER_SUMMARY: {
                    Number body[]      = (Number[]) message.getBody();
                    int statementCount = (Integer) body[0];
                    int syntaxErrors   = (Integer) body[1];
                    float elapsedTime  = (Float)   body[2];

                    System.out.println(
                        String.format(PARSER_SUMMARY_FORMAT, statementCount, syntaxErrors, elapsedTime)
                    );
                    break;
                }
            }
        }
    }

    private static final String INTERPRETER_SUMMARY_FORMAT = "\n%,20d statements executed." +
                                                             "\n%,20d runtime errors." +
                                                             "\n%,20.2f seconds total execution time.\n";
    private static final String COMPILER_SUMMARY_FORMAT = "\n%,20d instructions generated." +
                                                          "\n%,20.2f seconds total code generation time.\n";

    private class BackendMessageListener implements MessageListener {
        @Override
        public void messageReceived(Message message) {
            MessageType type = message.getType();

            switch (type) {
                case INTERPRETER_SUMMARY: {
                    Number body[]      = (Number[]) message.getBody();
                    int executionCount = (Integer) body[0];
                    int runtimeErrors  = (Integer) body[1];
                    float elapsedTime  = (Float) body[2];

//                    System.out.println(
//                        String.format(INTERPRETER_SUMMARY_FORMAT, executionCount, runtimeErrors, elapsedTime)
//                    );
                    break;
                }
                case COMPILER_SUMMARY: {
                    Number body[]        = (Number[]) message.getBody();
                    int instructionCount = (Integer) body[0];
                    float elapsedTime    = (Float) body[1];

                    System.out.println(
                        String.format(COMPILER_SUMMARY_FORMAT, instructionCount, elapsedTime)
                    );
                    break;
                }
            }
        }
    }

}
