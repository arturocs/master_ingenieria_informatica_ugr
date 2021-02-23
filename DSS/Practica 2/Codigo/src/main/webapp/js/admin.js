const formulario_nuevo_producto = document.getElementById('nuevo_producto');


formulario_nuevo_producto.addEventListener('submit', (event) => {
    event.preventDefault();

    nuevo_producto(
        document.querySelector('#nombre').value,
        document.querySelector('#precio').value,
        document.querySelector('#descripcion').value,
    )
    location.reload()
});

async function fetchAsync (url, methodHttp) {
  let response = await fetch(url,{
      method: methodHttp
  });
  let data = await response.json();
  return data;
}

async function nuevo_producto(nombre, precio, descripcion){
    let response = await fetch('http://localhost:8080/productos/api/ordenadores/',{
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({nombre, precio, descripcion})
    });
}


function remove(id){
    fetchAsync('http://localhost:8080/productos/api/ordenadores/catalogo/'+id, 'DELETE').then((r) => {
        console.log(r);
        location.reload()
    }).catch(e=>location.reload())
}


function crear_tabla(){

    fetchAsync('http://localhost:8080/productos/api/ordenadores', 'GET').then( (data) => {

        let htmlTable = document.getElementById('productos');
        let table = document.createElement('table');
        let tableBody = document.createElement('tbody');
        table.setAttribute("id","prod")
        table.appendChild(tableBody);

        let headerTR = ['Id', 'Nombre', 'Precio', 'Descripcion', 'Borrar'];

        let tr = document.createElement('tr');
        headerTR.forEach((header)=> {
            let th = document.createElement('th');
            th.textContent = header;
            tr.appendChild(th);
        })
        tableBody.appendChild(tr);



        data.forEach((value) => {
            tr = document.createElement('tr');
            
            let td = document.createElement('td');
            td.textContent = value.id;
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.nombre
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.precio
            tr.appendChild(td);

            td = document.createElement('td');
            td.textContent = value.descripcion
            tr.appendChild(td);


            td = document.createElement('td');
            td.innerHTML = "<button type=\"button\" value=\" "+ value.id + "\" onclick=\"remove("+ value.id + ");\" >Borrar producto</button>"
            tr.appendChild(td);
            tableBody.appendChild(tr);
        });

        htmlTable.appendChild(table);
    })
}

window.onload = ()=>{crear_tabla();}