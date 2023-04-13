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

package zhuang.shi.qi;

/**
 * 装饰器
 *
 * @author Snow
 * @date 2020/10/22 3:15 下午
 */
public class CarStructureAddTires extends Structure {

	private final Structure structure;

	public CarStructureAddTires(Structure structure) {
		this.structure = structure;
	}

	@Override
	public String getMark() {
		return this.structure.getMark() + "轮胎、";
	}
}
