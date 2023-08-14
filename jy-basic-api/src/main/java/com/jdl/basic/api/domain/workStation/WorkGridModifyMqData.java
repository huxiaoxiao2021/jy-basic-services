package com.jdl.basic.api.domain.workStation;

import java.io.Serializable;
import java.util.Date;

/**
 * 场地网格变更mq-实体类
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
public class WorkGridModifyMqData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 操作标识
		ADD(1,"新增"),
		MODIFY(2,"修改"),
		DELETE (3,"删除")
	 * 
	 */
	private Integer editType;
	/**
	 * 操作人编码
	 */
	private String operateUserCode;
	/**
	 * 操作人名称
	 */
	private String operateUserName;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 
	 */
	WorkGrid gridData;
	
	public Integer getEditType() {
		return editType;
	}
	public void setEditType(Integer editType) {
		this.editType = editType;
	}
	public String getOperateUserCode() {
		return operateUserCode;
	}
	public void setOperateUserCode(String operateUserCode) {
		this.operateUserCode = operateUserCode;
	}
	public String getOperateUserName() {
		return operateUserName;
	}
	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public WorkGrid getGridData() {
		return gridData;
	}
	public void setGridData(WorkGrid gridData) {
		this.gridData = gridData;
	}
	
}
