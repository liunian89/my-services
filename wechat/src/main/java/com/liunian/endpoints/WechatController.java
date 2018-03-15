package com.liunian.endpoints;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;

@RestController
public class WechatController {

    @GetMapping("/")
    public String root() {
        return "Hello Wechat!";
    }

    /**
     * 验证微信服务器
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     */
    @RequestMapping(value = "/wechat1", method = RequestMethod.GET)
    public String verifyToken(@RequestParam String signature,
                              @RequestParam String timestamp,
                              @RequestParam String nonce,
                              @RequestParam String echostr) {
        System.out.println(String.format("[%s] [%s] [%s] [%s]", signature, timestamp, nonce, echostr));

        if (isValid(signature, timestamp, nonce)) {
            return echostr;
        }
        return null;
    }

    /**
     * 验证微信服务器
     *
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     */
    @RequestMapping(value = "/wechat2", method = RequestMethod.GET)
    public void verifyToken(PrintWriter out,
                            HttpServletResponse response,
                            @RequestParam String signature,
                            @RequestParam String timestamp,
                            @RequestParam String nonce,
                            @RequestParam String echostr) {
        System.out.println(String.format("[%s] [%s] [%s] [%s]", signature, timestamp, nonce, echostr));

        if (isValid(signature, timestamp, nonce)) {
            out.print(echostr);
        }
    }

    private boolean isValid(String signature, String timestamp, String nonce) {
        String[] stringList = {timestamp, nonce, "7_ecjAzWq89Int9AGw9FlK5SPBE7BwwoQuU-eoJ4-jZfQmFg4wBuJCRnk42XtIKSxCx5rfBURN7g0PMWzOCFTcez7ekfgfZyPsyAWZR887S4YZvGHmz6KAzfXtvt9TvnvzH_Nbp00l7GCcQJg2NCGfADATIM"};
        Arrays.sort(stringList);
        String join = org.apache.commons.lang.StringUtils.join(stringList);
        String encoded = DigestUtils.sha1Hex(join);
        return signature.equals(encoded);
    }

}
