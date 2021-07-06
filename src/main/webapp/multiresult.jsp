<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="CSS/stylesheet.css" />
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body class="bg-dark">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<a class="navbar-brand" href="#">MVC Film</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav">
				<li class="nav-item active"><a class="nav-link" href="home.do">Home</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="findForm.html">Find
						Film <span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="createFilm.html">Add
						Film</a></li>
			</ul>
		</div>
	</nav>






<c:forEach items="${films}" var="film">
<ul>
  <li>${film.title}</li>
  <li>${film.description}</li>
  <li>${film.rating}</li>
  </ul>
  
  <form action="delete.do" method="GET">
  <input type="hidden" name="filmId" value="${film.id }"/> <input type="submit"
			value="Delete Film" />
	</form>
	
  <form action="editView.do" method="GET">
  <input type="hidden" name="filmId" value="${film.id }"/> <input type="submit"
			value="Edit Film" />
	</form>
</c:forEach>
</body>
</html>