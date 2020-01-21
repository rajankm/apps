if (!jQuery) {throw new TypeError("jQuery is required.");};
(function ($, window, document, undefined) {
	'use strict';

	$('#loginModal').on('click', function(){
		$.ajax({
			url:"user/login",
		}).done(function(response){
			$('#modal').html(response);
			$('#appModal').modal('show');
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
