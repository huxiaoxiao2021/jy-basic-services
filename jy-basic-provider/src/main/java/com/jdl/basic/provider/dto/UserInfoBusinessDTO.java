package com.jdl.basic.provider.dto;

import java.util.Date;
import lombok.Data;

@Data
public class UserInfoBusinessDTO {
  /**
   * 数据来源
   */
  private String dataSource;

  /**
   * 机构全称
   */
  private String organizationFullname;

  /**
   * 机构名称
   */
  private String organizationName;

  /**
   * 机构全编码
   */
  private String organizationFullPath;

  /**
   * 机构编码
   */
  private String organizationCode;

  /**
   * 岗位描述
   */
  private String positionName;

  /**
   * 岗位编码
   */
  private String positionCode;

  /**
   * 标准岗位名称
   */
  private String stdPositionName;

  /**
   * 标准岗位编码
   */
  private String stdPositionCode;

  /**
   * 城市code
   */
  private String cityCode;

  /**
   * 城市名称
   */
  private String cityName;

  /**
   * 地点编码
   */
  private String placeCode;

  /**
   * 地点名称
   */
  private String placeName;

  /**
   * 用工性质
   */
  private String nature;

  /**
   * 用工性质描述
   */
  private String natureDesc;
  /**
   * 入职日期
   */
  private java.util.Date entryDate;

  /**
   * ERP账号
   */
  private String userName;

  /**
   * HR状态
   */
  private Integer workState;
  /**
   * 人资系统是否在职 1在职 2离职（通过招聘系统办理入职人员的状态）
   */
  private Integer status;

  /**
   * 最后工作日
   */
  private java.util.Date quitDate;

  /**
   * 离职生效日期
   */
  private java.util.Date quitActionDate;

  /**
   * 合同主体
   */
  private String companyName;

  /**
   * 合同code
   */
  private String companyCode;

  /**
   * 计费方式
   */
  private String chargeType;

  /**
   * 挂靠结算
   */
  private Integer isRelyOn;

  /**
   * 员工姓名
   */
  private String realName;

  /**
   * 性别
   */
  private String sex;

  /**
   * 用户座机
   */
  private String telephone;

  /**
   * 身份证地址
   */
  private String placeOrigin;

  /**
   * 身份证有效截止日期
   */
  private String idDueDate;

  /**
   * 职级描述
   */
  private String levelName;

  /**
   * 职级编码
   */
  private String levelCode;

  /**
   * 职等描述
   */
  private String gradeName;

  /**
   * 职等code
   */
  private String gradeCode;

  /**
   * 合同工时制
   */
  private String contractWorkType;

  /**
   * 员工ID
   */
  private String userCode;

  /**
   * 系统code
   */
  private String systemCode;
  /**
   * 系统名称
   */
  private String systemName;
  /**
   * 姓名拼音
   */
  private String pinyin;
  /**
   * 转正日期
   */
  private Date positiveDate;
  /**
   * 计费方式描述
   */
  private String chargeTypeDesc;
  /**
   * 用工性质描述
   */
  private String workTypeDesc;

  /**
   * 业务条线
   */
  private String organizationBusiness;

  /**
   * 业务条线描述
   */
  private String organizationBusinessDesc;

  /**
   * 合同-工时类型
   */
  private String workType;

  /**
   * 修改者
   */
  private String modifier;

  /**
   * 修改时间
   */
  private String modifyTime;

  /**
   * 用户编码（仓库使用）
   */
  private String userCodeZH;

  /**
   * 业务机构编码
   */
  private String orgCode;

  /**
   * 业务机构orgId
   */
  private String orgId;

  /**
   * 区域名称
   */
  private String regionName;

  /**
   * 职责
   */
  private String duty;

  /**
   * 是否录入人脸
   */
  private Integer isFaceRecord;
  /**
   * 账号系统离职时间
   */
  private Date leaveTime;

  /**
   * 结算类型
   */
  private Integer accountType;
  /**
   * 账号系统是否在职 1在职 0离职（在仓或业务部门办理入职人员状态）
   */
  private Integer statusZH;
  /**
   * 数据变更来源(1、管道变更 2、账号系统变更 注：JMQ使用，接口调用无此字段)
   */
  private Integer source;
  /**
   * 账号系统创建人
   */
  private String createUserZH;

  /**
   * 账号系统创建时间
   */
  private Date createTimeZH;

  /**
   * 账号系统更新人
   */
  private String updateUserZH;

  /**
   * 账号系统更新时间
   */
  private Date updateTimeZH;

  /**
   * 绩效方案ID
   */
  private String performancePlanId;

  /**
   * 绩效描述
   */
  private String performanceDescription;


  /**
   * 管道系统创建时间
   */
  private Date createTime;

}
