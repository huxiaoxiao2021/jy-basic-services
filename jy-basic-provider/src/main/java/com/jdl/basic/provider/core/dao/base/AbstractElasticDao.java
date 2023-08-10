package com.jdl.basic.provider.core.dao.base;

import com.alibaba.fastjson.JSONObject;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.utils.EsBeanPropertyUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.sort.SortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author : xumigen
 * @date : 2019/5/16
 */
public abstract class AbstractElasticDao<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractElasticDao.class);

    public static final String SEARCH_TOTAL_HITS = "totalHits";

    private String index;
    private String type;
    private TransportClient transportClient;
    private int retryOnConflict = 2;

    public T getByDocId(String docId,Class<T> clazz) {
        try {
            GetResponse getResponse = transportClient.prepareGet(index, type, docId).execute().get();
            if(!getResponse.isExists()){
                return null;
            }
            return convertToBean(getResponse.getSource(),clazz);
        }catch (ExecutionException | InstantiationException | IllegalAccessException e) {
            logger.error("执行ES查询失败返回错误信息[{}]",docId,e);
        }catch (InterruptedException e){
            logger.error("执行ES查询失败返回错误信息[{}]",docId,e);
            Thread.currentThread().interrupt();
        }
        return null;
    }

    public List<T> searchListByQueryParam(BoolQueryBuilder queryParam, int offset, int rows, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        SearchHits hits = getSearchHits(queryParam, offset, rows);
        if (hits.getTotalHits() < 1) {
            return list;
        }
        try {
            for (SearchHit hit : hits) {
                list.add(convertToBean(hit.getSourceAsMap(),clazz));
            }
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("执行ES查询失败返回错误信息[{}]",queryParam,e);
        }
        return list;
    }

    public List<T> searchListByQueryParam(BoolQueryBuilder queryParam, int offset, int rows,SortBuilder sortBuilder, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        SearchHits hits = getSearchHits(queryParam, offset, rows,sortBuilder);
        if (hits.getTotalHits() < 1) {
            return list;
        }
        try {
            for (SearchHit hit : hits) {
                list.add(convertToBean(hit.getSourceAsMap(),clazz));
            }
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("执行ES查询失败返回错误信息[{}]",queryParam,e);
        }
        return list;
    }

    /**
     * 分页获取数据
     *
     * @param queryParam
     * @param offset
     * @param rows
     * @param sortBuilder
     * @param clazz
     * @return left：总数 right：分页查询的数据
     */
    public ImmutablePair<Long, List<T>> selectByPage(BoolQueryBuilder queryParam, int offset, int rows, SortBuilder sortBuilder, Class<T> clazz){
        SearchHits hits = getSearchHits(queryParam, offset, rows, sortBuilder);
        List<T> list = new ArrayList<>();
        try {
            for (SearchHit hit : hits) {
                list.add(convertToBean(hit.getSourceAsMap(),clazz));
            }
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("执行ES查询失败返回错误信息[{}]",queryParam,e);
        }
        return ImmutablePair.of(hits.totalHits, list);
    }

    public int insert(T obj){
        try {
            Map<String,Object> data = objConvertMap(obj);
            String _id = (String)data.get(EsBeanPropertyUtil.ES_PRIMARY_KEY);
            if(StringUtils.isEmpty(_id)){
                throw new IllegalArgumentException("对象里没有主键字段");
            }
            data.remove(EsBeanPropertyUtil.ES_PRIMARY_KEY);
            if (insert(_id, data)) {
                return 0;
            }
        } catch (IllegalAccessException |IOException e) {
            logger.error("执行ES新增失败返回错误信息[{}]", JsonHelper.toJSONString(obj),e);
        }

        return 1;
    }


    /**
     * 批量插入
     * @param objs
     * @return
     */
    public int bulkInsert(List<T> objs) {

        BulkRequestBuilder bulkRequest = this.prepareBulk();
        try {
            for (T obj : objs) {
                Map<String, Object> stringObjectMap = objConvertMap(obj);
                bulkRequest.add(this.prepareIndex().setSource(stringObjectMap));
            }
            BulkResponse bulkItemResponses = bulkRequest.execute().actionGet();
            if (bulkItemResponses.hasFailures()) {
                logger.error("批量插入失败:{}", JSONObject.toJSONString(bulkItemResponses));

            }
            return 0;
        } catch (IllegalAccessException e) {
            logger.error("批量插入失败", e);
        }
        return 1;
    }

    public int insert(String id,T obj){
        try {
            Map<String,Object> data = EsBeanPropertyUtil.objConvertMapFilterNull(obj);
            data.remove(EsBeanPropertyUtil.ES_PRIMARY_KEY);
            if (insert(id, data)) {
                return 0;
            }
        } catch (IllegalAccessException |IOException e) {
            logger.error("执行ES新增失败返回错误信息id[{}]",id,e);
        }
        return 1;
    }

    /**
     * 【aggregationBuilders不建议超过三个】
     * @param queryBuilder
     * @param aggregationBuilders
     * @return
     */
    public Map<String, Double> sumByQueryBuilderAndAggregation(BoolQueryBuilder queryBuilder, AggregationBuilder... aggregationBuilders) {
        SearchRequestBuilder searchRequestBuilder = this.prepareSearch().setQuery(queryBuilder);
        if (aggregationBuilders == null || aggregationBuilders.length == 0) {
            return Collections.emptyMap();
        }
        for (AggregationBuilder agg : aggregationBuilders) {
            searchRequestBuilder.addAggregation(agg);
        }
        SearchResponse searchResponse = searchRequestBuilder.setFrom(0).setSize(0).execute().actionGet();
        Map<String, Double> result = new HashMap<>(aggregationBuilders.length,1);
        Aggregations aggregations = searchResponse.getAggregations();
        for (AggregationBuilder aggr : aggregationBuilders) {
            Sum sum = aggregations.get(aggr.getName());
            result.put(aggr.getName(),sum.getValue());
        }
        return result;
    }

    /**
     * 【aggregationBuilders不建议超过三个】
     * @param queryBuilder
     * @param aggregationBuilders
     * @return
     */
    public Map<String, Object> sumByQueryBuilderAndCount(BoolQueryBuilder queryBuilder, AggregationBuilder... aggregationBuilders) {
        SearchRequestBuilder searchRequestBuilder = this.prepareSearch().setQuery(queryBuilder);
        if (aggregationBuilders == null || aggregationBuilders.length == 0) {
            return Collections.emptyMap();
        }
        for (AggregationBuilder agg : aggregationBuilders) {
            searchRequestBuilder.addAggregation(agg);
        }
        SearchResponse searchResponse = searchRequestBuilder.setFrom(0).setSize(0).execute().actionGet();
        Map<String, Object> result = new HashMap<>(aggregationBuilders.length,1);
        Aggregations aggregations = searchResponse.getAggregations();
        for (AggregationBuilder aggr : aggregationBuilders) {
            Sum sum = aggregations.get(aggr.getName());
            result.put(aggr.getName(),sum.getValue());
        }
        SearchHits searchHits = searchResponse.getHits();
        if(searchHits == null){
            result.put(SEARCH_TOTAL_HITS, 0L);
        }else {
            result.put(SEARCH_TOTAL_HITS, searchHits.getTotalHits());
        }
        return result;
    }

    /**
     * 根据查询条件查询结果
     * @param queryBuilder 查询条件
     * @param rows 取多少条记录
     * @param includes 查询的字段名
     * @return 查询结果
     */
    public SearchResponse getSearchResponse(QueryBuilder queryBuilder, Integer rows, String [] includes) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(queryBuilder);
        SearchRequestBuilder searchRequestBuilder = this.prepareSearch();
        searchRequestBuilder.setQuery(boolQueryBuilder).setFetchSource(includes, null);
        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
        if (rows != null) {
            searchRequestBuilder.setSize(rows);
        }
        return searchRequestBuilder.execute().actionGet();
    }


    private boolean insert(String id, Map<String, Object> data) throws IOException {
        XContentBuilder jsonBuild = getXContentBuilder(data);
        BulkRequestBuilder bulkRequest = this.prepareBulk();
        IndexRequestBuilder indexRequestBuilder;
        if(StringUtils.isNotEmpty(id)){
            indexRequestBuilder = prepareIndex(id);
        }else{
            indexRequestBuilder = prepareIndex();
        }
        bulkRequest.add(indexRequestBuilder.setSource(jsonBuild));
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            logger.error("执行ES新增失败返回错误信息" + bulkResponse.buildFailureMessage());
            return true;
        }
        return false;
    }

    public int upsert(T obj){
        try {
            Map<String,Object> data = objConvertMap(obj);
            String _id = (String)data.get(EsBeanPropertyUtil.ES_PRIMARY_KEY);
            if(StringUtils.isEmpty(_id)){
                throw new IllegalArgumentException("对象里没有主键字段");
            }
            data.remove(EsBeanPropertyUtil.ES_PRIMARY_KEY);
            return updateProcess(_id, data,Boolean.TRUE);
        } catch (IllegalAccessException | IOException | ExecutionException e) {
            logger.error("执行ES更新失败返回错误信息obj[{}]", JsonHelper.toJSONString(obj),e);
        }catch (InterruptedException e){
            logger.error("执行ES查询失败返回错误信息[{}]",JsonHelper.toJSONString(obj),e);
            Thread.currentThread().interrupt();
        }
        return 0;
    }

    public int updateById(T obj,String id){
        try {
            Map<String,Object> data = objConvertMap(obj);
            data.remove(EsBeanPropertyUtil.ES_PRIMARY_KEY);
            return updateProcess(id, data,Boolean.FALSE);
        } catch (IllegalAccessException | IOException | ExecutionException e) {
            logger.error("执行ES更新失败返回错误信息obj[{}]", id,e);
        }catch (InterruptedException e){
            logger.error("执行ES查询失败返回错误信息[{}]",id,e);
            Thread.currentThread().interrupt();
        }
        return 0;
    }

    /**
     * 更新或者插入
     * @param id 主键
     * @param data 数据
     * @param isUpsert true 如果es没有则插入，false 只更新
     * @return 1成功，0失败
     */
    private int updateProcess(String id, Map<String, Object> data,boolean isUpsert) throws IOException, InterruptedException, ExecutionException {
        XContentBuilder jsonBuild = getXContentBuilder(data);
        UpdateRequest request = prepareUpdateRequest(jsonBuild,id,isUpsert);
        UpdateResponse response = transportClient.update(request).get();
        if (StringUtils.isBlank(response.getId())) {
            logger.error("执行ES更新失败返回错误信息index:" + response.getIndex() + "type:" + response.getType() + "version:" + response.getVersion());
            return 0;
        }
        return 1;
    }

    public SearchHits getSearchHits(BoolQueryBuilder queryParam, int offset, int rows) {
        SearchRequestBuilder searchRequestBuilder = this.prepareSearch();
        SearchResponse searchResponse = searchRequestBuilder.setQuery(queryParam).setFrom(offset).setSize(rows).setExplain(true)
                .execute().actionGet();
        return searchResponse.getHits();
    }

    private SearchHits getSearchHits(BoolQueryBuilder queryParam, int offset, int rows, SortBuilder sortBuilder) {
        SearchRequestBuilder searchRequestBuilder = this.prepareSearch();
        SearchResponse searchResponse = searchRequestBuilder.addSort(sortBuilder).setQuery(queryParam).setFrom(offset).setSize(rows).setExplain(true)
                .execute().actionGet();
        return searchResponse.getHits();
    }

    protected SearchRequestBuilder prepareSearch() {
        return this.getTransportClient().prepareSearch(index).setTypes(type);
    }

    private UpdateRequest prepareUpdateRequest(XContentBuilder jsonBuild,String docId,boolean isUpsert) {
        return transportClient.prepareUpdate(index, type, docId)
                    .setDoc(jsonBuild)
                    .setDetectNoop(true)
                    .setDocAsUpsert(isUpsert)
                    .setRetryOnConflict(retryOnConflict)
                    .request();
    }

    private XContentBuilder getXContentBuilder(Map<String, Object> data) throws IOException {
        XContentBuilder jsonBuild = XContentFactory.jsonBuilder().startObject();
        for(Map.Entry<String,Object> entry : data.entrySet()){
            jsonBuild.field(entry.getKey(),entry.getValue());
        }
        jsonBuild.endObject();
        return jsonBuild;
    }

    protected Map<String,Object> objConvertMap(T obj)throws IllegalArgumentException, IllegalAccessException{
        return EsBeanPropertyUtil.objConvertMapFilterNull(obj);
    }

    protected T convertToBean(Map<String, Object> source,Class<T> clazz)throws InstantiationException,IllegalAccessException{
        return EsBeanPropertyUtil.mapConvertBean(source,clazz);
    }

    private BulkRequestBuilder prepareBulk(){
        return this.getTransportClient().prepareBulk();
    }

    private IndexRequestBuilder prepareIndex(String id) {
        return this.getTransportClient().prepareIndex(index, type,id);
    }

    private IndexRequestBuilder prepareIndex() {
        return this.getTransportClient().prepareIndex(index, type);
    }

    public void setTransportClient(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    public TransportClient getTransportClient() {
        return transportClient;
    }

    public void setIndex(String index) {
        this.index = index;
    }
    public String getIndex() {
        return this.index;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
}
