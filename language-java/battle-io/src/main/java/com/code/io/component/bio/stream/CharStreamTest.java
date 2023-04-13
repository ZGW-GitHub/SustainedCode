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

package com.code.io.component.bio.stream;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author Snow
 * @date 2022/8/2 11:43
 */
@Slf4j
public class CharStreamTest {
	public static void main(String[] args) throws IOException {

		File file = new File("/Users/k");

		FileWriter     writer         = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		bufferedWriter.close();

		FileReader     reader         = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(reader);
		bufferedReader.close();

	}
}
