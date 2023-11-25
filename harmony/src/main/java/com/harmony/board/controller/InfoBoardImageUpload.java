//package com.harmony.board.controller;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//import java.util.UUID;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
//
///**
// * Servlet implementation class InfoBoardImageUpload
// */
//@WebServlet("/board/imageUpload.do")
//public class InfoBoardImageUpload extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public InfoBoardImageUpload() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// 멀티파트 요청 확인
//        if (!ServletFileUpload.isMultipartContent(request)) {
//            throw new ServletException("Content type is not multipart/form-data");
//        }
//
//        response.setContentType("text/html; charset=UTF-8");
//        PrintWriter out = response.getWriter();
//
//        try {
//            // 이미지 저장 디렉토리 설정
//            String uploadPath = getServletContext().getRealPath("/upload/board");
//            File uploadDir = new File(uploadPath);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdirs();
//            }
//
//            // 이미지 저장 및 URL 반환 로직
//            List<FileItem> formItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
//            for (FileItem item : formItems) {
//                if (!item.isFormField()) {
//                    String fileName = new File(item.getName()).getName();
//                    String fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
//
//                    // 파일 확장자 검사
//                    if (!ALLOWED_FILE_EXTENSIONS.contains(fileExt)) {
//                        throw new ServletException("허용되지 않는 파일 형식입니다.");
//                    }
//
//                    String newFileName = UUID.randomUUID().toString() + fileExt; // 고유한 파일명 생성
//                    String filePath = uploadPath + File.separator + newFileName;
//                    File storeFile = new File(filePath);
//                    // 서버에 파일 저장
//                    item.write(storeFile);
//                    // 이미지 URL 반환
//                    out.println(request.getContextPath() + "/upload/board/" + newFileName);
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            out.println("Error: 이미지 업로드 중 오류가 발생했습니다.");
//        }
//    }
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}
//
//}
