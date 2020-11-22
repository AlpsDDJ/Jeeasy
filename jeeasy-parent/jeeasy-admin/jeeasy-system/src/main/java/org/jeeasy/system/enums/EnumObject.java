package org.jeeasy.system.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * EnumObject: TODO
 *
 * @author AlpsDDJ
 * @version v1.0
 * @date 2020/11/22 17:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EnumObject implements Serializable {
    private Serializable value;
    private String text;
}
