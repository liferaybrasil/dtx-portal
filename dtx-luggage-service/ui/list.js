var list = document.querySelector('.list');


// Insert fetch data method below

WeDeploy
	.data('https://db-dtx.wedeploy.io')
	.get('luggage')
	.then(function(luggage) {
		console.log(movie);
	});

// Insert fetch data method above

function appendTasks(tasks) {
	var taskList = '';

	tasks.forEach(function(task) {
		taskList += `<input type="text" value="${task.name}" readonly>`;
	});

	list.innerHTML = taskList;
}