

# Especificación de la operación IHotelMgt::makeReservation()

**Por: Arturo Cortés Sánchez**

Utilizando los operadores: exists, includes, select y  asSequence->first, para especificar la operación  IHotelMgt::makeReservation (...) completamente con la notación OCL. En  la postcondición nos podremos referir al estado antes de realizar la  operación con ‘@pre’ de OCL.



```
 IHotelMgt::makeReservation(in res: ReservationDetails, in cus: CustomerId, out:resRef:String):boolean
 
 pre:
 	- cus es un identificador valido de cliente
      	customer->exists(c : CustomerId | c.id = cus) 
    - res.hotelID es un identificador válido de hotel
    	hotel->exists(h : Hotel | h.id = res.hotelId)
    - res.rtname identifica correctamente un tipo de habitación
    	roomType -> exists (rt : RoomType | rt.name = res.rtname)
    - comprobamos que no hay una reserva existente con las mismas condiciones
    	reservation -> exists(r : Reservation | r.hotelId = res.hotelId and 
    	r.DatesRange = res.DatesRange and r.claimed = res.allocation->Empty)
 
 post:
 	- Buscamos una habitacion libre que cumpla con lo especificado y 
 	  creamos una reserva
 		let newReservation = Reservation
 		newReservation.hotel = Set{select(h:Hotel | h.id = res.hotelId),
		newReservation.allocation = select(r:Reservation | r.HotelId =
        res.HotelId) and r.datesRange = res.datesRange and r.claimed = 
        r.allocation -> Empty )} -> asSequence() -> first
		newReservation.claimed = newReservation.allocation -> NoEmpty
		newReservation.datesRange = res.datesRange
	- Devolvemos la habitación y la reserva
		resRef = newReservation.resRef
		result = newReservation.claimed
 
```



