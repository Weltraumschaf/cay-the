package de.weltraumschaf.caythe.frontend.pascal.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode;
import de.weltraumschaf.caythe.frontend.pascal.PascalToken;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.ERROR;

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
    public void extract() throws Exception {

    }

}
