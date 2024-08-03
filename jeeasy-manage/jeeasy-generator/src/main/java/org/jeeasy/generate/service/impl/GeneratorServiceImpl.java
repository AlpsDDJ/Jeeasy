package org.jeeasy.generate.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.generate.domain.GenTable;
import org.jeeasy.generate.domain.GenTemplate;
import org.jeeasy.generate.domain.dto.GeneratorDto;
import org.jeeasy.generate.generator.TableInfo;
import org.jeeasy.generate.service.GenTableService;
import org.jeeasy.generate.service.GenTemplateService;
import org.jeeasy.generate.service.GeneratorService;
import org.jeeasy.generate.service.vo.GenResultVo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GeneratorServiceImpl implements GeneratorService {

    @Resource
    GenTableService genTableService;
    @Resource
    GenTemplateService templateService;

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
                    //String type = item.getType();
                    Template template = getTemplateFromResources(item.getTemplate());
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

    }

    @Override
    public List<GenResultVo> genFiles(GeneratorDto module) {
        String tableId = module.getTableId();
        GenTable table = genTableService.getById(tableId);
        genTableService.setFields(table);
        log.debug("table ---> {}", table);
        List<GeneratorDto.GeneratorFile> files = module.getFiles();
        TableInfo tableInfo = new TableInfo(table, module);
        List<GenResultVo> outFiles = new ArrayList<>();
        files.forEach(item -> {
            String templateType = item.getTemplateType();
            String template = item.getTemplate();
            GenTemplate genTemplate = templateService.getByTypeAndNFileName(templateType, template);
            if (Tools.isNotEmpty(genTemplate)) {
                Context context = tableInfo.toContext();
                outFiles.add(render(genTemplate, context, item.getOutputName()));
            } else {
                log.warn("template not found ---> type: {}, file: {}", templateType, template);
            }
        });

        return outFiles;
    }

    private Template getTemplateFromResources(String temp) {
        //templateService.getById()
        return velocityEngine.getTemplate("templates/default/" + temp, Charset.defaultCharset().displayName());
        //return temp;
    }

    private GenResultVo render(GenTemplate genTemplate, Context context, String outPutName) {
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, String.format("%s: %s", genTemplate.getType(), genTemplate.getFileName()), genTemplate.getContext());
        GenResultVo resultVo = new GenResultVo();
        resultVo.setName(genTemplate.getName());
        resultVo.setContent(writer.toString());
        resultVo.setOutputName(outPutName);
        return resultVo;
    }

}
