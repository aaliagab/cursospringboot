// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuarios();
  $('#usuarios').DataTable();
  cargarEmailUser();
});

function cargarEmailUser(){
	document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarUsuarios(){
    const request = await fetch('api/usuarios', {
      method: 'GET',
      headers: getHeaders()
    });
    const usuarios = await request.json();
    let listadoHTML = '';
    let botonEliminar = '<a href="#" onclick = "eliminarUsuario(123)" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
    for(let user of usuarios){
		let botonEliminar = '<a href="#" onclick = "eliminarUsuario('+user.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
      	let usuario = '<tr><td>'+user.id+
                    '</td><td>'+user.nombre+' '+user.apellido+
                    '</td><td>'+user.email+
                    '</td><td>'+user.telefono+
                    '</td><td>'+botonEliminar+'</td></tr>';
      listadoHTML +=usuario;
    }  
    document.querySelector('#usuarios tbody').outerHTML = listadoHTML;
}

function getHeaders(){
	return {
	        'Accept': 'application/json',
	        'Content-Type': 'application/json',
	        'Autorization': localStorage.token
	      };
}

async function eliminarUsuario(id){
	if(confirm('Desea eliminar este registro?')){
		const request = await fetch('api/usuarios/'+id, {
	      method: 'DELETE',
	      headers: getHeaders()
	    });
	    location.reload()
	}
}
