package cn.jeremy.marry.service.impl;

import cn.jeremy.marry.bean.Comment;
import cn.jeremy.marry.dao.ContentMapper;
import cn.jeremy.marry.model.Content;
import cn.jeremy.marry.model.ContentExample;
import cn.jeremy.marry.service.ContentService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("contentService")
public class ContentServiceImpl implements ContentService
{
    @Resource
    ContentMapper contentMapper;

    @Override
    public int insert(Content record)
    {
        try
        {
            return contentMapper.insert(record);
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    @Override
    public int insert(String nickname, String headicon, String openid, String content)
    {
        Content record = new Content();
        record.setContent(content);
        record.setCreateTime(new Date());
        record.setHeadicon(headicon);
        record.setOpenid(openid);
        record.setNickname(nickname);

        return insert(record);
    }

    @Override
    public List<Comment> selectAllContent()
    {
        ContentExample contentExample = new ContentExample();
        contentExample.setOrderByClause("create_time desc");
        List<Content> contents = contentMapper.selectByExampleWithBLOBs(contentExample);
        if (contents != null && contents.size() > 0)
        {
            List<Comment> list = new ArrayList<>(contents.size());
            for (Content content : contents)
            {
                Comment comment = new Comment();
                comment.setContent(content.getContent());
                comment.setHeadicon(content.getHeadicon());
                comment.setNickname(content.getNickname());
                comment.setOpenid(content.getOpenid());
                Date date = new Date(System.currentTimeMillis());
                String pattern = "yyyy/MM/dd HH时mm分ss秒";
                SimpleDateFormat
                    sdf = new SimpleDateFormat(pattern);
                String datestr = sdf.format(content.getCreateTime());
                comment.setCreateTime(datestr);
                list.add(comment);
            }
            return list;
        }
        return new ArrayList<>();
    }
}
