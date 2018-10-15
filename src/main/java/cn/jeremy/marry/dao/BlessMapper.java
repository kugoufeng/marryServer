package cn.jeremy.marry.dao;

import cn.jeremy.marry.model.Bless;
import cn.jeremy.marry.model.BlessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlessMapper {
    long countByExample(BlessExample example);

    int deleteByExample(BlessExample example);

    int insert(Bless record);

    int insertSelective(Bless record);

    List<Bless> selectByExample(BlessExample example);

    int updateByExampleSelective(@Param("record") Bless record, @Param("example") BlessExample example);

    int updateByExample(@Param("record") Bless record, @Param("example") BlessExample example);
}