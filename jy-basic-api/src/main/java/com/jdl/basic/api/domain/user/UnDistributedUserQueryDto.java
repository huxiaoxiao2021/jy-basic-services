package com.jdl.basic.api.domain.user;

import java.io.Serializable;
import lombok.Data;

@Data
public class UnDistributedUserQueryDto implements Serializable {

  private static final long serialVersionUID = -7991565801120903968L;
  private String siteCode;
  private Integer pageNo;
  private Integer pageSize;
  private String checkDay;
}
