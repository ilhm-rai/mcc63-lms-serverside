/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.repository;

import co.id.mii.mcc63lmsserverside.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Agung
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query(value = "SELECT * FROM category LIMIT ?1", nativeQuery = true)
  List<Category> getCategoryLimit(Integer limit);
}
