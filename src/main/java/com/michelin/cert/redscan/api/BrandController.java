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

package com.michelin.cert.redscan.web;

import com.michelin.cert.redscan.service.BrandService;
import com.michelin.cert.redscan.utils.models.Brand;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kong.unirest.json.JSONObject;


/**
 * Brand API controller.
 *
 * @author Maxime ESCOURBIAC
 * @author Axel REMACK
 */
@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    BrandService brandService;

    /**
     * Default constructor.s
     */
    @Autowired
    public BrandController() {
    }

    /**
     * Get all brands.
     *
     * @return All brands.
     */
    @GetMapping()
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    /**
     * Get a brand.
     *
     * @param name Brand name.
     * @return A specific brand.
     */
    @GetMapping("/{name}")
    public Brand find(@PathVariable String name) {
        Brand brand = new Brand(name);
        return brandService.find(brand);
    }


    /**
     * Create a brand.
     *
     * @param brand Brand to create.
     * @return True if the creation succeed.
     */
    @PostMapping()
    public boolean create(@RequestBody Brand brand) {
        return brandService.create(brand);
    }

}
