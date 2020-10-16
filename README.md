## Product Comparison Service

A service that helps the end customer to decide which website or retail shop
they can use to buy their product by comparing data from different providers.

Assume there is an AI service that uses customer reviews for different products and provides recommendations on
product providers, so we need to return the search result ranked based on result from recommendation service.

``Note: please use Test API to import test 10000 data.``

## Tech Stack

- Java 11
- MongoDB
- Spring
- Docker
- Lombok
- MapStruct
- LRU-cache (native)
- Swagger

## Docker
To deploy the app you can simply run `docker-compose.yml`

## API

    # Actuator
    # http://localhost:8080/actuator
    
    # Swagger
    # http://localhost:8080/swagger-ui/


### Find products
    # GET http://localhost:8080/api/v1/products?name={name}&category={categoty}
    # example:
    # GET http://localhost:8080/api/v1/products?name=iPhone 12&category=MOBILE_PHONE

response:
```JSON
[
    {
        "id": "19e3a8fd-66c0-4ce1-9b0e-35c2a5591bb3",
        "name": "iPhone 12",
        "category": "MOBILE_PHONE",
        "provider": "Apple",
        "score": 99.2
    },
    {
        "id": "47bbe177-273f-4b9f-9a9e-22bd4582431e",
        "name": "iPhone 12",
        "category": "MOBILE_PHONE",
        "provider": "Amazon",
        "score": 83.67
    },
    {
        "id": "47265665-f754-4e15-8bfc-2cc1cf66d4e5",
        "name": "iPhone 12",
        "category": "MOBILE_PHONE",
        "provider": "Ebay",
        "score": 64.3
    }
]
```

### Import products

    # PUT http://localhost:8080/api/v1/products

body:
```JSON
[
    {
        "name": "Model 3",
        "category": "VEHICLE",
        "provider": "Tesla"
    },
    {
        "name": "Model 3",
        "category": "VEHICLE",
        "provider": "Ebay"
    },
    {
        "name": "Model 3",
        "category": "VEHICLE",
        "provider": "Google"
    },
    {
        "name": "Model Y",
        "category": "VEHICLE",
        "provider": "Tesla"
    }
]
```
and then:

    # GET http://localhost:8080/api/v1/products?name=Model 3&category=VEHICLE

```JSON
[
    {
        "id": "f2109e66-889e-4b7c-bb39-a3b402f1821c",
        "name": "Model 3",
        "category": "VEHICLE",
        "provider": "Tesla",
        "score": 100.0
    },
    {
        "id": "08100ba5-f8ec-4a18-a706-c13247cc232b",
        "name": "Model 3",
        "category": "VEHICLE",
        "provider": "Google",
        "score": 65.3
    },
    {
        "id": "762fadac-28ce-4936-9d36-55ade96adf1c",
        "name": "Model 3",
        "category": "VEHICLE",
        "provider": "Ebay",
        "score": 64.3
    }
]
```

## Test API

    # import 10000+ test products
    # POST http://localhost:8080//api/v1/test/products/import