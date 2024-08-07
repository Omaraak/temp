package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    ArrayList<User> users = new ArrayList<>();

    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean updateUser(User user, int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public  String buy(int userId, int productId, int merchantId) {
        for (User user : users) {
            if (user.getId() == userId) {
                for (Merchant merchant : merchantService.getMerchants()) {
                    if (merchant.getId() == merchantId) {
                        for (Product product : productService.getProducts()) {
                            if (product.getId() == productId) {
                                for (MerchantStock merchantStock : merchantStockService.getMerchantStocks()) {
                                    if (merchantStock.getMerchantID() == merchantId && merchantStock.getProductID() == productId) {
                                        if (user.getBalance() < product.getPrice()) {
                                            return "Balance to low";
                                        }
                                        else if (merchantStock.getStock() <= 0) {
                                            return "Out of stock";
                                        }
                                        merchantStock.setStock(merchantStock.getStock() - 1);
                                        user.setBalance(user.getBalance() - product.getPrice());
                                        product.setBoughtDate(LocalDate.now());
                                        user.getPurchasedProducts().add(product);
                                        return "Successfully bought product";
                                    }
                                }
                            }
                            return "Product not found";
                        }
                    }
                }
                return "Merchant not found";
            }
        }
        return "User not found";
    }

    public boolean addToCart(int userId, int productId, int merchantId, int quantity) {
        for (User user : users) {
            if (user.getId() == userId) {
                for (int i = 0; i < quantity; i++) {
                    if (productService.getProduct(productId) != null && merchantService.getMerchant(merchantId) != null){
                        user.getCartP().add(productService.getProduct(productId));
                        user.getCartM().add(merchantService.getMerchant(merchantId));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public String checkout(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                if (user.getCartP().isEmpty()) {
                    return "Cart is empty";
                }
                double totalPrice = 0;
                for (Product product : user.getCartP()) {
                    totalPrice += product.getPrice();
                }
                if (totalPrice > user.getBalance()) {
                    return "total price exceeded user balance";
                }
                for (int i = 0; i < user.getCartP().size(); i++) {
                    buy(userId, user.getCartP().get(i).getId(), user.getCartM().get(i).getId());
                }
                user.getCartP().clear();
                user.getCartM().clear();
                return "Successfully bought all items in cart";
            }
        }
        return "User not found";
    }

    public ArrayList<Product> history(int userId){
        for (User user : users) {
            if (user.getId() == userId) {
                return user.getPurchasedProducts();
            }
        }
        return null;
    }

    public String returnProduct(int userId, int productId, int merchantId) {
        MerchantStock temp = merchantStockService.getMerchantStock(productId, merchantId);
        for (User user : users) {
            if (user.getId() == userId) {
                for (Product product : user.getPurchasedProducts()) {
                    if (product.getId() == productId) {
                        user.setBalance(user.getBalance() + product.getPrice());
                        temp.setStock(temp.getStock() + 1);
                        return "Successfully returned product";
                    }
                }
                return "You haven't purchased the product yet";
            }
        }
        return "User not found";
    }
}

