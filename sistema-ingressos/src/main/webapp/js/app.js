// Sistema de Ingressos — JS utilitário

document.addEventListener('DOMContentLoaded', () => {

    // Mostra/esconde campo extra conforme tipo selecionado
    const tipoSelect = document.getElementById('tipo');
    const extraField = document.getElementById('extraField');
    const extraLabel = document.getElementById('extraLabel');
    const extraInput = document.getElementById('extra');

    if (tipoSelect) {
        tipoSelect.addEventListener('change', () => {
            const val = tipoSelect.value;
            if (val === 'VIP') {
                extraLabel.textContent = 'Benefícios VIP';
                extraInput.placeholder = 'Ex: Acesso backstage, open bar...';
                extraField.classList.add('visible');
            } else if (val === 'MEIA') {
                extraLabel.textContent = 'Documento Comprobatório';
                extraInput.placeholder = 'Ex: Carteira de estudante, RG...';
                extraField.classList.add('visible');
            } else {
                extraField.classList.remove('visible');
            }
        });

        // Gatilha no load caso já venha selecionado
        tipoSelect.dispatchEvent(new Event('change'));
    }

    // Preview do valor em tempo real
    const precoInput = document.getElementById('precoBase');
    const previewEl  = document.getElementById('valorPreview');

    function atualizarPreview() {
        if (!precoInput || !previewEl || !tipoSelect) return;
        const base = parseFloat(precoInput.value) || 0;
        const tipo = tipoSelect.value;
        let final = base;
        if (tipo === 'VIP')  final = base * 1.5;
        if (tipo === 'MEIA') final = base * 0.5;
        previewEl.textContent = 'R$ ' + final.toFixed(2).replace('.', ',');
    }

    if (precoInput) {
        precoInput.addEventListener('input', atualizarPreview);
        if (tipoSelect) tipoSelect.addEventListener('change', atualizarPreview);
        atualizarPreview();
    }

    // Confirmação de ações destrutivas
    document.querySelectorAll('[data-confirm]').forEach(el => {
        el.addEventListener('click', e => {
            if (!confirm(el.dataset.confirm)) e.preventDefault();
        });
    });
});
