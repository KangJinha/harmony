package com.harmony.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.admin.model.dto.Notice;
import com.harmony.admin.model.dto.NoticeAttachFile;
import com.harmony.admin.service.AdminService;
import com.harmony.common.exception.HarmonyException;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class AdminNoticeWriteServlet
 */
@WebServlet(name = "adminNoticeWrite", urlPatterns = { "/admin/notice/write.do" })
public class AdminNoticeWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminNoticeWriteServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int no = -1;
		String type = "";
		try {
			no = Integer.parseInt(request.getParameter("no"));
			type = "edit";
		} catch (NumberFormatException e) {
			no = -1;
			type = "write";
		}
		request.setAttribute("type", type);
		if (no > 0) {
			Notice notice = AdminService.getService().selectNoticeByNo(no, false);
			if (notice != null) {
				request.setAttribute("notice", notice);
			} else {
				throw new HarmonyException("유효하지 않은 공지글 번호입니다.");
			}
		}
		request.getRequestDispatcher("/views/admin/views/noticewrite.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = getServletContext().getRealPath("/upload/admin/notice/");
		int fileMaxSize = 1024 * 1024 * 100;
		String encode = "UTF-8";
		DefaultFileRenamePolicy renamePolicy = new DefaultFileRenamePolicy();
		MultipartRequest req = new MultipartRequest(request, path, fileMaxSize, encode, renamePolicy);
		String type = req.getParameter("type");
		// 파일 여러개 등록할 때는 MultipartParser
//		MultipartParser parser = new MultipartParser(request, fileMaxSize);
//		parser.setEncoding("UTF-8");
//		while(true) {
//			Part part = parser.readNextPart();
//			if(part == null) {
//				break;
//			}
//			if(part.isFile()) {
//				FilePart filePart = (FilePart) part;
//				String oriName = filePart.getFileName();
//				String reName = System.currentTimeMillis()+"";
//				File file = new File(path, reName);
//				filePart.writeTo(file);
//				notice.getAttachFileList().add(NoticeAttachFile.builder().oriName(oriName).reName(reName).build());
//			}else {
//				// type,title,delFile,
//				ParamPart paramPart = (ParamPart)part;
//				String param = paramPart.getName();
//				String value = paramPart.getStringValue();
//				String[] 
//			}
//		}

		String no = "";
		int noticeNo = -1;

		String title = req.getParameter("title");
		String[] delFile = req.getParameterValues("delFile");
		String[] delFileName = null;
		String writer = req.getParameter("writer");
		String content = req.getParameter("content");

		Notice notice = Notice.builder().title(title).noticeWriter(writer).content(content).build();
		try {
			Enumeration<String> files = req.getFileNames();
			while (files.hasMoreElements()) {
				String paramName = files.nextElement();
				String reName = req.getFilesystemName(paramName);
				String oriName = req.getOriginalFileName(paramName);
				notice.getAttachFileList().add(NoticeAttachFile.builder().oriName(oriName).reName(reName).build());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			notice.getAttachFileList().forEach(t -> {
				File deleteFile = new File(path + "/" + t.getReName());
				if (deleteFile.exists())
					deleteFile.delete();
			});
			throw new HarmonyException("파일 처리중 오류가 발생하였습니다.");
		}
		String redirectPath = "";
		try {
			if (type.equals("edit")) {
				no = req.getParameter("no");
				noticeNo = Integer.parseInt(no);
			}
			if (noticeNo > 0) {
				notice.setNoticeNo(noticeNo);
			}
			int result = -1;
			switch (type) {
			case "write":
				result = AdminService.getService().insertNotice(notice);
				break;
			case "edit":
				if(delFile!=null && delFile.length>0) {
					delFileName=AdminService.getService().getDelFileList(delFile);
				}
				result = AdminService.getService().updateNotice(notice, delFile);
				if(result >0 && delFile.length>0) {
					for(String f : delFileName) {
						File deleteFile = new File(path + "/"+f);
						if(deleteFile.exists())deleteFile.delete();
					}
				}
				break;
			}
			
			if (result > 0 && type.equals("edit")) {
				redirectPath = "/admin/notice.do?no=" + noticeNo;
			} else if (result > 0 && type.equals("write")) {
				redirectPath = "/admin/notice.do?no=" + result;
			} else {
				throw new RuntimeException();
			}
		} catch (Exception e) {
			notice.getAttachFileList().forEach(t -> {
				File deleteFile = new File(path + "/" + t.getReName());
				if (deleteFile.exists())
					deleteFile.delete();
			});
			e.printStackTrace();
			String msg = type.equals("edit") ? "수정중 오류가 발생하였습니다." : "입력중 오류가 발생하였습니다";
			throw new HarmonyException(msg);
		}
		response.sendRedirect(request.getContextPath() + redirectPath);
	}

}
