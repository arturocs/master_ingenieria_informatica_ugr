# Práctica 2: Algoritmos genéticos

## Introducción

En esta práctica se pide resolver el problema de la asignación cuadrática mediante algoritmos genéticos. Este problema se puede reducir encontrar un p que minimice la siguiente sumatoria:

 ![\sum _{a,b\in P}w(a,b)\cdot d(f(a),f(b))](https://wikimedia.org/api/rest_v1/media/math/render/svg/05d30494a1a27d09c83ad564ccaf41194006d89e)

 Donde w y d son dos matrices que vienen en el archivo `tai256c.dat` de la biblioteca [QAPLIB](http://www.seas.upenn.edu/qaplib/). Estas dos matrices tienen cada una una peculiaridad que permite aplicar ciertas optimizaciones. El código desarrollado hace uso de estas optimizaciones y por tanto no es válido para otros archivos de [QAPLIB](http://www.seas.upenn.edu/qaplib/).

## Instrucciones

Para ejecutar este proyecto es necesario tener instalado el lenguaje de programación Rust. Se puede instalar siguiendo las [instrucciones oficiales](https://www.rust-lang.org/tools/install) o desde los repositorios de algunas distribuciones Linux. La versión mínima necesaria es la 1.43 y se ha comprobado que funciona en la última versión estable, a fecha de escritura la 1.50.

Una vez instalado Rust basta con ir a la carpeta raíz del proyecto y ejecutar:

```
cargo run --release
```

Si se quiere generar la documentación hay que ejecutar la siguiente linea:

```
cargo doc --no-deps --open
```

