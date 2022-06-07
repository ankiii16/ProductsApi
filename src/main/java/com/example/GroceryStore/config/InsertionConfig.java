package com.example.GroceryStore.config;

import com.example.GroceryStore.Utils;
import com.example.GroceryStore.entity.*;
import com.example.GroceryStore.repository.BrandRepository;
import com.example.GroceryStore.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

@Configuration
@Slf4j
public class InsertionConfig {
    @Bean
    CommandLineRunner categoryCommandLine(
            CategoryRepository categoryRepository,
            BrandRepository brandRepository
    ) {
        return args -> {
//            saveAllCategories(categoryRepository,brandRepository);
        };
    }


    private void saveAllCategories(CategoryRepository categoryRepository,BrandRepository brandRepository) {
        String filePath="/Users/akshay.lalwani/Desktop/grocery complete data/CompleteJson.json";
        JSONParser jsonParser = new JSONParser();
        HashMap<String,HashMap<String,Set<String>>> categoriesHashMap=new HashMap<>();
        HashMap<String,List<Product>> productCategoryHashMap=new HashMap<>();
        HashMap<String,List<Product>> productSuperCategoryHashMap=new HashMap<>();
        HashMap<String,List<Product>> productSubCategoryHashMap=new HashMap<>();
        List<Category> allCompleteCategories=new ArrayList<>();
        HashMap< String,HashMap<String,List<Product>>> productsByBrandCategoryHashMap=new HashMap<>();
        Set<Brand> allCompleteBrandsWithProduct=new HashSet<>();
        try (FileReader reader = new FileReader(filePath)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray productList = (JSONArray) obj;
            for (int i=0;i<productList.size();i++){
                String superCategory=Utils.getJsonProperty("super_category", (JSONObject) productList.get(i));
                String category=Utils.getJsonProperty("category", (JSONObject) productList.get(i));
                String subcategory=Utils.getJsonProperty("sub_category", (JSONObject) productList.get(i));


                String s3url=Utils.getJsonProperty("s3url", (JSONObject)  productList.get(i) );
                String productName=Utils.getJsonProperty("name", (JSONObject)  productList.get(i) );
                String price=Utils.getJsonProperty("price", (JSONObject) productList.get(i) );
                String discountedPrice=Utils.getJsonProperty("discounted_price", (JSONObject) productList.get(i) );
                String descriptionAboutProduct=Utils.getJsonProperty("description", (JSONObject) productList.get(i) );
                String expiry=Utils.getJsonProperty("expiry", (JSONObject) productList.get(i) );
                String ingredients=Utils.getJsonProperty("ingredients", (JSONObject) productList.get(i) );
                String weight=Utils.getJsonProperty("weight", (JSONObject) productList.get(i));
                String title=Utils.getJsonProperty("title", (JSONObject) productList.get(i));
                String brand=Utils.getJsonProperty("brand", (JSONObject) productList.get(i));

                Description description=getProductDescription(title,weight,ingredients,expiry,descriptionAboutProduct);
                Product product=getProduct(productName,price,discountedPrice,s3url,description);

                if(productsByBrandCategoryHashMap.containsKey(category)){
                    if(productsByBrandCategoryHashMap.get(category).containsKey(brand)){
                        productsByBrandCategoryHashMap.get(category).get(brand).add(product);
                    }
                    else {
                        productsByBrandCategoryHashMap.get(category).put(brand,new ArrayList<>());
                        productsByBrandCategoryHashMap.get(category).get(brand).add(product);
                    }
                }
                else{
                    productsByBrandCategoryHashMap.put(category,new HashMap<>());
                    productsByBrandCategoryHashMap.get(category).put(brand,new ArrayList<>());
                    productsByBrandCategoryHashMap.get(category).get(brand).add(product);
                }
                if(categoriesHashMap.containsKey(category)){
                    if (!categoriesHashMap.get(category).containsKey(superCategory)) {
                        //super category does not exist
                        categoriesHashMap.get(category).put(superCategory, new HashSet<>());
                    }
                }
                else{
                    categoriesHashMap.put(category,new HashMap<>());
                    categoriesHashMap.get(category).put(superCategory,new HashSet<>());
                }
                if(!subcategory.equalsIgnoreCase("none"))
                    categoriesHashMap.get(category).get(superCategory).add(subcategory);

                if(productCategoryHashMap.containsKey(category)){
                    productCategoryHashMap.get(category).add(product);
                }
                else{
                    productCategoryHashMap.put(category,new ArrayList<>());
                    productCategoryHashMap.get(category).add(product);
                }
                if(productSuperCategoryHashMap.containsKey(superCategory)){
                    productSuperCategoryHashMap.get(superCategory).add(product);
                }
                else{
                    productSuperCategoryHashMap.put(superCategory,new ArrayList<>());
                    productSuperCategoryHashMap.get(superCategory).add(product);
                }
                if(productSubCategoryHashMap.containsKey(subcategory)){
                    productSubCategoryHashMap.get(subcategory).add(product);
                }
                else{
                    productSubCategoryHashMap.put(subcategory,new ArrayList<>());
                    productSubCategoryHashMap.get(subcategory).add(product);
                }

            }

            for (Map.Entry<String,HashMap<String,Set<String>>> category : categoriesHashMap.entrySet()) {
                Category createdCategory=new Category();
                String nameOfCategory=category.getKey();
                createdCategory.setName(nameOfCategory);

                for (Map.Entry<String, Set<String>> superCategory : categoriesHashMap.get(nameOfCategory).entrySet()) {
                    SuperCategory createdSuperCategory=new SuperCategory();
                    String nameOfSuperCategory = superCategory.getKey();
                    createdSuperCategory.setName(nameOfSuperCategory);
                    Set<String> subCatOfSupCat = superCategory.getValue();
                    for (String subCat:subCatOfSupCat
                    ) {
                        SubCategory subCategory = new SubCategory();
                        subCategory.setName(subCat);
                        List<Product> products = productSubCategoryHashMap.get(subCat);
                        subCategory.getProducts().addAll(products);
                        createdSuperCategory.getSubCategories().add(subCategory);
                    }
                    List<Product> products = productSuperCategoryHashMap.get(nameOfSuperCategory);
                    createdSuperCategory.getProducts().addAll(products);

                    createdCategory.getSuperCategories().add(createdSuperCategory);
                }
                List<Product> products = productCategoryHashMap.get(nameOfCategory);

                createdCategory.getProducts().addAll(products);
                HashMap<String, List<Product>> stringListHashMap = productsByBrandCategoryHashMap.get(nameOfCategory);
                for (Map.Entry<String, List<Product>> brands : stringListHashMap.entrySet()) {
                    Brand b=new Brand();
                    String brandName=brands.getKey();
                    b.setName(brandName);
                    b.setProducts(brands.getValue());
                    createdCategory.getBrands().add(b);
                }
                allCompleteCategories.add(createdCategory);
            }
            categoryRepository.saveAll(allCompleteCategories);
        }
        catch (Exception e){
            System.out.println("Exception is "+ e);
        }

    }

    private SuperCategory getSuperCategory(String superCategory) {
        SuperCategory superCategory1=new SuperCategory();
        superCategory1.setName(superCategory);
        return  superCategory1;
    }

    private Product getProduct(String productName,String price,String discountedPrice,String s3url,Description description){
        Product product=new Product();
        product.setName(productName);
        product.setName(productName);
        if(price=="" && discountedPrice!=""){
            product.setPrice(0);
            product.setDiscountedPrice(Float.parseFloat(discountedPrice));
        }
        else if (price=="" && discountedPrice==""){
            product.setPrice(0);
            product.setDiscountedPrice(0);
        }
        else{
            product.setPrice(Float.parseFloat(price));
            product.setDiscountedPrice(0);
        }
        product.setS3ImageUrl(s3url);
        product.setQrCode(BigDecimal.valueOf(0));
        product.setDescription(description);
        return  product;
    }
    private Description getProductDescription(String title, String weight, String ingredients, String expiry, String descriptionAboutProduct) {
        Description d=new Description();
        if(descriptionAboutProduct.equals(""))
            descriptionAboutProduct="No Description Found";
        d.setDescriptionAboutProduct(descriptionAboutProduct);
        d.setIngredients(ingredients);
        d.setTitle(title);
        d.setExpiry(expiry);
        d.setWeight(weight);
        return  d;

    }
}
