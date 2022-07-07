$('#search').on('click', function() {
	$("#account-table-body").empty();
	$('#account-container').hide();
	$("#transactions-table-body").empty();
	$('#transactions-container').hide();

	var account = $('#account').val();

	if (account === '' || account.length != 20) {
		$('#invalid-account-id').text("Invalid account id");
		$('#invalid-account-id').show();
		return;
	} else {
		$('#invalid-account-id').hide();
	}

	$.ajax({
		url: "/api/account/" + account,
		method: 'GET',
		success: function(data) {
			$('#account-table').append('<tr>' +
				'<td>' + data.id + '</td>' +
				'<td>' + data.active + '</td>' +
				'<td>' + data.name + '</td>' +
				'<td>' + data.surname + '</td>' +
				'<td>' + data.balance + '</td>' +
				'</tr>');
			$('#account-container').show();

			for (i = 0; i < data.transactions.length; i++) {
				$('#transactions-table').append('<tr>' +
					'<td>' + data.transactions[i].id + '</td>' +
					'<td>' + data.transactions[i].amount + '</td>' +
					'<td>' + data.transactions[i].timestamp + '</td>' +
					'<td>' + data.transactions[i].type + '</td>' +
					'</tr>');
			}
			$('#transactions-table-body tr:last').css("font-weight", "bold");
			$('#transactions-container').show();
		},
		error: function(xhr) {
			$('#invalid-account-id').text(xhr.responseJSON.message);
			$('#invalid-account-id').show();
		}
	})
});