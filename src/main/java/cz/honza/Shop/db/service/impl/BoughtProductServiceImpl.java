package cz.honza.Shop.db.service.impl;

import cz.honza.Shop.db.repository.BoughtProductRepository;
import cz.honza.Shop.db.service.api.BoughtProductService;
import cz.honza.Shop.domain.BoughtProduct;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * four classes clean code, easy to understand and extend
 * BoughtProductService, BoughtProductServiceImpl, BoughtProductRepository, BoughtProductRowmapper
 * tasks are divided to more layers
 */
@Service
public class BoughtProductServiceImpl implements BoughtProductService {
    private final BoughtProductRepository boughtProductRepository;  // bean

    public BoughtProductServiceImpl(BoughtProductRepository boughtProductRepository) {
        this.boughtProductRepository = boughtProductRepository;     // constructor injection by bean
    }

    @Override
    public void add(BoughtProduct boughtProduct) {
        boughtProductRepository.add(boughtProduct);
    }

    @Override
    public List<BoughtProduct> getAllByCustomerId(int customerId) {
        //business logic could be there before logic
        return boughtProductRepository.getAllByCustomerId(customerId);
    }
}
