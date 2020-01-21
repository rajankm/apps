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

	$('#myform').submit(function(e){
		 e.preventDefault();
		$.ajax({
			url: $(this).attr('action'),
			type: $(this).attr('method'),
			contentType: "application/json; charset=utf-8",
			dataType: 'json',
			data: $(this).serialize()
		}).done(function(data) {
			if(data.status=="OK"){
				
			}else{
				$('#errorSpan').empty().append(data.value).css('visibility','visible');
			}
		}).fail(function(data){
			alert(JSON.stringify(data));
		});
	});

})(jQuery, window, document);
