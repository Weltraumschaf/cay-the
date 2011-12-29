package de.weltraumschaf.caythe;

import de.weltraumschaf.caythe.util.ParseTreePrinter;
import de.weltraumschaf.caythe.backend.Backend;
import de.weltraumschaf.caythe.backend.BackendFactory;
import de.weltraumschaf.caythe.frontend.FrontendFactory;
import de.weltraumschaf.caythe.frontend.Parser;
import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.intermediate.Code;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.SymbolTableStack;
import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageListener;
import de.weltraumschaf.caythe.message.MessageType;
import de.weltraumschaf.caythe.util.CrossReferencer;
import java.io.BufferedReader;
import java.io.FileReader;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.ROUTINE_INTERMEDIATE_CODE;

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
    private Code intermediateCode;
    private SymbolTableStack symbolTableStack;
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

        intermediateCode = parser.getIntermediateCode();


        if (parser.getErrorCount() == 0) {
            symbolTableStack = parser.getSymbolTableStack();
            SymbolTableEntry programId = symbolTableStack.getProgramId();
            intermediateCode = (Code) programId.getAttribute(ROUTINE_INTERMEDIATE_CODE);

            if (xref) {
                CrossReferencer crossReferencer = new CrossReferencer(System.out);
                crossReferencer.print(symbolTableStack);
            }

            if (intermediate) {
                ParseTreePrinter treePrinter = new ParseTreePrinter(System.out);
                treePrinter.print(intermediateCode);
            }
        }

        backend.process(intermediateCode, symbolTableStack);
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
    private static final String TOKEN_FORMAT = ">>> %-15s line=%03d, pos=%2d, text=\"%s\"";
    private static final String VALUE_FORMAT = ">>>                 value=%s";
    private static final int PREFIX_WIDTH = 5;

    private class ParserMessageListener implements MessageListener {
        @Override
        public void messageReceived(Message message) {
            MessageType type = message.getType();

            switch (type) {
                case TOKEN: {
                    Object body[]       = (Object[]) message.getBody();
                    int line            = (Integer) body[0];
                    int position        = (Integer) body[1];
                    TokenType tokenType = (TokenType) body[2];
                    String tokenText    = (String) body[3];
                    Object tokenValue   = body[4];

                    System.out.println(String.format(TOKEN_FORMAT,
						     tokenType,
						     line,
						     position,
						     tokenText));

                    if (null != tokenValue) {
                        if (STRING == tokenType) {
                            tokenValue = "\"" + tokenValue + "\"";
                        }

                        System.out.println(String.format(VALUE_FORMAT,
							 tokenValue));
                    }

                    break;
                }
                case SYNTAX_ERROR: {
                    Object body[]       = (Object[]) message.getBody();
                    int lineNumber      = (Integer) body[0];
                    int position        = (Integer) body[1];
                    String tokenText    = (String) body[2];
                    String errorMessage = (String) body[3];

                    int spaceCount = PREFIX_WIDTH + position;
                    StringBuilder flagBuffer = new StringBuilder();

                    // Spaces up to the error position
                    for (int i =1; i < spaceCount; ++i) {
                        flagBuffer.append(' ');
                    }

                    flagBuffer.append("^\n*** ")
                              .append(errorMessage);

                    if (null != tokenText) {
                        flagBuffer.append(" [at\"")
                                  .append(tokenText)
                                  .append("\"]");
                    }

                    System.out.println(flagBuffer.toString());
                    break;
                }
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
    private static final String ASSIGN_FORMAT = ">>> LINE %03d: %s = %s\n";

    private class BackendMessageListener implements MessageListener {
        private boolean firstOutputMessage = true;

        @Override
        public void messageReceived(Message message) {
            MessageType type = message.getType();

            switch (type) {
                case ASSIGN: {
                    if (firstOutputMessage) {
                        System.out.println("\n===== OUTPUT =====\n");
                        firstOutputMessage = false;
                    }

                    Object body[] = (Object[]) message.getBody();
                    int lineNumber = (Integer) body[0];
                    String variableName = (String) body[1];
                    Object value = body[2];

                    System.out.printf(ASSIGN_FORMAT, lineNumber, variableName, value);
                    break;
                }

                case RUNTIME_ERROR: {
                    Object body[] = (Object []) message.getBody();
                    String errorMessage = (String) body[0];
                    Integer lineNumber = (Integer) body[1];

                    System.out.print("*** RUNTIME ERROR");
                    if (lineNumber != null) {
                        System.out.print(" AT LINE " +
                                         String.format("%03d", lineNumber));
                    }
                    System.out.println(": " + errorMessage);
                    break;
                }

                case INTERPRETER_SUMMARY: {
                    Number body[]      = (Number[]) message.getBody();
                    int executionCount = (Integer) body[0];
                    int runtimeErrors  = (Integer) body[1];
                    float elapsedTime  = (Float) body[2];

                    System.out.println(
                        String.format(INTERPRETER_SUMMARY_FORMAT, executionCount, runtimeErrors, elapsedTime)
                    );
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
