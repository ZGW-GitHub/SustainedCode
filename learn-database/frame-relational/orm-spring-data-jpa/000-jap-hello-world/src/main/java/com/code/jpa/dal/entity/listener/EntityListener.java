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

package com.code.jpa.dal.entity.listener;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * @author Snow
 * @date 2020/7/31 4:06 下午
 */
public class EntityListener {

	@PrePersist
	public void preSave(Object entity) {
		System.out.println("save 执行前！");
	}

	@PreUpdate
	public void preUpdate(Object entity) {
		System.out.println("update 执行前！");
	}

	@PreRemove
	public void preRemove(Object entity) {
		System.out.println("remove 执行前！");
	}

	@PostPersist
	public void postSave(Object entity) {
		System.out.println("save 执行后！");
	}

	@PostUpdate
	public void postUpdate(Object entity) {
		System.out.println("update 执行后！");
	}

	@PostRemove
	public void postRemove(Object entity) {
		System.out.println("remove 执行后！");
	}

}
