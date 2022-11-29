package com.fastcampus.ch2.controller;

import com.fastcampus.ch2.domain.CommentDto;
import com.fastcampus.ch2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller
//@ResponseBody
@RestController
public class CommentController {
    @Autowired
    CommentService service;

    //    {
//        "pcno":0,
//            "comment" : "hihihi",
//            "commenter" : "asdf"
//    }
    // ����� �����ϴ� �޼���
    @PatchMapping("/comments/{cno}")   // /ch4/comments/26  PATCH
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto) {
//        String commenter = (String)session.getAttribute("id");
        String commenter = "asdf";
        dto.setCommenter(commenter);
        dto.setCno(cno);
        System.out.println("dto = " + dto);

        try {
            if(service.modify(dto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    //    {
//        "pcno":0,
//            "comment" : "hi"
//    }
    // ����� ����ϴ� �޼���
    @PostMapping("/comments")   // /ch4/comments?bno=1085  POST
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) {
//        String commenter = (String)session.getAttribute("id");
        String commenter = "asdf";
        dto.setCommenter(commenter);
        dto.setBno(bno);
        System.out.println("dto = " + dto);

        try {
            if(service.write(dto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // ������ ����� �����ϴ� �޼���
    @DeleteMapping("/comments/{cno}")  // DELETE /comments/1?bno=1085  <-- ������ ��� ��ȣ
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {
//        String commenter = (String)session.getAttribute("id");
        String commenter = "asdf";

        try {
            int rowCnt = service.remove(cno, bno, commenter);

            if(rowCnt!=1)
                throw new Exception("Delete Failed");

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // ������ �Խù��� ��� ����� �������� �޼���
    @GetMapping("/comments")  // /comments?bno=1080   GET
    public ResponseEntity<List<CommentDto>> list(Integer bno) {
        List<CommentDto> list = null;
        try {
            list = service.getList(bno);
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);  // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); // 400
        }
    }


}