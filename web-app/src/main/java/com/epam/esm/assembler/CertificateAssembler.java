package com.epam.esm.assembler;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.criteria.FilterParams;
import com.epam.esm.dto.CertificateDto;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import javax.swing.SortOrder;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateAssembler
        implements RepresentationModelAssembler<CertificateDto, EntityModel<CertificateDto>> {

    @NonNull
    @Override
    public EntityModel<CertificateDto> toModel(@NonNull final CertificateDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(CertificateController.class).getTagsByCertificateId(dto.getId())).withRel("tags"),
                linkTo(methodOn(OrderController.class).getAllOrdersByUserId(dto.getId())).withRel("user"),
                linkTo(methodOn(CertificateController.class).getCertificateById(dto.getId())).withSelfRel(),
                linkTo(methodOn(CertificateController.class).delete(dto.getId())).withRel("delete")
//                linkTo(methodOn(CertificateController.class).create(dto)).withRel("create"),
//                linkTo(methodOn(CertificateController.class).update(dto, dto.getId())).withRel("update"),
        );
    }

    @NonNull
    @Override
    public CollectionModel<EntityModel<CertificateDto>> toCollectionModel(
            final Iterable<? extends CertificateDto> entities) {
        return CollectionModel.of(StreamSupport
                        .stream(entities.spliterator(), false)
                        .map(this::toModel)
                        .collect(toList()),
                linkTo(methodOn(CertificateController.class)
                        .getAll(SortOrder.UNSORTED, FilterParams.ID, 0, 25))
                        .withSelfRel());
    }
}
