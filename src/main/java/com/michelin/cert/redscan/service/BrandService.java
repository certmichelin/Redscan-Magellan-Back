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

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import org.springframework.stereotype.Service;


/**
 * Brand service.
 *
 * @author Maxime ESCOURBIAC
 * @author Axel REMACK
 */
@Service
public class BrandService {

  /**
   * Default constructor.
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
    } catch (DatalakeStorageException ex) {
      LogManager.getLogger(BrandService.class).error(String.format("BrandService : Datalake storage exception %s", ex.getMessage()));
    }
    return brands;
  }

  /**
   * Get all brands with pagination.
   *
   * @param page Page number.
   * @param size Number of brands in each page.
   * @return All brands in a specific page.
   */
  public List<Brand> findAll(String page, String size) {
    LogManager.getLogger(BrandService.class).info("BrandService : Get brands with pagination");
    List<Brand> brands = null;
    try {
      brands = (new Brand()).findAll(page, size);
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
    boolean created = false;
    try {
      created = brand.create();
      if (created && brand.getLastScanDate() == null) {
        brand.setLastScanDate(new Date());
        created = brand.upsert();
      }
    } catch (DatalakeStorageException ex) {
      LogManager.getLogger(BrandService.class).error(String.format("BrandService : Datalake storage exception %s", ex.getMessage()));
    }
    return created;
  }

  /**
   * Delete a brand.
   *
   * @param brand Brand to delete.
   * @return True if the entity is deleted.
   */
  public boolean delete(Brand brand) {
    LogManager.getLogger(BrandService.class).info(String.format("BrandService : Delete brand %s", (brand != null) ? brand.getName() : "null"));
    boolean success = false;
    try {
      brand = brand.find();
      success = (brand != null) && (brand.delete());
    } catch (DatalakeStorageException ex) {
      LogManager.getLogger(BrandService.class).error(String.format("BrandService : Datalake storage exception %s", ex.getMessage()));
    }
    return success;
  }

  /**
   * Update Brand.
   *
   * @param brand Brand to update.
   * @return True if the update succeed.
   */
  public boolean update(Brand brand) {
    LogManager.getLogger(BrandService.class).info(String.format("BrandService : Update brand %s", (brand != null) ? brand.toJson() : "null"));
    boolean success = false;
    try {
      success = (brand.find() != null && brand.upsert());
    } catch (DatalakeStorageException ex) {
      LogManager.getLogger(BrandService.class).error(String.format("BrandService : Datalake storage exception %s", ex.getMessage()));
    }
    return success;
  }

}
