$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateUserForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidUserIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "UserManage",   
			type : type,  
			data : $("#formUser").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onUserSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onUserSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divUserGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidUserIDSave").val("");  
	$("#formUser")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidUserIDSave").val($(this).closest("tr").find('#hidUserIDUpdate').val());     
	$("#UName").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#UPhoneNo").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#UEmail").val($(this).closest("tr").find('td:eq(2)').text());
	$("#UsrName").val($(this).closest("tr").find('td:eq(3)').text());   
	$("#UPass").val($(this).closest("tr").find('td:eq(4)').text()); 
}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "UserManage",   
		type : "DELETE",   
		data : "UsID=" + $(this).data("userid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onUserDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onUserDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divUserGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateUserForm() 
{  
	// NAME-----------------------
	if ($("#UName").val().trim() == "")  
	{   
		return "Insert Name.";  
	} 
	
	// PHONE---------------------------  
	var tmpPhone = $("#UPhoneNo").val().trim();
	if (!$.isNumeric(tmpPhone)) 
	{
	return "Insert Phone No.";
	}
	
	
	// EMAIL------------------------------
	if ($("#UEmail").val().trim() == "")  
	{   
		return "Insert Email.";  
	}
	
	// USERNAME-------------------------------
	if ($("#UsrName").val().trim() == "")  
	{   
		return "Insert Username.";  
	}
	
	// PASSWORD---------------------------  
	if ($("#UPass").val().trim() == "")  
	{   
		return "Insert Password.";  
	}
		
	return true; 
}