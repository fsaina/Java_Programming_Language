package hr.fer.zemris.java.webserver;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * Context class for manipulating the http context data
 */
public class RequestContext {

    /**
     * Output stream of the context
     */
    private OutputStream outputStream;

    /**
     * Reference used in request context class
     */
    private Character character;

    /**
     * Encoding used in transmission
     */
    private String encoding = "UTF-8";

    /**
     * Status code to be used in transmission
     */
    private int statusCode = 200;

    /**
     * Text of the http message
     */
    private String statusText = "OK";

    /**
     * Type of the http data transferred
     */
    private String mimeType = "text/html";

    /**
     * Immutable parameters map
     */
    private final Map<String, String> parameters;

    /**
     * Map of temporary parameters of the context
     */
    private Map<String, String> temporaryParameters;

    /**
     * Map of other persistent parameters
     */
    private Map<String, String> persistentParameters;

    /**
     * Cookies store to send to the client
     */
    private List<RCCookie> outputCookies;

    /**
     * Flag indicating if the header was generated for the first time
     */
    private boolean headerGenerated = false;


    /**
     * Default class constructor
     *
     * @param outputStream         OutputStream to the client object
     * @param parameters           Map of parameters used in communication
     * @param persistentParameters Map of persistent data used in between sessions
     * @param outputCookies        List of cookies used in communication with the client
     */
    public RequestContext(
            OutputStream outputStream,
            Map<String, String> parameters,
            Map<String, String> persistentParameters,
            List<RCCookie> outputCookies) {

        if (outputStream == null)
            throw new IllegalArgumentException("Argument for outputStream must not be null!");

        if (parameters == null) {
            this.parameters = new HashMap<String, String>();
        } else {
            this.parameters = parameters;
        }

        if (persistentParameters == null) {
            this.persistentParameters = new HashMap<String, String>();
        } else {
            this.persistentParameters = persistentParameters;
        }

        if (outputCookies == null) {
            this.outputCookies = new ArrayList<RCCookie>();
        } else {
            this.outputCookies = outputCookies;
        }

        this.outputStream = outputStream;
        this.temporaryParameters = new HashMap<>();
    }

    /**
     * Getter method for the parameters map
     *
     * @param name String of parameters used in transmission
     * @return String value within the map for the key name
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Getter method for the parameters map Set of all keys
     *
     * @return Set collection of all the keys
     */
    public Set<String> getParameterNames() {
        return parameters.keySet();
    }


    /**
     * Getter method for the persistent parameters map
     *
     * @param name String of parameters used in transmission
     * @return String value within the map for the key name
     */
    public String getPersistentParameter(String name) {
        return persistentParameters.get(name);
    }

    /**
     * Getter method for the persistent parameters map Set of all keys
     *
     * @return Set collection of all the keys
     */
    public Set<String> getPersistentParameterNames() {
        return persistentParameters.keySet();
    }

    /**
     * Method sets the persistent parameter entry with the key name and value value.
     *
     * @param name  key of the new entry
     * @param value value of the new entry
     */
    public void setPersistentParameter(String name, String value) {
        persistentParameters.put(name, value);
    }

    /**
     * Method removes a element with the given key in the persistantParameter map
     *
     * @param name element by key to remove from the map
     */
    public void removePersistentParameter(String name) {
        persistentParameters.remove(name);
    }

    /**
     * Getter method for the temporary parameters map
     *
     * @param name String of parameters used in transmission
     * @return String value within the map for the key name
     */
    public String getTemporaryParameter(String name) {
        return temporaryParameters.get(name);
    }

    /**
     * Getter method for the temporary parameters map Set of all keys
     *
     * @return Set collection of all the keys
     */
    public Set<String> getTemporaryParameterNames() {
        return temporaryParameters.keySet();
    }

    /**
     * Method sets the temporary parameter entry with the key name and value value.
     *
     * @param name  key of the new entry
     * @param value value of the new entry
     */
    public void setTemporaryParameter(String name, String value) {
        temporaryParameters.put(name, value);
    }

    /**
     * Method removes a element with the given key in the temporary parameter map
     *
     * @param name element by key to remove from the map
     */
    public void removeTemporaryParameter(String name) {
        temporaryParameters.remove(name);
    }

    /**
     * Method writes all the data to the output stream defined int the constructor of the class
     *
     * @param data byte array of data to be written
     * @return This object
     *
     * @throws IOException If a error occurred while writing
     */
    public RequestContext write(byte[] data) throws IOException {
        if (headerGenerated == false) {
            headerGenerated = true;
            outputStream.write(generateHeaderData());
        }

        outputStream.write(data);
        outputStream.flush();
        return this;
    }

    /**
     * Method adds a cookie to the internal cookie list
     *
     * @param cookie Cookie object to be added
     */
    public void addRCCookie(RCCookie cookie) {
        if (cookie == null)
            throw new IllegalArgumentException("Null pointer cannot be added to the cookie list");

        if (headerGenerated)
            throw new RuntimeException("No new cookies can be added after the HTTP header is generated");

        outputCookies.add(cookie);
    }

    /**
     * Method generates the header data from all of the defined information form within the class
     *
     * @return byte array string encoded in the defined encoding
     */
    private byte[] generateHeaderData() {

        mimeType += mimeType.startsWith("text/") ? "; charset=" + encoding : "";

        StringBuilder cookieTextBuilder = new StringBuilder();
        for (RCCookie cookie : outputCookies) {
            cookieTextBuilder.append(cookie.toHTTPHeaderElement());
        }


//        "Transfer-Encoding: chunked \r\n" +
        return ("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                "Content-Type: " + mimeType + "\r\n" +
                cookieTextBuilder.toString() +
                "\r\n").getBytes(StandardCharsets.US_ASCII);

    }

    /**
     * Method writes all the data to the output stream defined int the constructor of the class
     *
     * @param text string data to be written
     * @return This object
     *
     * @throws IOException If a error occurred while writing
     */
    public RequestContext write(String text) throws IOException {
        byte[] data = text.getBytes(Charset.forName(encoding));
        return write(data);
    }

    /**
     * Method returns the output stream currently in used
     *
     * @return Output stream in use
     */
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * Method returns the character currently in used
     *
     * @return character in use
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Method sets the encoding to a given value
     *
     * @param encoding ecoding string
     * @return this object
     */
    public RequestContext setEncoding(String encoding) {
        if (headerGenerated)
            throw new RuntimeException("Encoding is immutable after the HTTP header is generated");

        this.encoding = encoding;
        return this;
    }

    /**
     * Method sets the status code of the next request
     *
     * @param statusCode status code of the next request
     * @return this object
     */
    public RequestContext setStatusCode(int statusCode) {
        if (headerGenerated)
            throw new RuntimeException("Status code is immutable after the HTTP header is generated");

        this.statusCode = statusCode;
        return this;
    }

    /**
     * Method sets the status text of the next request
     *
     * @param statusText status text of the next request
     * @return this object
     */
    public RequestContext setStatusText(String statusText) {
        if (headerGenerated)
            throw new RuntimeException("Status text is immutable after the HTTP header is generated");

        this.statusText = statusText;
        return this;
    }

    /**
     * Method sets the mime type of the next request
     *
     * @param mimeType mime type of the next request
     * @return this object
     */
    public RequestContext setMimeType(String mimeType) {
        if (headerGenerated)
            throw new RuntimeException("Mime type is immutable after the HTTP header is generated");

        this.mimeType = mimeType;
        return this;
    }

    /**
     * Class abstraction of a http protocol cookie
     */
    public static class RCCookie {

        /**
         * Name of the cookie used
         */
        private final String name;

        /**
         * Value of the cookie
         */
        private final String value;

        /**
         * Domain to which it is assigned for
         */
        private final String domain;

        /**
         * Path to which it is assigned to
         */
        private final String path;

        /**
         * Maximal age of the cookie in milliseconds
         */
        private final Integer maxAge;

        /**
         * Flag indicating if the cookie is a http only type
         */
        private boolean httpOnly = false;

        /**
         * Default class constructor
         *
         * @param name   String name of the cookie
         * @param value  String value of the cookie
         * @param maxAge String maximal age of the cookie
         * @param domain String domain of the cookie
         * @param path   String path of the cookie
         */
        public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
            this.name = name;
            this.value = value;
            this.domain = domain;
            this.path = path;
            this.maxAge = maxAge;
        }

        /**
         * Method returns a string representation of the generated String of a single cookie request row in a
         * http request
         *
         * @return String forming the data
         */
        public String toHTTPHeaderElement() {
            StringBuilder cookieBuilder = new StringBuilder();

            cookieBuilder.append("Set-Cookie: ");
            if (name != null) {
                cookieBuilder.append(name + "=\"" + value + "\"");
            }

            if (domain != null) {
                cookieBuilder.append("; Domain=" + domain);
            }

            if (path != null) {
                cookieBuilder.append("; Path=" + path);
            }

            if (maxAge != null) {
                cookieBuilder.append("; Max-Age=" + maxAge);
            }

            if (httpOnly) {
                cookieBuilder.append("; HttpOnly");
            }

            cookieBuilder.append("\r\n");


            return cookieBuilder.toString();
        }

        /**
         * Setter method for the http only flag
         *
         * @param httpOnly true if the cookie is http only, false otherwise
         */
        public void setHttpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
        }

        /**
         * Getter for the name value
         *
         * @return String containing the name
         */
        public String getName() {
            return name;
        }

        /**
         * Getter for the value
         *
         * @return String containing the value
         */
        public String getValue() {
            return value;
        }

        /**
         * Getter for the domain value
         *
         * @return String containing the domain
         */
        public String getDomain() {
            return domain;
        }

        /**
         * Getter for the path object String
         *
         * @return String contacting the path
         */
        public String getPath() {
            return path;
        }

        /**
         * Getter for the max age used in the transmission
         *
         * @return Integer representing the max age
         */
        public Integer getMaxAge() {
            return maxAge;
        }
    }
}
