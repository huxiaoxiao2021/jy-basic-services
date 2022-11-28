package com.jdl.basic.provider.core.service.easyFreezeSite.impl;

import com.alibaba.fastjson.JSON;
import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteDto;
import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteQueryDto;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.common.enums.AreaEnum;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.easyFreezeSite.EasyFreezeSiteDao;
import com.jdl.basic.provider.core.po.EasyFreezeSitePO;
import com.jdl.basic.provider.core.service.easyFreezeSite.EasyFreezeSiteService;
import com.jdl.basic.rpc.Rpc.BaseMajorRpc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/11/9 15:04
 * @Description:
 */
@Slf4j
@Service
public class EasyFreezeSiteServiceImpl implements EasyFreezeSiteService {

    @Autowired
    private BaseMajorRpc baseMajorRpc;

    @Autowired
    private EasyFreezeSiteDao easyFreezeSiteDao;

    @Override
    public Result<Boolean> insert(EasyFreezeSiteDto dto, LoginUser loginUser) {
        Result<Boolean> result = new Result<>();
        result.toSuccess("添加成功!");
        EasyFreezeSitePO po = easyFreezeSiteDao.selectOneBysiteCode(dto.getSiteCode());
        if(Objects.nonNull(po)){
            result.toFail("该场地已存在，请勿重复录入!");
            return result;
        }
        EasyFreezeSitePO sitePO = new EasyFreezeSitePO();
        BeanUtils.copyProperties(dto,sitePO);
        sitePO.setCreateTime(new Date());
        sitePO.setUpdateTime(new Date());
        sitePO.setCreateUser(loginUser.getUserErp());
        sitePO.setUpdateUser(loginUser.getUserErp());
        log.info("易冻品场地添加-{}",JSON.toJSONString(sitePO));
        easyFreezeSiteDao.insert(sitePO);
        return result;
    }

    @Override
    public Result<EasyFreezeSiteDto> selectByPrimaryKey(Long id) {
        Result<EasyFreezeSiteDto>  result = new Result<>();
        result.toSuccess("获取单个数据成功!");
        EasyFreezeSitePO sitePO = easyFreezeSiteDao.selectByPrimaryKey(id);
        EasyFreezeSiteDto dto = new EasyFreezeSiteDto();
        BeanUtils.copyProperties(sitePO,dto);
        result.setData(dto);
        return result;
    }

    @Override
    public Result<Boolean> updateByPrimaryKeySelective(EasyFreezeSiteDto dto, LoginUser loginUser) {
        Result<Boolean> result = new Result<>();
        result.toSuccess("更新成功!");
        EasyFreezeSitePO po = new EasyFreezeSitePO();
        BeanUtils.copyProperties(dto,po);
        po.setUpdateTime(new Date());
        po.setUpdateUser(loginUser.getUserErp());
        easyFreezeSiteDao.updateByPrimaryKeySelective(po);
        return result;
    }

    @Override
    public PageDto<EasyFreezeSiteDto> getEasyFreezeSiteListBypage(EasyFreezeSiteQueryDto dto) {
        PageDto<EasyFreezeSiteDto> result = new PageDto<>();
        dto.setOffset();
        int count = easyFreezeSiteDao.countByCondition(dto);
        result.setTotalRow(count);
        if(count == 0){
            result.setResult(Collections.EMPTY_LIST);
            return result;
        }
        List<EasyFreezeSitePO> pos = easyFreezeSiteDao.selectByCondition(dto);
        List<EasyFreezeSiteDto> dtos = new ArrayList<>();
        pos.stream().forEach(item ->{
            EasyFreezeSiteDto siteDto = new EasyFreezeSiteDto();
            BeanUtils.copyProperties(item,siteDto);
            dtos.add(siteDto);
        });
        result.setResult(dtos);
        return result;
    }

    @Override
    @Transactional
    public Result<Boolean> importEasyFreezeSiteList(List<EasyFreezeSiteDto> dtoList, LoginUser loginUser) {
        log.info("importEasyFreezeSiteList-数据导入入参-{}", JSON.toJSONString(dtoList));
        Result<Boolean> result = new Result<>();
        result.toSuccess("导入成功!");
        fillSiteDate(dtoList,loginUser);

        List<EasyFreezeSitePO> addList = new ArrayList<>();
        List<EasyFreezeSitePO> updateList = new ArrayList<>();

        //遍历所有数据 判断是更新还是新增
        dtoList.stream().forEach(item->{
            EasyFreezeSitePO sitePO = easyFreezeSiteDao.selectOneBysiteCode(item.getSiteCode());
            EasyFreezeSitePO po = new EasyFreezeSitePO();
            BeanUtils.copyProperties(item,po);

            if(Objects.nonNull(sitePO)){
                updateList.add(po);
            }else {
                addList.add(po);
            }
        });
        log.info("新增数据-{}，更新数据-{}",JSON.toJSONString(addList),JSON.toJSONString(updateList));
        // 分别对数据进行更新或者插入
        if(CollectionUtils.isNotEmpty(updateList)){
            easyFreezeSiteDao.batchUpdateBySiteCode(updateList);
        }
        if(CollectionUtils.isNotEmpty(addList)){
            easyFreezeSiteDao.batchInsert(addList);
        }
        return result;
    }

    @Override
    public Result<EasyFreezeSiteDto> selectOneBysiteCode(Integer siteCode) {
        log.info("selectOneBysiteCode-获取单个配置信息 入参-{}", siteCode);
        Result<EasyFreezeSiteDto> result = new Result<>();
        result.toSuccess("成功");
        EasyFreezeSitePO po = easyFreezeSiteDao.selectOneConfigBysiteCode(siteCode, null);
        EasyFreezeSiteDto dto = new EasyFreezeSiteDto();
        if(Objects.nonNull(po)){
            BeanUtils.copyProperties(po,dto);
            result.setData(dto);
        }
        return result;
    }

    /**
     * 补充数据字段值
     * @param dtoList
     * @param loginUser
     */
    private void fillSiteDate(List<EasyFreezeSiteDto> dtoList, LoginUser loginUser){
        dtoList.stream().forEach(dto -> {
            BaseStaffSiteOrgDto baseSite = baseMajorRpc.getBaseSiteBySiteId(dto.getSiteCode());
            if(baseSite != null){
                dto.setSiteName(baseSite.getSiteName());
                dto.setSiteType(baseSite.getSubType().equals(Constants.B2B_SITE_TYPE)? "转运":"分拣");
                dto.setCityName(baseSite.getCityName());
                dto.setOrgCode(baseSite.getOrgId());
                Arrays.asList(AreaEnum.values()).forEach(item -> {
                   if(dto.getOrgCode().equals(item.getCode())){
                       dto.setOrgName(item.getName());
                   }
                });
                dto.setCreateTime(new Date());
                dto.setUpdateTime(new Date());
                dto.setCreateUser(loginUser.getUserErp());
                dto.setUpdateUser(loginUser.getUserErp());
            }
        });

    }

}
