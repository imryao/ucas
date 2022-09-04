package org.mryao.ucas.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class UcasSecret {

    private Long id;

    private String ucasId;

    private String sepSecret;

    private String sepWatermark;

    private Instant insertedAt;

    private Instant updatedAt;
}
