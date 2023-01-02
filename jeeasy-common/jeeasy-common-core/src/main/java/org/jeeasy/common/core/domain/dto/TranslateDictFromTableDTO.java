package org.jeeasy.common.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeeasy.common.core.domain.vo.TableDictVo;

/**
 * TODO
 *
 * @author wei.yang
 * @date 2023-01-02 17:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslateDictFromTableDTO {
    private TableDictVo tableDict;
    private Object value;
}
