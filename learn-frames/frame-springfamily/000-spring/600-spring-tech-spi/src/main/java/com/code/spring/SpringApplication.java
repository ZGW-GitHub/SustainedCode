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

package com.code.spring;

import com.code.spring.spi.SpiService;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.ServiceLoader;

public class SpringApplication {
    public static void main(String[] args) {

        // javaSpiTest();
        springSpiTest();

    }

    /**
     * Java SPI 测试
     */
    private static void javaSpiTest() {
        ServiceLoader<SpiService> serviceLoader = ServiceLoader.load(SpiService.class);

        serviceLoader.forEach(System.err::println);
    }

    /**
     * Spring SPI 测试
     */
    private static void springSpiTest() {
        List<SpiService> spiServiceList = SpringFactoriesLoader.loadFactories(SpiService.class, Thread.currentThread().getContextClassLoader());

        spiServiceList.forEach(System.err::println);
    }

}
