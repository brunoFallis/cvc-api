$(document).ready(function(){
	
	function validateFields(){
		
		var errorMessage = '';
		
		var checkin = $('#checkin').val();
		var checkout = $('#checkout').val();
		var adultAmount = $('#adultAmount').val();
		var childAmount = $('#childAmount').val();
		
		if( !checkin || checkin == '' ){
			errorMessage = 'Checkin';
		}
		
		if( !checkout || checkout == '' ){
			errorMessage += errorMessage.length > 0 ? ', Checkout.' : 'Checkout.';
		}
		
		if( !adultAmount ){
			errorMessage += errorMessage.length > 0 ? ', Adultos.' : 'Adultos.';
		}
		
		if( new Date( checkin ) > new Date( checkout ) ){
			errorMessage = 'A data de Checkin não pode ser posterior a data de Checkout';
			$('#error').html( errorMessage );
			return false;
		}
		
		if( errorMessage || errorMessage != '' ){
			$('#error').html( 'Os seguintes campos são obrigatórios: ' + errorMessage );
			return false;
		}
		
		return true;
	}
	
    $(document).on('click', '#sendSearch', function(event){

        event.preventDefault();

        getResults();

    });
	
	$(document).on('click', '.paginate', function(){
		
		var currentVal = Number( $('#paginateNum').val() );
		
		if( $(this).hasClass('back') ){
			currentVal -= 10;		
		}
		else{
			currentVal += 10;	
		}
		
		if( currentVal == 0 ){
			$('#first').val(1);
		}
		else{
			$('#first').val(0);
		}
		
		
		$('#paginateNum').val( currentVal );
		
		getResults();
	});
	
	function getResults(){
		
		$('#error').html( '' );
		
		if( !validateFields() ){
			return false;
		}
		
		$.ajax({
            url: 'http://localhost:8080/hotels/prices/',
            data: $('#form').serializeArray(),
            method: 'GET'
        }).done(function( data ) {
                
        	console.log( data );
			
            $('#list-result').html( data );

        });
		
	}

	$(document).on('.list-group-item', 'mouseover', function() {
		$(this).addClass('active');
	});
	
	$(document).on('.list-group-item', 'mouseout', function() {
		$(this).removeClass('active');
	});
  
});