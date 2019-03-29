package be.ac.umons.slay.g02.level;

/**
 * Exception thrown then the files on disk do not match the standards
 */
public class FileFormatException extends Exception {

    public FileFormatException() {
        super();
    }

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
