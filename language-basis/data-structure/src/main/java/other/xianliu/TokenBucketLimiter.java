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

package other.xianliu;

import java.util.Date;

/**
 * 限流算法：漏桶
 *
 * @author Snow
 * @date 2021/9/17 22:54
 */
public class TokenBucketLimiter {

	private int capacity;//令牌桶容量
	private int rate;//令牌产生速率
	private int tokenAmount;//令牌数量

	private TokenBucketLimiter() {}

	public TokenBucketLimiter(int capacity, int rate) {
		this.capacity = capacity;
		this.rate = rate;
		tokenAmount = capacity;
		new Thread(new Runnable() {
			@Override
			public void run() {
				//以恒定速率放令牌
				while (true) {
					synchronized (this) {
						tokenAmount++;
						if (tokenAmount > capacity) {
							tokenAmount = capacity;
						}
					}
					try {
						Thread.sleep(1000 / rate);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public synchronized boolean tryAcquire(Request request) {
		if (tokenAmount > 0) {
			tokenAmount--;
			handleRequest(request);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 处理请求
	 *
	 * @param request req
	 */
	private void handleRequest(Request request) {
		request.setHandleTime(new Date());
		System.out.println(request.getCode() + "号请求被处理，请求发起时间：" + request.getLaunchTime() + ",请求处理时间：" + request.getHandleTime() + ",处理耗时：" + (request.getHandleTime().getTime() - request.getLaunchTime().getTime()) + "ms");
	}

	/**
	 * 请求类，属性只包含一个名字字符串
	 */
	static class Request {
		private int code;
		private Date launchTime;
		private Date handleTime;

		private Request() {}

		public Request(int code, Date launchTime) {
			this.launchTime = launchTime;
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public Date getLaunchTime() {
			return launchTime;
		}

		public void setLaunchTime(Date launchTime) {
			this.launchTime = launchTime;
		}

		public Date getHandleTime() {
			return handleTime;
		}

		public void setHandleTime(Date handleTime) {
			this.handleTime = handleTime;
		}
	}


	public static void main(String[] args) {
		TokenBucketLimiter tokenBucketLimiter = new TokenBucketLimiter(5, 2);
		for (int i = 1; i <= 10; i++) {
			Request request = new Request(i, new Date());
			if (tokenBucketLimiter.tryAcquire(request)) {
				System.out.println(i + "号请求被接受");
			} else {
				System.out.println(i + "号请求被拒绝");
			}
		}
	}

}
