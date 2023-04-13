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

package com.code.java.proxy.cglib.lazy.loader;

import com.code.java.proxy.cglib.ProxyUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.LazyLoader;

import java.util.Random;

/**
 * @author Snow
 * @date 2021/11/4 14:44
 */
@Slf4j
@Data
public class BlogModel {

	private Integer id;
	private String title;
	private BlogContentModel blogContentModel;

	public BlogModel(String title) {
		this.id = new Random().nextInt(10);
		this.title = title;
		this.blogContentModel = getBlogContent(id);
	}

	private BlogContentModel getBlogContent(Integer id) {
		return (BlogContentModel) ProxyUtil.getProxy(BlogContentModel.class, (LazyLoader) () -> {
			// 模拟从数据库根据 blog 的 id 查询 blogContent
			System.err.printf("开始从数据库查询博客[%s]的博客内容\n", id);
			BlogContentModel blogContentModel = new BlogContentModel();
			blogContentModel.setId(new Random().nextInt(10));
			blogContentModel.setBlogId(id);
			blogContentModel.setContent("CGLIB 动态代理的学习");
			return blogContentModel;
		});
	}

}
