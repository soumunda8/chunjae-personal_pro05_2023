package com.chunjae.project05.controller;

import com.chunjae.project05.biz.BoardService;
import com.chunjae.project05.biz.CommentService;
import com.chunjae.project05.biz.FileDTOService;
import com.chunjae.project05.biz.UserService;
import com.chunjae.project05.entity.*;
import com.chunjae.project05.util.BoardPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/board")
public class BoardCtrl {

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileDTOService fileDTOService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    private final String toUseFileByBoard = "board";

    @PostMapping("/getBoardMgnList.do")
    public ResponseEntity getBoardMgnList() throws Exception {
        List<BoardMgn> boardMgnListForHeader = boardService.listBoardMgnForHeader();
        return new ResponseEntity<>(boardMgnListForHeader, HttpStatus.OK);
    }

    @GetMapping( "/list.do")
    public ModelAndView boardList(HttpServletRequest request, Principal principal) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        String sid = principal != null ? principal.getName() : "";

        String type = request.getParameter("type");
        String keyword = request.getParameter("keyword");
        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int bmNo = request.getParameter("no") != null ? Integer.parseInt(request.getParameter("no")) : 1;

        BoardPage page = new BoardPage();
        page.setSearchType(type);
        page.setSearchKeyword(keyword);
        page.setBmNo(bmNo);
        int total = boardService.boardCount(page);

        page.makeBlock(curPage, total);
        page.makeLastPageNum(total);
        page.makePostStart(curPage, total);
        List<BoardVO> boardList = boardService.boardList(page);

        for(BoardVO boardVO : boardList) {
            String authorNm = boardVO.getUserName();
            if(!authorNm.equals("관리자")) {
                String nm = authorNm.substring(0, 1);
                for(int i = 0; i < authorNm.length()-2; i++){
                    nm += "*";
                }
                nm += authorNm.substring(authorNm.length() - 1);
                boardVO.setUserName(nm);
            }
        }

        BoardMgn boardMgn = boardService.getBoardMgn(bmNo);

        // 권한 관련 - 등록
        boolean addCheck = false;
        if(!sid.equals("") && (boardMgn.getAboutAuth() >= userService.getUserByLoginId(sid).getRoleId() || sid.equals("admin"))) {
            addCheck = true;
        }

        modelAndView.addObject("type", type);
        modelAndView.addObject("keyword", keyword);
        modelAndView.addObject("page", page);
        modelAndView.addObject("curPage", curPage);

        if (boardList.isEmpty()) {
            modelAndView.addObject("boardList", "");
        } else {
            modelAndView.addObject("boardList", boardList);
        }

        modelAndView.addObject("boardMgn", boardMgn);
        modelAndView.addObject("addCheck", addCheck);

        // 페이지 공통 설정
        modelAndView.addObject("titleName", boardMgn.getBoardName());
        modelAndView.addObject("pageType", "sub");

        modelAndView.setViewName("board/list");
        return modelAndView;
    }

    @GetMapping("/add.do")
    public ModelAndView boardAdd(HttpServletRequest request, Principal principal) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        String sid = principal != null ? principal.getName() : "";
        int bmNo = Integer.parseInt(request.getParameter("no"));

        if(principal != null) {
            BoardMgn boardMgn = boardService.getBoardMgn(bmNo);
            modelAndView.addObject("boardMgn", boardMgn);

            // 페이지 공통 설정
            modelAndView.addObject("titleName", boardMgn.getBoardName());

            modelAndView.setViewName("board/add");
            return modelAndView;
        } else {
            modelAndView.setViewName("redirect:/board/list.do?no=" + bmNo);
            return modelAndView;
        }
    }

    @PostMapping("/add.do")
    public ModelAndView boardAddPro(HttpServletRequest request, Board board, List<MultipartFile> uploadFiles, Principal principal) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        String sid = principal != null ? principal.getName() : "";
        int bmNo = Integer.parseInt(request.getParameter("no"));

        board.setAuthor(sid);
        board.setBmNo(bmNo);
        BoardVO boardVO = boardService.boardInsert(board);

        if(uploadFiles != null) {
            ServletContext application = request.getSession().getServletContext();
            String realPath = application.getRealPath("/upload");

            SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd");
            Date date = new Date();
            String dateFolder = sdf.format(date);

            File uploadPath = new File(realPath, dateFolder);
            if(!uploadPath.exists()) {uploadPath.mkdirs();}

            for(MultipartFile multipartFile : uploadFiles) {
                if(multipartFile.isEmpty()) {continue;}

                String originalFilename = multipartFile.getOriginalFilename();
                UUID uuid = UUID.randomUUID();
                String uploadFilename = uuid.toString() + "_" + originalFilename;

                FileDTO fileDTO = new FileDTO();
                fileDTO.setParNo(boardVO.getBno());
                fileDTO.setSaveFolder(dateFolder);

                String fileType = multipartFile.getContentType();
                String[] fileTypeArr = fileType.split("/");
                fileDTO.setFileType(fileTypeArr[0]);

                fileDTO.setOriginName(originalFilename);
                fileDTO.setSaveName(uploadFilename);
                fileDTO.setToUse(toUseFileByBoard);

                multipartFile.transferTo(new File(uploadPath, uploadFilename));     // 서버에 파일 업로드 수행
                fileDTOService.filesInsert(fileDTO);                                  // DB 등록
            }

        }

        modelAndView.setViewName("redirect:/board/get.do?bno=" + boardVO.getBno());
        return modelAndView;
    }

    @GetMapping("/get.do")
    public ModelAndView boardDetail(HttpServletRequest request, HttpServletResponse response, Principal principal) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        String sid = principal != null ? principal.getName() : "";
        int bno = Integer.parseInt(request.getParameter("bno"));

        Cookie[] cookies = request.getCookies();
        boolean hasCookie = false;
        if (cookies != null) {
            String bcookie = "board"+bno;
            for (Cookie cookie : cookies) {
                if (bcookie.equals(cookie.getName())) {
                    hasCookie = true;
                    break;
                }
            }
            if(!hasCookie){
                Cookie cookie = new Cookie(bcookie, bcookie);
                cookie.setMaxAge(3600); // 초 단위, 1시간

                // 응답 헤더에 쿠키 추가
                response.addCookie(cookie);
            }
        }

        BoardVO board = boardService.boardGet(hasCookie, bno, sid);
        String userName = board.getUserName();

        if(!userName.equals("관리자")) {
            String nm = userName.substring(0, 1);
            for(int i = 0; i < userName.length()-2; i++){
                nm += "*";
            }
            nm += userName.substring(userName.length() - 1);
            board.setUserName(nm);
        }
        modelAndView.addObject("board", board);

        // 권한 관련 - 수정
        boolean addCheck = false;
        if(!sid.equals("") && board.getAuthor().equals(sid)) {
            addCheck = true;
        }
        modelAndView.addObject("addCheck", addCheck);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setParNo(board.getBno());
        fileDTO.setToUse(toUseFileByBoard);
        List<FileDTO> fileList = fileDTOService.fileListByPar(fileDTO);
        if (fileList.isEmpty()) {
            modelAndView.addObject("fileList", "");
        } else {
            modelAndView.addObject("fileList", fileList);
        }

        List<CommentVO> commentList = commentService.commentList(bno);
        if (commentList.isEmpty()) {
            modelAndView.addObject("commentList", "");
        } else {
            for(CommentVO commentVO :commentList) {
                String originNm = commentVO.getUserName();
                if(!originNm.equals("관리자")) {
                    String nm = originNm.substring(0, 1);
                    for(int i = 0; i < originNm.length()-2; i++){
                        nm += "*";
                    }
                    nm += originNm.substring(originNm.length() - 1);
                    commentVO.setUserName(nm);
                }
            }
            modelAndView.addObject("commentList", commentList);
        }

        BoardMgn boardMgn = boardService.getBoardMgn(board.getBmNo());
        modelAndView.addObject("boardMgn", boardMgn);
        modelAndView.addObject("sid", sid);

        // 페이지 공통 설정
        modelAndView.addObject("titleName", boardMgn.getBoardName());

        modelAndView.setViewName("board/get");

        return modelAndView;
    }

    @GetMapping("/update.do")
    public ModelAndView boardUpdate(HttpServletRequest request, HttpServletResponse response, Principal principal) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        String sid = principal != null ? principal.getName() : "";
        int bno = Integer.parseInt(request.getParameter("bno"));

        Cookie[] cookies = request.getCookies();
        boolean hasCookie = false;
        if (cookies != null) {
            String bcookie = "board"+bno;
            for (Cookie cookie : cookies) {
                if (bcookie.equals(cookie.getName())) {
                    hasCookie = true;
                    break;
                }
            }
            if(!hasCookie){
                Cookie cookie = new Cookie(bcookie, bcookie);
                cookie.setMaxAge(3600); // 초 단위, 1시간

                // 응답 헤더에 쿠키 추가
                response.addCookie(cookie);
            }
        }

        BoardVO board = boardService.boardGet(hasCookie, bno, sid);
        modelAndView.addObject("board", board);

        // 권한 관련 - 수정
        boolean addCheck = false;
        if(!sid.equals("") && board.getAuthor().equals(sid)) {
            addCheck = true;
        }
        modelAndView.addObject("addCheck", addCheck);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setParNo(board.getBno());
        fileDTO.setToUse(toUseFileByBoard);

        List<FileDTO> fileList = fileDTOService.fileListByPar(fileDTO);
        modelAndView.addObject("fileList", fileList);

        /*String pathUrl = "/board/list.do?no=" + board.getBmNo();
        modelAndView.addObject("fileList", fileList);
        model.addAttribute("pathUrl", pathUrl);*/

        // 페이지 공통 설정
        modelAndView.addObject("titleName", board.getBoardName());

        modelAndView.setViewName("board/update");
        return modelAndView;
    }

    @PostMapping("/update.do")
    public ModelAndView boardUpdatePro(HttpServletRequest request, HttpServletResponse response, List<MultipartFile> uploadFiles, Principal principal) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        String sid = principal != null ? principal.getName() : "";
        int bno = Integer.parseInt(request.getParameter("bno"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        Cookie[] cookies = request.getCookies();
        boolean hasCookie = false;
        if (cookies != null) {
            String bcookie = "board"+bno;
            for (Cookie cookie : cookies) {
                if (bcookie.equals(cookie.getName())) {
                    hasCookie = true;
                    break;
                }
            }
            if(!hasCookie){
                Cookie cookie = new Cookie(bcookie, bcookie);
                cookie.setMaxAge(3600); // 초 단위, 1시간

                // 응답 헤더에 쿠키 추가
                response.addCookie(cookie);
            }
        }

        BoardVO boardVO = boardService.boardGet(hasCookie, bno, sid);
        //BoardMgn boardMgn = boardService.getBoardMgn(boardVO.getBmNo());

        Board board = new Board();
        board.setBno(bno);
        board.setTitle(title);
        board.setContent(content);
        boardService.boardUpdate(board);

        if(uploadFiles != null) {
            ServletContext application = request.getSession().getServletContext();
            String realPath = application.getRealPath("/upload");                                                             // 운영 서버

            SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd");
            Date date = new Date();
            String dateFolder = sdf.format(date);

            File uploadPath = new File(realPath, dateFolder);
            if(!uploadPath.exists()) {uploadPath.mkdirs();}

            for(MultipartFile multipartFile : uploadFiles) {
                if(multipartFile.isEmpty()) {continue;}

                String originalFilename = multipartFile.getOriginalFilename();
                UUID uuid = UUID.randomUUID();
                String uploadFilename = uuid.toString() + "_" + originalFilename;

                FileDTO fileDTO = new FileDTO();
                fileDTO.setParNo(bno);
                fileDTO.setSaveFolder(dateFolder);

                String fileType = multipartFile.getContentType();
                String[] fileTypeArr = fileType.split("/");
                fileDTO.setFileType(fileTypeArr[0]);

                fileDTO.setOriginName(originalFilename);
                fileDTO.setSaveName(uploadFilename);
                fileDTO.setToUse(toUseFileByBoard);

                multipartFile.transferTo(new File(uploadPath, uploadFilename));     // 서버에 파일 업로드 수행
                fileDTOService.filesInsert(fileDTO);                                  // DB 등록
            }

        }

        modelAndView.setViewName("redirect:/board/get.do?bno=" + bno);

        return modelAndView;

    }

    @GetMapping("/delete.do")
    public ModelAndView boardDeletePro(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr, Principal principal) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        String sid = principal != null ? principal.getName() : "";
        int bno = Integer.parseInt(request.getParameter("bno"));

        Cookie[] cookies = request.getCookies();
        boolean hasCookie = false;
        if (cookies != null) {
            String bcookie = "board"+bno;
            for (Cookie cookie : cookies) {
                if (bcookie.equals(cookie.getName())) {
                    hasCookie = true;
                    break;
                }
            }
            if(!hasCookie){
                Cookie cookie = new Cookie(bcookie, bcookie);
                cookie.setMaxAge(3600); // 초 단위, 1시간

                // 응답 헤더에 쿠키 추가
                response.addCookie(cookie);
            }
        }

        BoardVO boardVO = boardService.boardGet(hasCookie, bno, sid);
        int bmNo = boardVO.getBmNo();

        if(sid.equals(boardVO.getAuthor()) || sid.equals("admin")) {

            FileDTO fileDTO = new FileDTO();
            fileDTO.setParNo(bno);
            fileDTO.setToUse(toUseFileByBoard);
            List<FileDTO> fileList = fileDTOService.fileListByPar(fileDTO);
            for(FileDTO files : fileList) {
                ServletContext application = request.getSession().getServletContext();
                String realPath = application.getRealPath("/upload");
                File file = new File( realPath + File.separator + files.getSaveFolder() + File.separator + files.getSaveName());
                if (file.exists()) {
                    file.delete();
                    fileDTOService.filesDeleteAll(bno);
                }
            }
            commentService.commentDeleteAll(bno);
            boardService.boardDelete(bno);
            modelAndView.setViewName("redirect:/board/list.do?no=" + bmNo);
        } else {
            rttr.addFlashAttribute("msg", "fail");
            modelAndView.setViewName("redirect:/board/get.do?bno=" + bno);
        }

        return modelAndView;

    }

    @PostMapping("commentAdd.do")
    @ResponseBody
    public CommentVO commentInsert(@RequestParam("parNo") int parNo, @RequestParam("content") String content, Principal principal) throws Exception {
        String sid = principal != null ? principal.getName() : "";
        Comment comment = new Comment();
        comment.setAuthor(sid);
        comment.setParNo(parNo);
        comment.setContent(content);
        CommentVO commentVO = commentService.commentInsert(comment);
        String originNm = commentVO.getUserName();
        if(!originNm.equals("관리자")) {
            String nm = originNm.substring(0, 1);
            for(int i = 0; i < originNm.length()-2; i++){
                nm += "*";
            }
            nm += originNm.substring(originNm.length() - 1);
            commentVO.setUserName(nm);
        }
        return commentVO;
    }

    @PostMapping("commentRemove.do")
    @ResponseBody
    public boolean commentDelete(@RequestParam("cno") int cno, Principal principal) throws Exception {
        boolean result = false;
        String sid = principal != null ? principal.getName() : "";
        CommentVO commentVO = commentService.comment(cno);
        if(commentVO.getAuthor().equals(sid) || sid.equals("admin")) {
            commentService.commentDelete(commentVO.getCno());
            result = true;
        }
        return result;
    }

}