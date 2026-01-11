package io.github.enelrith.bluebay.enums;

/**
 * Enum for the id_document_type in the users table
 */
public enum UserIdDocumentType {
    NATIONAL_ID,
    PASSPORT;

    public static boolean isValid(String value) {
        for (UserIdDocumentType type : UserIdDocumentType.values()) {
            if (type.toString().equals(value))
                return true;
        }
        return false;
    }
}
