package com.example.GroceryStore;

import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.entity.Brand;
import com.example.GroceryStore.entity.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Utils {

    public static String getJsonProperty(String key, JSONObject jsonObj) {
        Object keyvalue = jsonObj.get(key);
        return keyvalue.toString();
    }

    public static JSONArray createJsonArrayFromSet(Set<String> brands,String columnName) {
        List<String> brandStringList = new ArrayList<>(brands);
        JSONArray array = new JSONArray();
        for (int i=0;i<brandStringList.size();i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(columnName,i);
            jsonObject.put("name",brandStringList.get(i));
            array.add(jsonObject);
        }
        return  array;
    }
    public static void writeInJsonFile(String filePathToWrite,JSONArray list){
        try (FileWriter file = new FileWriter(filePathToWrite)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(list.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ProductDto convertProductToProductDto(Product product) {
        ProductDto productDto=new ProductDto();
        productDto.setPrice(product.getPrice());
        productDto.setDiscountedPrice(product.getDiscountedPrice());
        productDto.setName(product.getName());
        productDto.setS3ImageUrl(product.getS3ImageUrl());
        productDto.setId(product.getProduct_id());
        productDto.setQrCode(product.getQrCode());
        return productDto;
    }

    public static int optionalToValue(Optional<Integer> integer){
        if(integer.isPresent())
            return integer.get();
        else return 0;
    }
    public static Page<ProductDto> getPageFromList(Pageable paging,List <ProductDto> returningList){
        int start = Math.min((int)paging.getOffset(), returningList.size());
        int end = Math.min((start + paging.getPageSize()), returningList.size());
        Page<ProductDto> pd=new PageImpl<>(returningList.subList(start,end),paging,returningList.size());
        return pd;
    }
}