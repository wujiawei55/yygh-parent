package com.lc.yygh.hosp.repository;

import com.lc.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName HpsipitalRepository
 * Description
 * Create by lujun
 * Date 2021/4/28 14:18
 */
@Repository
public interface HpsipitalRepository  extends MongoRepository<Hospital,String> {
    //根据医院编码查询一个医院
    Hospital findByHoscode(String hoscode);

    List<Hospital> findByHosnameLike(String hosname);
}
