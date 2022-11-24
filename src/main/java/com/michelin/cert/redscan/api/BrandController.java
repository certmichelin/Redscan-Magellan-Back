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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Brand controller.
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
   * Default constructor.
   */
  @Autowired
  public BrandController() {  }

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
   * Get all brands with pagination.
   *
   * @param page Page number.
   * @param size Number of brands in each page.
   * @return All brands in a specific page.
   */
  @GetMapping("/{page}/{size}")
  public List<Brand> findAll(@PathVariable String page, @PathVariable String size) {
    return brandService.findAll(page, size);
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

  /**
   * Delete a brand.
   *
   * @param name Brand name.
   * @return True if the entity is deleted.
   */
  @DeleteMapping("/{name}")
  public boolean delete(@PathVariable String name) {
    Brand brand = new Brand(name);
    return brandService.delete(brand);
  }

  /**
   * Update a brand.
   *
   * @param name Brand name.
   * @return True if the entity is updated.
   */
  @PutMapping()
  public boolean update(@RequestBody Brand brand) {
    return brandService.update(brand);
  }


}
