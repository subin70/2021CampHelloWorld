<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ page info="composed by Sonoo Jaiswal" %>  
Today is: <%= new java.util.Date() %>  

  
<%@ include file="form.html" %>  
  
Today is: <%= java.util.Calendar.getInstance().getTime() %>  


  

 
</body>
</html>