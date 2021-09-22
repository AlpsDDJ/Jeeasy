package org.jeeasy.system.modules.dept.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.StringUtil;
import org.jeeasy.common.core.tools.YouBianCodeUtil;
import org.jeeasy.system.enums.dept.OrgTypeEnum;
import org.jeeasy.system.modules.dept.domain.SysDept;
import org.jeeasy.system.modules.dept.mapper.SysDeptMapper;
import org.jeeasy.system.modules.dept.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2020/11/24 11:41
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {


    @Override
    public void saveDeptData(SysDept dept) {
        if (dept != null) {
            if (dept.getParentId() == null) {
                dept.setParentId("");
            }
            // 获取父级ID
            SysDept newSubDeptParams = this.getNewSubDeptParams(dept.getParentId());
            dept.setOrgCode(newSubDeptParams.getOrgCode());
            dept.setOrgType(newSubDeptParams.getOrgType());
            this.save(dept);
        }
    }

    @Override
    public SysDept getNewSubDeptParams(String parentId) {
        LambdaQueryWrapper<SysDept> query = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<SysDept> query1 = new LambdaQueryWrapper<>();
        // 创建一个List集合,存储查询返回的所有SysDept对象
//        List<SysDept> deptList = new ArrayList<>();
        //定义部门类型
        Integer orgType;
        // 定义新编码字符串
        String newOrgCode = "";
        // 定义旧编码字符串
        String oldOrgCode = "";

        //如果是最高级,则查询出同级的org_code, 调用工具类生成编码并返回
        if (StringUtil.isNullOrEmpty(parentId)) {
            // 线判断数据库中的表是否为空,空则直接返回初始编码
            query1.eq(SysDept::getParentId, "").or().isNull(SysDept::getParentId);
            query1.orderByDesc(SysDept::getOrgCode);
            List<SysDept> deptList = this.list(query1);
            if (deptList == null || deptList.size() == 0) {
                newOrgCode = YouBianCodeUtil.getNextYouBianCode(null);
                orgType = OrgTypeEnum.TOP.getValue();
                return new SysDept().setOrgCode(newOrgCode).setOrgType(orgType);
            } else {
                SysDept dept = deptList.get(0);
                oldOrgCode = dept.getOrgCode();
                orgType = dept.getOrgType();
                newOrgCode = YouBianCodeUtil.getNextYouBianCode(oldOrgCode);
            }
        } else {//反之则查询出所有同级的部门,获取结果后有两种情况,有同级和没有同级
            // 封装查询同级的条件
            query.eq(SysDept::getParentId, parentId);
            // 降序排序
            query.orderByDesc(SysDept::getOrgCode);
            // 查询出同级部门的集合
            List<SysDept> parentList = this.list(query);
            // 查询出父级部门
            SysDept dept = this.getById(parentId);
            // 获取父级部门的Code
            String parentCode = dept.getOrgCode();
            // 根据父级部门类型算出当前部门的类型
            orgType = dept.getOrgType() + 1;
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
        return new SysDept().setOrgCode(newOrgCode).setOrgType(orgType);
    }
}
