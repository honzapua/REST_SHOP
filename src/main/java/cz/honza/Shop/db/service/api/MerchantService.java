package cz.honza.Shop.db.service.api;

import cz.honza.Shop.domain.Merchant;
import org.springframework.lang.Nullable;

import java.util.List;

public interface MerchantService {
    List<Merchant> getMerchants();

    @Nullable
    Merchant get(int id);

    @Nullable
    Integer add(Merchant merchant);   //return generated id from DB after merchant is created
}
