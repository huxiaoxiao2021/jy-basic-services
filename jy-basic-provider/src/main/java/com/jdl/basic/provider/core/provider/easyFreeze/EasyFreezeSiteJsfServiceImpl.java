package com.jdl.basic.provider.core.provider.easyFreeze;

import com.alibaba.fastjson.JSON;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteDto;
import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteQueryDto;
import com.jdl.basic.api.service.easyFreeze.EasyFreezeSiteJsfService;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.easyFreezeSite.EasyFreezeSiteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/11/9 15:51
 * @Description:
 */
@Slf4j
@Service
public class EasyFreezeSiteJsfServiceImpl implements EasyFreezeSiteJsfService {

    @Autowired
    private EasyFreezeSiteService easyFreezeSiteService;

    @Autowired
    private BaseMajorManager baseMajorManager;

    @Override
    public Result<Boolean> insert(EasyFreezeSiteDto dto, LoginUser loginUser) {
        Result<Boolean> result = new Result<>();
        try {
            if (Objects.isNull(dto)
                    || Objects.isNull(dto.getRemindStartTime())
                    || Objects.isNull(dto.getRemindEndTime())
                    || Objects.isNull(dto.getSiteCode())
                    || Objects.isNull(dto.getUseState())) {
                result.toFail("入参不能为空!");
                return result;
            }
            BaseStaffSiteOrgDto basicDto = baseMajorManager.getBaseSiteBySiteId(dto.getSiteCode());
            log.info("{}--基础资料basicDto--{}",dto.getSiteCode(),basicDto);
            if(basicDto == null){
                result.toFail("此站点在基础资料未查到!");
                return result;
            }
            if(!(basicDto.getOrgId().equals(dto.getOrgCode()))){
                result.toFail("此站点实际所属的大区与实际不一致，请重新选择!");
                return result;
            }
            // init basic info
            fillBasicInfo(dto, basicDto);
            
            return easyFreezeSiteService.insert(dto, loginUser);
        } catch (Exception e) {
            log.error("添加易冻品场地配置异常-{}", e.getMessage(), e);
            result.toError("添加易冻品场地配置异常!");
        }
        return result;
    }

    private void fillBasicInfo(EasyFreezeSiteDto dto, BaseStaffSiteOrgDto basicDto) {
        dto.setCityName(basicDto.getCityName());
        if(basicDto.getSubType() != null){
            dto.setSiteType(basicDto.getSubType().equals(Constants.B2B_SITE_TYPE)? "转运":"分拣");;
        }else {
            dto.setSiteType("分拣");
        }
        dto.setProvinceAgencyCode(basicDto.getProvinceAgencyCode());
        dto.setProvinceAgencyName(basicDto.getProvinceAgencyName());
        dto.setAreaHubCode(basicDto.getAreaCode());
        dto.setAreaHubName(basicDto.getAreaName());
    }

    @Override
    public Result<EasyFreezeSiteDto> selectByPrimaryKey(Long id) {
        Result<EasyFreezeSiteDto> result = new Result<>();
        if (Objects.isNull(id)) {
            result.toFail("id不能为空!");
            return result;
        }
        return easyFreezeSiteService.selectByPrimaryKey(id);
    }

    @Override
    public Result<Boolean> updateByPrimaryKeySelective(EasyFreezeSiteDto dto, LoginUser loginUser) {
        Result<Boolean> result = new Result<>();
        try {
            if (Objects.isNull(dto)
                    || Objects.isNull(dto.getId())
                    || Objects.isNull(dto.getRemindStartTime())
                    || Objects.isNull(dto.getRemindEndTime())
                    || Objects.isNull(dto.getSiteCode())) {
                result.toFail("入参不能为空!");
                return result;
            }
            BaseStaffSiteOrgDto basicDto = baseMajorManager.getBaseSiteBySiteId(dto.getSiteCode());
            if(basicDto == null){
                result.toFail("此站点在基础资料未查到!");
                return result;
            }
            if(!(basicDto.getOrgId().equals(dto.getOrgCode()))){
                result.toFail("此站点实际所属的大区与实际不一致，请重新选择!");
                return result;
            }
            // init basic info
            fillBasicInfo(dto, basicDto);
            return easyFreezeSiteService.updateByPrimaryKeySelective(dto, loginUser);
        } catch (Exception e) {
            log.error("更新易冻品场地配置异常-{}", e.getMessage(), e);
            result.toError("更新易冻品场地配置异常!");
        }
        return result;
    }

    @Override
    public Result<PageDto<EasyFreezeSiteDto>> getEasyFreezeSiteListBypage(EasyFreezeSiteQueryDto queryDto) {
        log.info("getEasyFreezeSiteListBypage 获取分页查询信息 入参-{}", JSON.toJSONString(queryDto));
        Result<PageDto<EasyFreezeSiteDto>> result = new Result<>();
        result.toSuccess("获取分页数据成功!");
        try {
            if (queryDto != null) {
                if (StringUtils.isNotBlank(queryDto.getSiteName())) {
                    queryDto.setSiteName(queryDto.getSiteName().trim());
                }
                if (StringUtils.isNotBlank(queryDto.getCityName())) {
                    queryDto.setCityName(queryDto.getCityName().trim());
                }
            }
            PageDto<EasyFreezeSiteDto> listBypage = easyFreezeSiteService.getEasyFreezeSiteListBypage(queryDto);
            result.setData(listBypage);
        } catch (Exception e) {
            log.error("getEasyFreezeSiteListBypage-获取分页数据异常-{}", e.getMessage(), e);
            result.toError("获取分页数据异常!");
        }
        return result;
    }

    @Override
    public Result<Boolean> importEasyFreezeSiteList(List<EasyFreezeSiteDto> dtoList, LoginUser loginUser) {
        Result<Boolean> result = new Result<>();
        try {
            if (CollectionUtils.isEmpty(dtoList)) {
                result.toFail("导入数据为空!");
                return result;
            }
            for (int i = 0; i < dtoList.size(); i++) {
                if( Objects.isNull(dtoList.get(i).getSiteCode())
                    || Objects.isNull(dtoList.get(i).getRemindEndTime())
                    || Objects.isNull(dtoList.get(i).getRemindStartTime())
                    || Objects.isNull(dtoList.get(i).getUseState())){
                    result.toFail("导入数据值不能为空! 请检查后重新导入!");
                    return result;
                }
                BaseStaffSiteOrgDto basicDto = baseMajorManager.getBaseSiteBySiteId(dtoList.get(i).getSiteCode());
                if(basicDto == null){
                    result.toFail("无此站点{ "+dtoList.get(i).getSiteCode()+" }信息! 请检查后重新导入!");
                    return result;
                }

            }
            return easyFreezeSiteService.importEasyFreezeSiteList(dtoList,loginUser);
        } catch (Exception e) {
            log.error("数据导入异常! -{}",e.getMessage(),e);
            result.toFail("数据导入异常!");
        }
        return result;
    }

    @Override
    public Result<EasyFreezeSiteDto> selectOneBysiteCode(Integer siteCode) {
        Result<EasyFreezeSiteDto> result = new Result<>();
        result.toSuccess("成功");
        try{
            if(Objects.isNull(siteCode)){
                result.toFail("站点编码不能为空!");
                return result;
            }
            return easyFreezeSiteService.selectOneBysiteCode(siteCode);
        } catch (Exception e) {
            log.error("获取单个易冻品站点配置异常! -{}",e.getMessage(),e);
            result.toSuccess("获取单个易冻品站点配置异常！");
        }
        return result;
    }

}
