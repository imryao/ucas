package org.mryao.ucas.view;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mryao.ucas.util.CryptoUtil;

/**
 * @author mryao
 */
@Data
@NoArgsConstructor
public class SepLoginRequest {

    private String username;

    private String password;

    public SepLoginRequest(String username, String password) {
        this.username = CryptoUtil.base64encode(username);
        this.password = CryptoUtil.base64encode(password);
    }
}
