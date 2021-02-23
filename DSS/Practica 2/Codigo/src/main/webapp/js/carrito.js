
async function fetchAsync(url, methodHttp) {
    let response = await fetch(url, {
        method: methodHttp,
    });
    let data = await response.json();
    return data;
}

function borrar(id, email) {
    fetchAsync('http://localhost:8080/productos/api/ordenadores/carrito/' + email + "/" + id, 'DELETE').then((r) => {
        console.log(r);
        location.reload()
    }).catch(e=>location.reload())
}

function crear_tabla() {

    fetchAsync('http://localhost:8080/productos/api/ordenadores', 'GET').then((data) => {


        let htmlTable = document.getElementById('productos');
        let table = document.createElement('table');
        let tableBody = document.createElement('tbody');
        table.setAttribute("id", "prod")
        table.appendChild(tableBody);



        let headerTR = ['Nombre', 'Precio', 'Descripcion', 'Eliminar'];

        let tr = document.createElement('tr');
        headerTR.forEach((header) => {
            let th = document.createElement('th');
            th.textContent = header;
            tr.appendChild(th);
        })
        tableBody.appendChild(tr);



        data.forEach((value) => {
            tr = document.createElement('tr');


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
            td.innerHTML = "<button type=\"button\" value=\" " + value.id + "\" onclick=\"borrar(" + value.id + ",'d');\">Eliminar</button>"
            tr.appendChild(td);
            tableBody.appendChild(tr);
        });

        htmlTable.appendChild(table);
    })
}


window.onload = () => { crear_tabla(); }