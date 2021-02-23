#!/usr/bin/env python
import keras
from keras.datasets import mnist
from keras.utils import to_categorical
from keras import models
from keras import layers
import numpy as np
from datetime import datetime
# Cargamos el dataset mnist
(train_images, train_labels), (test_images, test_labels) = mnist.load_data()

# Transformamos las imagenes para que tengan un tamaño de 28 x 28 en escala de grises
# y codificamoso cada pixel como un numero en coma flotante
train_images = train_images.reshape(60000, 28, 28, 1)
train_images = train_images.astype('float32') / 255

test_images = test_images.reshape(10000, 28, 28, 1)
test_images = test_images.astype('float32') / 255

# Convertimos las etiquetas en vectores one hot para facilitar el aprendizaje de la red
train_labels = to_categorical(train_labels)
test_labels = to_categorical(test_labels)

# Red neuronal
network = models.Sequential()
network.add(keras.Input(shape=(28, 28, 1)))
network.add(layers.Conv2D(64, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Conv2D(32, kernel_size=(3, 3), activation="relu"))
network.add(layers.MaxPooling2D(pool_size=(2, 2)))
network.add(layers.Flatten())
network.add(layers.Dropout(0.5))
network.add(layers.Dense(10, activation="softmax"))

# Fijamos el metodo de entrenamiento
network.compile(optimizer='adam',
                loss='categorical_crossentropy', metrics=['accuracy'])

# Entrenamos la red y contamos cuanto tarda
t1 = datetime.now()
network.fit(train_images, train_labels, epochs=40, batch_size=128)
t = (datetime.now()-t1).seconds

# Evaluamos el conjunto de prueaba
test_loss, test_acc = network.evaluate(test_images, test_labels)

# Obtenemos las etiquetas y las escribimos
a = np.argmax(network.predict(test_images), axis=-1)
for i in a:
    f = open("etiquetas4", "a")
    f.write(str(i))

f.write("\n")
f.close()

# Imprimimos el tiempo de entrenamiento y la precisión sobre el conjunto de pruebas
print(t)
print('test_acc:', test_acc)
