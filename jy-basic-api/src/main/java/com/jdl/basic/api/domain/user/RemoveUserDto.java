package com.jdl.basic.api.domain.user;

import java.util.Date;
import lombok.Data;

@Data
public class RemoveUserDto {

  private long userId;
  private String updateUserErp;
  private String updateUserName;
  private Date updateTime;
}
