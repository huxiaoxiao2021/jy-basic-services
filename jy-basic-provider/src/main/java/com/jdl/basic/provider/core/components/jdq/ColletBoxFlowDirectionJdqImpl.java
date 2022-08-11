package com.jdl.basic.provider.core.components.jdq;


import com.jdl.basic.provider.config.jdq.AbstractJDQ4Producer;
import com.jdl.basic.provider.config.jdq.JdqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("colletBoxFlowDirectionJDQ4Producer")
public class ColletBoxFlowDirectionJdqImpl extends AbstractJDQ4Producer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JDQProperties jdqProperties;

    @Override
    protected JdqConfig initJdqConfig() {
        JdqConfig colletBoxFlowDirection = jdqProperties.getConfigById("colletBoxFlowDirection");
        colletBoxFlowDirection.setEnabled(jdqProperties.enabled);
        return colletBoxFlowDirection;
    }
}
