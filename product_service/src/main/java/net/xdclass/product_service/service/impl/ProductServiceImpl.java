package net.xdclass.product_service.service.impl;

import net.xdclass.product_service.domain.Product;
import net.xdclass.product_service.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {

    private static Map<Integer,Product> daoMap = new HashMap<>();


    static {
        Product p1 = new Product(1,"iphone",9999,10);
        Product p2 = new Product(2,"tv",9999,10);
        Product p3 = new Product(3,"gun",9999,10);
        Product p4 = new Product(4,"girl",9999,10);

        daoMap.put(p1.getId(),p1);
        daoMap.put(p2.getId(),p2);
        daoMap.put(p3.getId(),p3);
        daoMap.put(p4.getId(),p4);

    }
    @Override
    public List<Product> listProduct() {

        Collection<Product> products = daoMap.values();
        ArrayList<Product> list = new ArrayList<>(products) ;
        return list;
    }

    @Override
    public Product findById(int id) {

        Map<String,Object> result= new HashMap<>() ;




        return

                daoMap.get(id);
    }
}
