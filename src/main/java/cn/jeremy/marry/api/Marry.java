package cn.jeremy.marry.api;

import cn.jeremy.marry.bean.Code2SessionBean;
import cn.jeremy.marry.bean.Comment;
import cn.jeremy.marry.dao.ContentMapper;
import cn.jeremy.marry.model.Bless;
import cn.jeremy.marry.model.Content;
import cn.jeremy.marry.service.BlessService;
import cn.jeremy.marry.service.ContentService;
import cn.jeremy.marry.utils.HttpClientUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/marry")
public class Marry
{
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static Logger LOGGER = LoggerFactory.getLogger(Marry.class);

    private final String baseUrl = "https://www.kugoufeng.com/static/images/";

    private static final String baseListUrl = "https://www.kugoufeng.com/static/images/list/";

    @Resource
    BlessService blessService;

    @Resource
    ContentService contentService;

    @RequestMapping("foo")
    String foo(@RequestParam(value = "openid") String openid, @RequestParam(value = "headicon") String headicon,
        @RequestParam(value = "nickname") String nickname, @RequestParam(value = "content") String content)
    {
        int insert = contentService.insert(nickname, headicon, openid, content);
        return "你的评论!";
    }

    @RequestMapping("getCommentList")
    String getCommentList()
        throws JsonProcessingException
    {
        List<Comment> contents = contentService.selectAllContent();
        if (contents == null)
        {
            return null;
        }
        return MAPPER.writeValueAsString(contents);
    }

    @RequestMapping("bless")
    String bless(@RequestParam(value = "openid") String openid, @RequestParam(value = "headicon") String headicon,
        @RequestParam(value = "nickname") String nickname)
    {
        int insert = blessService.insert(openid, headicon, nickname);
        return "你的祝福!";
    }

    @RequestMapping("getBlessList")
    String getBlessList()
        throws JsonProcessingException
    {
        List<Bless> blesses = blessService.selectAllBless();
        if (blesses == null)
        {
            return null;
        }
        return MAPPER.writeValueAsString(blesses);
    }

    @RequestMapping("getSwiterImages")
    String getSwiterImages()
        throws JsonProcessingException
    {
        List<String> urls = new ArrayList<>();
        String baseUrl = this.baseUrl.concat("swiper/");
        for (int i = 1; i <= 8; i++)
        {
            urls.add(baseUrl.concat(i + ".jpg"));
        }
        return MAPPER.writeValueAsString(urls);
    }

    @RequestMapping("getMusicUrl")
    String getMusicUrl()
    {
        return "http://dl.stream.qqmusic.qq.com/C100000gSW7F2IKT1w.m4a?fromtag=46";
    }

    @RequestMapping("getOpenid")
    String getOpenid(@RequestParam(value = "code") String code)
    {
        String url =
            "https://api.weixin.qq.com/sns/jscode2session?appid=wx3ee0fceb7cafbf1e&secret" +
                "=bdc1441294265e42a7f154a52d6bce22&js_code=JSCODE" +
                "&grant_type" +
                "=authorization_code";
        url = url.replace("JSCODE", code);
        Code2SessionBean code2SessionBean = HttpClientUtils.httpGet(url, Code2SessionBean.class);
        if (null != code2SessionBean)
        {
            return code2SessionBean.getOpenid();
        }
        return null;
    }

    @RequestMapping("getListImages")
    String getListImages()
        throws JsonProcessingException
    {
        List<ListImage> urls = new ArrayList<>();
        urls.add(new ListImage(ListPicType.XS));
        urls.add(new ListImage(ListPicType.XA));
        urls.add(new ListImage(ListPicType.HL));
        urls.add(new ListImage(ListPicType.SH));
        return MAPPER.writeValueAsString(urls);
    }

    @RequestMapping("getDetailImages")
    String getDetailImages(@RequestParam(value = "type") String type)
        throws JsonProcessingException
    {
        List<String> urls = new ArrayList<>();
        String baseUrl = this.baseUrl;
        for (int i = 1; i <= 6; i++)
        {
            urls.add(baseUrl.concat(type).concat("/" + i + ".jpg"));
        }
        return MAPPER.writeValueAsString(urls);
    }

    @RequestMapping("getInvitation")
    String getInvitation()
        throws JsonProcessingException
    {
        Invitation invitation = new Invitation();
        invitation.setCover(this.baseUrl.concat("cover.jpg"));
        invitation.setMr("冯");
        invitation.setMiss("刘");
        invitation.setYangli("2018年10月1日");
        invitation.setYinli("戊戌年八月廿二");
        invitation.setAddress("潢川县邹家湾村胜利园林主题庄园");
        invitation.setLatitude("32.099523");
        invitation.setLongitude("115.052922");
        invitation.setScale("15");
        return MAPPER.writeValueAsString(invitation);
    }

    enum ListPicType
    {
        //相识
        XS("相识", "xs", baseListUrl.concat("1.jpg")),
        XA("相爱", "xa", baseListUrl.concat("2.jpg")),
        HL("婚礼", "hl", baseListUrl.concat("3.jpg")),
        SH("生活", "sh", baseListUrl.concat("4.jpg")),;

        private String name;

        private String type;

        private String url;

        ListPicType(String name, String type, String url)
        {
            this.name = name;
            this.type = type;
            this.url = url;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }
    }

    class ListImage
    {
        private String imageUrl;

        private String name;

        private String type;

        public ListImage()
        {
        }

        public ListImage(ListPicType picType)
        {
            this.imageUrl = picType.getUrl();
            this.name = picType.getName();
            this.type = picType.getType();
        }

        public String getImageUrl()
        {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl)
        {
            this.imageUrl = imageUrl;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        @Override
        public String toString()
        {
            final StringBuffer sb = new StringBuffer("ListImage{");
            sb.append("imageUrl='").append(imageUrl).append('\'');
            sb.append(", name='").append(name).append('\'');
            sb.append(", type='").append(type).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    class Invitation
    {
        private String cover;

        private String mr;

        private String miss;

        private String yangli;

        private String yinli;

        private String address;

        private String latitude;

        private String longitude;

        private String scale;

        public String getCover()
        {
            return cover;
        }

        public void setCover(String cover)
        {
            this.cover = cover;
        }

        public String getMr()
        {
            return mr;
        }

        public void setMr(String mr)
        {
            this.mr = mr;
        }

        public String getMiss()
        {
            return miss;
        }

        public void setMiss(String miss)
        {
            this.miss = miss;
        }

        public String getYangli()
        {
            return yangli;
        }

        public void setYangli(String yangli)
        {
            this.yangli = yangli;
        }

        public String getYinli()
        {
            return yinli;
        }

        public void setYinli(String yinli)
        {
            this.yinli = yinli;
        }

        public String getAddress()
        {
            return address;
        }

        public void setAddress(String address)
        {
            this.address = address;
        }

        public String getLatitude()
        {
            return latitude;
        }

        public void setLatitude(String latitude)
        {
            this.latitude = latitude;
        }

        public String getLongitude()
        {
            return longitude;
        }

        public void setLongitude(String longitude)
        {
            this.longitude = longitude;
        }

        public String getScale()
        {
            return scale;
        }

        public void setScale(String scale)
        {
            this.scale = scale;
        }
    }

}
