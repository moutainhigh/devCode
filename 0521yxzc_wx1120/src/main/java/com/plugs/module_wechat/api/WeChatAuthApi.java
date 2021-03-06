package com.plugs.module_wechat.api;

import com.google.gson.Gson;
import com.plugs.module_wechat.constants.Constants;
import com.plugs.module_wechat.pojo.SNSUserInfo;
import com.plugs.module_wechat.pojo.WeixinOauth2Token;
import com.plugs.module_wechat.util.AdvancedUtil;
import com.plugs.utils.StringUtils;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/5/19.
 */
@Controller
@RequestMapping("/wechat/auth")
@Api(value = "微信认证", description = "微信认证")
public class WeChatAuthApi {
    private static final Logger logger = Logger.getLogger(WeChatAuthApi.class);

    private static final String AREA_UUID = "f807671564b0409aa647b7b80af555b6";//厦门区域UUID

    /**
     * 生成用于获取access_token的Code的Url
     *
     * @return
     */
    @RequestMapping("/auth")
    public String wxAuth() {
        logger.info("auth api called");
        return "redirect:" + this.getRequestCodeUrl(Constants.REDIRECT_URL);
    }

    /**
     * 生成用于获取access_token的Code的Url
     *
     * @param redirectUrl
     * @return
     */
    private String getRequestCodeUrl(String redirectUrl) {
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("translateFromYxAuth err:" + e.getMessage());
        }

        String url=String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                Constants.APPID, redirectUrl, "snsapi_userinfo", "YXZC_STATE");
        logger.info("url:"+url);
        return url;
    }

    /**
     * 微信菜单的调用地址，获取授权信息并且返回跳转给前台
     *
     * @param request
     * @return
     */
    @RequestMapping("/getUserInfoAndRedirct")
    public String getUserInfoAndRedirct(HttpServletRequest request, Model model) {
        // 用户同意授权后，能获取到code
        logger.info("getUserInfoAndRedirct api called");

        String code = request.getParameter("code");
        String state ="YXZC_STATE";//String state = request.getParameter("state");

        // 用户同意授权
        if (!"authdeny".equals(code)) {
            logger.info("用户同意授权");
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(Constants.APPID, Constants.APPSECRET, code);
            if (weixinOauth2Token.getErrorCode() != null && weixinOauth2Token.getErrorCode() == 40163) {
                //微信不允许5分钟之内相同Code重新请求，所以遇到这个错误我们就重新请求授权
                String url = getRequestCodeUrl(Constants.REDIRECT_URL);
                return "redirect:" + url;
            }
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

            logger.info("snsUserInfo:"+new Gson().toJson(snsUserInfo));

            // 设置要传递的参数
            request.getSession().setAttribute("snsUserInfo", snsUserInfo);
            request.getSession().setAttribute("state", state);
            String areaSession = (String) request.getSession().getAttribute("areaUuid");
            if (StringUtils.isEmpty(areaSession)) {
                request.getSession().setAttribute("areaUuid",AREA_UUID);
            }

            //跳转到主页
            return "wxPage/index.fz";
        }else {
            logger.info("用户不同意授权");
        }

        //跳转到主页
        return "redirect:" + this.getRequestCodeUrl(Constants.REDIRECT_URL);

    }


}
