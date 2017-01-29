package de.weltraumschaf.caythe.frontend;

import org.antlr.v4.runtime.ANTLRErrorListener;

interface ErrorListener extends ANTLRErrorListener {

    void debug(boolean debug);

}
