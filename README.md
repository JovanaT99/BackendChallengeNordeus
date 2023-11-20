# Nordeus BackendChallenge
## Description
This is Java Spring Boot Backend created for Nordeus Backend Challenge at Job Fair 2023.
Requirements for this backend were to create API that will allow users (managers) to:
- Get list of active auctions
- Join an auction
- Bid on an auction
 
Backend should also:
- Generate new auctions automatically every 10 minutes
- Notify users about:
    - End of auction
    - New bid places on auction they follow
    - New auctions 
 
*Note: Other systems like: authentification, caching, deployment (dokcer) etc were not required.*
 
## Implementation overview
There Auctions, Managers, Players and Bids.
Each Auction has one player attached to it, and Managers can join (follow) or bid on that auction. With each bid, price of that auction goes up by 1, and if bid is done in last 5 seconds before auctions end, then auction is prolonged by 5 seconds. When auction ends no new bids will be allowed. Each 10 minutes 10 new auctions are created. 
 
Managers are notified about new auctions, when auctions that they joined are ended, and when there is new bid on auction that they joined.
 
## Running the backend
Please edit `application.properties` before running the backend as DB connection details it needs to be configured. You can also set SMTP config if you want to use your own.
 
## Endpoints 
Endpoints that are implemented and can be used by Frontend are:
 
| Endpoint      | Description |
| ----------- | ----------- |
| GET /auctions      | Returns all active auctions       |
| GET /auctions/{auctionId}   | Returns auction and it's bids        |
| POST /auctions/{auctionId}/follow?managerId={managerId}   | Allows manager to join/follow the auction        |
| POST /auctions/{auctionId}/bid?managerId={managerId}   | Allows manager to bid on the auction        |
| POST /managers/{managerId}/auctions   | Allows manager to list all his auctions        |
| POST /players   | Lists all players        |
 
### Auctions endpoint
This endpoint returns a list of all active auctions that managers can bid on.
```
GET /auctions
Response: 
[
  {
    "id": 1,
    "player": {
      "id": 1,
      "name": "Jovana 0",
      "position": "Golman",
      "rank": 1,
      "createdAt": "2023-11-20T17:12:33.697714"
    },
    "startPrice": 146.0,
    "currentPrice": 146.0,
    "status": "active",
    "startAt": "2023-11-20T17:12:33.7146",
    "endAt": "2023-11-20T17:14:33.714613",
    "updatedAt": "2023-11-20T17:12:33.716116",
    "createdAt": "2023-11-20T17:12:33.716095"
  },
  {
    "id": 2,
    "player": {
      "id": 2,
      "name": "Andrej 1",
      "position": "Golman",
      "rank": 7,
      "createdAt": "2023-11-20T17:12:33.718132"
    },
    "startPrice": 158.0,
    "currentPrice": 158.0,
    "status": "active",
    "startAt": "2023-11-20T17:12:33.718863",
    "endAt": "2023-11-20T17:14:33.718869",
    "updatedAt": "2023-11-20T17:12:33.719029",
    "createdAt": "2023-11-20T17:12:33.719019"
  },
  ,,,
  {
    "id": 10,
    "player": {
      "id": 10,
      "name": "Andrej 9",
      "position": "Napadač",
      "rank": 3,
      "createdAt": "2023-11-20T17:12:33.734659"
    },
    "startPrice": 196.0,
    "currentPrice": 196.0,
    "status": "active",
    "startAt": "2023-11-20T17:12:33.735492",
    "endAt": "2023-11-20T17:14:33.735502",
    "updatedAt": "2023-11-20T17:12:33.735752",
    "createdAt": "2023-11-20T17:12:33.735736"
  }
]
```
 
### Managers auctions endpoint
This endpoint returns all auctions that manager follows or has bid on.
```
GET /managers/{managerId}/auctions
Response: 
[
  {
    "id": 1,
    "player": {
      "id": 1,
      "name": "Jovana 0",
      "position": "Golman",
      "rank": 1,
      "createdAt": "2023-11-20T17:12:33.697714"
    },
    "startPrice": 146.0,
    "currentPrice": 147.0,
    "status": "active",
    "startAt": "2023-11-20T17:12:33.7146",
    "endAt": "2023-11-20T17:14:33.714613",
    "updatedAt": "2023-11-20T17:14:07.622809",
    "createdAt": "2023-11-20T17:12:33.716095"
  }
]
```
 
### Auction and it's bids
Returns auction details and bids on that auction
```
Reponse: 
{
  "auction": {
    "id": 1,
    "player": {
      "id": 1,
      "name": "Jovana 0",
      "position": "Golman",
      "rank": 1,
      "createdAt": "2023-11-20T17:12:33.697714"
    },
    "startPrice": 146.0,
    "currentPrice": 147.0,
    "status": "active",
    "startAt": "2023-11-20T17:12:33.7146",
    "endAt": "2023-11-20T17:14:33.714613",
    "updatedAt": "2023-11-20T17:14:07.622809",
    "createdAt": "2023-11-20T17:12:33.716095"
  },
  "bids": [
    {
      "id": 1,
      "manager": {
        "id": 1,
        "name": "Jovana",
        "email": "jovanablagojevic98@gmail.com",
        "phone": 638121528,
        "createdAt": "2023-11-20T17:12:33.772186"
      },
      "price": 146.0,
      "bidAt": "2023-11-20T17:14:07.605115",
      "updatedAt": "2023-11-20T17:14:07.605842",
      "createdAt": "2023-11-20T17:14:07.605821"
    }
  ]
}
```
 
### Join/Follow auction
This endpoint allows manager to follow auction and get notification about it.
```
POST /auctions/{auctionId}/follow?managerId={managerId}
Response: {
	"id": 1,
	"auction": {
		"id": 1,
		"player": {
			"id": 1,
			"name": "Jovana 0",
			"position": "Golman",
			"rank": 1,
			"createdAt": "2023-11-20T17:12:33.697714"
		},
		"startPrice": 146.0,
		"currentPrice": 147.0,
		"status": "active",
		"startAt": "2023-11-20T17:12:33.7146",
		"endAt": "2023-11-20T17:14:33.714613",
		"updatedAt": "2023-11-20T17:14:07.622809",
		"createdAt": "2023-11-20T17:12:33.716095"
	},
	"manager": {
		"id": 1,
		"name": "Jovana",
		"email": "jovanablagojevic98@gmail.com",
		"phone": 638121528,
		"createdAt": "2023-11-20T17:12:33.772186"
	}
}
```
 
### Bid on an auction
This endpoint allows manager to bid on auction (in addition in follows the auction).
```
POST /auctions/{auctionId}/bid?managerId={managerId}
Response: 
{
	"id": 1,
	"auction": {
		"id": 1,
		"player": {
			"id": 1,
			"name": "Jovana 0",
			"position": "Golman",
			"rank": 1,
			"createdAt": "2023-11-20T17:12:33.697714"
		},
		"startPrice": 146.0,
		"currentPrice": 147.0,
		"status": "active",
		"startAt": "2023-11-20T17:12:33.7146",
		"endAt": "2023-11-20T17:14:33.714613",
		"updatedAt": "2023-11-20T17:14:07.622809",
		"createdAt": "2023-11-20T17:12:33.716095"
	},
	"manager": {
		"id": 1,
		"name": "Jovana",
		"email": "jovanablagojevic98@gmail.com",
		"phone": 638121528,
		"createdAt": "2023-11-20T17:12:33.772186"
	},
	"price": 146.0,
	"bidAt": "2023-11-20T17:14:07.605115",
	"updatedAt": "2023-11-20T17:14:07.605842",
	"createdAt": "2023-11-20T17:14:07.605821"
}
```
 
### Players endpint
This endpoint allows manager to bid on auction (in addition in follows the auction).
```
POST /players
Response: 
 
  {
    "id": 1,
    "name": "Jovana 0",
    "position": "Golman",
    "rank": 1,
    "createdAt": "2023-11-20T17:12:33.697714"
  },
  {
    "id": 2,
    "name": "Andrej 1",
    "position": "Golman",
    "rank": 7,
    "createdAt": "2023-11-20T17:12:33.718132"
  },
  {
    "id": 3,
    "name": "Zlatko 2",
    "position": "Golman",
    "rank": 8,
    "createdAt": "2023-11-20T17:12:33.719962"
  },
  ...
]
```
