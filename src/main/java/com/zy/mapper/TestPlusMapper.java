package com.zy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.entity.PlusTest;

public interface TestPlusMapper extends BaseMapper<PlusTest> {

    public void insertSample();
}
