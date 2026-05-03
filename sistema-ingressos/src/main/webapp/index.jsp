<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Ingressos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>

<nav class="navbar">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">
        <i data-lucide="ticket" style="width:18px;height:18px;"></i>
        <span>Ingresso</span>OS
    </a>
    <div style="margin-left:auto;display:flex;gap:.25rem;">
        <a href="${pageContext.request.contextPath}/ingressos/lista" class="nav-link">Lista</a>
        <a href="${pageContext.request.contextPath}/ingressos/novo" class="nav-link">Novo Ingresso</a>
    </div>
</nav>

<div class="page-wrapper">
    <div class="hero">
        <p class="hero-label">Gerenciamento de Eventos</p>
        <h1 class="hero-title">Sistema de <em>Ingressos</em></h1>
        <p class="hero-desc">Cadastre, consulte e gerencie ingressos com suporte a Normal, VIP e Meia-Entrada.</p>
        <div style="display:flex;gap:.75rem;justify-content:center;flex-wrap:wrap;">
            <a href="${pageContext.request.contextPath}/ingressos/novo" class="btn-primary-custom" style="padding:.65rem 1.75rem;font-size:.9rem;">
                <i data-lucide="plus" style="width:16px;height:16px;"></i> Novo Ingresso
            </a>
            <a href="${pageContext.request.contextPath}/ingressos/lista" class="btn-outline-custom" style="padding:.65rem 1.75rem;font-size:.9rem;">
                <i data-lucide="list" style="width:16px;height:16px;"></i> Ver Lista
            </a>
        </div>

        <div class="feature-grid">
            <div class="feature-card">
                <div class="feature-icon"><i data-lucide="ticket" style="width:20px;height:20px;"></i></div>
                <div class="feature-title">Ingresso Normal</div>
                <div class="feature-desc">Acesso padrão ao evento. Valor integral sem acréscimos.</div>
            </div>
            <div class="feature-card">
                <div class="feature-icon" style="background:rgba(155,89,182,.1);color:#9b59b6;">
                    <i data-lucide="star" style="width:20px;height:20px;"></i>
                </div>
                <div class="feature-title">Ingresso VIP</div>
                <div class="feature-desc">Acesso exclusivo com benefícios. Acréscimo de 50% sobre o valor base.</div>
            </div>
            <div class="feature-card">
                <div class="feature-icon" style="background:rgba(46,204,113,.1);color:#2ecc71;">
                    <i data-lucide="tag" style="width:20px;height:20px;"></i>
                </div>
                <div class="feature-title">Meia-Entrada</div>
                <div class="feature-desc">50% de desconto com documento comprobatório.</div>
            </div>
            <div class="feature-card">
                <div class="feature-icon" style="background:rgba(52,152,219,.1);color:#3498db;">
                    <i data-lucide="database" style="width:20px;height:20px;"></i>
                </div>
                <div class="feature-title">MongoDB Atlas</div>
                <div class="feature-desc">Persistência em nuvem com MongoDB Atlas via driver oficial.</div>
            </div>
        </div>
    </div>
</div>

<script>lucide.createIcons();</script>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
