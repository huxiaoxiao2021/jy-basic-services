package com.jdl.basic.provider.core.dao.basic;

import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.core.dao.base.AbstractElasticDao;
import com.jdl.basic.provider.core.po.BasicSiteEsDto;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

/**
 * 站点DAO
 *
 * @author hujiping
 * @date 2021/2/23 4:56 下午
 */
public class BasicSiteEsDao extends AbstractElasticDao<BasicSiteEsDto> {

    /**
     * 根据siteCode获取Es 的docId
     *
     * @param siteCode
     * @return
     */
    public String getEsDocIdBySIteCode(Integer siteCode) {

        SearchRequestBuilder searchRequestBuilder = this.prepareSearch();
        SearchResponse searchResponse = searchRequestBuilder.setQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery(BasicSiteEsDto.SITE_CODE, siteCode)))
                .setFrom(Constants.CONSTANT_NUMBER_ZERO).setSize(Constants.CONSTANT_NUMBER_ONE).setExplain(false)
                .execute().actionGet();

        if (searchResponse.getHits().totalHits == Constants.CONSTANT_NUMBER_ZERO) {
            return null;
        } else {
            SearchHit[] hits = searchResponse.getHits().getHits();
            for (SearchHit hit : hits) {
                return hit.getId();
            }
            return null;
        }
    }

}
