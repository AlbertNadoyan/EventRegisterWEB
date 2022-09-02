<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 03.09.2022
  Time: 2:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add event</title>
</head>
<body>
    <h1>Input event's data</h1>
    <form action="/events/add" method="post">
        <input type="text" name="name" placeholder="input name"><br>
        <input type="text" name="place" placeholder="input place"><br>
        Is Online? <br>
        YES <input type="radio" name="isOnline" value="true">
        NO <input type="radio" name="isOnline" value="false">
        <br>
        Event Type
        <select name="eventType" id="">
            <option value="FILM">FILM</option>
            <option value="SPORT">SPORT</option>
            <option value="GAME">GAME</option>
        </select>
        <br>
        <input type="number" name="price" placeholder="input price">
        <br>
        <input type="date" name="eventDate">
        <br>
        <input type="submit" value="ADD">
    </form>
</body>
</html>
