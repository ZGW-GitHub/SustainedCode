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

package com.code.service.template.api;

import com.code.service.template.api.domain.request.TemplateCreateReqVO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Snow
 * @date 2023/5/20 19:34
 */
public interface TemplateApi {

	String test();

	Long save(@RequestBody TemplateCreateReqVO reqVO);

}
