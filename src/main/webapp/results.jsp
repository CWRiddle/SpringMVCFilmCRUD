<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Results</title>
  </head>
  <body>
    <c:choose>
      <c:when test="${! empty film}  ">
        <ul>
          <li>${film.title}</li>
          <li>${film.description}</li>
          <li>${film.rating}</li>
        </ul>
      </c:when>
      <c:otherwise>
        <p>No Film Found</p>
      </c:otherwise>
    </c:choose>
  </body>
</html>