$('#search').on('click', function() {
	$('#result').css("font-weight", "");
	$('#result').css("color", "");
	$('#result').hide();

	var source = $('#source').val();
	var destination = $('#destination').val();
	var amount = parseInt($('#amount').val());
	var error = false;

	if (source === '' || source.length != 20) {
		$('#invalid-source-account-id').text("Invalid account id");
		$('#invalid-source-account-id').show();
		error = true;
	} else {
		$('#invalid-source-account-id').hide();
	}

	if (destination === '' || destination.length != 20) {
		$('#invalid-destination-account-id').text("Invalid account id");
		$('#invalid-destination-account-id').show();
		error = true;
	} else {
		$('#invalid-destination-account-id').hide();
	}

	if (isNaN(amount) || amount < 0) {
		$('#invalid-amount').text("Invalid amount");
		$('#invalid-amount').show();
		error = true;
	} else {
		$('#invalid-amount').hide();
	}

	if (error) {
		return;
	}

	$.ajax({
		url: "/api/transfer/",
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify({
			"from": source,
			"to": destination,
			"amount": amount
		}),
		success: function() {
			$('#result').html('Successfully transfered <strong>' + amount + '</strong> from <strong>' + source + '</strong> to <strong>' + destination + '</strong>');
			$('#result').css("color", "green");
			$('#result').show();
		},
		error: function(xhr) {
			$('#result').text(xhr.responseJSON.message);
			$('#result').css("font-weight", "bold");
			$('#result').css("color", "red");
			$('#result').show();
		}
	})
});