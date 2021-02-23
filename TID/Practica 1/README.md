# Práctica 1 TID: Preparación de Datos

**Por: Arturo Cortés Sánchez**

### 1. Discretización

Antes de cargar accidentes.xls en knime he reemplazado los valores desconocidos por celdas vacías y he creado una nueva columna a la que he llamado gravedad. Dicha columna resulta de realizar el siguiente calculo de 3 x FATALITIES + 2 x INJURY_CRASH + PRPTYDMG_CRASH.

​	

a) ![](./img/Untitled1.svg)



![](./img/arbol_a.svg)



​	b)![](./img/Untitled.svg)

![](./img/arbol_b.svg)

​	c)

* MONTH: Mes del año en el que ocurrió el accidente. Una posible categorización sería agruparlos en trimestres

* WEEKDAY: Día de la semana en el que ocurrió el accidente. Una posible categorización sería agruparlos en fin de semana y entre semana.

* HOUR:  Hora del día en el que ocurrió el accidente. Una posible categorización sería agruparlas en si es de dia o de noche.

* VEH_INVL: Numero de vehículos involucrados en el accidente.  Una posible categorización sería agruparlo en si el accidente ha involucrado o no a varios vehículos.

* NON_INVL: Numero de no motoristas involucrados en el accidente.  Una posible categorización sería agruparlo en si el accidente ha involucrado o no a varios no motoristas.

* LAND_USE: Población del área en la que ha ocurrido el accidente. Una posible categorización sería distinguir entre si es ciudad o pueblo.

* MAN_COL: Forma de colisionar. Una posible categorización sería ver si se ha colisionado con el entorno o con otro vehículo.

* REL_JCT: Relación con un cruce. Una posible categorización sería ver si el accidente ha ocurrido o no en un cruce.

* REL_RWY: Relación con la acera. Una posible categorización sería  sería ver si el accidente ha ocurrido o no sobre una acera.

* TRAF_WAY: Tipo de tráfico. Una posible categorización sería ver si la carretera es de doble sentido o no.

* NUM_LAN:  Numero de carriles. Una posible categorización sería ver si la carretera tiene mas de un carril o no.

* ALIGN: Alineamiento de la carretera. Una posible categorización sería ver si la carretera es curva o no.

* PROFILE: Perfil de la carretera . Una posible categorización sería clasificarla por rangos de inclinación.

* SUR_COND: Condición de la superficie de la carretera. Una posible categorización sería ver si la carretera estaba en mal estado o no.

* TRAF_CON: Dispositivo de control de trafico. Una posible categorización sería ver si había señales luminosas o no.

* SPD_LIM: Limite de velocidad. Una posible categorización sería dividir las posibles velocidades entre 0 y la máxima velocidad permitida en 4 tramos.

* LGHT_CON: Condiciones de luz. Una posible categorización sería ver si es de día o de noche.

* WEATHER: Condiciones climáticas. Una posible categorización serían distintos eventos climáticos que pueden afectar a la conducción. Por ejemplo lluvia, nieve o niebla.

* PED_ACC: Tipo de accidente de peatón o ciclista. Una posible categorización sería ver si  el accidente ha afectado a un peatón/ciclista, y si lo ha hecho ¿Cómo ha acabado? Indemne, contusión leve, rotura de hueso o muerte

* REGION: Región del país. Una posible categorización sería especificar el estado en el que ha ocurrido el accidente.

  

### 2. Valores perdidos

a)

![](./img/Untitled1.svg)

![](./img/2a.svg)

b)

![](./img/arbol_a.svg)

c)

![2c](./img/2c.svg)

![2c2](./img/2c2.svg)

d)

![](./img/2c.svg)

![2d](./img/2d.svg)



e)

![2d2](./img/2d2.svg)

![2d](./img/2d.svg)



### 3. Selección de características

a)

![Untitled1](./img/Untitled1.svg)





b)

![3b](./img/3b.svg)

![3b2](./img/3b2.svg)

### 4. Selección de instancias

![4a2](./img/4a2.svg)

![4a](./img/4a.svg)