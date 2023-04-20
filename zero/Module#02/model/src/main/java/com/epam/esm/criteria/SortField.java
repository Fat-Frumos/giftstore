package com.epam.esm.criteria;

import lombok.Getter;

/**
 * Enum representing the possible fields that can be used for sorting in a query.
 * <p>
 * Each field is associated with the corresponding database column name.
 */
@Getter
public enum SortField {

    /**
     * Field representing the certificate's last update date
     */
    DATE,
    /**
     * Field representing the certificate's name
     */
    NAME,
    /**
     * Field representing the certificate's id by default
     */
    ID;

    public String value() {
        if (ordinal() == 1) {
            return "c.name";
        } else if (ordinal() == 0) {
            return "c.create_date";
        }
        return "c.id";
    }
}

