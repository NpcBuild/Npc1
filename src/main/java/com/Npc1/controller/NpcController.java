package com.Npc1.controller;

import com.Npc1.QrCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class NpcController {

    //映射请求，也就是通过它来指定控制器可以处理哪些URL请求，相当于Servlet中在web.xml中配置
    @RequestMapping("/create")
//    找到Spring解析器, 将java对象转为json格式的数据
// 将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML数据。
//一般在异步获取数据时使用【也就是AJAX】
    @ResponseBody
    public String createNpc(@RequestParam(value = "logoFile",required = false) MultipartFile file,
                             @RequestParam(value = "text") String text,
                             @RequestParam(value = "flag") String flag){
        try {
            if (file == null) {
                //if ("normal".equals(flag)){
                    //return ZxingUtil.createImage(text, null);
                    return QrCodeUtil.normal(text);
                //}else if ("color".equals(flag)){
                 //   return QrCodeUtil.color(text);
                //}
            }
            else{
                //if ("normal".equals(flag)){
                    //return ZxingUtil.createImage(text,file.getInputStream());
                    return QrCodeUtil.logo(text,file.getInputStream());
                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "hello";
    }
}
