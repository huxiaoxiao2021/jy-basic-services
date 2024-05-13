package com.jdl.basic.provider.core.provider.ncAllowedAutoSortList;

import com.jdl.basic.api.domain.ncAllowedAutoSortList.NCAllowedAutoSortListQuery;
import com.jdl.basic.api.dto.ncAllowedAutoSortList.NCAllowedAutoSortListDTO;
import com.jdl.basic.api.dto.ncAllowedAutoSortList.NCAllowedAutoSortRuleDTO;
import com.jdl.basic.api.service.ncAllowedAutoSortList.NCAllowedAutoSortListJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.po.NCAllowedAutoSortList;
import com.jdl.basic.provider.core.po.NCAllowedAutoSortRule;
import com.jdl.basic.provider.core.service.ncAllowedAutoSortList.NCAllowedAutoSortListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("NCAllowedAutoSortListJsfServiceImpl")
@Slf4j
public class NCAllowedAutoSortListJsfServiceImpl implements NCAllowedAutoSortListJsfService {

    @Autowired
    private NCAllowedAutoSortListService ncAllowedAutoSortListService;

    private static final String LENGTH = "length";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String VOLUME = "volume";

    private static final Double  DIFF = 1e-6;

    @Override
    public Result<Integer> add(NCAllowedAutoSortListDTO dto) {
        int added = ncAllowedAutoSortListService.add(getListPo(dto), getRulesPo(dto));
        return Result.success(added);
    }

    @Override
    public Result<Integer> modify(NCAllowedAutoSortListDTO dto) {
        int modified = ncAllowedAutoSortListService.modify(getListPo(dto), getRulesPo(dto));
        return Result.success(modified);
    }

    @Override
    public Result<Integer> remove(int id) {
        int removed = ncAllowedAutoSortListService.removeById(id);
        return Result.success(removed);
    }

    @Override
    public Result<PageDto<NCAllowedAutoSortListDTO>> queryByCondition(NCAllowedAutoSortListQuery query) {
        List<NCAllowedAutoSortList> ncAllowedAutoSortList = ncAllowedAutoSortListService.queryListByCondition(query);
        int total = ncAllowedAutoSortListService.countByCondition(query);
        PageDto<NCAllowedAutoSortListDTO> pageDto = new PageDto<>();
        pageDto.setTotalRow(total);
        pageDto.setResult(makeListDTO(ncAllowedAutoSortList));
        return Result.success(pageDto);
    }


    @Override
    public Result<Integer> countByCondition(NCAllowedAutoSortListQuery query) {
        return Result.success(ncAllowedAutoSortListService.countByCondition(query));
    }

    /*
     *入参： 长 宽 高 体积 重量 物品描述 预判是否为NC 白名单json串
     *返回值： 0-不是nc 1-是nc
     */
    @Override
    public Result<String> evaluate(String length, String width, String height, String volume, String weight , String description) {
        return Result.success("");
    }



    private List<NCAllowedAutoSortRule> getRulesPo(NCAllowedAutoSortListDTO ncAllowedAutoSortListDTO) {
        List<NCAllowedAutoSortRuleDTO> rulesDTO = ncAllowedAutoSortListDTO.getRules();
        if (Objects.isNull(rulesDTO) || rulesDTO.size() == 0) return null;
        List<NCAllowedAutoSortRule> ncAllowedAutoSortRules = new ArrayList<>();
        for (NCAllowedAutoSortRuleDTO item : rulesDTO) {
            NCAllowedAutoSortRule ncAllowedAutoSortRule = new NCAllowedAutoSortRule();
            BeanUtils.copyProperties(item, ncAllowedAutoSortRule);
            ncAllowedAutoSortRule.setRefId(ncAllowedAutoSortListDTO.getId());
            ncAllowedAutoSortRules.add(ncAllowedAutoSortRule);
        }
        return ncAllowedAutoSortRules;
    }

    private NCAllowedAutoSortList getListPo(NCAllowedAutoSortListDTO dto) {
        NCAllowedAutoSortList ncAllowedAutoSortList = new NCAllowedAutoSortList();
        BeanUtils.copyProperties(dto, ncAllowedAutoSortList);
        return ncAllowedAutoSortList;
    }

    private List<NCAllowedAutoSortRuleDTO> makeRulesDTO(List<NCAllowedAutoSortRule> rule) {
        List<NCAllowedAutoSortRuleDTO> dtos = new ArrayList<>();
        for (NCAllowedAutoSortRule item : rule) {
            NCAllowedAutoSortRuleDTO dto = new NCAllowedAutoSortRuleDTO();
            BeanUtils.copyProperties(item, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    private List<NCAllowedAutoSortListDTO> makeListDTO(List<NCAllowedAutoSortList> ncAllowedAutoSortLists) {
        List<NCAllowedAutoSortListDTO> list = new ArrayList<>();
        for (NCAllowedAutoSortList item : ncAllowedAutoSortLists) {
            NCAllowedAutoSortListDTO dto = new NCAllowedAutoSortListDTO();
            BeanUtils.copyProperties(item, dto);
            List<NCAllowedAutoSortRule> ncAllowedAutoSortRules = ncAllowedAutoSortListService.queryRuleByRefId(item.getId());
            List<NCAllowedAutoSortRuleDTO> ncAllowedAutoSortRuleDTOS = makeRulesDTO(ncAllowedAutoSortRules);
            dto.setRules(ncAllowedAutoSortRuleDTOS);
            list.add(dto);
        }
        return list;
    }


}
