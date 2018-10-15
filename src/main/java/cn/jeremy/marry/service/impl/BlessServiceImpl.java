package cn.jeremy.marry.service.impl;

import cn.jeremy.marry.dao.BlessMapper;
import cn.jeremy.marry.model.Bless;
import cn.jeremy.marry.service.BlessService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("blessService")
public class BlessServiceImpl implements BlessService
{
    @Resource
    BlessMapper blessMapper;

    @Override
    public int insert(Bless record)
    {
        try
        {
            return blessMapper.insert(record);
        }
        catch (Exception e)
        {
            return -1;
        }

    }

    @Override
    public int insert(String openid, String headicon, String nickname)
    {
        Bless bless = new Bless();
        bless.setOpenid(openid);
        bless.setHeadicon(headicon);
        bless.setNickname(nickname);
        return insert(bless);
    }

    @Override
    public List<Bless> selectAllBless()
    {
        return blessMapper.selectByExample(null);
    }

}
