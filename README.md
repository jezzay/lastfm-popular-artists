# lastfm-popular-artists

lastfm-popular-artists is an API server that makes information from Last.fm available through the following APIs

# API Endpoints

## Top Artist by country

GET http://{host}:{port}/api/geo/top-artist/{country}/{pageNumber}/

Example: GET http://localhost:8080/api/geo/top-artist/Australia/1/

Path Parameters: 

{country} An ISO 3166-1 country name. eg Australia, Spain

{pageNumber} The page number, starting at 1

## Artist Info

GET http://{host}:{port}/api/artist/{mbid}/

Example: GET http://localhost:8080/api/artist/b071f9fa-14b0-4217-8e97-eb41da73f598/

Path Parameters: 

{mbid} The musicbrainz id for the artist


## Artist Top Tracks

GET http://{host}:{port}/api/artist/{mbid}/top-tracks/{pageNumber}/

Example: GET http://localhost:8080/api/artist/b071f9fa-14b0-4217-8e97-eb41da73f598/top-tracks/1/

Path Parameters: 

{mbid} The musicbrainz id for the artist

{pageNumber} The page number, starting at 1

# Configuration 

The following environment variables can be set to configure lastfm-popular-artists 

LAST_FM_API_KEY (required) This is your last fm api key

LAST_FM_API_SERVER_PORT (optional, default 8080) The port the Last fm Popular Artists API server will listen on

