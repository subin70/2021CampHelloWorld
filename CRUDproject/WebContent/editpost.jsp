<!-- post 를 수정하는 소스 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.crud.dao.BoardDAO"%>

<% request.setCharacterEncoding("utf-8"); %>
<!-- bean 사용 -->
<jsp:useBean id="u" class="com.crud.bean.BoardVO" />
<jsp:setProperty property="*" name="u"/>

<%
	BoardDAO boardDAO = new BoardDAO();
	int i=boardDAO.updateBoard(u);
	response.sendRedirect("posts.jsp");
%>