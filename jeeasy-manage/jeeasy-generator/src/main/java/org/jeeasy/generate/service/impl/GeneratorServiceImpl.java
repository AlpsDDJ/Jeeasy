package org.jeeasy.generate.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.jeeasy.generate.domain.GenTable;
import org.jeeasy.generate.domain.dto.GeneratorDto;
import org.jeeasy.generate.generator.TableInfo;
import org.jeeasy.generate.service.GenTableService;
import org.jeeasy.generate.service.GeneratorService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Service
@Slf4j
public class GeneratorServiceImpl implements GeneratorService {

    @Resource
    GenTableService genTableService;

    @Resource
    VelocityEngine velocityEngine;

    @Override
    public void generator(GeneratorDto module) {
        //FastAutoGenerator.create()
        String tableId = module.getTableId();
        GenTable table = genTableService.getById(tableId);
        genTableService.setFields(table);
        log.debug("table ---> {}", table);
        //Context context = new VelocityContext();
        //context.put("module", module);
        //context.put("table", table);
        try {
            List<GeneratorDto.GeneratorFile> files = module.getFiles();
            TableInfo tableInfo = new TableInfo(table, module);
            files.forEach(item -> {
                //StringWriter writer = new StringWriter();
                File outFile = new File("d:\\output\\", item.getOutputName());
                try {
                    FileUtils.writeStringToFile(outFile, "", Charset.defaultCharset());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try (FileWriter fileWriter = new FileWriter(outFile)) {
                    String type = item.getType();
                    Template template = getTemplate(item.getTemplate());
                    template.merge(tableInfo.toContext(), fileWriter);
                    //velocityEngine.evaluate(tableInfo.toContext(), fileWriter, "generator-" + type, template);

                    //log.info("generator ---> {}", writer.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            //velocityEngine.init();


        } catch (Exception e) {
            e.printStackTrace();
        }

        //PackageConfig packageConfig = packageConfig(module);
        //TemplateConfig templateConfig = templateConfig(table);
        //InjectionConfig injectionConfig = injectionConfig(table, module);
        //JeeasyAutoGenerator generator = null;
        //try {
        //    generator = new JeeasyAutoGenerator();
        //} catch (Exception ignored) {
        //    ignored.printStackTrace();
        //}
        //generator.setTemplate(templateConfig);
        //generator.setPackageInfo(packageConfig);
        //generator.setInjection(injectionConfig);
        //generator.table(table);
        //generator.execute();
        //generator
    }

    //protected  Optional<String> getTemplateFilePath(@NotNull Function<TemplateConfig, String> function) {
    //    TemplateConfig templateConfig = this.getConfigBuilder().getTemplateConfig();
    //    String filePath = (String)function.apply(templateConfig);
    //    return StringUtils.isNotBlank(filePath) ? Optional.of(this.templateFilePath(filePath)) : Optional.empty();
    //}
    private Template getTemplate(String temp) {
        return velocityEngine.getTemplate("templates/default/" + temp, Charset.defaultCharset().displayName());
        //return temp;
    }

    //private PackageConfig packageConfig(GenModule module) {
    //
    //    String pkg = module.getPkg();
    //    String moduleCode = module.getModuleCode();
    //    pkg = pkg.replace("{module}", moduleCode);
    //    return new PackageConfig.Builder()
    //            .parent(pkg)
    //            .moduleName(moduleCode)
    //            .entity(module.getEntity())
    //            .mapper(module.getMapper())
    //            .xml(module.getXml())
    //            .service(module.getService())
    //            .serviceImpl(module.getServiceImpl())
    //            .controller(module.getController())
    //            //.other("other")
    //            //.pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://generator"))
    //            .build();
    //}
    //
    //private String getTemplatePath(GenTable table) {
    //    String tableType = table.getTableType();
    //    String templatePath = "/templates/";
    //    if ("single".equals(tableType)) {
    //        templatePath += "default";
    //    } else {
    //    }
    //    return templatePath;
    //}
    //
    //private TemplateConfig templateConfig(GenTable table) {
    //    String templatePath = getTemplatePath(table);
    //    return new TemplateConfig.Builder()
    //            .disable(TemplateType.ENTITY)
    //            .entity(templatePath + "/entity.java")
    //            .service(templatePath + "/service.java")
    //            .serviceImpl(templatePath + "/serviceImpl.java")
    //            .mapper(templatePath + "/mapper.java")
    //            .xml(templatePath + "/mapper.xml")
    //            .controller(templatePath + "/controller.java")
    //            .build();
    //
    //}
    //
    //private InjectionConfig injectionConfig(GenTable table, GenModule module) {
    //    String templatePath = getTemplatePath(table);
    //    String moduleName = module.getName();
    //    String pascalModuleName = NamingCase.toPascalCase(moduleName);
    //    String tableName = table.getName();
    //    String pascalTableName = NamingCase.toPascalCase(tableName);
    //    String webFilePath = String.format("/%s/%s", pascalModuleName, pascalTableName);
    //    Map<String, String> wenFileMap = new HashMap<>();
    //    wenFileMap.put(webFilePath + "/index.vue", templatePath + "/web/list.vm");
    //    wenFileMap.put(webFilePath + "/model.ts", templatePath + "/web/model.vm");
    //    return new InjectionConfig.Builder()
    //            .beforeOutputFile((tableInfo, objectMap) -> {
    //                log.info("tableInfo: {} objectMap: {}", tableInfo.getEntityName(), objectMap.size());
    //            })
    //            .customMap(Collections.singletonMap("table", table))
    //            .customFile(wenFileMap)
    //            .build();
    //
    //}
}
