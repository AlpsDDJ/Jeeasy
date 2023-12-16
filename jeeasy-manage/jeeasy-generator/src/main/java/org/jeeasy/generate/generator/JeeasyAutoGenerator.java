//package org.jeeasy.generate.generator;
//
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.jeeasy.generate.domain.GenTable;
//
//@Slf4j
//@Data
//public class JeeasyAutoGenerator {
//    protected JeeasyConfigBuilder config;
//    protected InjectionConfig injection;
//    private StrategyConfig strategy;
//    private PackageConfig packageInfo;
//    private TemplateConfig template;
//    private GlobalConfig globalConfig;
//
//
//    private GenTable table;
//
//    public JeeasyAutoGenerator table(GenTable table) {
//        this.table = table;
//        return this;
//    }
//
//    public void execute() {
//        JeeasyTemplateEngine templateEngine = new JeeasyTemplateEngine();
//        log.debug("========================== Jeeasy 准备生成文件... ==========================");
//        if (null == this.config) {
//            this.config = new JeeasyConfigBuilder(this.getPackageInfo(), this.getStrategy(), this.getTemplate(), this.getGlobalConfig(), this.injection, table);
//        }
//
//        templateEngine.setConfigBuilder(this.config);
//        AbstractTemplateEngine engine = templateEngine.init(this.config).batchOutput();
//        engine.open();
//        log.debug("========================== Jeeasy 文件生成完成！！！ ==========================");
//    }
//}
