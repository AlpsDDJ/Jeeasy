//package org.jeeasy.generate.generator;
//
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.generator.config.ConstVal;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
//import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
//import com.baomidou.mybatisplus.generator.util.RuntimeUtils;
//import org.apache.velocity.app.VelocityEngine;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//
//public class JeeasyTemplateEngine extends VelocityTemplateEngine {
//    private VelocityEngine velocityEngine;
//    private JeeasyConfigBuilder configBuilder;
//
//    public AbstractTemplateEngine setConfigBuilder(JeeasyConfigBuilder configBuilder) {
//        this.configBuilder = configBuilder;
//        return this;
//    }
//
//    public VelocityTemplateEngine init(JeeasyConfigBuilder configBuilder) {
//        if (null == this.velocityEngine) {
//            Properties p = new Properties();
//            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//            p.setProperty("resource.loader.file.path", "");
//            p.setProperty("UTF-8", ConstVal.UTF8);
//            p.setProperty("resource.default_encoding", ConstVal.UTF8);
//            p.setProperty("file.resource.loader.unicode", "true");
//            this.velocityEngine = new VelocityEngine(p);
//        }
//
//        return this;
//    }
//
//    @Override
//    public void open() {
//        String outDir = this.configBuilder.getGlobalConfig().getOutputDir();
//        if (!StringUtils.isBlank(outDir) && (new File(outDir)).exists()) {
//            if (this.configBuilder.getGlobalConfig().isOpen()) {
//                try {
//                    RuntimeUtils.openDir(outDir);
//                } catch (IOException var3) {
//                    this.LOGGER.error(var3.getMessage(), var3);
//                }
//            }
//        } else {
//            System.err.println("未找到输出目录：" + outDir);
//        }
//
//    }
//
//    @Override
//    public AbstractTemplateEngine batchOutput() {
//        try {
//            List<TableInfo> tableInfoList = this.configBuilder.getTableInfoList();
//            tableInfoList.forEach((tableInfo) -> {
//                Map<String, Object> objectMap = this.getObjectMap(this.configBuilder, tableInfo);
//                Optional.ofNullable(this.configBuilder.getInjectionConfig()).ifPresent((t) -> {
//                    t.beforeOutputFile(tableInfo, objectMap);
//                    this.outputCustomFile(t.getCustomFiles(), tableInfo, objectMap);
//                });
//                this.outputEntity(tableInfo, objectMap);
//                this.outputMapper(tableInfo, objectMap);
//                this.outputService(tableInfo, objectMap);
//                this.outputController(tableInfo, objectMap);
//            });
//            return this;
//        } catch (Exception var3) {
//            throw new RuntimeException("无法创建文件，请检查配置信息！", var3);
//        }
//    }
//
//    public Map<String, Object> getObjectMap(JeeasyConfigBuilder config, TableInfo tableInfo) {
//        StrategyConfig strategyConfig = config.getStrategyConfig();
//        Map<String, Object> controllerData = strategyConfig.controller().renderData(tableInfo);
//        Map<String, Object> objectMap = new HashMap(controllerData);
//        Map<String, Object> mapperData = strategyConfig.mapper().renderData(tableInfo);
//        objectMap.putAll(mapperData);
//        Map<String, Object> serviceData = strategyConfig.service().renderData(tableInfo);
//        objectMap.putAll(serviceData);
//        Map<String, Object> entityData = strategyConfig.entity().renderData(tableInfo);
//        objectMap.putAll(entityData);
//        objectMap.put("config", config);
//        objectMap.put("package", config.getPackageConfig().getPackageInfo());
//        GlobalConfig globalConfig = config.getGlobalConfig();
//        objectMap.put("author", globalConfig.getAuthor());
//        objectMap.put("kotlin", globalConfig.isKotlin());
//        objectMap.put("swagger", globalConfig.isSwagger());
//        objectMap.put("springdoc", globalConfig.isSpringdoc());
//        objectMap.put("date", globalConfig.getCommentDate());
//        String schemaName = "";
//        //if (strategyConfig.isEnableSchema()) {
//        //    schemaName = config.getDataSourceConfig().getSchemaName();
//        //    if (StringUtils.isNotBlank(schemaName)) {
//        //        schemaName = schemaName + ".";
//        //        tableInfo.setConvert(true);
//        //    }
//        //}
//
//        objectMap.put("schemaName", schemaName);
//        objectMap.put("table", tableInfo);
//        objectMap.put("entity", tableInfo.getEntityName());
//        return objectMap;
//    }
//
//}
