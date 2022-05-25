package com.example.GroceryStore.config;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.GroceryStore.entity.ProductCategory;
import com.example.GroceryStore.entity.ProductSubCategory;
import com.example.GroceryStore.repository.ProductSubCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DeflaterOutputStream;

@Configuration
@Slf4j
public class ProductSubCategoryConfig {

    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;
    @Bean
    CommandLineRunner commandLineRunner(ProductSubCategoryRepository productSubCategoryRepository) {
        return args -> {
            ProductCategory productCategory = new ProductCategory("Herbs and sweets");
            ProductSubCategory productSubCategory=new ProductSubCategory("abcd",productCategory);
            productSubCategoryRepository.save(productSubCategory);
        };
    }
    //uncomment for s3

//    @Bean
//    public void uploadImageToS3AndUpdateJson(){
//        JSONParser jsonParser = new JSONParser();
//
//        String filePath="/Users/akshay.lalwani/Desktop/grocery complete data/Snacks & Yummies/SnacksAndYummies.json";
//        String updatedFilePath="/Users/akshay.lalwani/Desktop/grocery complete data/Snacks & Yummies/CompleteSnacksAndYummies.json";
//        String dirName="Snacks & Yummies/";
//        String category="Snacks & Yummies";
//        try (FileReader reader = new FileReader(filePath))
//        {
//            //Read JSON file
//            Object obj = jsonParser.parse(reader);
//
//            JSONArray employeeList = (JSONArray) obj;
//            System.out.println(employeeList.size());
//
//            JSONArray productList = new JSONArray();
//
//
//
//
//
//
//
//
//
//
//            for (int i=0;i<employeeList.size();i++){
//                {
//                    String bigImage=getJsonProperty("big_image", (JSONObject)  employeeList.get(i) );
//                    String smallImage=getJsonProperty("small_image", (JSONObject)  employeeList.get(i) );
//                    String productName=getJsonProperty("name", (JSONObject)  employeeList.get(i) );
//                    String price=getJsonProperty("price", (JSONObject) employeeList.get(i) );
//                    String description=getJsonProperty("description", (JSONObject) employeeList.get(i) );
//                    String discountedPrice=getJsonProperty("discounted_price", (JSONObject) employeeList.get(i) );
//                    String expiry=getJsonProperty("expiry", (JSONObject) employeeList.get(i) );
//                    String ingridients=getJsonProperty("ingridients", (JSONObject) employeeList.get(i) );
//                    String superCategory=getJsonProperty("sub_category", (JSONObject) employeeList.get(i));
//                    String subcategory=getJsonProperty("super_category", (JSONObject) employeeList.get(i));
//                    String brand=getJsonProperty("brand", (JSONObject) employeeList.get(i));
//                    String weight=getJsonProperty("weight", (JSONObject) employeeList.get(i));
//                    String title=getJsonProperty("title", (JSONObject) employeeList.get(i));
//
//                    try(InputStream in = new URL(smallImage).openStream()){
//                        File file = new File(productName.replaceAll("[^a-zA-Z0-9]", "")+".jpg");
//                        try(OutputStream outputStream = new FileOutputStream(file)
//
//                        ){
//                            IOUtils.copy(in, outputStream);
//                        } catch (FileNotFoundException e) {
//                            // handle exception here
//                        } catch (IOException e) {
//                            // handle exception here
//                        }
//                        System.out.println("file.getName()"+file.getName());
//                        s3Client.putObject(new PutObjectRequest(bucketName,dirName+file.getName(),file));
//                        JSONObject products = new JSONObject();
//
//                        products.put("id",i);
//                        products.put("s3url",s3Client.getUrl(bucketName, dirName+file.getName()).toExternalForm());
//                        products.put("name",productName);
//                        products.put("price",price);
//                        products.put("category",category);
//                        products.put("sub_category",subcategory);
//                        products.put("super_category",superCategory);
//                        products.put("brand",brand);
//                        products.put("ingredients",ingridients);
//                        products.put("description",description);
//                        products.put("small_image",smallImage);
//                        products.put("big_image",bigImage);
//                        products.put("weight",weight);
//                        products.put("title",title);
//                        products.put("discounted_price",discountedPrice);
//                        products.put("expiry",expiry);
//
//                        productList.add(products);
//
//                    } catch (MalformedURLException e) {
//                        throw new RuntimeException(e);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//            try (FileWriter file = new FileWriter(updatedFilePath)) {
//                //We can write any JSONArray or JSONObject instance to the file
//                file.write(productList.toJSONString());
//                file.flush();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//
//    }
//    public static String getJsonProperty(String key,JSONObject jsonObj) {
//        Object keyvalue = jsonObj.get(key);
//        return keyvalue.toString();
//    }

}
