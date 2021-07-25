import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface Parser {

    void toParse() throws IOException, ParseException;
}
