/**
 * 
 */

$('tbody tr').on('click',(e)=>{
	const no = $(e.currentTarget).find('th');
	console.log(no.text());
	location.href=viewPath+"?no="+no.text();
});
$('button[name="writeBtn"]').on('click',()=>{
	location.href=writePath;
});