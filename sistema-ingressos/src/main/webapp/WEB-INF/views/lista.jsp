<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Ingressos — Sistema</title>
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
        <a href="${pageContext.request.contextPath}/ingressos/lista" class="nav-link active">Lista</a>
        <a href="${pageContext.request.contextPath}/ingressos/novo" class="nav-link">Novo Ingresso</a>
    </div>
</nav>

<div class="page-wrapper">

    <%-- Stats --%>
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-label">Total</div>
            <div class="stat-value">${ingressos.size()}</div>
            <div class="stat-sub">ingressos cadastrados</div>
        </div>
        <div class="stat-card">
            <div class="stat-label" style="color:var(--type-normal);">Normal</div>
            <div class="stat-value">
                <c:set var="cNormal" value="0"/>
                <c:forEach var="i" items="${ingressos}">
                    <c:if test="${i.tipo == 'NORMAL'}"><c:set var="cNormal" value="${cNormal + 1}"/></c:if>
                </c:forEach>
                ${cNormal}
            </div>
            <div class="stat-sub">ingressos normais</div>
        </div>
        <div class="stat-card">
            <div class="stat-label" style="color:var(--type-vip);">VIP</div>
            <div class="stat-value">
                <c:set var="cVIP" value="0"/>
                <c:forEach var="i" items="${ingressos}">
                    <c:if test="${i.tipo == 'VIP'}"><c:set var="cVIP" value="${cVIP + 1}"/></c:if>
                </c:forEach>
                ${cVIP}
            </div>
            <div class="stat-sub">ingressos VIP</div>
        </div>
        <div class="stat-card">
            <div class="stat-label" style="color:var(--type-meia);">Meia</div>
            <div class="stat-value">
                <c:set var="cMeia" value="0"/>
                <c:forEach var="i" items="${ingressos}">
                    <c:if test="${i.tipo == 'MEIA'}"><c:set var="cMeia" value="${cMeia + 1}"/></c:if>
                </c:forEach>
                ${cMeia}
            </div>
            <div class="stat-sub">meia-entrada</div>
        </div>
    </div>

    <%-- Header --%>
    <div class="page-header">
        <div>
            <h1 class="page-title">Ingressos</h1>
            <p class="page-subtitle">Gerenciar todos os ingressos do sistema</p>
        </div>
        <a href="${pageContext.request.contextPath}/ingressos/novo" class="btn-primary-custom">
            <i data-lucide="plus" style="width:15px;height:15px;"></i> Novo Ingresso
        </a>
    </div>

    <%-- Filtros --%>
    <div class="filter-bar">
        <a href="${pageContext.request.contextPath}/ingressos/lista"
           class="filter-btn ${empty filtroTipo ? 'active' : ''}">
            <i data-lucide="list" style="width:13px;height:13px;"></i> Todos
        </a>
        <a href="${pageContext.request.contextPath}/ingressos/lista?tipo=NORMAL"
           class="filter-btn ${filtroTipo == 'NORMAL' ? 'active' : ''}">
            <i data-lucide="ticket" style="width:13px;height:13px;"></i> Normal
        </a>
        <a href="${pageContext.request.contextPath}/ingressos/lista?tipo=VIP"
           class="filter-btn ${filtroTipo == 'VIP' ? 'active' : ''}">
            <i data-lucide="star" style="width:13px;height:13px;"></i> VIP
        </a>
        <a href="${pageContext.request.contextPath}/ingressos/lista?tipo=MEIA"
           class="filter-btn ${filtroTipo == 'MEIA' ? 'active' : ''}">
            <i data-lucide="tag" style="width:13px;height:13px;"></i> Meia-Entrada
        </a>
    </div>

    <%-- Lista de ingressos --%>
    <c:choose>
        <c:when test="${empty ingressos}">
            <div class="empty-state">
                <i data-lucide="inbox" style="width:52px;height:52px;display:block;margin:0 auto;"></i>
                <h3>Nenhum ingresso encontrado</h3>
                <p>Cadastre o primeiro ingresso para este evento.</p>
                <br>
                <a href="${pageContext.request.contextPath}/ingressos/novo" class="btn-primary-custom">
                    <i data-lucide="plus" style="width:15px;height:15px;"></i> Cadastrar Ingresso
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="tickets-grid">
                <c:forEach var="ing" items="${ingressos}">
                    <div class="ticket-card">
                        <div class="ticket-stripe ${ing.tipo}"></div>
                        <div class="ticket-body">
                            <div style="display:flex;align-items:center;justify-content:space-between;margin-bottom:.5rem;">
                                <span class="ticket-tipo ${ing.tipo}">${ing.tipo}</span>
                                <span class="badge-status ${ing.status}">${ing.status}</span>
                            </div>
                            <div class="ticket-evento">${ing.nomeEvento}</div>
                            <div class="ticket-meta">
                                <span><i data-lucide="map-pin" style="width:12px;height:12px;"></i>${ing.localEvento}</span>
                                <span><i data-lucide="calendar" style="width:12px;height:12px;"></i>${ing.dataEvento}</span>
                                <span><i data-lucide="user" style="width:12px;height:12px;"></i>${ing.nomeComprador}</span>
                            </div>
                        </div>
                        <div class="ticket-footer">
                            <span class="ticket-valor">R$ <fmt:formatNumber value="${ing.calcularValor()}" pattern="#,##0.00"/></span>
                            <div class="ticket-actions">
                                <a href="${pageContext.request.contextPath}/ingressos/detalhe?id=${ing.id}"
                                   class="btn-outline-custom" title="Detalhes">
                                    <i data-lucide="eye" style="width:13px;height:13px;"></i>
                                </a>
                                <c:if test="${ing.status == 'ATIVO'}">
                                    <a href="${pageContext.request.contextPath}/ingressos/utilizar?id=${ing.id}"
                                       class="btn-success-custom" title="Utilizar"
                                       data-confirm="Marcar ingresso como UTILIZADO?">
                                        <i data-lucide="check" style="width:13px;height:13px;"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/ingressos/cancelar?id=${ing.id}"
                                       class="btn-danger-custom" title="Cancelar"
                                       data-confirm="Deseja cancelar este ingresso?">
                                        <i data-lucide="x" style="width:13px;height:13px;"></i>
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

</div>

<script>lucide.createIcons();</script>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
