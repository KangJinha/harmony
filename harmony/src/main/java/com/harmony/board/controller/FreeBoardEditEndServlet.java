package com.harmony.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.board.free.model.dto.FreeBoard;
import com.harmony.board.model.service.FreeBoardService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class FreeBoardEditEndServlet
 */
@WebServlet("/board/FreeBoardEditEnd.do")
public class FreeBoardEditEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardEditEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  request.setCharacterEncoding("UTF-8");

		  String path = getServletContext().getRealPath("/upload/");
	        path += "board"; // "board" 디렉토리를 추가
	        File dir = new File(path);
	        if (!dir.exists()) dir.mkdirs(); // 디렉토리가 존재하지 않으면 생성

	        MultipartRequest mr = new MultipartRequest(request, path, 10 * 1024 * 1024, "UTF-8", new DefaultFileRenamePolicy());

	        int no = Integer.parseInt(mr.getParameter("no"));
	        FreeBoardService service = new FreeBoardService();
	        FreeBoard oldBoard = service.selectBoardByNo(no, false);

	        String newFileName = mr.getFilesystemName("upfile");
	        if (newFileName != null && !newFileName.isEmpty()) {
	            String oldFileName = oldBoard.getFreBrdTitleImg();
	            if (oldFileName != null && !oldFileName.isEmpty()) {
	                File oldFile = new File(path + oldFileName);
	                if (oldFile.exists()) {
	                    oldFile.delete();
	                }
	            }
	        } else {
	            newFileName = oldBoard.getFreBrdTitleImg(); // 새 파일이 없으면 기존 파일명을 유지
	        }

	        String title = mr.getParameter("title");
	        String content = mr.getParameter("bo_content");

	        FreeBoard board = FreeBoard.builder()
	            .freBrdNo(no)
	            .freBrdTitle(title)
	            .freBrdContent(content)
	            .freBrdTitleImg(newFileName) // 파일명 설정
	            .build();

	        int result = service.updateFreeBoard(board);

	        String msg = result > 0 ? "게시글이 성공적으로 수정되었습니다." : "게시글 수정에 실패하였습니다.";
	        String loc = "/board/freeboardView.do?no=" + no;

	        request.setAttribute("msg", msg);
	        request.setAttribute("loc", loc);
	        request.getRequestDispatcher("/views/lesson/common/msg.jsp").forward(request, response);
	    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
