package com.example.GroceryStore.config;


import com.amazonaws.services.s3.AmazonS3;
import com.example.GroceryStore.entity.*;
import com.example.GroceryStore.repository.ProductDescriptionRepository;
import com.example.GroceryStore.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@Slf4j
public class ProductDetailConfig {

//    @Value("${application.bucket.name}")
//    private String bucketName;
//    @Autowired
//    private AmazonS3 s3Client;

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository,
                                        ProductDescriptionRepository productDescriptionRepository
    ) {
        return args -> {
//            insertDataIntoDb(productDescriptionRepository);
//            makeOneCompleteJson();
        };
    }

    private void insertDataIntoDb( ProductDescriptionRepository productDescriptionRepository){
        String filePath="/Users/akshay.lalwani/Desktop/grocery complete data/CompleteJson.json";
        List<ProductDescription> productDescriptions=new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray productList = (JSONArray) obj;
            System.out.println(productList.size());
            for (int i=0;i<productList.size();i++){


                String bigImage=getJsonProperty("big_image", (JSONObject)  productList.get(i) );
                String s3url=getJsonProperty("s3url", (JSONObject)  productList.get(i) );
                String productName=getJsonProperty("name", (JSONObject)  productList.get(i) );
                String price=getJsonProperty("price", (JSONObject) productList.get(i) );
                String description=getJsonProperty("description", (JSONObject) productList.get(i) );
                String discountedPrice=getJsonProperty("discounted_price", (JSONObject) productList.get(i) );
                String expiry=getJsonProperty("expiry", (JSONObject) productList.get(i) );
                String ingredients=getJsonProperty("ingredients", (JSONObject) productList.get(i) );
                String superCategory=getJsonProperty("super_category", (JSONObject) productList.get(i));
                String category=getJsonProperty("category", (JSONObject) productList.get(i));
                String subcategory=getJsonProperty("sub_category", (JSONObject) productList.get(i));
                String brand=getJsonProperty("brand", (JSONObject) productList.get(i));
                String weight=getJsonProperty("weight", (JSONObject) productList.get(i));
                String title=getJsonProperty("title", (JSONObject) productList.get(i));


                Product p=new Product();
                p.setName(productName);
                p.setS3ImageUrl(s3url);
                if(price=="" && discountedPrice!=""){
                    p.setPrice(0);
                    p.setDiscountedPrice(Float.parseFloat(discountedPrice));
                }
                else if (price=="" && discountedPrice==""){
                    p.setPrice(0);
                    p.setDiscountedPrice(0);
                }
                else{
                    p.setPrice(Float.parseFloat(price));
                    p.setDiscountedPrice(0);
                }

                p.setQrCode(BigDecimal.valueOf(0));


                Description d=new Description();

                if(description.equals(""))
                    description="No Description Found";
                d.setDescriptionAboutProduct(description);
                d.setIngredients(ingredients);
                d.setTitle(title);
                d.setExpiry(expiry);
                d.setWeight(weight);
                Brand b=null;
                Category c=null;
                for (int j = 0; j< productDescriptions.size(); j++){
                    Brand foundBrand= productDescriptions.get(j).getBrand();
                    if(foundBrand.getName().equals(brand))
                    {
                        b=foundBrand;
                        break;
                    }
                }
                if(b==null){
                    b=new Brand();
                    b.setName(brand);
                }
                for (int j = 0; j< productDescriptions.size(); j++){
                    Category foundCategory= productDescriptions.get(j).getCategory();
                    if(foundCategory.getName().equals(brand))
                    {
                        c=foundCategory;
                        break;
                    }
                }
                if(c==null){
                    c=new Category();
                    c.setName(category);
                }

                SubCategory subCategory=null;
                for (int j = 0; j< productDescriptions.size(); j++){
                    SubCategory foundCategory= productDescriptions.get(j).getSubCategory();
                    if(foundCategory.getName().equals(brand))
                    {
                        subCategory=foundCategory;
                        break;
                    }
                }
                if(subCategory==null){
                    subCategory=new SubCategory();
                    subCategory.setName(subcategory);
                }

                SuperCategory supCategory=null;
                for (int j = 0; j< productDescriptions.size(); j++){
                    SuperCategory foundCategory= productDescriptions.get(j).getSuperCategory();
                    if(foundCategory.getName().equals(brand))
                    {
                        supCategory=foundCategory;
                        break;
                    }
                }
                if(supCategory==null){
                    supCategory=new SuperCategory();
                    supCategory.setName(superCategory);
                }
                ProductDescription productDescription=new ProductDescription();
                productDescription.setDescription(d);
                productDescription.setBrand(b);
                productDescription.setCategory(c);
                productDescription.setSuperCategory(supCategory);
                productDescription.setSubCategory(subCategory);
                productDescription.setProduct(p);
                productDescriptions.add(productDescription);


            }
            productDescriptionRepository.saveAll(productDescriptions);

        }
        catch (Exception e){
            System.out.println("Exception is "+ e);
        }
        finally {
            productDescriptionRepository.saveAll(productDescriptions);
        }

    }

    private void makeOneCompleteJson(){
        JSONParser jsonParser = new JSONParser();
        List<ProductDescription> productDescriptions=new ArrayList<>();
        String updatedFilePath="/Users/akshay.lalwani/Desktop/grocery complete data/CompleteJson.json";
        JSONArray productList = new JSONArray();
        List<String> filePaths=getAllFilePaths();

        for (String filePath:filePaths){
            readFile(filePath,jsonParser,productList);
        }
        writeInJsonFile(updatedFilePath,productList);
    }
    private void readFile(String filePath,JSONParser jsonParser,JSONArray addToThisList){
        try (FileReader reader = new FileReader(filePath)){
            Object obj = jsonParser.parse(reader);
            JSONArray productList = (JSONArray) obj;
            for (int i=0;i<productList.size();i++){
                String bigImage=getJsonProperty("big_image", (JSONObject)  productList.get(i) );
                String s3url=getJsonProperty("s3url", (JSONObject)  productList.get(i) );
                String productName=getJsonProperty("name", (JSONObject)  productList.get(i) );
                String price=getJsonProperty("price", (JSONObject) productList.get(i) );
                String description=getJsonProperty("description", (JSONObject) productList.get(i) );
                String discountedPrice=getJsonProperty("discounted_price", (JSONObject) productList.get(i) );
                String expiry=getJsonProperty("expiry", (JSONObject) productList.get(i) );
                String ingredients=getJsonProperty("ingredients", (JSONObject) productList.get(i) );
                String superCategory=getJsonProperty("super_category", (JSONObject) productList.get(i));
                String category=getJsonProperty("category", (JSONObject) productList.get(i));
                String subcategory=getJsonProperty("sub_category", (JSONObject) productList.get(i));
                String brand=getJsonProperty("brand", (JSONObject) productList.get(i));
                String weight=getJsonProperty("weight", (JSONObject) productList.get(i));
                String title=getJsonProperty("title", (JSONObject) productList.get(i));
                JSONObject product = new JSONObject();

                product.put("big_image",bigImage);
                product.put("s3url",s3url);
                product.put("name",productName);
                product.put("price",price);
                product.put("description",description);
                product.put("discounted_price",discountedPrice);
                product.put("expiry",expiry);
                product.put("ingredients",ingredients);
                product.put("super_category",superCategory);
                product.put("category",category);
                product.put("sub_category",subcategory);
                product.put("weight",weight);
                product.put("brand",brand);
                product.put("title",title);
                addToThisList.add(product);
            }

        }
        catch (Exception e){

        }
    }
    private List<String> getAllFilePaths(){
        List<String> filePaths=new ArrayList<>();
        filePaths.add("/Users/akshay.lalwani/Desktop/grocery complete data/Bakery, Rusks & Dairy/CompleteBiscuitsRusksAndDairy.json");
        filePaths.add("/Users/akshay.lalwani/Desktop/grocery complete data/Beauty & Hygiene/CompleteBeautyAndHygiene.json");
        filePaths.add("/Users/akshay.lalwani/Desktop/grocery complete data/Beverages/CompleteBeverages.json");
        filePaths.add("/Users/akshay.lalwani/Desktop/grocery complete data/Fruits & Vegetables/CompleteFruitsAndVegetables.json");
        filePaths.add("/Users/akshay.lalwani/Desktop/grocery complete data/Health & Ayurvedic/CompleteHealthAndAyurvedic.json");
        filePaths.add("/Users/akshay.lalwani/Desktop/grocery complete data/Household & Kitchenware/CompleteHouseholdAndKitchenware.json");
        filePaths.add("/Users/akshay.lalwani/Desktop/grocery complete data/Non Vegetarian/CompleteNonVegetarian.json");
        filePaths.add("/Users/akshay.lalwani/Desktop/grocery complete data/Rice, Flours, Lentils, Oils & Spices/CompleteRiceFloursLentilsOilsSpices.json");
        filePaths.add("/Users/akshay.lalwani/Desktop/grocery complete data/Snacks & Yummies/CompleteSnacksAndYummies.json");
        return  filePaths;
    }

    public static String getJsonProperty(String key, JSONObject jsonObj) {
        Object keyvalue = jsonObj.get(key);
        return keyvalue.toString();
    }
    public void writeInJsonFile(String filePathToWrite,JSONArray productList){
        try (FileWriter file = new FileWriter(filePathToWrite)) {
                //We can write any JSONArray or JSONObject instance to the file
                file.write(productList.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
