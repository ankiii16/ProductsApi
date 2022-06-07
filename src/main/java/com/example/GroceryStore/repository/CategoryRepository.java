package com.example.GroceryStore.repository;

import com.example.GroceryStore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<List<Category>> findProductsByName(String name);
    Optional<Category> findSuperCategoriesByName(String name);

    @Query(value = "select c.name from category c inner join product_by_category ps on ps.category_id=c.category_id where ps.product_id=:id",nativeQuery = true)
    Optional<String> findCategoryNameByProductId(long id);

    @Query(value = "select sup.name from super_category sup inner join product_by_super_category pSup on pSup.sup_cat_id=sup.sup_cat_id where pSup.product_id=:id",nativeQuery = true)
    Optional<String> findSuperCategoryNameByProductId(long id);

    @Query(value = "select sub.name from sub_category sub inner join product_by_sub_category pSub on pSub.sub_cat_id=sub.sub_cat_id where pSub.product_id=:id",nativeQuery = true)
    Optional<String> findSubCategoryNameByProductId(long id);


    @Query(value = "select b.name from brand b inner join product_by_brand pb on pb.brand_id=b.brand_id where pb.product_id=:id",nativeQuery = true)
    Optional<String> findBrandNameByProductId(long id);


//    @Query(value = "select d.desc_id as id,d.title as title,d.weight as weight,d.ingredients as ingredients, d.description_about_product as description, d.expiry as expiry  from description d inner join products p on p.desc_id=d.desc_id where product_id=:id",nativeQuery = true)
//    Optional<DescriptionDto> findDescriptionByProductId(long id);


}
