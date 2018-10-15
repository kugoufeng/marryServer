package cn.jeremy.marry.bean;

import java.io.Serializable;

public class Code2SessionBean implements Serializable
{
    private static final long serialVersionUID = 3743049264131533411L;

    private String openid;
    private String session_key;
    private String unionid;
    private int errcode;
    private String errMsg;

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }

    public String getSession_key()
    {
        return session_key;
    }

    public void setSession_key(String session_key)
    {
        this.session_key = session_key;
    }

    public String getUnionid()
    {
        return unionid;
    }

    public void setUnionid(String unionid)
    {
        this.unionid = unionid;
    }

    public int getErrcode()
    {
        return errcode;
    }

    public void setErrcode(int errcode)
    {
        this.errcode = errcode;
    }

    public String getErrMsg()
    {
        return errMsg;
    }

    public void setErrMsg(String errMsg)
    {
        this.errMsg = errMsg;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("Code2SessionBean{");
        sb.append("openid='").append(openid).append('\'');
        sb.append(", session_key='").append(session_key).append('\'');
        sb.append(", unionid='").append(unionid).append('\'');
        sb.append(", errcode=").append(errcode);
        sb.append(", errMsg='").append(errMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
