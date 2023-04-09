package com.epam.esm.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "gift_certificates")
public class GiftCertificate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;
    private Integer duration;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    public GiftCertificate addTag(Tag tag) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        this.tags.add(tag);
        return this;
    }

    @Override
    public String toString() {
        return String.format("Certificate{name:'%s', description:'%s', tags:%s}", name, description, tags);
    }
}
