package com.liunian.endpoints;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;

@RestController
public class WechatController {
    private static final String TOKEN = "7_ixt7Mne7k9YL9DOmovsnguOoC-i-5rTd2QkqEpvc-rrsvQwa7RWpMS8Mwmz1fqom2ss1Z55w4cmvHdgvj7MAu1LlrJqRIazAdfdWVWDeY8oaJR7nzy2ZLnQPlQz5R7xNfwm1YU26aMQE3tzyYKGgAEAOAW";


    // Verify wechat - server communication
    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public String serverValidation(@RequestParam String signature,
                                   @RequestParam String timestamp,
                                   @RequestParam String nonce,
                                   @RequestParam String echostr) {
        System.out.println(String.format("[%s] [%s] [%s] [%s]", signature, timestamp, nonce, echostr));

        if (isValid(signature, timestamp, nonce)) {
            return echostr;
        }
        return null;
    }

    // Verify wechat - server communication
    @RequestMapping(value = "/wechat2", method = RequestMethod.GET)
    public void serverValidation(PrintWriter out,
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
        String[] stringList = {timestamp, nonce, TOKEN};
        Arrays.sort(stringList);
        String join = org.apache.commons.lang.StringUtils.join(stringList);
        String encoded = DigestUtils.sha1Hex(join);
        return signature.equals(encoded);
    }

    @PostMapping("/wechat")
    public void onMessage(@RequestBody String message) {
        System.out.println(message);
    }

}
