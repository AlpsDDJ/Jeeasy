//package org.jeeasy.generate.generator;
//
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.jeeasy.generate.domain.GenTable;
//
//import java.util.*;
//import java.util.regex.Pattern;
//
//@Slf4j
//@Data
//public class JeeasyConfigBuilder {
//
//    private final GenTable table;
//    private final TemplateConfig templateConfig;
//    private final List<TableInfo> tableInfoList = new ArrayList();
//    private final Map<OutputFile, String> pathInfo = new HashMap();
//    private StrategyConfig strategyConfig;
//    private GlobalConfig globalConfig;
//    private InjectionConfig injectionConfig;
//    private static final Pattern REGX = Pattern.compile("[~!/@#$%^&*()+\\\\\\[\\]|{};:'\",<.>?]+");
//    private final PackageConfig packageConfig;
//
//
//    public JeeasyConfigBuilder(PackageConfig packageConfig, StrategyConfig strategyConfig, TemplateConfig templateConfig, GlobalConfig globalConfig, InjectionConfig injectionConfig, GenTable table) {
//        this.strategyConfig = (StrategyConfig) Optional.ofNullable(strategyConfig).orElseGet(GeneratorBuilder::strategyConfig);
//        this.globalConfig = (GlobalConfig) Optional.ofNullable(globalConfig).orElseGet(GeneratorBuilder::globalConfig);
//        this.templateConfig = (TemplateConfig) Optional.ofNullable(templateConfig).orElseGet(GeneratorBuilder::templateConfig);
//        this.packageConfig = (PackageConfig) Optional.ofNullable(packageConfig).orElseGet(GeneratorBuilder::packageConfig);
//        this.injectionConfig = (InjectionConfig) Optional.ofNullable(injectionConfig).orElseGet(GeneratorBuilder::injectionConfig);
//        this.pathInfo.putAll((new JeeasyPathInfoHandler(this.globalConfig, this.templateConfig, this.packageConfig)).getPathInfo());
//        this.table = table;
//    }
//
//    public static boolean matcherRegTable(String tableName) {
//        return REGX.matcher(tableName).find();
//    }
//
//    public List<TableInfo> getTableInfoList() {
//        log.debug("{}", table);
//        return new ArrayList<>();
//    }
//}
