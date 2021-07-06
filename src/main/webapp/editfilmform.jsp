<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>Edit Film</title>
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






<form action="edit.do">
		<label for="filmTitle">Film Title:</label><br> <input type="text"
			id="filmTitle" name="filmTitle" value="${film.title}"><br> <label
			for="filmDesc">Description</label><br> <input type="text"
			id="filmDesc" name="filmDesc" value="${film.description }"><br> <label
			for="releaseYear">Release Year</label><br> <input type="text"
			id="filmDesc" name="releaseYear" value="${film.releaseYear }"><br> <label
			for="filmDesc">Language ID</label><br> <input type="text"
			id="filmDesc" name="languageId" value="${film.languageId }"><br> <label
			for="filmDesc">Language</label><br> <input type="text"
			id="filmDesc" name="language" value="${film.language}"><br> <label
			for="filmDesc">Length</label><br> <input type="text"
			id="filmDesc" name="length" value="${film.length}"><br> <label
			for="filmDesc">Rating</label><br> <input type="text"
			id="filmDesc" name="rating" value="${film.rating}"><br> <label
			for="filmDesc">Category</label><br> <input type="text"
			id="filmDesc" name="category" value="category"><br> <label
			for="filmDesc">Rental Rate</label><br> <input type="text"
			id="filmDesc" name="rentalRate" value="${film.rentalRate}"><br> <label
			for="filmDesc">Replacement Cost</label><br> <input type="text"
			id="filmDesc" name="replCost" value="${film.replacementCost }"><br>
		<br> <input type="submit" value="Create Film">
	</form>



</body>
</html>