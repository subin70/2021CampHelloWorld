        
package com.member.common;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.member.bean.MemberVO;
import com.member.dao.MemberDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


public class FileUpload {
	public  MemberVO uploadPhoto(HttpServletRequest request) {
		String filename = "";  // 업로드되는 파일이름 저장용 
		int sizeLimit = 15 * 1024 * 1024;  // 파일크기 (15MB)
		
		// 실제로 서버에 저장되는 path를 upload로 정한다.
		String realPath = request.getServletContext().getRealPath("upload");
		//System.out.println(realPath);
		
		// 	혹시 저장될 경로가 없으면 생성한다.
		File dir = new File(realPath);
		if (!dir.exists()) dir.mkdirs();

		MemberVO one = null;
		MultipartRequest multipartRequest = null;
		try {
			// 파일 업로드 처리하는 과정 
			// 파라미터 : request, 파일저장경로, 최대용량, 인코딩, 중복파일명에 대한 정책
			// DefaultFileRenamePolicy() - 중복된 이름이 존재할 경우의 처리방법 - 뒤에 숫자붙임
			multipartRequest = new MultipartRequest(request, realPath, 
					sizeLimit, "utf-8",new DefaultFileRenamePolicy());

			// "photo"라는 이름으로 전송되어 업로드된 파일 이름을 가져옴 
			filename = multipartRequest.getFilesystemName("photo");
			
			one = new MemberVO();
			String sid = multipartRequest.getParameter("sid");
			if(sid!=null&&!sid.equals("")) one.setSid(Integer.parseInt(sid));
			one.setUserid(multipartRequest.getParameter("userid"));
			one.setUsername(multipartRequest.getParameter("username"));
			one.setPassword(multipartRequest.getParameter("password"));
			one.setEmail(multipartRequest.getParameter("email"));
			one.setDetail(multipartRequest.getParameter("detail"));
			
			if (sid!=null&&!sid.equals("")) {  // edit 인 경우 기존 파일이름과 비교해야 함 
				MemberDAO dao = new MemberDAO();
				String oldfilename = dao.getPhotoFilename(Integer.parseInt(sid));
				if(filename!=null && oldfilename!=null) // 새로 덮어씌울 파일이 전송된 경우 이전 파일을 제거함 
					FileUpload.deleteFile(request, oldfilename);
				else if(filename==null && oldfilename!=null) // 새로운 파일이 없는 경우 이전 파일을 유지함 
					filename = oldfilename;
			}
			one.setPhoto(filename);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return one;
	}
	
	public static void deleteFile(HttpServletRequest request, String filename) {
		String filePath = request.getServletContext().getRealPath("upload");
		
		File f = new File(filePath + "/" + filename); 
		if( f.exists()) f.delete(); 
	}
	
	public static String uploadFile(HttpServletRequest request) {
		String filename = "";
		int sizeLimit = 15 * 1024 * 1024;
		
		String realPath = request.getServletContext().getRealPath("upload");
		File dir = new File(realPath);
		if (!dir.exists()) dir.mkdirs();
				
		MultipartRequest multpartRequest = null;
		try {
			multpartRequest = new MultipartRequest(request, realPath, 
					sizeLimit, "utf-8",new DefaultFileRenamePolicy());
		
			filename = multpartRequest.getFilesystemName("photo");
			if (filename == null) filename="";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename;
	}		
}

    