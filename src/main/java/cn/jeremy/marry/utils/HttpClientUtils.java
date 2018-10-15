package cn.jeremy.marry.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * HttpClient4.3工具类
 *
 * @author hang.luo
 */
public class HttpClientUtils
{
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static RequestConfig requestConfig = null;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static
    {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();

        // 忽略不存在的字段
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.setSerializationInclusion(Include.NON_NULL);
    }

    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     *
     * @param url url地址
     * @param strParam 参数
     */
    public static <T> T httpPost(String url, String strParam, Class<T> clazz)
    {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try
        {
            if (null != strParam)
            {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                try
                {
                    // 读取服务器返回过来的json字符串数据
                    String str = EntityUtils.toString(result.getEntity(), "utf-8");
                    if (clazz == String.class)
                    {
                        return (T)str;
                    }
                    return MAPPER.readValue(str, clazz);
                }
                catch (Exception e)
                {
                    logger.error("post请求提交失败:{}, e:{}", url, e);
                }
            }
        }
        catch (IOException e)
        {
            logger.error("post请求提交失败:{}, e:{}", url, e);
        }
        finally
        {
            httpPost.releaseConnection();
        }
        return null;
    }

    /**
     * 发送get请求
     *
     * @param url 路径
     */
    public static <T> T httpGet(String url, Class<T> clazz)
    {
        // get请求返回结果
        JSONObject jsonResult = null;
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        try
        {
            CloseableHttpResponse response = client.execute(request);

            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // 读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                if (clazz == String.class)
                {
                    return (T)strResult;
                }
                return MAPPER.readValue(strResult, clazz);
            }
            else
            {
                logger.error("get请求提交失败:{}", url);
            }
        }
        catch (IOException e)
        {
            logger.error("get请求提交失败:{}, e:{}" , url, e);
        }
        finally
        {
            request.releaseConnection();
        }
        return null;
    }

}
