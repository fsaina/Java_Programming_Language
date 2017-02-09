package hr.fer.zemris.java.webserver;


/**
 * WebWorker interface for next jobs to implement
 */
public interface IWebWorker {

    /**
     * Method that sets all the necessary values over the given context object. In method call write on the
     * context object to perform writing to the stream.
     *
     * @param context RequestContext object given for manipulating the return values
     */
    void processRequest(RequestContext context);
}
