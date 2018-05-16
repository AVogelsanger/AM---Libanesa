$('#form').submit(function(e) {
	if ($('#form\\:senha').val() != $('#form\\:confirmacao-senha').val()) {
		alert('Senhas nao coincidem!');
		e.preventDefault();
	}
	
	if (!$('#form\\:email').val().contains('@') || !$('#form\\:email').val().contains('.')) {
		alert('E-mail invalido!');
		e.preventDefault();
	}
});
