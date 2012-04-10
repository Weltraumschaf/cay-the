package de.weltraumschaf.caythe.frontend.caythe.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode;
import de.weltraumschaf.caythe.frontend.caythe.CayTheToken;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.ERROR;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CayTheErrorToken extends CayTheToken {

    public CayTheErrorToken(Source source, CayTheErrorCode errorCode, String tokenText) throws Exception {
        super(source);
	this.text  = tokenText;
	this.type  = ERROR;
	this.value = errorCode;
    }

    @Override
    public void extract() throws Exception {

    }

}
