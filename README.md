Book My Show : Class Diagram
![alt text](https://i.imgur.com/Tnoj8eT.png)

Steps to build:

Download source code and run run `mvn clean install`.

Run `BookmyshowApplication` 
You can see the database here `http://localhost:8080/h2-console/` 

<img width="432" alt="image" src="https://user-images.githubusercontent.com/1450268/176239846-75776627-73ad-4f03-876e-16748a89153f.png">

Sample usage:

Admin flow:

Create Theaters:
```
curl --request POST \
  --url http://localhost:8080/admin/theaters \
  --header 'Content-Type: application/json' \
  --data '[
  {
    "name": "PVR",
    "city": "HYD",
    "address": {
      "street": "FORUMMALL",
      "pincode": "500049"
    }
  },
  {
    "name": "AMB",
    "city": "HYD",
    "address": {
      "street": "SUJANAMALL",
      "pincode": "500001"
    }
  }
]'
```

Add seats to theaters:
```
curl --request POST \
  --url http://localhost:8080/admin/theaterseats \
  --header 'Content-Type: application/json' \
  --data '[
  {
    "theater": 1,
		"seatType": "BASIC"
  },
 {
    "theater": 1,
	 		"seatType": "BASIC"
  },
	{
    "theater": 1,
		"seatType": "PREMIUM"
  },
	{
    "theater": 2,
		"seatType": "BASIC"
  },
	{
    "theater": 2,
		"seatType": "PREMIUM"
  }
]'
```

Create Movies:

```
curl --request POST \
  --url http://localhost:8080/admin/movies \
  --header 'Content-Type: application/json' \
  --data '[
	{
		"name": "THOR"
	},
	{
		"name": "VIKRAM"
	}
 ]
'
```

Create Shows:

```
curl --request POST \
  --url http://localhost:8080/admin/shows \
  --header 'Content-Type: application/json' \
  --data '[
	{
		"date": "2022-06-25",
		"startTime": "15:00:00",
		"movie": "1",
		"theater": "1"
	},
	{
		"date": "2022-06-25",
		"startTime": "18:00:00",
		"movie": "1",
		"theater": "2"		
	},
	{
		"date": "2022-06-25",
		"startTime": "16:00:00",
		"movie": "2",
		"theater": "1"

	},
	{
		"date": "2022-06-26",
		"startTime": "17:00:00",
		"movie": "2",
		"theater": "2"
		
	}
]'
```

Create Customers:

```
curl --request POST \
  --url http://localhost:8080/admin/customers \
  --header 'Content-Type: application/json' \
  --data '[
  {
    "name": "John"
  },
 {
    "name": "Jacob"
  }
]'
```

Create Offers for Theaters

```
curl --request POST \
  --url http://localhost:8080/admin/offers \
  --header 'Content-Type: application/json' \
  --data '[
  {
    "offerType": "THIRD_TICKET",
		"theaters": [1,2]
  },
 {
    "offerType": "AFTERNOON_TICKET",
		"theaters": [1]
  }
]'
```


User flow:

Fetch movie shows:

```
curl --request GET \
  --url http://localhost:8080/bms/movieshows \
  --header 'Content-Type: application/json' \
  --data '{
"moviename": "THOR", 
"date": "2022-06-25",
"city": "HYD"
}
'
```

Fetch Seats for a show:

```
curl --request GET \
  --url http://localhost:8080/bms/showseats \
  --header 'Content-Type: application/json' \
  --data '{
"showid": 1
}
'
```

Select seats and start booking (reservation):

```
curl --request POST \
  --url http://localhost:8080/bms/reserveseats \
  --header 'Content-Type: application/json' \
  --data '{
	
  "seats": [
    1,2
  ],
  "customerId": 1
}'
```

Proceed with payment. 
If payment is successful, below query converts the reservation to a confirmed booking:
```
curl --request POST \
  --url http://localhost:8080/bms/confirmseats \
  --header 'Content-Type: application/json' \
  --data '{
	
  "seats": [
    1,2
  ],
  "customerId": 1
}'
```
If payment is not completed withing the session timeout period, the reservation is deleted and seats are again available.

