package org.jeeasy.generate.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeneratorDto {
    private String tableId;
    private String author;

    private List<GeneratorFile> files;

    @Data
    public static class GeneratorFile {
        private String templateType = "default";
        private String pkg;
        private String path;
        private String type;
        private String template;
        private String outputName;
        private String className;

    }
}
