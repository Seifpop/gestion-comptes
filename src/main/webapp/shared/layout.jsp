<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>
        <%= Objects.toString(request.getParameter("title"), "FilRouge") %>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        form {
            display: flex;
            flex-direction: column;
            justify-content: center;

            gap: 0.5rem;
            max-width: 400px;

            div {
                display: flex;
                align-content: center;
                justify-content: space-between;
            }
        }

        .alert-danger {
            color: #842029;
            background-color: #f8d7da;

            padding: 1rem 1rem;
            margin-bottom: 1rem;
            border: 1px solid #f5c2c7;
            border-radius: .25rem;
        }

        a.button {
            padding: 1px 6px;
            border: 1px outset buttonborder;
            border-radius: 3px;
            color: buttontext;
            background-color: buttonface;
            text-decoration: none;
        }
    </style>
</head>
<body>
