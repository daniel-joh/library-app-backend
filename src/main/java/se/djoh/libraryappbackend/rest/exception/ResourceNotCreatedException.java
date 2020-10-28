package se.djoh.libraryappbackend.rest.exception;

public class ResourceNotCreatedException extends RuntimeException{
    private String message;

    public ResourceNotCreatedException() {
    }

    public ResourceNotCreatedException(String message) {
        super(message);
    }
}

