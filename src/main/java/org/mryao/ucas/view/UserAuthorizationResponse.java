package org.mryao.ucas.view;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mryao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserAuthorizationResponse extends BaseResponse {

    private String email;

    private String ucasId;
}
