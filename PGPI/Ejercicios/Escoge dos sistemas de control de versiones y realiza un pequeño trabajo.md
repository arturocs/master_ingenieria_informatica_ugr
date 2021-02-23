## Escoge dos sistemas de control de versiones y realiza un pequeño trabajo de una cara indicando cómo funcionan, cuáles son sus principales características y qué ventajas tiene uno frente al otro.

**Por: Arturo Cortés Sánchez**

Git es un software de control de versiones diseñado por Linus Torvalds, pensando en la eficiencia, la confiabilidad y compatibilidad del  mantenimiento de versiones de aplicaciones cuando éstas tienen un gran  número de archivos de código fuente. Su propósito es llevar registro de los cambios en archivos de  computadora incluyendo coordinar el trabajo que varias personas realizan sobre archivos compartidos en un repositorio de código. Git fue creado por Linus Torvalds en 2005 para el desarrollo del kernel de Linux, con otros desarrolladores de kernel contribuyendo a su desarrollo inicial.



 Apache Subversion es una herramienta de control de versiones open source basada en un repositorio cuyo funcionamiento se asemeja enormemente al de un sistema de ficheros. Utiliza el concepto de revisión para guardar los cambios  producidos en el repositorio. Entre dos revisiones solo guarda el  conjunto de modificaciones (delta), optimizando así al máximo el uso de espacio en disco. SVN permite al usuario crear, copiar y borrar carpetas con la misma flexibilidad con la que se haría si estuviese en un disco duro local.  

Subversion fue creado por CollabNet Inc. en 2000, y ahora es un proyecto Apache de alto nivel que está siendo construido y utilizado por una comunidad global de contribuyentes. 



Un sistema de control de versiones centralizado opera con la idea de  que hay una sola copia del proyecto a la cual los desarrolladores  realizar el commit de los cambios, y un solo lugar en el cual todas las  versiones de un proyecto se encuentran guardadas.

Un sistema de control de versiones distribuido, por otro lado,  trabaja con el principio de que cada desarrollador «clona» el  repositorio del proyecto al disco duro de su dispositivo.

Una copia del proyecto es guardado en cada maquina local, y los cambios  deben ser subidos y bajados  hacia y desde el  repositorio online para actualizar la versión que cada desarrollador  tiene en su maquina local.

![CVS Subversion vs Git](https://api.nerion.es/wp-content/uploads/2016/06/git-vs-subversion.jpg)



### Tabla comparativa



|                            | SVN                                                          | Git                                                          |
| -------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| **Control de versiones**   | Centralizada                                                 | Distribuida                                                  |
| **Repositorio**            | Un repositorio central donde se generan copias de trabajo    | Copias locales del repositorio en las que se trabaja directamente |
| **Autorización de acceso** | Dependiendo de la ruta de acceso                             | Para la totalidad del directorio                             |
| **Seguimiento de cambios** | Basado en archivos                                           | Basado en contenido                                          |
| **Historial de cambios**   | Solo en el repositorio completo, las copias de trabajo incluyen únicamente la versión más reciente | Tanto el repositorio como las copias de trabajo individuales incluyen el historial completo |
| **Conectividad de red**    | Con cada acceso                                              | Solo necesario para pull o fetch                             |



### Fuentes:

https://es.wikipedia.org/wiki/Subversion_(software)

https://es.wikipedia.org/wiki/Git

https://guiadev.com/git-vs-svn/

https://www.nerion.es/blog/subversion-vs-git/

https://www.ionos.es/digitalguide/paginas-web/desarrollo-web/git-vs-svn-una-comparativa-del-control-de-versiones/