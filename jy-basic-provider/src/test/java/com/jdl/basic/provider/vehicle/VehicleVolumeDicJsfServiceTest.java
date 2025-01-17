package com.jdl.basic.provider.vehicle;

import com.jdl.basic.api.domain.schedule.BatchWorkGridScheduleQueryDto;
import com.jdl.basic.api.domain.vehicle.VehicleVolumeDicReq;
import com.jdl.basic.api.domain.vehicle.VehicleVolumeDicResp;
import com.jdl.basic.api.service.vehicle.VehicleVolumeDicJsfService;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.schedule.WorkGridScheduleService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class VehicleVolumeDicJsfServiceTest {
  /*@Autowired
  VehicleVolumeDicJsfService vehicleVolumeDicJsfService;*/
  @Autowired
  WorkGridScheduleService workGridScheduleService;

 /* @Test
  public void queryPageListTest(){
    VehicleVolumeDicReq vehicleVolumeDicReq =new VehicleVolumeDicReq();
    vehicleVolumeDicReq.setVehicleType(1);
    Result<VehicleVolumeDicResp> result= vehicleVolumeDicJsfService.queryVolumeByVehicleType(vehicleVolumeDicReq);
    if (result.isSuccess()){
      System.out.println(JsonHelper.toJSONString(result));
    }
    else {
      System.out.println(JsonHelper.toJSONString(result));
    }
  }
*/
  @Test
  public void queryPageListTest1(){
    BatchWorkGridScheduleQueryDto dto =new BatchWorkGridScheduleQueryDto();
    List<String> list =new ArrayList<>();
    list.add("CDWG00000022112-1-3");
    list.add("CDWG00000022112-3-2");
    dto.setScheduleKeyList(list);
    System.out.println(JsonHelper.toJSONString(workGridScheduleService.listWorkGridScheduleByKeys(dto)));
  }

}
