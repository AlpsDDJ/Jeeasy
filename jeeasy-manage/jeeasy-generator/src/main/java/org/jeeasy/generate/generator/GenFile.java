//package org.jeeasy.generate.generator;
//
//import lombok.Data;
//import org.jeeasy.generate.domain.GenModule;
//import org.jeeasy.generate.domain.GenTable;
//
//import java.util.List;
//
//@Data
//public class GenFile {
//
//    private String outputName;
//    private String outputPath;
//    private String pkg;
//    //private String parentPkg;
//
//    public static List<GenFile> createFiles(GenModule module, GenTable table) {
//
//        String moduleCode = module.getModuleCode();
//        String parentPkg = module.getPkg().replace("module", moduleCode);
//        String entityPkg = module.getEntity();
//        String mapperPkg = module.getMapper();
//        String xmlPkg = module.getMapperXml();
//        String servicePkg = module.getService();
//        String serviceImplPkg = module.getServiceImpl();
//        String controllerPkg = module.getController();
//        CaseName tableName = new CaseName(table.getName());
//
//
//        return null;
//    }
//
//    private GenFile(String parentPkg, String name, String ext) {
//        this.pkg = parentPkg + "." + name;
//    }
//
//}
