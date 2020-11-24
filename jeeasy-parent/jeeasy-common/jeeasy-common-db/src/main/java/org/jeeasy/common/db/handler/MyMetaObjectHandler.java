package org.jeeasy.common.db.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.jeeasy.common.core.entity.IAuthUser;
import org.jeeasy.common.core.enums.DelFlagEnum;
import org.jeeasy.common.core.enums.EnableFlagEnum;
import org.jeeasy.common.core.service.CurrentAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动注入属性值
 *
 * @author AlpsDDJ
 * @date 2020/11/24 8:59
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_TIME = "createTime";
    private static final String CREATE_BY = "createBy";
    private static final String UPDATE_TIME = "updateTime";
    private static final String UPDATE_BY = "updateBy";
    private static final String ENABLE_FLAG = "enableFlag";
    private static final String DEL_FLAG = "delFlag";
    private static final String STATUS = "status";

    public static final Integer DEFAULT_STATUS = 1;

    @Autowired
    CurrentAuthUserService currentAuthUserService;

    @Override
    public void insertFill(MetaObject metaObject) {
        IAuthUser currentAuthUser = currentAuthUserService.getCurrentAuthUser();
        this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, CREATE_BY, currentAuthUser::id, String.class);
        // 启用标记 - 默认启用
        this.strictInsertFill(metaObject, ENABLE_FLAG, EnableFlagEnum.YES::getValue, Integer.class);
        // 删除标记 - 默认未删除
        this.strictInsertFill(metaObject, DEL_FLAG, DelFlagEnum.NO::getValue, Integer.class);
        // 默认状态 - 1
        this.strictInsertFill(metaObject, STATUS, () -> DEFAULT_STATUS, Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        IAuthUser currentAuthUser = currentAuthUserService.getCurrentAuthUser();
        this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, UPDATE_BY, currentAuthUser::id, String.class);
    }
}
