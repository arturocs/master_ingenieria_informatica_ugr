

const formulario_registro = document.getElementById('register');
const formulario_login = document.getElementById('login');


formulario_registro.addEventListener('submit', (event) => {
    event.preventDefault();

    register(
        document.querySelector('#Nombre').value,
        document.querySelector('#Apellidos').value,
        document.querySelector('#email').value,
        document.querySelector('#Contrasena').value
    )
});

formulario_login.addEventListener('submit', (event) => {
    event.preventDefault();
    login(
        document.querySelector('#email1').value,
        document.querySelector('#Contrasena1').value
    )
});

async function fetchAsync(url, methodHttp) {
    let response = await fetch(url, {
        method: methodHttp
    });
    let data = await response.json();
    return data;
}

async function register(email, nombre, apellido, contrasena) {
    let response = await fetch('http://localhost:8080/productos/api/ordenadores/registrar', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, nombre, apellido, contrasena })
    });

}

async function login(email, contrasena) {
    let response = await fetch('http://localhost:8080/productos/api/ordenadores/iniciar_sesion', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, contrasena })
    });
    if (response.status == 200) {
        window.location.href = "http://localhost:8080/productos/cliente.html"
    } else if (response.status == 301) {
        window.location.href = "http://localhost:8080/productos/admin.html"
    }
}



function crear_tabla() {

    fetchAsync('http://localhost:8080/productos/api/ordenadores', 'GET').then((data) => {

        tabla_sup = document.getElementById('productos');
        tb=document.createElement('tbody')
        tabla = document.createElement('table');
        tabla.setAttribute("id", "prod")
        tabla.appendChild(tb);

        headerTR = ['Nombre', 'Precio', 'Descripcion'];

        tr = document.createElement('tr');
        th1 = document.createElement('th');
        th2 = document.createElement('th');
        th3 = document.createElement('th');
        th.textContent = "Nombre";
        th2.textContent = "Precio";
        th3.textContent = "Descripcion";
        tr.appendChild(th1);
        tr.appendChild(th2);
        tr.appendChild(th3);
        tb.appendChild(tr);

        data.forEach((value) => {
            console.log(value)
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


            tb.appendChild(tr);
        });

        tabla_sup.appendChild(tabla);
    })
}


window.onload = () => { crear_tabla(); }