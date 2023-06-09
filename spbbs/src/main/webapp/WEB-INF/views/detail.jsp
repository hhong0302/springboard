<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
<link rel="stylesheet" href="resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="resources/css/style.css" />
</head>
<body>
<div class="container my-5 detail">
<h3 class="py-4 mx-5">${detail.title}</h3>
	<div class="mx-5 info">
		${detail.uname} · ${detail.wdate} · 조회수 ${detail.hit}
	</div>
	<div class="mx-5 my-4 detailview">
		${detail.content}
	</div>
	<div class="button-group mx-5">
		<a href="list" class="btn btn-primary">목록보기</a>
		<a href="reply?num=${detail.num}" class="btn btn-primary">답글달기</a>
		<a href="modify?num=${detail.num}" class="btn btn-primary">수정하기</a>
		<a href="delete?num=${detail.num}" class="btn btn-primary">삭제하기</a>
	</div>
</div>
</body>
</html>