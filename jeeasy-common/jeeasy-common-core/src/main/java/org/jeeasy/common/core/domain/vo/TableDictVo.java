package org.jeeasy.common.core.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author mobie
 */
@Data
@Accessors(chain = true)
public class TableDictVo  implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tableName;

    private String databaseName;

    private String valueColumn;

    private String nameColumn;

}
