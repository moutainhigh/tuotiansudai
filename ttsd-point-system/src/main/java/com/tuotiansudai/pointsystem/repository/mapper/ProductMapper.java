package com.tuotiansudai.pointsystem.repository.mapper;


import com.tuotiansudai.pointsystem.repository.model.GoodsType;
import com.tuotiansudai.pointsystem.repository.model.ProductModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {

    void create(ProductModel productModel);

    void update(ProductModel productModel);

    List<ProductModel> findProductList(
            @Param(value = "goodsType") GoodsType goodsType
    );

    long findProductCount(
            @Param(value = "goodsType") GoodsType goodsType
    );

    ProductModel findById(long id);
}
