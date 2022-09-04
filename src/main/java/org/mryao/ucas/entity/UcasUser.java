package org.mryao.ucas.entity;

import lombok.Data;

import java.time.Instant;

/**
 * @author mryao
 */
@Data
public class UcasUser {

    private Long id;

    private String ucasId;

    private String ucasEmail;

    private String name;

    private String gender;

    private String department;

    private String mobile;

    private String email;

    private String idCard;

    private String politics;

    private String workflowVjuid;

    private String workflowVjvd;

    private String meVjuid;

    private String meVjvd;

    private Instant insertedAt;

    private Instant updatedAt;
}
