package org.jeeasy.common.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author wei.yang
 * @date 2023-01-02 17:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslateDictDTO {
    private String code;
    private Object value;
}
