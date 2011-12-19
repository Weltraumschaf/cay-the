package de.weltraumschaf.caythe.frontend.pascal.tokens;

import de.weltraumschaf.caythe.frontend.*;
import de.weltraumschaf.caythe.frontend.pascal.*;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class PascalErrorToken extends PascalToken {

    public PascalErrorToken(Source source, PascalErrorCode errorCode, String tokenText) throws Exception {
        super(source);
	this.text  = tokenText;
	this.type  = ERROR;
	this.value = errorCode;
    }

    @Override
    protected void extract() throws Exception {

    }

}
