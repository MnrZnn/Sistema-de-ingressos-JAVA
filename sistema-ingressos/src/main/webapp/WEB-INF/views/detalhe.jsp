<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="com.ingressos.model.IngressoVIP, com.ingressos.model.IngressoMeia" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Detalhe — ${ingresso.nomeEvento}</title>
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

<div class="page-wrapper" style="max-width:700px;">
    <div class="page-header">
        <div>
            <h1 class="page-title">${ingresso.nomeEvento}</h1>
            <p class="page-subtitle">Detalhes do ingresso</p>
        </div>
        <a href="${pageContext.request.contextPath}/ingressos/lista" class="btn-outline-custom">
            <i data-lucide="arrow-left" style="width:13px;height:13px;"></i> Voltar
        </a>
    </div>

    <%-- Cabeçalho do ingresso --%>
    <div class="card" style="margin-bottom:1.25rem;border-top:3px solid
        ${ingresso.tipo == 'VIP' ? 'var(--type-vip)' : ingresso.tipo == 'MEIA' ? 'var(--type-meia)' : 'var(--type-normal)'};">

        <div style="display:flex;align-items:center;justify-content:space-between;flex-wrap:wrap;gap:1rem;margin-bottom:1.5rem;">
            <div>
                <span class="ticket-tipo ${ingresso.tipo}" style="font-size:.8rem;">${ingresso.tipo}</span>
                <p style="font-family:var(--font-head);font-size:1.4rem;font-weight:700;margin-top:.3rem;">${ingresso.nomeEvento}</p>
            </div>
            <div style="text-align:right;">
                <div class="badge-status ${ingresso.status}">${ingresso.status}</div>
                <div class="valor-destaque" style="margin-top:.5rem;">
                    R$ <fmt:formatNumber value="${ingresso.calcularValor()}" pattern="#,##0.00"/>
                </div>
                <div style="color:var(--muted);font-size:.72rem;margin-top:.2rem;">
                    Base: R$ <fmt:formatNumber value="${ingresso.precoBase}" pattern="#,##0.00"/>
                    &nbsp;|&nbsp; ${ingresso.descricaoTipo}
                </div>
            </div>
        </div>

        <div class="detail-grid">
            <div class="detail-item">
                <div class="detail-item-label"><i data-lucide="map-pin" style="width:11px;height:11px;"></i> Local</div>
                <div class="detail-item-value">${ingresso.localEvento}</div>
            </div>
            <div class="detail-item">
                <div class="detail-item-label"><i data-lucide="calendar" style="width:11px;height:11px;"></i> Data</div>
                <div class="detail-item-value">${ingresso.dataEvento}</div>
            </div>
            <div class="detail-item">
                <div class="detail-item-label"><i data-lucide="user" style="width:11px;height:11px;"></i> Comprador</div>
                <div class="detail-item-value">${ingresso.nomeComprador}</div>
            </div>
            <div class="detail-item">
                <div class="detail-item-label"><i data-lucide="hash" style="width:11px;height:11px;"></i> ID</div>
                <div class="detail-item-value" style="font-size:.75rem;word-break:break-all;">${ingresso.id}</div>
            </div>

            <%-- Campo extra por tipo --%>
            <%
                Object ingressoObj = request.getAttribute("ingresso");
                if (ingressoObj instanceof IngressoVIP) {
            %>
            <div class="detail-item" style="grid-column:1/-1;">
                <div class="detail-item-label"><i data-lucide="star" style="width:11px;height:11px;"></i> Benefícios VIP</div>
                <div class="detail-item-value"><%= ((IngressoVIP)ingressoObj).getBeneficios() %></div>
            </div>
            <% } else if (ingressoObj instanceof IngressoMeia) { %>
            <div class="detail-item" style="grid-column:1/-1;">
                <div class="detail-item-label"><i data-lucide="file-text" style="width:11px;height:11px;"></i> Documento</div>
                <div class="detail-item-value"><%= ((IngressoMeia)ingressoObj).getDocumentoComprobatorio() %></div>
            </div>
            <% } %>
        </div>
    </div>

    <%-- Impressão polimórfica --%>
    <div class="card" style="margin-bottom:1.25rem;">
        <div class="card-header-section">
            <i data-lucide="terminal" style="width:16px;height:16px;color:var(--accent);"></i>
            <h5>Representação do Ingresso</h5>
        </div>
        <pre style="background:var(--surface2);border:1px solid var(--border);border-radius:var(--radius);
                    padding:1rem;font-size:.75rem;white-space:pre-wrap;word-break:break-word;
                    color:var(--accent);overflow:auto;">${ingresso.imprimirIngresso()}</pre>
    </div>

    <%-- Ações --%>
    <div style="display:flex;gap:.75rem;flex-wrap:wrap;">
        <c:if test="${ingresso.status == 'ATIVO'}">
            <a href="${pageContext.request.contextPath}/ingressos/utilizar?id=${ingresso.id}"
               class="btn-success-custom"
               data-confirm="Confirmar uso do ingresso?">
                <i data-lucide="check-circle" style="width:14px;height:14px;"></i> Marcar como Utilizado
            </a>
            <a href="${pageContext.request.contextPath}/ingressos/cancelar?id=${ingresso.id}"
               class="btn-danger-custom"
               data-confirm="Cancelar este ingresso?">
                <i data-lucide="x-circle" style="width:14px;height:14px;"></i> Cancelar Ingresso
            </a>
        </c:if>
        <a href="${pageContext.request.contextPath}/ingressos/deletar?id=${ingresso.id}"
           class="btn-outline-custom" style="color:var(--danger);margin-left:auto;"
           data-confirm="Excluir permanentemente este ingresso?">
            <i data-lucide="trash-2" style="width:14px;height:14px;"></i> Excluir
        </a>
    </div>
</div>

<script>lucide.createIcons();</script>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
