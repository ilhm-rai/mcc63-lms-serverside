/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.repository;

import co.id.mii.mcc63lmsserverside.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Agung
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}