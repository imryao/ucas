package org.mryao.ucas.view;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author mryao
 */
@Data
public class UserAuthorizationRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
}
