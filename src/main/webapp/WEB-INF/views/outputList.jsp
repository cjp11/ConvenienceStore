<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 목록 출력</title>

</head>
<body>

<div id="container">
   <h1>상품목록 조회 성공!</h1>
   <hr>
   <h3>상품정보</h3>
   
   <table border="1" align="center" >
      <tr align="center" bgcolor="lightgreen">
         <td width="7%"><b>상품 이름</b></td>
         <td width="7%"><b>가격</b></td>
         <td width="5%"><b>수량</b></td>
         <td width="7%"><b>이미지소스</b></td>
         
      </tr>
      <c:forEach var="vo" items="${list }" begin="0" end="${fn:length(list)-1 }" step="1">
      <tr align="center">
         
         <td><input type="text" name="productName" value="${vo.getProductName() }"></td>
         <td><input type="text" name="productPrice" value="${vo.getProductPrice() }"></td>
         <td><input type="text" name="productStock" value="${vo.getProductStock() }"></td>
         <td><img src = "${vo.getImageUrl() }" width="100px" height="50px"></td>
      
      </tr>
      </c:forEach>
      

   </table>
</div>

</body>
</html>