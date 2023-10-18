package com.jdl.basic.provider.core.provider.boxLimit;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.ump.annotation.JProEnum;
import com.jd.ump.annotation.JProfiler;
import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigQueryDto;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.api.service.boxLimit.BoxlimitConfigJsfService;

import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.contants.ResultCodeConstant;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.boxLimit.BoxlimitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 16:35
 * @Description:
 */
@Slf4j
@Component
public class BoxlimitConfigJsfServiceImpl implements BoxlimitConfigJsfService {

    @Autowired
    private BoxlimitService boxlimitService;

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".BoxlimitConfigJsfService.insertBoxlimitConfig", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result insertBoxlimitConfig(BoxLimitConfigDto po) {
        log.info("集箱包裹上限配置 insertBoxlimitConfig -{}", JSONObject.toJSONString(po));
        Result response = new Result();
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
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".BoxlimitConfigJsfService.listData", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<PageDto<BoxLimitConfigDto>> listData(BoxLimitConfigQueryDto dto) {
        log.info("获取集箱包裹上限配置列表信息入参-{}", JSONObject.toJSONString(dto));
        Result<PageDto<BoxLimitConfigDto>> response = new Result<>();
        response.toFail("获取集箱包裹上限配置列表信息失败！");
        try {
            PageDto<BoxLimitConfigDto> result = boxlimitService.listData(dto);
            if (result != null) {
                response.setData(result);
                response.toSuccess("获取集箱包裹上限配置列表信息成功！");

            }
        } catch (Exception e) {
            log.error("获取集箱包裹上限配置列表信息异常! {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".BoxlimitConfigJsfService.getSiteNameById", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<String> getSiteNameById(Integer siteId) {
        log.info("集箱包裹上限配置 getSiteNameById -{}", JSONObject.toJSONString(siteId));
        return boxlimitService.querySiteNameById(siteId);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".BoxlimitConfigJsfService.saveOrUpdate", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result saveOrUpdate(BoxLimitConfigDto dto, LoginUser loginUser) {
        log.info("新增或者修改集箱包裹限制-入参-{}", JSON.toJSONString(dto));
        if (dto.getId() == null) {
            return boxlimitService.create(dto, loginUser);
        } else {
            return boxlimitService.update(dto, loginUser);
        }
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".BoxlimitConfigJsfService.delete", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result delete(ArrayList<Long> ids, LoginUser loginUser) {
        log.info("集箱包裹上限配置 delete -{}-{}", JSONObject.toJSONString(ids),JSONObject.toJSONString(loginUser));
        return boxlimitService.delete(ids, loginUser.getUserErp());
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".BoxlimitConfigJsfService.toImport", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result toImport(List<BoxLimitConfigDto> dataList, LoginUser loginUser) {
        try {
            return boxlimitService.importData(dataList, loginUser);
        } catch (Exception e) {
            this.log.error("导入异常!-{}", e.getMessage(), e);
            Result response = new Result();
            response.toFail("导入异常!");
            return response;
        }
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".BoxlimitConfigJsfService.countByCondition", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Integer> countByCondition(BoxLimitConfigQueryDto dto) {
        log.info("集箱包裹上限配置 countByCondition -{}", JSONObject.toJSONString(dto));
        return boxlimitService.countByCondition(dto);
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".BoxlimitConfigJsfService.getBoxTypeList", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<List<String>> getBoxTypeList() {
        log.info("集箱包裹上限配置 getBoxTypeList");
        return boxlimitService.getBoxTypeList();
    }

    @Override
    @JProfiler(jKey = Constants.UMP_APP_NAME + ".BoxlimitConfigJsfService.getLimitNums", jAppName=Constants.UMP_APP_NAME, mState={JProEnum.TP,JProEnum.FunctionError})
    public Result<Integer> getLimitNums(Integer createSiteCode, String type) {
        if(log.isInfoEnabled()){
            log.info("集箱包裹上限配置 getLimitNums -{} -{}",createSiteCode,type);
        }
        if(StringUtils.isBlank(type)){
            return new Result<>();
        }
        return boxlimitService.getLimitNums(createSiteCode, type);
    }
}
