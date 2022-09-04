package org.mryao.ucas.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mryao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UcasToken {

    private Integer vjuid;

    private String vjvd;

    private String eaiSess;

    private String uuKey;

    public UcasToken(Integer vjuid, String vjvd) {
        this.vjuid = vjuid;
        this.vjvd = vjvd;
    }

    public UcasToken(String eaiSess, String uuKey) {
        this.eaiSess = eaiSess;
        this.uuKey = uuKey;
    }
}
