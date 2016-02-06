package obs.org.samplebtowedapp.Constants;


public class ConstantVariables {

    //http://192.237.253.117:8080/btowed/resources/rest/login
    //URL and Params for HTTP Post http://192.237.253.117:8080/btowed/home.xhtml
/*------------------------------------------------------------------------------------*/

    //SUNBOX URL TOKEN : http://192.237.253.117:8080/sunbox/oauth/token
    //SUNBOX URL LOGIN : http://192.237.253.117:8080/sunbox/login
    //username and password for Header.. Authorized
    //public static final String HEADER_USERNAME = "sunbox";
    // public static final String HEADER_PASSWORD = "sunbox";

    public static final String BASE_URL = "http://192.237.253.117:8080/sunbox/";

    //URL for token and login..
    public static final String LOGIN_URL = BASE_URL+"login";
    public static final String TOKEN_URL = BASE_URL+"oauth/token";

    //Actual Header...
    public static final String HEADER_KEY = "Authorization";
    public static final String HEADER_VALUE = "sunbox:sunbox";

    //username and password for Params.. grant type is needed only when acquiring token.
    public static final String PARAMS_USERNAME = "optisoltest@gmail.com";
    public static final String PARAMS_PASSWORD = "12345";
    public static final String PARAMS_GRANT_TYPE = "password";

    //For final user data access..
    public static String email = "";
    public static String password = "";

    //Forgot Password Screen Variables..
    public static char at = '@';
    public static char dot = '.';
    public static char letters = ' ';
    public static String words = "";
    public static Boolean check_at = false;
    public static Boolean check_dot = false;

    public static String progress_Dialog_Message;

}
