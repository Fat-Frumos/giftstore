package com.epam.esm.dao.mapper;

final public class QueriesContext {
    private QueriesContext() {
    }

    public static final String GET_BY_ID = "SELECT id as c_id, name as c_name, description as c_description, create_date as c_create_date, last_update_date as c_last_update_date, duration as c_duration FROM gift_certificates WHERE id = ?";
    public static final String GET_ALL_CERTIFICATE = "SELECT id as c_id, name as c_name, description as c_description, create_date as c_create_date, last_update_date as c_last_update_date, duration as c_duration FROM gift_certificates";
    public static final String INSERT_CERTIFICATE = "INSERT INTO gift_certificates (name, description, create_date, last_update_date, duration) VALUES (?, ?, ?, ?, ?)";
    public static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificates WHERE id = ?";
    public static final String GET_TAG_BY_ID = "SELECT tag.id t_id, tag.name t_name FROM tag WHERE tag.id=?";
    public static final String GET_ALL_TAGS = "SELECT tag.id t_id, tag.name t_name FROM tag";
    public static final String DELETE_TAG = "DELETE FROM tag WHERE id=?";
    public static final String GET_BY_NAME = "SELECT tag.id t_id, tag.name t_name FROM tag WHERE tag.name=?";
}
