/*
 * Copyright 2021 Michelin CERT (https://cert.michelin.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.michelin.cert.redscan.service;

import com.michelin.cert.redscan.utils.datalake.DatalakeStorageException;
import com.michelin.cert.redscan.utils.datalake.DatalakeStorageItem;
import com.michelin.cert.redscan.utils.models.Brand;
import com.michelin.cert.redscan.utils.models.ServiceLevel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import kong.unirest.json.JSONObject;

/**
 * Brand service.
 *
 * @author Maxime ESCOURBIAC
 * @author Axel REMACK
 */
@Service
public class BrandService {

    /**
     * Default constructor
     */
    public BrandService() {    }


    /**
     * Get all brands.
     *
     * @return All brands.
     */
    public List<Brand> findAll() {
        LogManager.getLogger(BrandService.class).info("BrandService : Get all brands");
        List<Brand> brands = null;
        try {
            brands = (new Brand()).findAll();
            //Brand b3 = new Brand("brand3");
            //b3.upsert();
            System.out.println("truc");
        } catch (DatalakeStorageException ex) {
            LogManager.getLogger(BrandService.class).error(String.format("BrandService : Datalake storage exception %s", ex.getMessage()));
        }
        return brands;
    }

    /**
     * Get a brand.
     *
     * @param brand Brand to find.
     * @return Brand found.
     */
    public Brand find(Brand brand) {
        LogManager.getLogger(BrandService.class).info(String.format("BrandService : Search brand by name \"%s\"", (brand != null) ? brand.getName() : "null"));
        Brand found = null;
        try {
            found = brand.find();
            System.out.println(found);
        } catch (DatalakeStorageException ex) {
            LogManager.getLogger(BrandService.class).error(String.format("BrandService : Datalake storage exception %s", ex.getMessage()));
        }
        return found;
    }


    /**
     * Create Brand.
     *
     * @param brand Brand to create.
     * @return True if the creation succeed.
     */
    public boolean create(Brand brand) {
        LogManager.getLogger(BrandService.class).info(String.format("BrandService : Create brand %s", (brand != null) ? brand.toJson() : "null"));
        boolean create = false;
        try {
            create = brand.create();
            if (create && brand.getLastScanDate() == null) {
                brand.setLastScanDate(new Date());
                create = brand.upsert();
                //if (create) {
                //    rabbitTemplate.convertAndSend(brand.getFanoutExchangeName(), "", brand.toJson());
                //}
            }
        } catch (DatalakeStorageException ex) {
            LogManager.getLogger(BrandService.class).error(String.format("BrandService : Datalake storage exception %s", ex.getMessage()));
        }
        return create;
    }



}
