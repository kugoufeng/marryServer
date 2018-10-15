package cn.jeremy.marry.service;

import cn.jeremy.marry.bean.Comment;
import cn.jeremy.marry.model.Content;
import java.util.List;

public interface ContentService
{
    int insert(Content record);

    int insert(String nickname, String headicon, String openid, String content);

    List<Comment> selectAllContent();

}
