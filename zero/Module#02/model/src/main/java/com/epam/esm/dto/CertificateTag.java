package com.epam.esm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CertificateTag {
    private Long certificateId;
    private Long tagId;
}
