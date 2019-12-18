if (!jQuery) {throw new TypeError("jQuery is required.");};
(function ($, window, document, undefined) {
	'use strict';

	$('#loginForm').on('click', function(){
		$.ajax({
			url:"usr/form",
		}).done(function(response){
			//$('.modal').html(response);
			$('#modalLauncher').triggerHandler('click');
		});
	});
	/*
	$.ajax({
		  url: "test.html",
		  context: document.body
		}).done(function() {
		  $( this ).addClass( "done" );
		});	
	*/
	
	
	
})(jQuery, window, document);
