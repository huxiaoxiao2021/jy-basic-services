package com.jdl.basic.provider.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.jd.jmq.common.message.Message;
import com.jd.joyqueue.client.springboot2.annotation.JmqListener;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.canal.CanalColumn;
import com.jdl.basic.provider.canal.CanalRow;
import com.jdl.basic.provider.canal.CanalRowEnhanced;
import com.jdl.basic.provider.canal.DbOperation;
import com.jdl.basic.provider.core.service.site.SiteService;
import com.jdl.basic.provider.dto.BasicPsStoreInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 仓变更消息
 *
 * @author hujiping
 * @date 2023/5/30 2:32 PM
 */
@Component
@Slf4j
public class WmsSiteChangeConsumer {

    @Autowired
    private SiteService siteService;

    @JmqListener(id= "basic-consumer", topics = {"${mq.consumer.topic.wmsSite_Change}"})
    public void onMessage(List<Message> messages) {
        for (Message message : messages) {

            String content = message.getText();
            log.info("consumer wmsSite_Change topic: " + message.getTopic() + " , body: " + content);
            // 更新仓站点数据
            siteService.updateWmsDto(genBasicPsStoreInfoByCanalText(content));
        }
    }

    /**
     * mq转换为仓实体
     * @param content
     * @return
     */
    private BasicPsStoreInfo genBasicPsStoreInfoByCanalText(String content) {
        BasicPsStoreInfo dto = new BasicPsStoreInfo();
        try {
            //把消息体包装成CanalRow
            CanalRow canalRow = wrapMessageText2CanalRow(content);
            List<CanalColumn> canalColumnList = canalRow.getColumnList();
            if (canalColumnList != null && canalColumnList.size() > 0) {
                dto = getDtoByCanalColumn(canalColumnList);
            }
        } catch (Exception e) {
            log.error("转换仓变更消息异常", e);
        }
        return dto;
    }

    /**
     * 把jmq的消息体包装成canalrow
     *
     * @param jsonText
     * @return
     */
    private CanalRow wrapMessageText2CanalRow(String jsonText) {
        CanalRowEnhanced canalRow = JSONObject.parseObject(jsonText, CanalRowEnhanced.class);
        switch (DbOperation.parse(canalRow.getEventType())) {
            case INSERT:
            case UPDATE:
                canalRow.setColumnList(canalRow.getAfterChangeOfColumns());
                break;
            case DELETE:
                canalRow.setColumnList(canalRow.getBeforeChangeOfColumns());
                break;
            default:
        }

        return canalRow;
    }

    /**
     * 把canalColumn的消息体包装成dto
     *
     * @param canalColumnList
     * @return
     */
    public BasicPsStoreInfo getDtoByCanalColumn(List<CanalColumn> canalColumnList){
        BasicPsStoreInfo dto = new BasicPsStoreInfo();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (CanalColumn canalColumn : canalColumnList) {
            if(BasicPsStoreInfo.ID.equalsIgnoreCase(canalColumn.getName())){
                if(canalColumn.getValue()!=null){
                    dto.setId(Long.parseLong(canalColumn.getValue()));
                }
            }
            if(BasicPsStoreInfo.DMS_STORE_ID.equalsIgnoreCase(canalColumn.getName())){
                dto.setDmsStoreId(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.DMS_STORE_NAME.equalsIgnoreCase(canalColumn.getName())){
                dto.setDmsStoreName(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.STORE_ID.equalsIgnoreCase(canalColumn.getName())){
                if(canalColumn.getValue()!=null){
                    dto.setStoreID(Integer.parseInt(canalColumn.getValue()));
                }
            }
            if(BasicPsStoreInfo.LOC_NO.equalsIgnoreCase(canalColumn.getName())){
                if(canalColumn.getValue()!=null){
                    dto.setLocNo(Integer.parseInt(canalColumn.getValue()));
                }
            }
            if(BasicPsStoreInfo.ORG_ID.equalsIgnoreCase(canalColumn.getName())){
                if(canalColumn.getValue()!=null){
                    dto.setOrgID(Integer.parseInt(canalColumn.getValue()));
                }
            }
            if(BasicPsStoreInfo.REMARK.equalsIgnoreCase(canalColumn.getName())){
                dto.setRemark(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.DMS_STORE_TYPE.equalsIgnoreCase(canalColumn.getName())){
                if(canalColumn.getValue()!=null){
                    dto.setDmsStoreType(canalColumn.getValue());
                }
            }

            if(BasicPsStoreInfo.DMS_SITE_ID.equalsIgnoreCase(canalColumn.getName())){
                if(canalColumn.getValue()!=null){
                    dto.setDmsSiteId(Integer.parseInt(canalColumn.getValue()));
                }
            }
            if(BasicPsStoreInfo.CREATE_DATE.equalsIgnoreCase(canalColumn.getName())){
                String create_time = canalColumn.getValue();
                if(StringUtils.isNotBlank(create_time)){
                    try {
                        dto.setCreateDate(formatter.parse(create_time));
                    } catch (ParseException e) {
                        log.error("把canalColumn的消息体转换dto的create_time Exception:", e);
                    }
                }
            }
            if(BasicPsStoreInfo.UPDATE_TIME.equalsIgnoreCase(canalColumn.getName())){
                String update_time = canalColumn.getValue();
                if(StringUtils.isNotBlank(update_time)){
                    try {
                        dto.setUpdateTime(formatter.parse(update_time));
                    } catch (ParseException e) {
                        log.error("把canalColumn的消息体转换dto的update_time Exception:", e);
                    }
                }
            }

            if(BasicPsStoreInfo.DMS_CODE.equalsIgnoreCase(canalColumn.getName())){
                dto.setDmsCode(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.CREATE_OPERATOR_NAME.equalsIgnoreCase(canalColumn.getName())){
                dto.setCreateOperatorName(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.UPDATE_OPERATOR_NAME.equalsIgnoreCase(canalColumn.getName())){
                dto.setUpdateOperatorName(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.PROVINCE_AGENCY_CODE.equalsIgnoreCase(canalColumn.getName())){
                dto.setProvinceAgencyCode(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.PROVINCE_AGENCY_NAME.equalsIgnoreCase(canalColumn.getName())){
                dto.setProvinceAgencyName(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.PROVINCE_ID.equalsIgnoreCase(canalColumn.getName())){
                if(canalColumn.getValue()!=null && !"".equals(canalColumn.getValue())){
                    dto.setProvinceId(Integer.parseInt(canalColumn.getValue()));
                }
            }
            if(BasicPsStoreInfo.CITY_ID.equalsIgnoreCase(canalColumn.getName())){
                if(canalColumn.getValue()!=null && !"".equals(canalColumn.getValue())){
                    dto.setCityId(Integer.parseInt(canalColumn.getValue()));
                }
            }
            if(BasicPsStoreInfo.SITE_NAME_PYM.equalsIgnoreCase(canalColumn.getName())){
                dto.setSiteNamePym(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.COUNTY_ID.equalsIgnoreCase(canalColumn.getName())){
                if(canalColumn.getValue()!=null && !"".equals(canalColumn.getValue())){
                    dto.setCountyId(Integer.parseInt(canalColumn.getValue()));
                }
            }
            if(BasicPsStoreInfo.TOWN_ID.equalsIgnoreCase(canalColumn.getName())){
                if(canalColumn.getValue()!=null && !"".equals(canalColumn.getValue())){
                    dto.setTownId(Integer.parseInt(canalColumn.getValue()));
                }
            }
            if(BasicPsStoreInfo.ADDRESS.equalsIgnoreCase(canalColumn.getName())){
                dto.setAddress(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.CONNECTOR.equalsIgnoreCase(canalColumn.getName())){
                dto.setConnector(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.TELEPHONE.equalsIgnoreCase(canalColumn.getName())){
                dto.setTelephone(canalColumn.getValue());
            }
            if(BasicPsStoreInfo.TS.equalsIgnoreCase(canalColumn.getName())){
                String ts = canalColumn.getValue();
                if(StringUtils.isNotBlank(ts)){
                    try {
                        dto.setTs(formatter.parse(ts));
                    } catch (ParseException e) {
                        log.error("把canalColumn的消息体转换dto的ts Exception:", e);
                    }
                }
            }
            if(BasicPsStoreInfo.YN.equalsIgnoreCase(canalColumn.getName())){
                if(canalColumn.getValue()!=null){
                    dto.setYn(Integer.parseInt(canalColumn.getValue()));
                }
            }
        }

        return dto;
    }
}
