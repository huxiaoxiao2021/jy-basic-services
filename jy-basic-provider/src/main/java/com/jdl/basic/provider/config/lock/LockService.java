package com.jdl.basic.provider.config.lock;

import com.jdl.basic.provider.hander.ResultHandler;

/**
 * 加锁服务
 * @author wuyoude
 *
 */
public interface LockService {
	/**
	 * 指定的key加锁，超时时间30秒
	 * @param key
	 * @param handler
	 */
	void tryLock(String key, ResultHandler handler);
	/**
	 * 指定的key加锁，指定超时时间（单位：毫秒）
	 * @param key
	 * @param timeOut 超时时间（单位：毫秒）
	 * @param handler
	 */
	void tryLock(String key,long timeOut,ResultHandler handler);

	/**
	 * 指定的key加锁，指定超时时间（单位：毫秒）
	 * 获取锁失败，会进行重试。100毫秒重试一次，最长重试4秒钟
	 * @param key
	 * @param timeOut 超时时间（单位：毫秒）
	 * @param handler
	 */
	void lock(String key,long timeOut,ResultHandler handler);
}
