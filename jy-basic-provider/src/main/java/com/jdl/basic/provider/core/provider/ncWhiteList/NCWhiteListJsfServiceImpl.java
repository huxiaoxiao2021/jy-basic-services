package com.jdl.basic.provider.core.provider.ncWhiteList;

import com.jdl.basic.api.domain.ncWhiteList.NCWhiteListQuery;
import com.jdl.basic.api.dto.ncWhiteList.NCWhiteListDTO;
import com.jdl.basic.api.dto.ncWhiteList.NCWhiteRuleDTO;
import com.jdl.basic.api.service.ncWhiteList.NCWhiteListJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.po.NCWhiteList;
import com.jdl.basic.provider.core.po.NCWhiteRule;
import com.jdl.basic.provider.core.service.ncWhitelist.NCWhiteListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NCWhiteListJsfServiceImpl implements NCWhiteListJsfService {

    @Autowired
    private NCWhiteListService ncWhiteListService;

    @Override
    public Result<Integer> add(NCWhiteListDTO dto) {
        int added = ncWhiteListService.add(getWhiteListPo(dto), getRulesPo(dto));
        return Result.success(added);
    }

    @Override
    public Result<Integer> modify(NCWhiteListDTO dto) {
        int modified = ncWhiteListService.modify(getWhiteListPo(dto), getRulesPo(dto));
        return Result.success(modified);
    }

    @Override
    public Result<Integer> remove(int id) {
        int removed = ncWhiteListService.removeById(id);
        return Result.success(removed);
    }

    @Override
    public Result<PageDto<NCWhiteListDTO>> queryByCondition(NCWhiteListQuery query) {
        List<NCWhiteList> ncWhiteLists = ncWhiteListService.queryWhiteListByCondition(query);
        int total = ncWhiteListService.countByCondition(query);
        PageDto<NCWhiteListDTO> pageDto = new PageDto<>();
        pageDto.setTotalPage(total);
        pageDto.setResult(makeWhiteListDTO(ncWhiteLists));
        return Result.success(pageDto);
    }


    @Override
    public Result<Integer> countByCondition(NCWhiteListQuery query) {
        return Result.success(ncWhiteListService.countByCondition(query));
    }

    private List<NCWhiteRule> getRulesPo(NCWhiteListDTO ncWhiteListDTO) {
        List<NCWhiteRuleDTO> rulesDTO = ncWhiteListDTO.getRules();
        List<NCWhiteRule> ncWhiteRules = new ArrayList<>();
        for (NCWhiteRuleDTO item : rulesDTO) {
            NCWhiteRule ncWhiteRule = new NCWhiteRule();
            BeanUtils.copyProperties(item, ncWhiteRule);
            ncWhiteRule.setRefId(ncWhiteListDTO.getId());
            ncWhiteRules.add(ncWhiteRule);
        }
        return ncWhiteRules;
    }

    private NCWhiteList getWhiteListPo(NCWhiteListDTO dto) {
        NCWhiteList ncWhiteList = new NCWhiteList();
        BeanUtils.copyProperties(dto, ncWhiteList);
        return ncWhiteList;
    }

    private List<NCWhiteRuleDTO> makeRulesDTO(List<NCWhiteRule> rule) {
        List<NCWhiteRuleDTO> dtos = new ArrayList<>();
        for (NCWhiteRule item : rule) {
            NCWhiteRuleDTO dto = new NCWhiteRuleDTO();
            BeanUtils.copyProperties(item, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    private List<NCWhiteListDTO> makeWhiteListDTO(List<NCWhiteList> ncWhiteList) {
        List<NCWhiteListDTO> list = new ArrayList<>();
        for (NCWhiteList item : ncWhiteList) {
            NCWhiteListDTO dto = new NCWhiteListDTO();
            BeanUtils.copyProperties(item, dto);
            List<NCWhiteRule> ncWhiteRules = ncWhiteListService.queryRuleByRefId(item.getId());
            List<NCWhiteRuleDTO> ncWhiteRuleDTOS = makeRulesDTO(ncWhiteRules);
            dto.setRules(ncWhiteRuleDTOS);
            list.add(dto);
        }
        return list;
    }


}
