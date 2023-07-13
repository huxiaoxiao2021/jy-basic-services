package com.jdl.basic.api.domain.work;

import java.util.List;

public class WorkGridManagerCaseWithItem extends WorkGridManagerCase{

	private static final long serialVersionUID = 1L;

	private List<WorkGridManagerCaseItem> caseItemList;

	public List<WorkGridManagerCaseItem> getCaseItemList() {
		return caseItemList;
	}

	public void setCaseItemList(List<WorkGridManagerCaseItem> caseItemList) {
		this.caseItemList = caseItemList;
	}
}
