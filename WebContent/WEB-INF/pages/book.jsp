<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
<%@include file="/commons/queryCondition.jsp" %>

</head>
<body>
     
     <center>
     <h3>详情页面</h3>
     <table >
        <tr >
          <td >Title:</td>
          <td>${book.title }</td>
        </tr>
        <tr>
          <td>Author:</td>
          <td>${book.author }</td>
        </tr>
        <tr>
          <td>Price:</td>
          <td>${book.price }</td>
        </tr>
        <tr>
          <td>PublishingDate:</td>
          <td>${book.publishingDate }</td>
        </tr>
        <tr>
          <td>Remark:</td>
          <td>${book.remark }</td>
        </tr>

     </table>
      <a href="bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a>

   
     </center>
</body>
</html>