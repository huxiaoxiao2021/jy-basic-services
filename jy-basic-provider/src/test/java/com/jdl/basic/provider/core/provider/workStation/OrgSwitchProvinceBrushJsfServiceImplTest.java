package com.jdl.basic.provider.core.provider.workStation;

import com.jdl.basic.api.service.workStation.OrgSwitchProvinceBrushJsfService;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 类的描述
 *
 * @author hujiping
 * @date 2023/6/6 3:34 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class OrgSwitchProvinceBrushJsfServiceImplTest {

    @Autowired
    private OrgSwitchProvinceBrushJsfService orgSwitchProvinceBrushJsfService;
    
    @Test
    public void workStationGridBrush() {
        
        orgSwitchProvinceBrushJsfService.workStationGridBrush();
    }

    @Test
    public void workGridBrush() {

        orgSwitchProvinceBrushJsfService.workGridBrush();
    }

    @Test
    public void workGridFlowDirectionBrush() {

        orgSwitchProvinceBrushJsfService.workGridFlowDirectionBrush();
    }
    
    @Test
    public void workGridFlowDetailOfflineBrush() {

        orgSwitchProvinceBrushJsfService.workGridFlowDetailOfflineBrush();
    }
    
    @Test
    public void siteWaveScheduleBrush() {

        orgSwitchProvinceBrushJsfService.siteWaveScheduleBrush();
    }

    @Test
    public void siteAttendPlanBrush() {

        orgSwitchProvinceBrushJsfService.siteAttendPlanBrush();
    }

    @Test
    public void collectBoxFlowDirectionConfBrush() {

        orgSwitchProvinceBrushJsfService.collectBoxFlowDirectionConfBrush(0, 1000);
    }
    
    @Test
    public void easyFreezeSiteConfBrush() {

        orgSwitchProvinceBrushJsfService.easyFreezeSiteConfBrush(0, 1000);
    }

    @Test
    public void configTransferDpSiteBrush() {

        orgSwitchProvinceBrushJsfService.configTransferDpSiteBrush(0, 1000);
    }

    @Test
    public void workStationAttendPlanBrush() {
        orgSwitchProvinceBrushJsfService.workStationAttendPlanBrush(0, 1000);
    }
    
    
}