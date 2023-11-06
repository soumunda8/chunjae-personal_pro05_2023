package com.chunjae.project05.controller;

import com.chunjae.project05.biz.FileDTOService;
import com.chunjae.project05.entity.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@Controller
@RequestMapping("/util/")
public class UtilCtrl {

    @Autowired
    private FileDTOService fileDTOService;

    @GetMapping("fileDownload.do")
    public String fileDownload(@RequestParam int no, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String urlPath = request.getHeader("referer");

        FileDTO files = fileDTOService.fileByFno(no);

        ServletContext application = request.getSession().getServletContext();
        String realPath = application.getRealPath("/upload/");                                                            // 운영 서버

        String saveFolder = realPath + files.getSaveFolder();
        String originalFile = files.getOriginName();
        String saveFile = files.getSaveName();
        File file = new File(saveFolder, saveFile);

        response.setContentType("apllication/download; charset=UTF-8");
        response.setContentLength((int) file.length());

        String header = request.getHeader("User-Agent");
        boolean isIE = header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1;
        String fileName = null;
        // IE는 다르게 처리
        if (isIE) {
            fileName = URLEncoder.encode(originalFile, "UTF-8").replaceAll("\\+", "%20");
        } else {
            fileName = new String(originalFile.getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                    out.flush();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            out.close();
        }

        return "redirect:" + urlPath;
    }

    @RequestMapping(value="fileRemove.do", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity fileRemove(@RequestParam("fno") int fno, HttpServletRequest request) throws Exception {
        boolean result = false;

        ServletContext application = request.getSession().getServletContext();
        String realPath = application.getRealPath("/upload");                                                           // 운영 서버

        FileDTO files = fileDTOService.fileByFno(fno);
        File file = new File(realPath + File.separator + files.getSaveFolder() + File.separator + files.getSaveName());

        if (file.exists()) { // 해당 파일이 존재하면
            file.delete(); // 파일 삭제
            fileDTOService.filesDelete(fno);
            result = true;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}