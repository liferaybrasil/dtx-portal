var form = document.querySelector('form');

form.addEventListener('submit', function(e) {
	e.preventDefault();

	WeDeploy
		.data('db-dtx.wedeploy.io')
		.create('luggage', {
			luggageId : 1,
			location: 'Balcão'
		})
		.then(function(response) {
			form.reset();
			form.item.focus();
			console.info('Saved:', response);
		})
		.catch(function(error) {
			console.error(error);
		});

		// Insert save data method below

// Insert save data method above
});


//name : form.item.value