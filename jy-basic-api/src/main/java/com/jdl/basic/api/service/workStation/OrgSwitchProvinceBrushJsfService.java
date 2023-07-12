package com.jdl.basic.api.service.workStation;

/**
 * 省区切换刷数接口
 *
 * @author hujiping
 * @date 2023/6/6 3:07 PM
 */
public interface OrgSwitchProvinceBrushJsfService {

    /**
     * work_station_grid 表刷数省区字段
     */
    void workStationGridBrush();

    /**
     * work_grid 表刷数省区字段
     */
    void workGridBrush();

    /**
     * work_grid_flow_direction 表刷数省区字段
     */
    void workGridFlowDirectionBrush();

    /**
     * work_grid_flow_detail_offline 表刷数省区字段
     */
    void workGridFlowDetailOfflineBrush();

    /**
     * site_wave_schedule 表刷数省区字段
     */
    void siteWaveScheduleBrush();

    /**
     * site_attend_plan 表刷数省区字段
     */
    void siteAttendPlanBrush();

    /**
     * sort_cross_detail 表刷数省区字段
     */
    void sortCrossDetailBrush(Integer startId, Integer maxLoopCount);

    /**
     * collect_box_flow_direction_conf 表刷数省区字段
     */
    void collectBoxFlowDirectionConfBrush(Integer startId, Integer maxLoopCount);

    /**
     * jy_easy_freeze_site_conf 表刷数省区字段
     */
    void easyFreezeSiteConfBrush(Integer startId, Integer maxLoopCount);

    /**
     * config_transfer_dp_site 表刷数省区字段
     */
    void configTransferDpSiteBrush(Integer startId, Integer maxLoopCount);

    /**
     * work_station_attend_plan 表刷数省区字段
     */
    void workStationAttendPlanBrush(Integer startId, Integer maxLoopCount);

}
