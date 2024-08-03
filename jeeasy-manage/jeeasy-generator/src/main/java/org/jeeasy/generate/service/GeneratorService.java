package org.jeeasy.generate.service;

import org.jeeasy.generate.domain.dto.GeneratorDto;
import org.jeeasy.generate.service.vo.GenResultVo;

import java.util.List;

public interface GeneratorService {
    void generator(GeneratorDto module);

    List<GenResultVo> genFiles(GeneratorDto module);
}
