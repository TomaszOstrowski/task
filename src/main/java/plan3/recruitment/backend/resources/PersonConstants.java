package plan3.recruitment.backend.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class PersonConstants {
    static final String APPLICATION_JSON_UTF8 = APPLICATION_JSON + "; charset=utf-8";
    static final String EMAIL_PARAM = "email";
    static final String EMAIL_PATH_PARAM = '{' + EMAIL_PARAM + '}';
}
