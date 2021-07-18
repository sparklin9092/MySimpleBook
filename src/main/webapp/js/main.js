$(function() {

	$('#transferBtn').on('click', transferView);
	$('#incomeBtn').on('click', incomeView);
	$('#spendBtn').on('click', spendView);
});

function transferView() {
	
	location.href = '/transfer';
}

function incomeView() {
	
	location.href = '/income';
}

function spendView() {
	
	location.href = '/spend';
}