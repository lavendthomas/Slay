package be.ac.umons.slay.g02.level;

/**
 *  TODO
 */
public class FileFormatException extends Exception {

    public FileFormatException() { super(); }
    public FileFormatException(String message) {
        super(message);
    }
    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
    public FileFormatException(Throwable cause) {
        super(cause);
    }
}
