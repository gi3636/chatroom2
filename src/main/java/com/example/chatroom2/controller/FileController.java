package com.example.chatroom2.controller;

import com.example.chatroom2.entity.User;
import com.example.chatroom2.service.UserService;
import com.example.chatroom2.util.FileNameUtils;
import com.example.chatroom2.util.JwtInfo;
import com.example.chatroom2.util.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class FileController {
    private final ResourceLoader resourceLoader;

    @Autowired
    UserService userService;

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    public FileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${web.upload-path}")
    private String path;

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file, HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        System.out.println("获取项目classes/static的地址 : " + staticPath);
        String fileName = FileNameUtils.getFileName(file.getOriginalFilename());  //获取文件名,以及改名
        String savePath = staticPath +"/img/"+ username +"/"+ fileName; //图片保存路径
        System.out.println("图片保存地址：" + savePath);
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
        }
        String visitPath = "/img/"+ username +"/"+ fileName;
        System.out.println("要存储的照片路径：" + visitPath);
        JwtInfo jwtInfo = sessionContext.getJwtInfo();
        userService.changeAvatar(visitPath, jwtInfo.getId());
        return visitPath;
    }


    /**
     * 显示单张图片
     *
     * @return
     */
    @GetMapping("/show/{username}")
    public ResponseEntity showPhotos(@PathVariable String username) {
        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        User user = userService.findUser(username);
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            System.out.println("file:" + staticPath + user.getAvatar());
            return ResponseEntity.ok(resourceLoader.getResource("file:" + staticPath + user.getAvatar()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
