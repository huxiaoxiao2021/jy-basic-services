package com.jdl.basic.provider.core.provider.boxLimit;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigQueryDto;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.api.service.BoxlimitConfigApi;

import com.jdl.basic.ommon.utils.PageDto;
import com.jdl.basic.provider.core.dao.boxLimit.BoxLimitConfigDao;
import com.jdl.basic.provider.core.service.boxLimit.BoxlimitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 16:35
 * @Description:
 */
@Slf4j
@Component
public class BoxlimitConfigApiImpl implements BoxlimitConfigApi {

    @Autowired
    private BoxlimitService boxlimitService;

    @Override
    public JDResponse insertBoxlimitConfig(@Valid BoxLimitConfigDto po) {
        JDResponse response = new JDResponse();
        try {
            boxlimitService.insertBoxlimitConfig(po);
            response.setCode(1);
            response.setMessage("成功");
            return response;
        } catch (Exception e) {
            log.error("保存配置异常{}", e.getMessage(), e);
            response.setCode(-1);
            response.setMessage(e.getMessage());
        }

        return response;
    }


    @Override
    public JDResponse<PageDto<BoxLimitConfigDto>> listData(BoxLimitConfigQueryDto dto) {
        log.info("获取集箱包裹上限配置列表信息入参-{}", JSONObject.toJSONString(dto));
        JDResponse<PageDto<BoxLimitConfigDto>> response = new JDResponse<>();
        response.setCode(JDResponse.CODE_FAIL);
        response.setMessage(JDResponse.MSG_FAIL);
        try {
            PageDto<BoxLimitConfigDto> result = boxlimitService.listData(dto);
            if (result != null) {
                response.setData(result);
                response.setCode(JDResponse.CODE_SUCCESS);
                response.setMessage(JDResponse.MSG_SUCCESS);
            }
        } catch (Exception e) {
            response.setCode(JDResponse.CODE_EXCEPTION);
            response.setMessage(JDResponse.MSG_EXCEPTION);
            log.error("获取集箱包裹上限配置列表信息异常! {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public JDResponse<String> getSiteNameById(Integer siteId) {
        return boxlimitService.querySiteNameById(siteId);
    }

    @Override
    public JDResponse saveOrUpdate(BoxLimitConfigDto dto, LoginUser loginUser) {
        log.info("新增或者修改集箱包裹限制-入参-{}", JSON.toJSONString(dto));
        if (dto.getId() == null) {
            return boxlimitService.create(dto, loginUser);
        } else {
            return boxlimitService.update(dto, loginUser);
        }
    }

    @Override
    public JDResponse delete(ArrayList<Long> ids, LoginUser loginUser) {
        return boxlimitService.delete(ids, loginUser.getUserErp());
    }

    @Override
    public JDResponse toImport(List<BoxLimitConfigDto> dataList, LoginUser loginUser) {
        try {
            return boxlimitService.importData(dataList, loginUser);
        } catch (Exception e) {
            this.log.error("导入异常!-{}", e.getMessage(), e);
            JDResponse response = new JDResponse();
            response.setCode(JDResponse.CODE_EXCEPTION);
            response.setMessage(JDResponse.MSG_EXCEPTION);
            return response;
        }
    }

    @Override
    public JDResponse<Integer> countByCondition(BoxLimitConfigQueryDto dto) {
        return boxlimitService.countByCondition(dto);
    }

    @Override
    public JDResponse<List<String>> getBoxTypeList() {
        return boxlimitService.getBoxTypeList();
    }

    @Override
    public JDResponse<Integer> getLimitNums(Integer createSiteCode, String type) {
        if(StringUtils.isBlank(type)){
            return new JDResponse<>();
        }
        return boxlimitService.getLimitNums(createSiteCode, type);
    }
}
