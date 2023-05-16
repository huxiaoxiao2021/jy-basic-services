package com.jdl.basic.provider.core.provider.ncWhiteList;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.ncWhiteList.NCWhiteListQuery;
import com.jdl.basic.api.dto.ncWhiteList.NCWhiteListDTO;
import com.jdl.basic.api.dto.ncWhiteList.NCWhiteRuleDTO;
import com.jdl.basic.api.service.ncWhiteList.NCWhiteListJsfService;
import com.jdl.basic.common.utils.JsonHelper;
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
import java.util.Objects;

@Service("NCWhiteListJsfServiceImpl")
@Slf4j
public class NCWhiteListJsfServiceImpl implements NCWhiteListJsfService {

    @Autowired
    private NCWhiteListService ncWhiteListService;

    private static final Integer NOT_INCLUDE = 0;
    private static final Integer INCLUDE = 1;

    private static final String LENGTH = "length";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String VOLUME = "volume";

    private static final Double  DIFF = 1e-6;

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
        pageDto.setTotalRow(total);
        pageDto.setResult(makeWhiteListDTO(ncWhiteLists));
        return Result.success(pageDto);
    }


    @Override
    public Result<Integer> countByCondition(NCWhiteListQuery query) {
        return Result.success(ncWhiteListService.countByCondition(query));
    }

    /*
     *入参： 长 宽 高 体积 重量 物品描述 白名单json串
     *返回值： 0-不在白名单内 1-是nc 2-不是nc
     */
    @Override
    public Result<Integer> evaluate(String length, String width, String height, String volume, String weight,
                            String description) {
        NCWhiteListQuery query = new NCWhiteListQuery();
        Result<PageDto<NCWhiteListDTO>> pageDtoResult = queryByCondition(query);
        List<NCWhiteListDTO> ncWhiteListDTOS = pageDtoResult.getData().getResult();
        Result<Integer> result = new Result<>();
        for (NCWhiteListDTO ncWhiteList : ncWhiteListDTOS) {
            String[] keywords = ncWhiteList.getKeyword().split("、");
            // 包含关键字就开始匹配
            for (String keyword : keywords) {
                if (description.contains(keyword)) {
                    List<NCWhiteRuleDTO> rules = ncWhiteList.getRules();
                    if (Objects.equals(ncWhiteList.getRuleType(), NOT_INCLUDE)) {
                        //没有规则，直接排除
                        if (Objects.isNull(rules) || rules.size() == 0) {
                            return Result.success(-1);
                        }
                        boolean isMatch = match(length,width,height,volume,weight,rules);
                        //符合排除规则直接排除
                        if (isMatch) {
                            return Result.success(-1);
                        } else {
                            return Result.success(1);
                        }
                    } else if (Objects.equals(ncWhiteList.getRuleType(), INCLUDE)) {
                        if (Objects.isNull(rules) || rules.size() == 0) {
                            return Result.success(1);
                        }
                        boolean isMatch = match(length,width,height,volume,weight,rules);
                        //符合包含规则要算
                        if (isMatch) {
                            return Result.success(1);
                        } else {
                            return Result.success(-1);
                        }
                    }
                }
            }
        }
        //白名单没有匹配到的情况
        return Result.success(0);
    }

    private Boolean match(String length, String width, String height, String volume, String weight, List<NCWhiteRuleDTO> rules) {
        Boolean isMatch = false;
        Double args = 0.0;
        for (NCWhiteRuleDTO rule : rules) {
            if (Objects.equals(rule.getQuotaName(), LENGTH) && Objects.nonNull(length)) {
                args = Double.valueOf(length);
            } else if (Objects.equals(rule.getQuotaName(), WIDTH) && Objects.nonNull(width)) {
                args = Double.valueOf(width);
            } else if (Objects.equals(rule.getQuotaName(), HEIGHT) && Objects.nonNull(height)) {
                args = Double.valueOf(height);
            } else if (Objects.equals(rule.getQuotaName(), WEIGHT) && Objects.nonNull(weight)) {
                args = Double.valueOf(weight);
            } else if (Objects.equals(rule.getQuotaName(), VOLUME) && Objects.nonNull(volume)) {
                args = Double.valueOf(volume);
            } else {
                //空值直接返回false
                return false;
            }
            isMatch = matchValue(args, rule);
            if (!isMatch) {
                return false;
            }
        }
        return true;
    }

    private Boolean matchValue(Double args, NCWhiteRuleDTO rule) {
        // < arg <
        if (rule.getGt() == 1 && rule.getGte() == 0 && rule.getLt() == 1 && rule.getLte() == 0) {
            if (args > rule.getGtValue() && args < rule.getLtValue()) {
                return true;
            }
            // <= arg <=
        } else if (rule.getGt() == 1 && rule.getGte() == 1 && rule.getLt() == 1 && rule.getLte() == 1) {
            if (args >= rule.getGtValue() && args <= rule.getLtValue()) {
                return true;
            }
            // == arg ==
        } else if (rule.getGt() == 0 && rule.getGte() == 1) {
            if (Math.abs(args - rule.getLtValue()) < DIFF) {
                return true;
            }
            // < arg <=
        } else if (rule.getGt() == 1 && rule.getGte() == 0 && rule.getLt() == 1 && rule.getLte() == 1) {
            if (args > rule.getGtValue() && args <= rule.getLtValue()) {
                return true;
            }
            // <= arg <
        } else if (rule.getGt() == 1 && rule.getGte() == 1 && rule.getLt() == 1 && rule.getLte() == 0) {
            if (args >= rule.getGtValue() && args < rule.getLtValue()) {
                return true;
            }
        }
        return false;
    }

    private List<NCWhiteRule> getRulesPo(NCWhiteListDTO ncWhiteListDTO) {
        List<NCWhiteRuleDTO> rulesDTO = ncWhiteListDTO.getRules();
        if (Objects.isNull(rulesDTO) || rulesDTO.size() == 0) return null;
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
