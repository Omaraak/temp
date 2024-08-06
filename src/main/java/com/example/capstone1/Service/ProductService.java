package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {
    ArrayList<Product> products = new ArrayList<>();
    private final CategoryService categoryService;

    public boolean addProduct(Product product) {
        for (Category category : categoryService.categories){
            if (category.getId() == product.getCategoryID()){
                products.add(product);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Product getProduct(int id) {
        for (Product product : products){
            if (product.getId() == id){
                return product;
            }
        }
        return null;
    }

    public boolean updateProduct(Product product, int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }
}
