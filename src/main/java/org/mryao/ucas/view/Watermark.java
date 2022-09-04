package org.mryao.ucas.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mryao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Watermark {

    @JsonProperty("i")
    private String id;

    @JsonProperty("t")
    private Long timestamp;

    public Watermark(String id) {
        this.id = id;
        this.timestamp = System.currentTimeMillis();
    }
}
