package com.jdl.basic.provider.core.service.boxLimit.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigQueryDto;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.common.enums.BoxTypeEnum;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.provider.core.dao.boxLimit.BoxLimitConfigDao;
import com.jdl.basic.provider.core.po.BoxLimitConfigPO;
import com.jdl.basic.provider.core.service.boxLimit.BoxlimitService;
import com.jdl.basic.rpc.Rpc.BaseMajorRpc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 11:40
 * @Description:
 */
@Slf4j
@Service
public class BoxlimitServiceImpl implements BoxlimitService {

    //场地建箱配置类型
    private static final Integer SITE_BOX_TYPE =2;

    @Autowired
    private BaseMajorRpc baseMajorRpc;


    @Autowired
    private BoxLimitConfigDao boxLimitConfigDao;

    @Override
    public void insertBoxlimitConfig(BoxLimitConfigDto po) {

        BoxLimitConfigPO dto=  new BoxLimitConfigPO();
        BeanUtils.copyProperties(po,dto);

        boxLimitConfigDao.insert(dto);
    }

    @Override
    public PageDto<BoxLimitConfigDto> listData(BoxLimitConfigQueryDto queryDto) {

        log.info("配置分页查询入参-{}", JSON.toJSONString(queryDto));
        PageDto<BoxLimitConfigDto> result = new PageDto<>();
        if(Objects.nonNull(queryDto) && StringUtils.isNotBlank(queryDto.getSiteName())){
            queryDto.setSiteName(queryDto.getSiteName().trim());
        }
        Integer count = boxLimitConfigDao.countByCondition(queryDto);
        result.setTotalRow(count);
        if (count == 0) {
            result.setResult(Collections.EMPTY_LIST);
            return result;
        }
        List<BoxLimitConfigPO> boxLimitConfigs = boxLimitConfigDao.queryByCondition(queryDto);
        List<BoxLimitConfigDto> list = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (BoxLimitConfigPO b : boxLimitConfigs) {
            BoxLimitConfigDto resultDto = new BoxLimitConfigDto();
            resultDto.setId(b.getId());
            resultDto.setSiteName(b.getSiteName());
            resultDto.setSiteId(b.getSiteId());
            resultDto.setLimitNum(b.getLimitNum());
            resultDto.setOperatorErp(b.getOperatorErp());
            resultDto.setOperatorSiteName(b.getOperatorSiteName());
            resultDto.setOperatingTime(format.format(b.getOperatingTime()));
            resultDto.setConfigType(b.getConfigType());
            resultDto.setBoxNumberType(b.getBoxNumberType());
            list.add(resultDto);
        }
        result.setResult(list);
        return result;
    }

    @Override
    public JDResponse importData(List<BoxLimitConfigDto> data, LoginUser operator) {
        JDResponse response = new JDResponse();
        Date now = new Date();
        String operatorErp = operator.getUserErp();
        Integer operatorSiteId = operator.getSiteCode();
        String operatorName = operator.getSiteName();
        List<BoxLimitConfigPO> addList = new ArrayList<>();
        //需要更新的数据id集合
        List<Long> updateList = new ArrayList<>();
        int failCount =0;
        for (BoxLimitConfigDto vo :data){
            BaseStaffSiteOrgDto siteOrgDto = baseMajorRpc.getBaseSiteBySiteId(vo.getSiteId());
            log.info("siteOrgDto -{}",JSON.toJSONString(siteOrgDto));
            if (siteOrgDto == null) {
                failCount++;
                continue;
            }
            if (!vo.getSiteName().equals(siteOrgDto.getSiteName())) {
                failCount++;
                continue;
            }
            BoxLimitConfigQueryDto queryDTO = new BoxLimitConfigQueryDto();
            queryDTO.setSiteId(vo.getSiteId());
            queryDTO.setSiteName(vo.getSiteName());
            queryDTO.setConfigType(SITE_BOX_TYPE);
            queryDTO.setBoxNumberType(vo.getBoxNumberType());
            //根据条件查询库中数据有无该记录 有则删除 并添加所有的导入上传数据
            List<BoxLimitConfigPO> result = boxLimitConfigDao.queryByCondition(queryDTO);
            if(!CollectionUtils.isEmpty(result)){
                BoxLimitConfigPO updateConfig = result.get(0);
                updateList.add(updateConfig.getId());
            }
            BoxLimitConfigPO b = new BoxLimitConfigPO();
            b.setSiteName(vo.getSiteName());
            b.setSiteId(vo.getSiteId());
            b.setLimitNum(vo.getLimitNum());
            b.setOperatorErp(operatorErp);
            b.setOperatorSiteId(operatorSiteId);
            b.setOperatorSiteName(operatorName);
            b.setOperatingTime(now);
            b.setCreateTime(now);
            b.setUpdateTime(now);
            b.setYn(true);
            b.setBoxNumberType(vo.getBoxNumberType());
            //场地建箱配置类型
            b.setConfigType(SITE_BOX_TYPE);
            addList.add(b);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            boxLimitConfigDao.batchDelete(updateList);
        }
        if(!CollectionUtils.isEmpty(addList)){
            List<List<BoxLimitConfigPO>> partition = Lists.partition(addList, 100);
            for (List<BoxLimitConfigPO> configs :partition){
                boxLimitConfigDao.batchInsert(configs);
            }
        }
        response.setData(failCount);
        return response;
    }

    @Override
    public JDResponse create(BoxLimitConfigDto dto, LoginUser operator) {

        JDResponse response = new JDResponse();
        checkDtoData(dto, response);
        log.info("建箱包裹数限制：create数据校验结果为:{}",JSON.toJSONString(response));
        if (!response.isSuccess()) {
            return response;
        }
        Date now = new Date();
        BoxLimitConfigPO boxLimitConfig = new BoxLimitConfigPO();
        boxLimitConfig.setSiteName(dto.getSiteName());
        boxLimitConfig.setSiteId(dto.getSiteId());
        boxLimitConfig.setLimitNum(dto.getLimitNum());
        boxLimitConfig.setOperatorErp(operator.getUserErp());
        boxLimitConfig.setOperatorSiteId(operator.getSiteCode());
        boxLimitConfig.setOperatorSiteName(operator.getSiteName());
        boxLimitConfig.setOperatingTime(now);
        boxLimitConfig.setCreateTime(now);
        boxLimitConfig.setUpdateTime(now);
        boxLimitConfig.setYn(true);
        boxLimitConfig.setConfigType(dto.getConfigType());
        boxLimitConfig.setBoxNumberType(dto.getBoxNumberType());

        boxLimitConfigDao.insert(boxLimitConfig);
        return response;
    }

    @Override
    public JDResponse update(BoxLimitConfigDto dto, LoginUser operator) {
        JDResponse response = new JDResponse();
        log.info("建箱包裹数限制：update数据校验结果为:{}", JSONObject.toJSONString(response));
        if (dto.getId() == null) {
            response.setCode(JDResponse.CODE_FAIL);
            response.setMessage("ID不能为空!");
            return response;
        }
        BoxLimitConfigPO boxLimitConfig = boxLimitConfigDao.selectByPrimaryKey(dto.getId());
        if (boxLimitConfig == null) {
            response.setCode(JDResponse.CODE_FAIL);
            response.setMessage("数据不存在!");
            return response;
        }
        if (dto.getLimitNum() == null || dto.getLimitNum() <= 0) {
            response.setCode(JDResponse.CODE_FAIL);
            response.setMessage("建箱包裹上限不正确!");
            return response;
        }
        Date now = new Date();
        boxLimitConfig.setUpdateTime(now);
        boxLimitConfig.setOperatingTime(now);
        boxLimitConfig.setOperatorErp(operator.getUserErp());
        boxLimitConfig.setOperatorSiteId(operator.getSiteCode());
        boxLimitConfig.setOperatorSiteName(operator.getSiteName());
        boxLimitConfig.setSiteId(dto.getSiteId());
        boxLimitConfig.setSiteName(dto.getSiteName());
        boxLimitConfig.setLimitNum(dto.getLimitNum());
        boxLimitConfig.setConfigType(dto.getConfigType());
        boxLimitConfig.setBoxNumberType(dto.getBoxNumberType());
        boxLimitConfigDao.updateByPrimaryKeySelective(boxLimitConfig);
        return response;
    }

    @Override
    public JDResponse delete(List<Long> ids, String operatorErp) {
        log.info("建箱包裹数限制 delete操作, 参数 ids={},操作人:{}", ids, operatorErp);
        boxLimitConfigDao.batchDelete(ids);
        return new JDResponse();
    }

    @Override
    public JDResponse<String> querySiteNameById(Integer siteId) {
        JDResponse<String> response = new JDResponse<>();
        BaseStaffSiteOrgDto siteOrgDto = baseMajorRpc.getBaseSiteBySiteId(siteId);
        if (siteOrgDto == null || siteOrgDto.getSiteName() == null) {
            response.setCode(JDResponse.CODE_FAIL);
            response.setMessage("站点不存在!");
        } else {
            response.setData(siteOrgDto.getSiteName());
            response.setCode(JDResponse.CODE_SUCCESS);
            response.setMessage("获取站点成功!");
        }
        return response;
    }



    @Override
    public JDResponse<Integer> countByCondition(BoxLimitConfigQueryDto dto) {
        if(Objects.nonNull(dto) && StringUtils.isNotBlank(dto.getSiteName())){
            dto.setSiteName(dto.getSiteName().trim());
        }
        JDResponse<Integer> response = new JDResponse<>();
        response.setCode(JDResponse.CODE_SUCCESS);
        response.setData(boxLimitConfigDao.countByCondition(dto));
        return response;
    }

    @Override
    public JDResponse<List<String>> getBoxTypeList() {
        JDResponse response= new JDResponse();
        List<String> boxTypes = new ArrayList<>();
        Map<String, String> map = BoxTypeEnum.getMap();
        for(String key : map.keySet()){
            boxTypes.add(key);
        }
        response.setData(boxTypes);
        response.setMessage(JDResponse.MSG_SUCCESS);
        response.setCode(JDResponse.CODE_SUCCESS);
        return response;
    }

    @Override
    @Cache(key = "BoxLimitServiceImpl.getLimitNums@args0@args1", memoryEnable = true, memoryExpiredTime = 2 * 60 * 1000
            ,redisEnable = true, redisExpiredTime = 2 * 60 * 1000)
    public JDResponse<Integer> getLimitNums(Integer createSiteCode, String type) {
        JDResponse<Integer> response = new JDResponse<>();
        response.setData(0);
        log.info("分拣数量限制拦截 createSiteCode:{}, type:{}", createSiteCode, type);
        BoxLimitConfigDto dto = new BoxLimitConfigDto();
        dto.setSiteId(createSiteCode);
        dto.setBoxNumberType(type);
        Integer limitNum =  boxLimitConfigDao.queryLimitNumBySiteId(dto);

        if (limitNum != null) {
            response.setData(limitNum);
            return response;
        }
        Integer commonLimitNum = boxLimitConfigDao.queryCommonLimitNum(type);
        log.info("分拣集包通用数量限制 箱号类型{}， 限制数量 {} ",type,commonLimitNum);
        if(commonLimitNum != null){
            response.setData(commonLimitNum);
            return response;
        }
        return response;
    }


    /**
     * 校验输入数据是否规范
     * @param dto
     * @param response
     */
    private void checkDtoData(BoxLimitConfigDto dto, JDResponse response) {
        //场地建箱配置需要判断机构id
        if (dto.getConfigType().equals(SITE_BOX_TYPE) && dto.getSiteId() == null) {
            response.setCode(JDResponse.CODE_FAIL);
            response.setMessage("机构ID为空!");
            return;
        }
        if (dto.getLimitNum() == null || dto.getLimitNum() <= 0) {
            response.setCode(JDResponse.CODE_FAIL);
            response.setMessage("建箱包裹上限不正确!");
            return;
        }
        if(org.springframework.util.StringUtils.isEmpty(dto.getBoxNumberType())){
            response.setCode(JDResponse.CODE_FAIL);
            response.setMessage("箱号类型为空!");
            return;
        }
        BoxLimitConfigQueryDto queryDTO = new BoxLimitConfigQueryDto();
        queryDTO.setSiteId(dto.getSiteId());
        queryDTO.setSiteName(dto.getSiteName());
        queryDTO.setConfigType(dto.getConfigType());
        queryDTO.setBoxNumberType(dto.getBoxNumberType());
        List<BoxLimitConfigPO> boxLimitConfigs = boxLimitConfigDao.queryByCondition(queryDTO);
        if (!CollectionUtils.isEmpty(boxLimitConfigs)) {
            if (dto.getId() == null || !dto.getId().equals(boxLimitConfigs.get(0).getId())) {
                if(dto.getConfigType().equals(SITE_BOX_TYPE)){
                    response.setCode(JDResponse.CODE_FAIL);
                    response.setMessage(String.format("ID为:%s 的机构配置箱号类型:%s 已经存在,请修改或者删除原配置", dto.getSiteId(),dto.getBoxNumberType()));
                    return;
                }
                response.setCode(JDResponse.CODE_FAIL);
                response.setMessage(String.format("配置箱号类型:%s 已经存在,不允许重复配置", dto.getBoxNumberType()));
                return;
            }
        }
        if(dto.getConfigType().equals(SITE_BOX_TYPE)){
            BaseStaffSiteOrgDto siteOrgDto = baseMajorRpc.getBaseSiteBySiteId(dto.getSiteId());
            if (siteOrgDto == null || org.springframework.util.StringUtils.isEmpty(siteOrgDto.getSiteName())) {
                response.setCode(JDResponse.CODE_FAIL);
                response.setMessage(String.format("ID为%s的机构不存在!", dto.getSiteId()));
                return;
            }
            dto.setSiteName(siteOrgDto.getSiteName());
        }
    }
}
