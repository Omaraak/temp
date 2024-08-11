package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {
    ArrayList<Merchant> merchants = new ArrayList<>();


    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);
    }

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public Merchant getMerchant(int id) {
        for (Merchant merchant : merchants) {
            if (merchant.getId() == id) {
                return merchant;
            }
        }
        return null;
    }

    public boolean updateMerchant(Merchant merchant, int id) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId() == id) {
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(int id) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId() == id) {
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }
}
