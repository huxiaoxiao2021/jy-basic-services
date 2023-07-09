package com.jdl.basic.api.domain.schedule;

import java.util.List;
import lombok.Data;

@Data
public class BatchWorkGridScheduleQueryDto {
  List<String> scheduleKeyList;
}
