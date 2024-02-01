package cz.honza.Shop.db.service.impl;

import cz.honza.Shop.db.repository.MerchantRepository;
import cz.honza.Shop.db.service.api.MerchantService;
import cz.honza.Shop.domain.Merchant;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Here is usually more business logic in bigger applications
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public List<Merchant> getMerchants() {
        return merchantRepository.getAll();
    }

    @Override
    public Merchant get(int id) {
        return merchantRepository.get(id);
    }

    @Override
    public Integer add(Merchant merchant) {
        return merchantRepository.add(merchant);
    }
}