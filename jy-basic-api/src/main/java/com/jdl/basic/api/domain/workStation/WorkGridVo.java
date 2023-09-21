package com.jdl.basic.api.domain.workStation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jdl.basic.api.domain.machine.Machine;

import lombok.Getter;
import lombok.Setter;

/**
 * 场地网格表-实体类
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Setter
@Getter
public class WorkGridVo extends WorkGrid {

	private static final long serialVersionUID = 1L;
	/**
	 * 流向配置-状态
	 */
	private String configFlowStatusName;
	
	private Map<String,FlowInfoItem> flowInfo;
	
	private WorkDataInfo workDataInfo;
	/**
	 * 流向类型 1-进 2-出 0-其他
	 */
	private Integer flowDirectionType;
	/**
	 * 负责人列表
	 */
	private List<WorkGridOwnerUser> ownerUserList;	
	
	public static class FlowInfoItem implements Serializable{
		
		private static final long serialVersionUID = -2582917155048441477L;
		
		private Integer flowNum;
		
		private List<WorkGridFlowDirection> flowList;
		
		public Integer getFlowNum() {
			return flowNum;
		}
		public void setFlowNum(Integer flowNum) {
			this.flowNum = flowNum;
		}
		public List<WorkGridFlowDirection> getFlowList() {
			return flowList;
		}
		public void setFlowList(List<WorkGridFlowDirection> flowList) {
			this.flowList = flowList;
		}
	}
	public static class WorkDataInfo implements Serializable{
		
		private static final long serialVersionUID = -2582917155048441477L;
		
		private List<WorkStationGrid> workStationGridList;
		private Integer workStationGridNum;
		/**
		 * 自动化设备列表
		 */
		private List<Machine> machineList;
		/**
		 * 编制人数
		 */
		private Integer standardNum;

		/**
		 * 负责人erp
		 */
		private String ownerUserErp;
		/**
		 * 月台号
		 */
		private String dockCode;

		/**
		 * 供应商编码
		 */
		private String supplierCode;

		/**
		 * 供应商名称
		 */
		private String supplierName;

		public List<WorkStationGrid> getWorkStationGridList() {
			return workStationGridList;
		}

		public void setWorkStationGridList(List<WorkStationGrid> workStationGridList) {
			this.workStationGridList = workStationGridList;
		}

		public Integer getWorkStationGridNum() {
			return workStationGridNum;
		}

		public void setWorkStationGridNum(Integer workStationGridNum) {
			this.workStationGridNum = workStationGridNum;
		}

		public List<Machine> getMachineList() {
			return machineList;
		}

		public void setMachineList(List<Machine> machineList) {
			this.machineList = machineList;
		}

		public Integer getStandardNum() {
			return standardNum;
		}

		public void setStandardNum(Integer standardNum) {
			this.standardNum = standardNum;
		}

		public String getOwnerUserErp() {
			return ownerUserErp;
		}

		public void setOwnerUserErp(String ownerUserErp) {
			this.ownerUserErp = ownerUserErp;
		}

		public String getDockCode() {
			return dockCode;
		}

		public void setDockCode(String dockCode) {
			this.dockCode = dockCode;
		}

		public String getSupplierCode() {
			return supplierCode;
		}

		public void setSupplierCode(String supplierCode) {
			this.supplierCode = supplierCode;
		}

		public String getSupplierName() {
			return supplierName;
		}

		public void setSupplierName(String supplierName) {
			this.supplierName = supplierName;
		}
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
