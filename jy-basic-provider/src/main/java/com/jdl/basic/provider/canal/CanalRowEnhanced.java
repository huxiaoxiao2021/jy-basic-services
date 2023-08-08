package com.jdl.basic.provider.canal;

import java.util.List;

/**
 * dms特用的canalrow格式
 * Created by fangqi1 on 2016/4/13.
 */
public class CanalRowEnhanced extends CanalRow {
    private List<CanalColumn> beforeChangeOfColumns;
    private List<CanalColumn> afterChangeOfColumns;

    public List<CanalColumn> getBeforeChangeOfColumns() {
        return beforeChangeOfColumns;
    }

    public void setBeforeChangeOfColumns(List<CanalColumn> beforeChangeOfColumns) {
        this.beforeChangeOfColumns = beforeChangeOfColumns;
    }

    public List<CanalColumn> getAfterChangeOfColumns() {
        return afterChangeOfColumns;
    }

    public void setAfterChangeOfColumns(List<CanalColumn> afterChangeOfColumns) {
        this.afterChangeOfColumns = afterChangeOfColumns;
    }
}
