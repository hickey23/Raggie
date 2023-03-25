package com.itliujiong.raggie.controller;


import com.itliujiong.raggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

//文件的下载和上传
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Value("${raggie.path}")
    private String basePath;

    //文件上传
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会被删除
        System.out.println("file:"+file);
//        System.out.println("@@@basePath:"+basePath);
        //获取原始文件名
        String originalFileName= file.getOriginalFilename();
        String suffix=originalFileName.substring(originalFileName.lastIndexOf("."));
        //使用uuid重新生成文件名，防止文件名重复

        String fileName=UUID.randomUUID().toString()+suffix;

        File dir=new File(basePath);
        if(dir.exists()){
            //目录不存在，就创建
            dir.mkdirs();
        }

        //将临时文件转存到指定位置
        file.transferTo(new File(basePath+fileName));
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(@RequestParam(value = "name") String name, HttpServletResponse response) throws IOException {
        log.info("获取到到name的值是：{}",name);
        //通过输入流，读取文件内容
        FileInputStream fileInputStream=new FileInputStream(new File(basePath+name));

        //通过输出流，返回给浏览器，浏览器显示图片
        ServletOutputStream servletOutputStream=response.getOutputStream();
        response.setContentType("image/jpeg");

        int len=0;
        byte[] bytes= new byte[1024];
        while ((len=fileInputStream.read(bytes))!=-1){
            servletOutputStream.write(bytes,0,len);
            //通过flush刷新一下
            servletOutputStream.flush();
        }
        //关闭资源
        servletOutputStream.close();
        fileInputStream.close();



    }
}
