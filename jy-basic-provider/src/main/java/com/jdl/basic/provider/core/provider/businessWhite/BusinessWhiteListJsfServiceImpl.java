package com.jdl.basic.provider.core.provider.businessWhite;

import com.jdl.basic.api.domain.businessWhite.BusinessWhiteList;
import com.jdl.basic.api.domain.businessWhite.BusinessWhiteListCondition;
import com.jdl.basic.api.service.businessWhite.BusinessWhiteListJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.dao.businessWhite.BusinessWhiteListDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BusinessWhiteListJsfServiceImpl implements BusinessWhiteListJsfService {


    @Autowired
    private BusinessWhiteListDao businessWhiteListDao;

    /**
     * 删除数据
     *
     * @param param
     * @return
     */
    @Override
    public Result<Boolean> deleteById(BusinessWhiteList param) {
        Result<Boolean> result = Result.success();
        try{
            Integer deleteNum = businessWhiteListDao.deleteById(param.getId());
            if (null == deleteNum || deleteNum <= 0){
                result.toFail("更新失败！");
            }
        }catch (Exception ex){
            log.error("BusinessWhiteListJsfServiceImpl.deleteById has error. The error is " + ex.getMessage(),ex);
            result.toFail("删除失败，请联系分拣小秘");
        }
        return result;
    }

    /**
     * 查询数据
     *
     * @param param
     * @return
     */
    @Override
    public Result<List<BusinessWhiteList>> selectByParam(BusinessWhiteListCondition param) {
        Result<List<BusinessWhiteList>> result = Result.success();
        try{
            List<BusinessWhiteList> datas = businessWhiteListDao.selectByParam(param);
            result.setData(datas);
        }catch (Exception ex){
            log.error("BusinessWhiteListJsfServiceImpl.selectByParam has error. The error is " + ex.getMessage(),ex);
            result.toFail("查询失败，请联系分拣小秘");
        }
        return result;
    }

    /**
     * 获取总数据量
     *
     * @param param
     * @return
     */
    @Override
    public Result<Long> count(BusinessWhiteListCondition param) {
        Result<Long> result = Result.success();
        try{
            Long nums = businessWhiteListDao.count(param);
            result.setData(nums);
        }catch (Exception ex){
            log.error("BusinessWhiteListJsfServiceImpl.count has error. The error is " + ex.getMessage(),ex);
            result.toFail("查询失败，请联系分拣小秘");
        }
        return result;
    }

    /**
     * 添加数据
     *
     * @param record
     * @return
     */
    @Override
    public Result<Boolean> insert(BusinessWhiteList record) {
        Result<Boolean> result = Result.success();
        try{
            //校验逻辑：一级大类和关键字不能重复
            List<BusinessWhiteList> savedOfData = businessWhiteListDao.selectByClasAndContent(record);
            if (CollectionUtils.isNotEmpty(savedOfData)){
                result.toFail("有重复记录数据！");
                return result;
            }
            Integer nums = businessWhiteListDao.insert(record);
            if (null == nums || nums <= 0){
                result.toFail("添加失败！");
            }
        }catch (Exception ex){
            log.error("BusinessWhiteListJsfServiceImpl.count has error. The error is " + ex.getMessage(),ex);
            result.toFail("添加失败，请联系分拣小秘");
        }
        return result;
    }
}
