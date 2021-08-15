package org.jeeasy.common.core.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author mobie
 */
@Data
@Accessors(chain = true)
public class TableDictVo {

    private String tableName;

    private String databaseName;

    private String valueColumn;

    private String nameColumn;

}
