package com.jdl.basic.provider.core.service.site;

import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jd.ql.basic.dto.BaseStoreInfoDto;
import com.jd.ql.basic.dto.PageDto;
import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.basic.BasicSiteEsDao;
import com.jdl.basic.provider.core.enums.SiteOperateStateEnum;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.po.BasicSiteEsDto;
import com.jdl.basic.provider.dto.BasicPsStoreInfo;
import com.jdl.basic.provider.dto.BasicSiteChangeMQ;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("siteService")
public class SiteServiceImpl implements SiteService {

    private static final Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private BasicSiteEsDao basicSiteEsDao;

    @Autowired
    private BaseMajorManager baseMajorManager;

    @Override
    public void syncBasicSite() {
        for (int index = 1; ; index++) {
            PageDto<List<Integer>> pageDto = baseMajorManager.getBaseSiteCodeNoYnByPage(null, index);
            if(pageDto == null || CollectionUtils.isEmpty(pageDto.getData())){
                logger.warn("分页获取站点id失败!");
                break;
            }
            logger.info("开始获取第{}页数据!", index);
            for (Integer siteId : pageDto.getData()) {
                logger.info("get siteId:[{}]", siteId);
                BaseStaffSiteOrgDto baseStaffSite = baseMajorManager.getBaseSiteBySiteId(siteId);
                if (baseStaffSite != null) {
                    BasicSiteEsDto basicSiteEsDto = new BasicSiteEsDto();
                    BeanUtils.copyProperties(baseStaffSite, basicSiteEsDto);
                    // 数据落库
                    insertStreamLinedBasicSiteEs(basicSiteEsDto);
                }
            }
            if (pageDto.getTotalPage() < (index + Constants.CONSTANT_NUMBER_ONE)) {
                break;
            }
        }
    }

    private void insertStreamLinedBasicSiteEs(BasicSiteEsDto basicSiteEsDto) {
        String docId = basicSiteEsDao.getEsDocIdBySIteCode(basicSiteEsDto.getSiteCode());
        if (StringUtils.isNotEmpty(docId)) {
            // 存在则更新
            basicSiteEsDao.updateById(basicSiteEsDto, docId);
        } else {
            // 不存在则插入
            basicSiteEsDao.insert(String.valueOf(basicSiteEsDto.getSiteCode()), basicSiteEsDto);
        }
    }

    @Override
    public void syncAllWmsNew() {
        for (int index = 1; ; index++) {
            PageDto<List<BaseStoreInfoDto>> pageDto = baseMajorManager.getBaseStoreInfoByPage(index);
            if(pageDto == null || CollectionUtils.isEmpty(pageDto.getData())){
                logger.warn("分页获取仓信息失败!");
                break;
            }
            logger.info("开始处理第{}页数据!", index);
            if(CollectionUtils.isNotEmpty(pageDto.getData())){
                for (BaseStoreInfoDto baseStoreInfoDto : pageDto.getData()){
                    BasicSiteEsDto basicSiteEsDto = convertToBasicDTO(baseStoreInfoDto);
                    // 数据落库
                    insertStreamLinedBasicSiteEs(basicSiteEsDto);
                }
            }
            if (pageDto.getTotalPage() < (index + Constants.CONSTANT_NUMBER_ONE)) {
                break;
            }
        }
    }

    /**
     * 仓数据转换为es对象
     * @param item
     * @return
     */
    private BasicSiteEsDto convertToBasicDTO(BaseStoreInfoDto item) {
        BasicSiteEsDto siteEsDto = new BasicSiteEsDto();
        siteEsDto.setSiteCode(item.getStoreSiteCode());
        siteEsDto.setDmsSiteCode(item.getDmsSiteCode());
        siteEsDto.setSiteName(item.getDmsStoreName());
        siteEsDto.setSiteNamePym(item.getSiteNamePym());
        siteEsDto.setParentSiteCode(item.getParentId());
        siteEsDto.setOrgId(item.getParentId());
        siteEsDto.setOrgName(item.getOrgName());
        siteEsDto.setSiteType(item.getDmsType());
        siteEsDto.setProvinceAgencyCode(item.getProvinceAgencyCode());
        siteEsDto.setProvinceAgencyName(item.getProvinceAgencyName());
        siteEsDto.setProvinceId(item.getProvinceId());
        siteEsDto.setCityId(item.getCityId());
        siteEsDto.setCountryId(item.getCountyId());
        siteEsDto.setCountrySideId(item.getTownId());
        siteEsDto.setAddress(item.getAddress());
        siteEsDto.setSiteContact(item.getConnector());
        siteEsDto.setYn(item.getYn() == null ? Constants.CONSTANT_NUMBER_ONE : item.getYn());
        siteEsDto.setOperateState(SiteOperateStateEnum.SITE_OPERATE_STATE_NOT_EXIST.getCode());
        return siteEsDto;
    }

    /**
     * 更新仓信息
     * @param storeInfo
     */
    @Override
    public void updateWmsDto(BasicPsStoreInfo storeInfo) {

        if(storeInfo == null){
            logger.warn("仓无效!");
            return;
        }
        if(storeInfo.getStoreID() == null
                || storeInfo.getStoreID() == Constants.CONSTANT_NUMBER_ZERO){
            logger.warn("站点id无值,站点名称：{}",storeInfo.getStoreID());
            return;
        }
        BasicSiteEsDto basicSiteEsDto = initBasicWmsEsDto(storeInfo);
        // 数据落库
        insertStreamLinedBasicSiteEs(basicSiteEsDto);
    }

    /**
     * 初始化仓对象
     * @param storeInfo
     * @return
     */
    private BasicSiteEsDto initBasicWmsEsDto(BasicPsStoreInfo storeInfo) {
        BasicSiteEsDto siteEsDto = new BasicSiteEsDto();
        siteEsDto.setSiteCode(storeInfo.getDmsSiteId());
        siteEsDto.setDmsSiteCode(storeInfo.getDmsCode());
        siteEsDto.setSiteName(storeInfo.getDmsStoreName());
        siteEsDto.setSiteNamePym(storeInfo.getSiteNamePym());
        siteEsDto.setOrgId(storeInfo.getOrgID());
        siteEsDto.setProvinceId(storeInfo.getProvinceId());
        siteEsDto.setProvinceAgencyCode(storeInfo.getProvinceAgencyCode());
        siteEsDto.setProvinceAgencyName(storeInfo.getProvinceAgencyName());
        siteEsDto.setCityId(storeInfo.getCityId());
        siteEsDto.setCountryId(storeInfo.getCountyId());
        siteEsDto.setCountrySideId(storeInfo.getTownId());
        siteEsDto.setAddress(storeInfo.getAddress());
        siteEsDto.setSiteContact(storeInfo.getConnector());
        siteEsDto.setYn(storeInfo.getYn() == null ? Constants.CONSTANT_NUMBER_ONE : storeInfo.getYn());
        siteEsDto.setOperateState(SiteOperateStateEnum.SITE_OPERATE_STATE_NOT_EXIST.getCode());
        return siteEsDto;
    }

    /**
     * 更新站点信息
     * @param basicSiteChangeMq
     */
    @Override
    public void updateBasicSite(BasicSiteChangeMQ basicSiteChangeMq) {
        if(basicSiteChangeMq == null){
            logger.warn("站点无效!");
            return;
        }
        if(basicSiteChangeMq.getSiteCode() == null
                || basicSiteChangeMq.getSiteCode() == Constants.CONSTANT_NUMBER_ZERO){
            logger.warn("站点id无值,站点名称：{}",basicSiteChangeMq.getSiteName());
            return;
        }
        // 初始化站点对象
        BasicSiteEsDto basicSiteEsDto = initBasicSiteEsDto(basicSiteChangeMq);

        String docId = basicSiteEsDao.getEsDocIdBySIteCode(basicSiteChangeMq.getSiteCode());
        if (StringUtils.isNotEmpty(docId)) {
            // 存在则更新
            basicSiteEsDao.updateById(basicSiteEsDto, docId);
        } else {
            // 不存在则插入
            BaseStaffSiteOrgDto baseStaffSite = baseMajorManager.getBaseSiteBySiteId(basicSiteChangeMq.getSiteCode());
            if(baseStaffSite != null){
                BeanUtils.copyProperties(baseStaffSite, basicSiteEsDto);
            }
            basicSiteEsDao.insert(String.valueOf(basicSiteEsDto.getSiteCode()), basicSiteEsDto);
        }
    }

    /**
     * 初始化es站点对象
     * @param basicSiteChangeMq
     * @return
     */
    private BasicSiteEsDto initBasicSiteEsDto(BasicSiteChangeMQ basicSiteChangeMq) {
        BasicSiteEsDto basicSiteEsDto = new BasicSiteEsDto();
        BeanUtils.copyProperties(basicSiteChangeMq, basicSiteEsDto);
        basicSiteEsDto.setDmsSiteCode(basicSiteChangeMq.getDmsCode());
        basicSiteEsDto.setCountryId(basicSiteChangeMq.getCountyId());
        basicSiteEsDto.setSiteContact(basicSiteChangeMq.getContact());
        basicSiteEsDto.setSiteMail(basicSiteChangeMq.getEmail());
        return basicSiteEsDto;
    }
}
