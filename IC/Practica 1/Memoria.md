## Memoria de la primera práctica de IC: OCR sobre MNIST con Keras

 **Por: Arturo Cortés Sánchez**

Inicialmente partí del ejemplo de Keras que había en Google classroom

```python
#!/usr/bin/env python
import keras
from keras.datasets import mnist
from keras.utils import to_categorical
from keras import models
from keras import layers

(train_images, train_labels), (test_images, test_labels) = mnist.load_data()

train_images = train_images.reshape((60000, 28 * 28))
train_images = train_images.astype('float32') / 255

test_images = test_images.reshape((10000, 28 * 28))
test_images = test_images.astype('float32') / 255

train_labels = to_categorical(train_labels)
test_labels = to_categorical(test_labels)

network = models.Sequential()
network.add(layers.Dense(512, activation='relu', input_shape=(28 * 28,)))
network.add(layers.Dense(10, activation='softmax'))

network.compile(optimizer='rmsprop', loss='categorical_crossentropy', metrics=['accuracy'])

network.fit(train_images, train_labels, epochs=5, batch_size=128)
test_loss, test_acc = network.evaluate(test_images, test_labels)

print('test_acc:', test_acc)
```

Al ejecutarlo obtuve una precisión de 0.980400025844574, algo mas alta de la que se especifica en el python notebook de ejemplo. Tras unas cuantas ejecuciones me di cuenta que los resultados podían variar considerablemente debido a la distribución inicial de pesos en la red neuronal y puede que otros factores. Así que decidí hacer la media de diez resultados obteniendo una precisión de 0.9790999889373779, la cual se acercaba mas al resultado del notebook. 

Viendo como pueden variar los resultados entre ejecuciones de una misma red, decidí que de ahora en adelante cada vez que se mencione un resultado de precisión, será el resultado medio de varias ejecuciones.

Lo primero que hice fue duplicar el numero de epochs, pues me parecía muy bajo. Al hacerlo la precisión aumentó ligeramente obteniendo  0.9814900040626526, lo que quiere decir que la red del ejemplo sufría de underfitting. Cuando vi esto decidí aumentar considerablemente el numero de epochs para tratar de causar overfitting, así que fijé el numero a 50 y para mi sorpresa la precisión volvió a mejorar obteniendo 0.9836279928684235. 

Continué aumentando los epochs pero el resultado prácticamente no cambió, así que procedí a experimentar con otras partes del código. Lo siguiente que hice fue aumentar el número de capas duplicando la capa oculta del ejemplo, pero la precisión bajó a 0.982699990272522. Al ver esto eliminé la segunda capa oculta y en lugar de añadir nuevas capas decidí aumentar el numero de neuronas pasando de 512 a 1024 y el resultado mejoró de nuevo pasando a ser 0.9846100002527237. Viendo como habían mejorado los resultados al aumentar las neuronas de una capa, decidí volver a repetir el proceso y duplicar el numero de neuronas pasando a 2048, de nuevo el resultado mejoró y obtuve una precisión de 0.9850699961185455. Quise repetir la idea una cuarta vez y usar 4096 neuronas pero la red resultante fue bastante lenta de entrenar y el resultado prácticamente no varió.

Tras haber experimentado con la topología de la red neuronal decidí probar a cambiar el algoritmo de entrenamiento. Fui a la documentación de Keras para ver que optimizadores soportaba, cogí la red que mejor resultado me había dado, la entrené con cada uno de ellos y obtuve los siguientes resultados:

- SGD:  0.9636500000953674

- RMSprop: 0.9854499995708466

- Adam: 0.9852299988269806

- Adadelta: 0.9378400027751923

- Adagrad: 0.9378400027751923

- Adamax: 0.9836999952793122

- Nadam: 0.9837100088596344

- Ftrl: 0.8971099972724914

  

Tras esta prueba descubrí que los mejores eran RMSprop y Adam, por lo que de ahora en adelante realizaría mis pruebas con esos dos optimizadores. 



Como no conseguía obtener mejores resultados de esta red neuronal básica decidí pasar a usar una red neuronal convolucional. Para ello me fui a la documentación de keras y encontré el siguiente [ejemplo](https://keras.io/examples/vision/mnist_convnet/) de red convolucional. Incorporé la topologia de red de ese ejemplo en mi código anterior y a partir de él creé distintos modelos que entrene con varios optimizadores y durante 10, 20 y 30 epochs para ver si con un aumento de epochs se tendía al overfitting.

Ejemplo de la documentación de keras:

```python
network = models.Sequential()
network.add(keras.Input(shape=input_shape))
network.add(layers.Conv2D(32, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Conv2D(64, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Flatten())
network.add(layers.Dropout(0.5))
network.add(layers.Dense(num_classes, activation="softmax"))
```

Resultados:

| Optimizador\nº epochs | 10                 | 20                 | 30                 |
| --------------------- | ------------------ | ------------------ | ------------------ |
| SGD                   | 0.9691399931907654 | 0.9772000074386596 | 0.9813400030136108 |
| RMSprop               | 0.989959990978241  | 0.9911999940872193 | 0.991700005531311  |
| Adam                  | 0.9924400091171265 | 0.9925800085067749 | 0.9930999994277954 |

Podemos ver que al aumentar los epochs la precisión aumenta, por lo que en el caso de que esta sea la mejor red, habría que hacer pruebas con 	epochs mayores.



Lo primero que se me ocurrió fue añadirle una capa convolucional mas de un tamaño mayor.

```python
network = models.Sequential()
network.add(keras.Input(shape=input_shape))
network.add(layers.Conv2D(32, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Conv2D(64, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Conv2D(128, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Flatten())
network.add(layers.Dropout(0.5))
network.add(layers.Dense(num_classes, activation="softmax"))
```

Resultados:

| Optimizador\nº epochs | 10                 | 20                 | 30                 |
| --------------------- | ------------------ | ------------------ | ------------------ |
| SGD                   | 0.961240005493164  | 0.9738399982452393 | 0.978440010547638  |
| RMSprop               | 0.9862799882888794 | 0.9876200079917907 | 0.9866199970245362 |
| Adam                  | 0.9895800113677978 | 0.9898599863052369 | 0.9902599930763245 |

Añadir dicha capa ha sido claramente perjudicial para el modelo, así que la siguiente opción que se me ocurrió fue duplicar el número de capas de la red:

```python
network = models.Sequential()
network.add(keras.Input(shape=input_shape))
network.add(layers.Conv2D(32, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Conv2D(32, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Conv2D(64, kernel_size=(3, 3), activation="relu"))
network.add(layers.Conv2D(64, kernel_size=(3, 3), activation="relu"))
network.add(layers.Flatten())
network.add(layers.Dropout(0.5))
network.add(layers.Dense(num_classes, activation="softmax"))
```

Resultados:

| Optimizador\nº epochs | 10                 | 20                 | 30                 |
| --------------------- | ------------------ | ------------------ | ------------------ |
| SGD                   | 0.9696999907493591 | 0.9809200048446656 | 0.9857999920845032 |
| RMSprop               | 0.9905800104141236 | 0.990779983997345  | 0.9888400077819824 |
| Adam                  | 0.9916000008583069 | 0.9925599932670593 | 0.9919999957084655 |



Si bien los resultados son mejores que con la capa de tamaño 128, siguen siendo peores que los del ejemplo inicial. El próximo experimento que hice fue invertir el orden de las capas convolucionales.

```python
network = models.Sequential()
network.add(keras.Input(shape=input_shape))
network.add(layers.Conv2D(64, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Conv2D(32, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Flatten())
network.add(layers.Dropout(0.5))
network.add(layers.Dense(num_classes, activation="softmax"))
model_list.append(network)
```



| Optimizador\nº epochs | 10                 | 20                 | 30                 |
| --------------------- | ------------------ | ------------------ | ------------------ |
| RMSprop               | 0.9890399932861328 | 0.9912699937820435 | 0.9917700052261352 |
| Adam                  | 0.9928699970245362 | 0.9929799914360047 | 0.9932399988174438 |



Para mi sorpresa los resultados mejoraron a los del ejemplo inicial, por lo que esta topología podría ser la definitiva.

 A continuación probé a invertir las capas de una de las redes anteriores:

```python
network = models.Sequential()
network.add(keras.Input(shape=input_shape))
network.add(layers.Conv2D(128, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Conv2D(64, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Conv2D(32, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Flatten())
network.add(layers.Dropout(0.5))
network.add(layers.Dense(num_classes, activation="softmax"))
```



Resultados:

| Optimizador\nº epochs | 10                 | 20                 | 30                 |
| --------------------- | ------------------ | ------------------ | ------------------ |
| SGD                   | 0.9479399919509888 | 0.9641600012779236 | 0.9710400104522705 |
| RMSprop               | 0.9843599915504455 | 0.9843600034713745 | 0.9827800035476685 |
| Adam                  | 0.9860799908638    | 0.9875399827957153 | 0.9880200028419495 |

Curiosamente los resultados fueron aun peores que la red de tres capas original.



Los dos siguientes intentos son bastante distintos a los anteriores, ya que no pretendía mejorar el resultado, si no ver como el numero de neuronas afecta al resultado. Para ello dejé una única capa convolucional

```python
network = models.Sequential()
network.add(keras.Input(shape=input_shape))
network.add(layers.Conv2D(32, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Flatten())
network.add(layers.Dropout(0.5))
network.add(layers.Dense(num_classes, activation="softmax"))
```

Resultados:

| Optimizador\nº epochs | 10                 | 20                 | 30                 |
| --------------------- | ------------------ | ------------------ | ------------------ |
| Adam                  | 0.9815600037574768 | 0.9845999956130982 | 0.9854400038719178 |
| RMSprop               | 0.9862599968910217 | 0.9861399888992309 | 0.9865599989891052 |

Evidentemente el resultado iba a ser malo, pero curiosamente para un número de epochs bajo, el resultado no es demasiado distinto al del resto de intentos.



En este intento simplemente dupliqué el numero de neuronas de la capa convolucional para compararlo con el resultado anterior.

```python
network = models.Sequential()
network.add(keras.Input(shape=input_shape))
network.add(layers.Conv2D(64, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Flatten())
network.add(layers.Dropout(0.5))
network.add(layers.Dense(num_classes, activation="softmax"))
```



Resultados:

| Optimizador\nº epochs | 10                 | 20                 | 30                 |
| --------------------- | ------------------ | ------------------ | ------------------ |
| Adam                  | 0.983679986000061  | 0.9860200047492981 | 0.986300015449524  |
| RMSprop               | 0.9869199872016907 | 0.9865399956703186 | 0.9867999911308288 |

Podemos comprobar que hay cierta mejoría, pero casi no es apreciable



En este ultimo intento decidí poner dos redes convolucionales de 64 neuronas. 

```python
network = models.Sequential()
network.add(keras.Input(shape=input_shape))
network.add(layers.Conv2D(64, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Conv2D(64, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Flatten())
network.add(layers.Dropout(0.5))
network.add(layers.Dense(num_classes, activation="softmax"))
```



Resultados:

| Optimizador\nº epochs | 10                 | 20                 | 30                 |
| --------------------- | ------------------ | ------------------ | ------------------ |
| RMSprop               | 0.9890399932861328 | 0.9912699937820435 | 0.9917700052261352 |
| Adam                  | 0.9928699970245362 | 0.9928699970245362 | 0.9928699970245362 |



El resultado es de los mejores hasta el momento, pero es inferior al cuarto intento (capa de 64 y capa de 32 neuronas), así que tras todos estos experimentos me puse a hacer mas pruebas con la red de 64 y 32 neuronas, y con el optimizador adam y 40 epochs obtuve una precisión de 0.993399977684021 sobre el conjunto de pruebas.

