# Ejercicio 2: Estimación

**Por: Arturo Cortés Sánchez**

Usando alguno de los modelos de estimación vistos en este tema realice las siguientes tareas:

- Escoja 3 tipos diferentes de software (por ejemplo: aplicación Web, sistema empotrado y middleware de alto rendimiento) y realice una comparativa entre las estimaciones realizadas para los 3 casos suponiendo que el tamaño del equipo es el mismo y la experiencia es similar en los 3 campos.

- Analice los resultados de las estimaciones e indique qué factores son los que podrían haber hecho que los resultados hayan sido diferentes (o similares)



He seleccionado el método Putnam  
$$
E=[LOC · B^{0,333}/P]^3*(1/t^4)
$$
El proyecto en cuestión tendría unas 70000 lineas de código. La formula por defecto del modelo de Putnam no permite fijar el numero de personas, pero podemos fijar los recursos humanos y despejar el tiempo. De esta forma obtenemos que para un equipo de 20 personas-año:



* Sistema empotrado:
  $$
  20 =[70000 · 0,395^{0,333}/2000]^3*(1/t^4)
  $$

  $$
  t=5,39565
  $$

* Software científico:
  $$
  20 =[70000 · 0,395^{0,333}/12000]^3*(1/t^4)
  $$

  $$
  t=1,40744
  $$

  

* Aplicación de gestión:
  $$
  20 =[70000 · 0,395^{0,333}/28000]^3*(1/t^4)
  $$

$$
t=0,7455
$$

El tiempo resultante está en años. Podemos comprobar que para un proyecto de un sistema empotrado el tiempo de desarrollo es muy superior que el resto de casos. Sin embargo la diferencia entre el desarrollo de un software científico y una aplicación de gestión es "solo" de aproximadamente el doble de tiempo.

