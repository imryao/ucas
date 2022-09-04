package org.mryao.ucas.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author mryao
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowGetNameResponse {

    private String name;

    @JsonProperty("sex")
    private String gender;

    @JsonProperty("number")
    private String ucasId;

    @JsonProperty("identity_type")
    private String identityType;

    @JsonProperty("identity_id")
    private String identityId;

    @JsonProperty("depart")
    private String department;

    @JsonProperty("college")
    private String college;

    @JsonProperty("class")
    private String classId;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("id_card")
    private String idCard;

    @JsonProperty("politics")
    private String politics;

    @JsonProperty("uid")
    private String uid;
}
