<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Novo Ingresso — Sistema</title>
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
        <a href="${pageContext.request.contextPath}/ingressos/novo" class="nav-link active">Novo Ingresso</a>
    </div>
</nav>

<div class="page-wrapper" style="max-width:620px;">
    <div class="page-header">
        <div>
            <h1 class="page-title">Novo Ingresso</h1>
            <p class="page-subtitle">Preencha os dados para cadastrar um ingresso</p>
        </div>
        <a href="${pageContext.request.contextPath}/ingressos/lista" class="btn-outline-custom">
            <i data-lucide="arrow-left" style="width:13px;height:13px;"></i> Voltar
        </a>
    </div>

    <div class="card">
        <form action="${pageContext.request.contextPath}/ingressos" method="post">

            <%-- Tipo de ingresso --%>
            <div class="form-group">
                <label class="form-label" for="tipo">
                    <i data-lucide="layers" style="width:12px;height:12px;"></i> Tipo de Ingresso
                </label>
                <select name="tipo" id="tipo" class="form-control-custom" required>
                    <option value="NORMAL">Normal — Valor integral</option>
                    <option value="VIP">VIP — +50%</option>
                    <option value="MEIA">Meia-Entrada — -50%</option>
                </select>
            </div>

            <div style="display:grid;grid-template-columns:1fr 1fr;gap:1rem;">
                <div class="form-group" style="grid-column:1/-1;">
                    <label class="form-label" for="nomeEvento">
                        <i data-lucide="music" style="width:12px;height:12px;"></i> Nome do Evento
                    </label>
                    <input type="text" name="nomeEvento" id="nomeEvento"
                           class="form-control-custom" placeholder="Ex: Show do Coldplay" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="localEvento">
                        <i data-lucide="map-pin" style="width:12px;height:12px;"></i> Local
                    </label>
                    <input type="text" name="localEvento" id="localEvento"
                           class="form-control-custom" placeholder="Ex: Arena BT" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="dataEvento">
                        <i data-lucide="calendar" style="width:12px;height:12px;"></i> Data
                    </label>
                    <input type="date" name="dataEvento" id="dataEvento"
                           class="form-control-custom" required>
                </div>

                <div class="form-group" style="grid-column:1/-1;">
                    <label class="form-label" for="nomeComprador">
                        <i data-lucide="user" style="width:12px;height:12px;"></i> Nome do Comprador
                    </label>
                    <input type="text" name="nomeComprador" id="nomeComprador"
                           class="form-control-custom" placeholder="Nome completo" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="precoBase">
                        <i data-lucide="dollar-sign" style="width:12px;height:12px;"></i> Preco Base (R$)
                    </label>
                    <input type="number" name="precoBase" id="precoBase"
                           class="form-control-custom" placeholder="0.00" step="0.01" min="0" required>
                </div>

                <%-- Preview do valor final --%>
                <div class="form-group">
                    <label class="form-label">
                        <i data-lucide="tag" style="width:12px;height:12px;"></i> Valor Final
                    </label>
                    <div style="background:var(--surface2);border:1px solid var(--border);border-radius:var(--radius);
                                padding:.65rem .9rem;display:flex;align-items:center;justify-content:space-between;">
                        <span id="valorPreview" style="font-family:var(--font-head);font-weight:700;
                              color:var(--accent);font-size:1.1rem;">R$ 0,00</span>
                        <span style="color:var(--muted);font-size:.7rem;" id="previewLabel">sem alteração</span>
                    </div>
                </div>
            </div>

            <%-- Campo extra condicional --%>
            <div class="form-group extra-field" id="extraField">
                <label class="form-label" id="extraLabel">Campo Extra</label>
                <input type="text" name="extra" id="extra"
                       class="form-control-custom" placeholder="...">
            </div>

            <hr class="divider">

            <div style="display:flex;gap:.75rem;justify-content:flex-end;">
                <a href="${pageContext.request.contextPath}/ingressos/lista" class="btn-outline-custom">Cancelar</a>
                <button type="submit" class="btn-primary-custom">
                    <i data-lucide="save" style="width:14px;height:14px;"></i> Cadastrar Ingresso
                </button>
            </div>
        </form>
    </div>
</div>

<script>lucide.createIcons();</script>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script>
    // Atualiza label de preview conforme tipo
    const tipoSel = document.getElementById('tipo');
    const prevLabel = document.getElementById('previewLabel');
    if (tipoSel && prevLabel) {
        tipoSel.addEventListener('change', () => {
            const v = tipoSel.value;
            prevLabel.textContent = v === 'VIP' ? '+50%' : v === 'MEIA' ? '-50%' : 'sem alteração';
        });
    }
</script>
</body>
</html>
