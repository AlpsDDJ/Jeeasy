package org.jeeasy.system.modules.depart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.StringUtil;
import org.jeeasy.common.core.tools.YouBianCodeUtil;
import org.jeeasy.system.enums.depart.OrgTypeEnum;
import org.jeeasy.system.modules.depart.entity.SysDepart;
import org.jeeasy.system.modules.depart.mapper.SysDepartMapper;
import org.jeeasy.system.modules.depart.service.SysDepartService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2020/11/24 11:41
 */
@Service
public class SysDepartServiceImpl extends ServiceImpl<SysDepartMapper, SysDepart> implements SysDepartService {


    @Override
    public void saveDepartData(SysDepart depart) {
        if (depart != null) {
            if (depart.getParentId() == null) {
                depart.setParentId("");
            }
            // 获取父级ID
            SysDepart newSubDepartParams = this.getNewSubDepartParams(depart.getParentId());
            depart.setOrgCode(newSubDepartParams.getOrgCode());
            depart.setOrgType(newSubDepartParams.getOrgType());
            this.save(depart);
        }
    }

    @Override
    public SysDepart getNewSubDepartParams(String parentId) {
        LambdaQueryWrapper<SysDepart> query = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<SysDepart> query1 = new LambdaQueryWrapper<>();
        // 创建一个List集合,存储查询返回的所有SysDepart对象
//        List<SysDepart> departList = new ArrayList<>();
        //定义部门类型
        Integer orgType;
        // 定义新编码字符串
        String newOrgCode = "";
        // 定义旧编码字符串
        String oldOrgCode = "";

        //如果是最高级,则查询出同级的org_code, 调用工具类生成编码并返回
        if (StringUtil.isNullOrEmpty(parentId)) {
            // 线判断数据库中的表是否为空,空则直接返回初始编码
            query1.eq(SysDepart::getParentId, "").or().isNull(SysDepart::getParentId);
            query1.orderByDesc(SysDepart::getOrgCode);
            List<SysDepart> departList = this.list(query1);
            if (departList == null || departList.size() == 0) {
                newOrgCode = YouBianCodeUtil.getNextYouBianCode(null);
                orgType = OrgTypeEnum.TOP.getValue();
                return new SysDepart().setOrgCode(newOrgCode).setOrgType(orgType);
            } else {
                SysDepart depart = departList.get(0);
                oldOrgCode = depart.getOrgCode();
                orgType = depart.getOrgType();
                newOrgCode = YouBianCodeUtil.getNextYouBianCode(oldOrgCode);
            }
        } else {//反之则查询出所有同级的部门,获取结果后有两种情况,有同级和没有同级
            // 封装查询同级的条件
            query.eq(SysDepart::getParentId, parentId);
            // 降序排序
            query.orderByDesc(SysDepart::getOrgCode);
            // 查询出同级部门的集合
            List<SysDepart> parentList = this.list(query);
            // 查询出父级部门
            SysDepart depart = this.getById(parentId);
            // 获取父级部门的Code
            String parentCode = depart.getOrgCode();
            // 根据父级部门类型算出当前部门的类型
            orgType = depart.getOrgType() + 1;
            // 处理同级部门为null的情况
            if (parentList == null || parentList.size() == 0) {
                // 直接生成当前的部门编码并返回
                newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, null);
            } else { //处理有同级部门的情况
                // 获取同级部门的编码,利用工具类
                String subCode = parentList.get(0).getOrgCode();
                // 返回生成的当前部门编码
                newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, subCode);
            }
        }
        // 返回最终封装了部门编码和部门类型的数组
        return new SysDepart().setOrgCode(newOrgCode).setOrgType(orgType);
    }
}
