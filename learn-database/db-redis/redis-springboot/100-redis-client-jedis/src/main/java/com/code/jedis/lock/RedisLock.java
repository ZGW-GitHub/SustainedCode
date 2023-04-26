/*
 * Copyright (C) <2023> <Snow>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.code.jedis.lock;

import com.code.jedis.tools.lock.RedisLockUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/12/11 17:24
 */
@Slf4j
public abstract class RedisLock {

	@Getter
	protected final String key;
	@Getter
	protected final String value;

	protected RedisLock(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public final void lock() {
		boolean locked = tryLock(0, null);
		if (!locked) {
			locked = RedisLockUtil.blockingLocking(0, 0, null, this);
		}
		if (locked) {
			RedisLockUtil.startWatchDog(this);
		}
	}

	public final void lock(long leaseTime, TimeUnit timeUnit) {
		boolean locked = tryLock(leaseTime, timeUnit);
		if (!locked) {
			RedisLockUtil.blockingLocking(0, leaseTime, timeUnit, this);
		}
	}

	public final boolean tryLock() {
		boolean locked = tryLock(0, null);
		if (locked) {
			RedisLockUtil.startWatchDog(this);
		}
		return locked;
	}

	public abstract boolean tryLock(long leaseTime, TimeUnit timeUnit);

	public final boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) {
		boolean locked = tryLock(0, null);
		return locked || RedisLockUtil.blockingLocking(waitTime, leaseTime, timeUnit, this);
	}

	public abstract boolean renewalLock();

	public abstract void unlock();

}
