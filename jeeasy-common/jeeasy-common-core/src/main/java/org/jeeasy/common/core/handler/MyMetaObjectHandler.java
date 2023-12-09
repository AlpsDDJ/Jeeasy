package org.jeeasy.common.core.handler;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.jeeasy.common.core.domain.IAuthUser;
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

    @Autowired(required = false)
    private CurrentAuthUserService currentAuthUserService;

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            IAuthUser currentAuthUser = currentAuthUserService.getCurrentAuthUser();

            if (BeanUtil.isNotEmpty(currentAuthUser)) {
                this.strictInsertFill(metaObject, CREATE_BY, currentAuthUser::id, String.class);
            }
        } catch (Exception e) {
            log.warn("MyMetaObjectHandler ---> {}", e.getMessage());
        }

        this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime::now, LocalDateTime.class);
        // 启用标记 - 默认启用
        this.strictInsertFill(metaObject, ENABLE_FLAG, EnableFlagEnum.YES::getValue, Integer.class);
        // 删除标记 - 默认未删除
        this.strictInsertFill(metaObject, DEL_FLAG, DelFlagEnum.NO::getValue, Integer.class);
        // 默认状态 - 1
        this.strictInsertFill(metaObject, STATUS, () -> DEFAULT_STATUS, Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            IAuthUser currentAuthUser = currentAuthUserService.getCurrentAuthUser();

            if (BeanUtil.isNotEmpty(currentAuthUser)) {
                this.strictUpdateFill(metaObject, UPDATE_BY, currentAuthUser::id, String.class);
            }
        } catch (Exception e) {
            log.warn("MyMetaObjectHandler ---> {}", e.getMessage());
        }
        this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
    }
}
