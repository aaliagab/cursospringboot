// Call the dataTables jQuery plugin
$(document).ready(function() {
  //on ready
});

async function registrarUsuario(){
	let datos = {};
	datos.nombre = document.getElementById('txtNombre').value;
	//tambien puede ser document.querySelector('#txtNombre').value
	datos.apellido = document.getElementById('txtApellidos').value;
	datos.email = document.getElementById('txtEmail').value;
	datos.telefono = document.getElementById('txtTelefono').value;
	datos.password = document.getElementById('txtPassword').value;
	
	let repPassword = document.getElementById('txtRepeatPassword').value;
	
	if(repPassword!=datos.password){
		alert('Las contraseñas no coinciden');
		return;
	}
	
    const request = await fetch('api/usuarios', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(datos)
    });
    
    const usuarios = await request.json();
    
}
