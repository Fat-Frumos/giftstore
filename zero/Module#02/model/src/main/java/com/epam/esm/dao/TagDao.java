package com.epam.esm.dao;

import com.epam.esm.dto.CertificateTag;
import com.epam.esm.entity.Tag;

import java.util.Set;

/**
 * The TagDao interface extends the generic Dao interface for Tag entities.
 * It defines specific operations related
 * to Tag entity that can be performed in a database.
 */
public interface TagDao extends Dao<Tag> {

    Set<Tag> saveAllTags(Set<Tag> tags);

    void saveAllRef(Set<CertificateTag> certificateTags);
}
