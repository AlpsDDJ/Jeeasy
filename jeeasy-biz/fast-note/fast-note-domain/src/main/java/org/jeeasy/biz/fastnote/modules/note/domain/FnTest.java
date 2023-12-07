package org.jeeasy.biz.fastnote.modules.note.domain;

import com.tangzc.mpe.autotable.annotation.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO
 *
 * @author wei.yang
 * @date 2022-09-18 14:43
 */
@Data
@Table(value = "fn_test", comment = "测试表")
public class FnTest {
    private String id;

    @Index
    @ColumnComment("用户名")
    private String username;

    @Column(length = 10, comment = "价格")
    private BigDecimal newPrice;
}
