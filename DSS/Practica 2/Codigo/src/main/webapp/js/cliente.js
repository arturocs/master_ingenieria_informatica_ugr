async function fetchAsync(url, methodHttp) {
    let response = await fetch(url, {
        method: methodHttp
    });
    let data = await response.json();
    return data;
}

async function nuevo_producto(title, album, artist, duration) {
    let response = await fetch('http://localhost:8080/productos/api/ordenadores/', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title, album, artist, duration })
    });
}


function comprar(id) {
    fetch('http://localhost:8080/productos/api/ordenadores/comprar/' + id, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email: "d", contrasena: "d" })
    }).then((r) => {
        console.log(r);
    });

}


function crear_tabla() {

    fetchAsync('http://localhost:8080/productos/api/ordenadores', 'GET').then((data) => {

        let htmlTable = document.getElementById('productos');
        let table = document.createElement('table');
        let tableBody = document.createElement('tbody');
        table.setAttribute("id", "prod")
        table.appendChild(tableBody);



        let headerTR = ['Nombre', 'Precio', 'Descripcion', 'Comprar'];

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
            td.innerHTML = "<button type=\"button\" value=\" " + value.id + "\" onclick=\"comprar(" + value.id + ");\" > Comprar</button>"
            tr.appendChild(td);
            tableBody.appendChild(tr);
        });

        htmlTable.appendChild(table);
    })
}


window.onload = () => { crear_tabla(); }