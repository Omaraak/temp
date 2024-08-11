package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    private final ProductService productService;
    private final MerchantService merchantService;

    public String addMerchantStock(MerchantStock merchantStock) {
        for (Product product : productService.getProducts()) {
            if (product.getId() == merchantStock.getProductID()) {
                for (Merchant merchant : merchantService.getMerchants()) {
                    if (merchant.getId() == merchantStock.getMerchantID()) {
                        merchantStocks.add(merchantStock);
                        return "Merchant stock added successfully";
                    }
                }
                return "Merchant not found";
            }
        }
        return "Product not found";

    }

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public MerchantStock getMerchantStock(int productId, int merchantId) {
        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getProductID() == productId && merchantStock.getMerchantID() == merchantId) {
                return merchantStock;
            }
        }
        return null;
    }

    public boolean updateMerchantStock(MerchantStock merchantStock, int id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId() == id) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(int id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId() == id) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean restockProduct(int productId, int merchantId, int amount) {
        MerchantStock merchantStock = getMerchantStock(productId, merchantId);
        if (merchantStock == null) {
            return false;
        }
        merchantStock.setStock(merchantStock.getStock() + amount);
        return true;
    }
}
