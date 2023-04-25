package com.jdl.basic.api.domain.workStation;

import com.jdl.basic.api.domain.BasePagerCondition;
import com.jdl.basic.common.utils.DateHelper;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: WorkGridFlowDetailOffline
 * @Description: 网格流向明细离线统计表-实体
 *
 */
@Data
public class WorkGridFlowDetailOfflineQuery extends BasePagerCondition implements Serializable {

	private static final long serialVersionUID = 1212129435314L;

	/**
	 * 业务key：ref_work_grid_key
	 */
	private String refWorkGridKey;

	/**
	 * 流向类型 1：进 2：出
	 */
	private String flowDirectionType;

	/**
	 * 线路类型
	 */
	private String lineType;

	/**
	 * 日期
	 */
	private String dt;

	/**
	 * 分页-pageSize
	 */
	private Integer pageSize;

	public String getDt() {
		return DateFormatUtils.format(new Date(), DateHelper.DATE_FORMAT_YYYY_MM_DD);
	}

	private void setDt(String dt) {
		this.dt = dt;
	}
}
