package com.jdl.basic.provider.config.lock.impl;


import com.jdl.basic.common.contants.Constants;
import com.jdl.basic.provider.config.cache.CacheService;
import com.jdl.basic.provider.config.lock.LockService;
import com.jdl.basic.provider.hander.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * jimdb 实现的分布式加锁
 * @author wuyoude
 *
 */
@Service("jimdbRemoteLockService")
public class JimdbRemoteLockServiceImpl implements LockService {
	
    @Autowired
    @Qualifier("jimdbCacheService")
    private CacheService cacheService;
	/**
	 * 默认过期时间为30秒
	 */
	private long exTime = 30*1000;
	
	@Override
	public void tryLock(String key, ResultHandler handler) {
		this.tryLock(key, exTime, handler);
	}
	@Override
	public void tryLock(String key,long timeOut, ResultHandler handler) {
		boolean lockSuc = false;
		if(handler == null) {
			throw new RuntimeException("tryLock参数ResultHandler不能为空！");
		}
		try {
			lockSuc = cacheService.setNx(key, Constants.FLAG_OPRATE_ON, timeOut,TimeUnit.MILLISECONDS);
			if(lockSuc) {
				handler.success();
			}else {
				handler.fail();
			}
		}catch (Exception e) {
			handler.error(e);
		}finally {
			if(lockSuc) {
				cacheService.del(key);
			}
		}
	}
}
