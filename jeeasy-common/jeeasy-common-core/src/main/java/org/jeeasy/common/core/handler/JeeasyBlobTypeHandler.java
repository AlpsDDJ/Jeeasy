//package org.jeeasy.common.core.handler;
//
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * 长文本二进制类型处理器
// *
// * @author wei.yang
// * @date 2023-12-17 23:56:27
// */
//@Component
//public class JeeasyBlobTypeHandler extends BaseTypeHandler<String> {
//
//    /**
//     * 设置非空参数
//     *
//     * @param preparedStatement PreparedStatement对象
//     * @param i                 参数索引
//     * @param s                 参数值
//     * @param jdbcType          JdbcType类型
//     * @throws SQLException 如果设置参数过程中出现异常
//     */
//    @Override
//    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
//        preparedStatement.setBytes(i, s.getBytes(StandardCharsets.UTF_8));
//    }
//
//    /**
//     * 获取空结果值
//     *
//     * @param resultSet ResultSet对象
//     * @param s         结果集列名
//     * @return 获取的结果值
//     * @throws SQLException 如果获取结果过程中出现异常
//     */
//    @Override
//    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
//        byte[] bytes = resultSet.getBytes(s);
//        return new String(bytes, StandardCharsets.UTF_8);
//    }
//
//    /**
//     * 获取空结果值
//     *
//     * @param resultSet ResultSet对象
//     * @param i         结果集列索引
//     * @return 获取的结果值
//     * @throws SQLException 如果获取结果过程中出现异常
//     */
//    @Override
//    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
//        byte[] bytes = resultSet.getBytes(i);
//        return new String(bytes, StandardCharsets.UTF_8);
//    }
//
//    /**
//     * 获取空结果值
//     *
//     * @param callableStatement CallableStatement对象
//     * @param i                 结果集列索引
//     * @return 获取的结果值
//     * @throws SQLException 如果获取结果过程中出现异常
//     */
//    @Override
//    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
//        byte[] bytes = callableStatement.getBytes(i);
//        return new String(bytes, StandardCharsets.UTF_8);
//    }
//}
//
