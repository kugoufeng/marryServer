package cn.jeremy.marry.service;

import cn.jeremy.marry.model.Bless;
import java.util.List;

public interface BlessService
{
    int insert(Bless record);

    int insert(String openid, String headicon, String nickname);

    List<Bless> selectAllBless();
}
