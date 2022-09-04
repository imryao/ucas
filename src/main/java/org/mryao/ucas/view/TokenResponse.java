package org.mryao.ucas.view;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TokenResponse extends BaseResponse {

    private String token;
}
