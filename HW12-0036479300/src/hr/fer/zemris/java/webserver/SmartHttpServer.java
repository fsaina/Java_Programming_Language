package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class implementation used for basic server manipulation over the TCP protocol
 *
 * @author Filip Saina
 */
public class SmartHttpServer {

    /**
     * Host ip address
     */
    private String address;

    /**
     * Port over which to start the communication
     */
    private int port;

    /**
     * Number of threads in the threadPool
     */
    private int workerThreads;

    /**
     * Number in milliseconds indicating the timeout of a singe session
     */
    private int sessionTimeout;

    /**
     * Map of all supported mime types
     */
    private Map<String, String> mimeTypes = new HashMap<>();

    /**
     * Map of all implemented workers class
     */
    private Map<String, IWebWorker> workersMap = new HashMap<>();

    /**
     * Map of all legged sessions
     */
    private volatile Map<String, SessionMapEntry> sessions = new HashMap<>();

    /**
     * Random number generator
     */
    private volatile Random sessionRandom = new Random();

    /**
     * Reference to the server thread serving the requests
     */
    private ServerThread serverThread = null;

    /**
     * Reference to the server thread pool
     */
    private ExecutorService threadPool;

    /**
     * Path to the accessible document root
     */
    private Path documentRoot;

    /**
     * Perfix used for the workers class load
     */
    private static final String FQCN_PREFIX = "hr.fer.zemris.java.webserver.workers.";

    /**
     * Constant used for converting milliseconds to minutes
     */
    private static final long MILI_TO_MIN = 60000;

    /**
     * Default SmartHttpServer class constructor
     *
     * @param configFileName String path to the server properties file
     */
    public SmartHttpServer(String configFileName) {
        Properties serverProp = null;
        Properties mimeProp = null;
        Properties workerProp = null;
        File confPath = new File(configFileName);

        try {
            serverProp = readConifgurationFile(confPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading the server configuration file!");
            System.exit(-1);
        }

        try {
            workerProp = readConifgurationFile(new File(serverProp.getProperty("server.workers")));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading the worker property file!");
            System.exit(-1);
        }

        //read the mime file
        try {
            mimeProp = readConifgurationFile(new File(serverProp.getProperty("server.mimeConfig")));
        } catch (IOException e) {
            System.err.println("Error reading the mime configuration file!");
            System.exit(-1);
        }

        //set the property files
        address = serverProp.getProperty("server.address");
        port = Integer.parseInt(serverProp.getProperty("server.port"));
        workerThreads = Integer.parseInt(serverProp.getProperty("server.workerThreads"));
        sessionTimeout = Integer.parseInt(serverProp.getProperty("session.timeout"));
        documentRoot = Paths.get(serverProp.getProperty("server.documentRoot"));

        //set the worker property file
        for (String key : workerProp.stringPropertyNames()) {

            String fqcn = workerProp.getProperty(key);
            IWebWorker worker = null;

            if (workersMap.containsValue(fqcn))
                throw new IllegalStateException("Multiple entry for the FQCN: " +
                        fqcn + " detected in the " +
                        serverProp.getProperty("server.workers") +
                        " file");
            try {
                worker = resolveFQCNToWebWorker(fqcn);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                System.err.println("Cannot resolve FQCN: " + fqcn + " to a class object");
                System.exit(-1);
            }

            workersMap.put(key, worker);
        }

        //set mime properties
        for (String key : mimeProp.stringPropertyNames()) {
            mimeTypes.put(key, mimeProp.getProperty(key));
        }

    }

    /**
     * Method used for resolving the FQCN to a new instance of that class within java VM.
     *
     * @param fqcn String of the FQCN
     * @return IWebWorker class implementation
     *
     * @throws ClassNotFoundException thrown when fqcn can not be found
     * @throws IllegalAccessException thrown when fqcn is not accessible
     * @throws InstantiationException thrown when class cannot be instantiated
     */
    private IWebWorker resolveFQCNToWebWorker(String fqcn) throws
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException {

        Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
        Object newObject = referenceToClass.newInstance();
        return (IWebWorker) newObject;
    }

    /**
     * Method used for reading the configuration file provided as the argument
     *
     * @param configFileName File abstraction of the configuration file
     * @return Properties file make from the file reference
     *
     * @throws IOException
     */
    @SuppressWarnings("unused")
	private Properties readConifgurationFile(File configFileName) throws IOException {

        Properties properties = new Properties();
        InputStream inputStrem = new FileInputStream(configFileName);

        if (inputStrem != null) {
            properties.load(inputStrem);
        } else {
            throw new FileNotFoundException("The requested properties file can not be found:  " +
                    configFileName);
        }
        return properties;
    }

    /**
     * Method instantiates the thread pool and runs a new instace of the server thread
     */
    protected synchronized void start() {
        threadPool = Executors.newFixedThreadPool(workerThreads);

        if (serverThread == null) {
            serverThread = new ServerThread();
            serverThread.start();
        }

    }

    /**
     * Method used for stopping the thread pool from executing
     */
    protected synchronized void stop() {
        threadPool.shutdown();
    }

    private String generateRandomString(int lenght) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lenght; i++) {
            char c = (char) (sessionRandom.nextInt(26) + 'A');
            sb.append(c);
        }

        return sb.toString();
    }

    /**
     * Server thread object representing the running execution thread used to server incoming clients.
     */
    protected class ServerThread extends Thread {
        @SuppressWarnings("resource")
		@Override
        public void run() {

            ServerSocket serverSocket = null;

            try {
                serverSocket = new ServerSocket();
                serverSocket.bind(new InetSocketAddress(address, port));
            } catch (IOException e) {
                System.err.println("Error opening the server socket on address: " + address + " and port: " + port);
                System.exit(-1);
            }

            startGarbageCollector();

            System.out.println("Server successfully running on address: " + address + "  and port: " + port);

            while (true) {
                try {
                    Socket client = serverSocket.accept();
                    ClientWorker cw = new ClientWorker(client);
                    threadPool.execute(cw);

                } catch (IOException e) {
                    System.err.println("Error accepting the serverSocket job");
                    System.exit(-1);
                }

            }
        }

        /**
         * Method starts the execution thread used for removing obsolete session entry elements
         */
        private void startGarbageCollector() {

            threadPool.execute(() -> {
                while (true) {
                    try {
                        Thread.sleep(5000 * MILI_TO_MIN);
                    } catch (InterruptedException e) {
                        //ignorable
                    }

                    for (String key : sessions.keySet()) {
                        SessionMapEntry entry = sessions.get(key);
                        if (entry.validUntil < new Date().getTime() / MILI_TO_MIN) {
                            sessions.remove(key);
                        }
                    }
                }
            });
        }

    }

    /**
     * Class object runnable used for executing incoming client requests
     */
    private class ClientWorker implements Runnable {

        /**
         * Socket object used for the client
         */
        private Socket csocket;

        /**
         * Input stream from the client
         */
        private PushbackInputStream istream;

        /**
         * Output stream used to the client
         */
        private OutputStream ostream;

        /**
         * HTTP protocol version used in communication
         */
        private String version;

        /**
         * Method used in the HTTP protocol communication
         */
        private String method;

        /**
         * Parameters map
         */
        private Map<String, String> params = new HashMap<>();

        /**
         * Permanent parameters used between sessions
         */
        private volatile Map<String, String> permPrams = null;

        /**
         * Output cookies
         */
        private List<RequestContext.RCCookie> outputCookies = new ArrayList<>();

        /**
         * Session id of the current client object
         */
        @SuppressWarnings("unused")
		private String SID;

        /**
         * Default class constructor
         *
         * @param csocket Socket object used for client manipulation
         */
        public ClientWorker(Socket csocket) {
            super();
            this.csocket = csocket;
        }

        @Override
        public void run() {
            try {
                istream = new PushbackInputStream(csocket.getInputStream());
                ostream = csocket.getOutputStream();
                byte[] request = readRequest(istream);
                if (request == null) {
                    sendError(ostream, 400, "Bad request");
                    return;
                }

                String requestStr = new String(
                        request,
                        StandardCharsets.US_ASCII
                );

                List<String> headers = readRequest(requestStr);

                String[] firstLine = headers.isEmpty() ?
                        null : headers.get(0).split(" ");
                if (firstLine == null || firstLine.length != 3) {
                    sendError(ostream, 400, "Bad request");
                    return;
                }

                method = firstLine[0].toUpperCase();
                if (!method.equals("GET")) {
                    sendError(ostream, 405, "Method Not Allowed");
                    return;
                }


                version = firstLine[2].toUpperCase();
                if (!(version.equals("HTTP/1.1") || version.equals("HTTP/1.0"))) {
                    sendError(ostream, 505, "HTTP Version Not Supported");
                    return;
                }

                checkSession(headers);

                String[] pathWithParameters = firstLine[1].split("\\?");
                String path = pathWithParameters[0];

                if (pathWithParameters.length == 2) {
                    String parameters = pathWithParameters[1];
                    parseParameters(parameters);
                }

                RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);

                if (path.contains("/ext/")) {
                    String className = FQCN_PREFIX + path.substring(path.lastIndexOf("/") + 1);

                    IWebWorker worker;

                    try {
                        worker = resolveFQCNToWebWorker(className);
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                        sendError(ostream, 404, "The ClassName object: " + className + " does not exist!");
                        return;
                    }
                    worker.processRequest(rc);

                } else if (workersMap.containsKey(path)) {
                    //requested path is mapped to a IWebWorker

                    workersMap.get(path).processRequest(rc);

                } else {

                    Path requestedPath = documentRoot.resolve(removeFirstSlash(path));

                    if (!requestedPath.toFile().exists()) {
                        sendError(ostream, 403, "Cannot resolve the file path");
                        return;
                    }
                    if (!(requestedPath.toFile().isFile() ||
                            requestedPath.toFile().canRead())) {
                        sendError(ostream, 404, "Cannot access the requested file");
                        return;
                    }

                    String extension = getFileExtension(requestedPath.toFile());
                    String mimeType = mimeTypes.get(extension);
                    if (mimeType == null) {
                        mimeType = "application/octet-stream";
                    }

                    rc.setMimeType(mimeType);
                    rc.setStatusCode(200);

                    if (extension.equals("smscr")) {
                        //process a smart-script

                        String file = new String(Files.readAllBytes(requestedPath.toAbsolutePath()),
                                StandardCharsets.UTF_8);
                        new SmartScriptEngine(
                                new SmartScriptParser(file).getDocumentNode(),
                                new RequestContext(ostream, params, permPrams, outputCookies)
                        ).execute();

                    } else {
                        rc.write(Files.readAllBytes(requestedPath.toAbsolutePath()));
                    }
                }

            } catch (IOException e) {
                System.err.println("Error while serving the client");
            }

        }

        /**
         * Private method used for generating new cookies for the current session
         *
         * @param sid String object containing the session id (SID)
         * @return RCCookie generated
         */
        private RequestContext.RCCookie generateNewCookie(String sid) {
            RequestContext.RCCookie cookie = new RequestContext.RCCookie("sid", sid, null, address, "/");
            cookie.setHttpOnly(true);
            return cookie;
        }

        /**
         * Method gives the current request a logical session determined upon the Cookie sid vlues
         *
         * @param headers all client headers parsed and given
         */
        private synchronized void checkSession(List<String> headers) {

            String sidCandidate = null;

            for (String line : headers) {
                if (!line.startsWith("Cookie:"))
                    continue;

                line = line.split(" ")[1];
                String[] split = line.split("\\=");
                String name = split[0];
                String value = split[1].substring(1, split[1].length() - 1);

                if (name.equals("sid")) {
                    sidCandidate = value;
                    break;
                }
            }

            SessionMapEntry entry = null;

            if (sidCandidate == null) {
                entry = generateNewCookieSessionEntry();

            } else {

                entry = sessions.get(sidCandidate);

                if (entry.validUntil < new Date().getTime() / MILI_TO_MIN) {
                    sessions.remove(sidCandidate);
                    entry = generateNewCookieSessionEntry();
                } else {
                    //valid
                    SID = sidCandidate;
                    entry.validUntil = new Date().getTime() / MILI_TO_MIN + sessionTimeout;
                }
            }

            permPrams = entry.map;
        }

        /**
         * Method creates a new logical session. From generating a new SID, creating new map entries and
         * arsing them to the session map
         *
         * @return SessionMapEntry created
         */
        private synchronized SessionMapEntry generateNewCookieSessionEntry() {
            String sessionId = generateRandomString(20);
            RequestContext.RCCookie cookie = generateNewCookie(sessionId);
            SessionMapEntry entry = new SessionMapEntry();
            entry.sid = sessionId;
            entry.validUntil = new Date().getTime() / MILI_TO_MIN + sessionTimeout;
            entry.map = new ConcurrentHashMap<>();
            outputCookies.add(cookie);
            sessions.put(entry.sid, entry);
            return entry;
        }


        /**
         * Method removes the first slash found in the given string
         *
         * @param pathWithParameter String to be processed
         * @return String after processing
         */
        private String removeFirstSlash(String pathWithParameter) {

            int index = pathWithParameter.indexOf('/');
            return pathWithParameter.substring(index + 1);

        }

        /**
         * Method used for parsing parameters used in the header
         *
         * @param parameters String of parameters provided
         */
        private void parseParameters(String parameters) {
            parameters = parameters.substring(parameters.indexOf("\\?") + 1);

            for (String element : parameters.split("\\&")) {
                String[] elements = element.split("=");
                String key = elements[0];
                String value = elements[1];

                params.put(key.trim(), value.trim());
            }
        }


        /**
         * Method determines the file extension used
         *
         * @param file File object to get the file extension from
         * @return String containing the extension
         */
        private String getFileExtension(File file) {
            String name = file.getName();
            try {
                return name.substring(name.lastIndexOf(".") + 1);
            } catch (Exception e) {
                return "";
            }
        }

        /**
         * Method reads the request from the input stream and returns it in byte form
         *
         * @param is InputStream used to read data from
         * @return byte[] array containing all the read data
         *
         * @throws IOException Thrown when the write to the output stream is unsuccessful
         */
        private byte[] readRequest(InputStream is)
                throws IOException {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int state = 0;
            l:
            while (true) {
                int b = is.read();
                if (b == -1) return null;
                if (b != 13) {
                    bos.write(b);
                }
                switch (state) {
                    case 0:
                        if (b == 13) {
                            state = 1;
                        } else if (b == 10) state = 4;
                        break;
                    case 1:
                        if (b == 10) {
                            state = 2;
                        } else state = 0;
                        break;
                    case 2:
                        if (b == 13) {
                            state = 3;
                        } else state = 0;
                        break;
                    case 3:
                        if (b == 10) {
                            break l;
                        } else state = 0;
                        break;
                    case 4:
                        if (b == 10) {
                            break l;
                        } else state = 0;
                        break;
                }
            }
            return bos.toByteArray();
        }

        /**
         * Method sends a error message to the standard output stream x
         *
         * @param cos        Output stream to write the output
         * @param statusCode Status code of the HTTP header
         * @param statusText Status text used to describe the HTTP header
         * @throws IOException Thrown when the write to the output stream is unsuccessful
         */
        private void sendError(OutputStream cos,
                               int statusCode, String statusText) throws IOException {

            cos.write(
                    ("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                            "Server: Simple java server\r\n" +
                            "Content-Type: text/plain;charset=UTF-8\r\n" +
                            "Content-Length: 0\r\n" +
                            "Connection: close\r\n" +
                            "\r\n").getBytes(StandardCharsets.US_ASCII)
            );
            cos.flush();

        }

        /**
         * Method reads the request format in the given String
         *
         * @param requestHeader String containing the given data
         * @return List of lines in the header parsed
         */
        private List<String> readRequest(
                String requestHeader) {

            List<String> headers = new ArrayList<String>();
            String currentLine = null;
            for (String s : requestHeader.split("\n")) {
                if (s.isEmpty()) break;
                char c = s.charAt(0);
                if (c == 9 || c == 32) {
                    currentLine += s;
                } else {
                    if (currentLine != null) {
                        headers.add(currentLine);
                    }
                    currentLine = s;
                }
            }
            if (!currentLine.isEmpty()) {
                headers.add(currentLine);
            }
            return headers;
        }
    }

    /**
     * Session map entry class used for describing a logical session
     */
    private static class SessionMapEntry {
        String sid;
        long validUntil;
        Map<String, String> map;
    }

    /**
     * Entry point of the application
     *
     * @param args command line arguments array. One argument reared -- path to the properties file of the
     *             server
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Path to server properties file expected");
            return;
        }

        String properties = args[0];

        SmartHttpServer smartHttpServer = new SmartHttpServer(properties);
        smartHttpServer.start();
    }
}