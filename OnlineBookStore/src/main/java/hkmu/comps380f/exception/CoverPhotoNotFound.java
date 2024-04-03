package hkmu.comps380f.exception;

import java.util.UUID;

public class CoverPhotoNotFound extends Exception {
    public CoverPhotoNotFound(UUID id) {
        super("CoverPhoto " + id + " does not exist.");
    }
}
