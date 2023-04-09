DROP TABLE IF EXISTS tags;

CREATE TABLE tags
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(55) NOT NULL
);


DROP TABLE IF EXISTS gift_certificate_tags;

CREATE TABLE gift_certificate_tags
(
    gift_certificate_id BIGINT REFERENCES gift_certificates (id),
    tag_id              BIGINT REFERENCES tags (id),
    PRIMARY KEY (gift_certificate_id, tag_id)
);
