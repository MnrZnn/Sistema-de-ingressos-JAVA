<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Erro — Sistema de Ingressos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>
<nav class="navbar">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">
        <i data-lucide="ticket" style="width:18px;height:18px;"></i>
        <span>Ingresso</span>OS
    </a>
</nav>
<div class="page-wrapper">
    <div class="empty-state">
        <i data-lucide="alert-triangle" style="width:52px;height:52px;display:block;margin:0 auto;opacity:.4;"></i>
        <h3>Ocorreu um erro</h3>
        <p>Não foi possível processar sua solicitação.</p>
        <br>
        <a href="${pageContext.request.contextPath}/" class="btn-primary-custom">Voltar ao inicio</a>
    </div>
</div>
<script>lucide.createIcons();</script>
</body>
</html>
