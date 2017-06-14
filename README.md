# lastfm-popular-artists

lastfm-popular-artists is an API server that makes information from Last.fm available through the following APIs

A React front end can be found in the [lastfm-fe](lastfm-fe) directory

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

The following environment variables can be set to configure lastfm-popular-artists. As a convention, config names 
starting with LAST_FM_REMOTE_ refers to configurations affecting the connection 
to the remote [last.fm API](http://www.last.fm/api/intro).  LAST_FM_API_SERVER_ refers to this API server.  

LAST_FM_REMOTE_API_KEY (required) This is your last fm api key

LAST_FM_API_REMOTE_API_BASE (optional, default https://ws.audioscrobbler.com/2.0/?method=) The API base path to be used
to connect to the last.fm API

LAST_FM_API_REMOTE_TOP_ARTIST_METHOD (optional, default geo.gettopartists) The API method for the 
[geo.getTopArtists API](http://www.last.fm/api/show/geo.getTopArtists)

LAST_FM_API_REMOTE_ARTIST_INFO_METHOD (optional, default artist.getInfo) The API method for the 
[artist.getInfo API](http://www.last.fm/api/show/artist.getInfo)

LAST_FM_API_REMOTE_ARTIST_TOP_TRACKS_METHOD (optional, default artist.gettoptracks) The API method for the 
[artist.gettoptracks API](http://www.last.fm/api/show/artist.gettoptracks)


LAST_FM_API_SERVER_PORT (optional, default 8080) The port the Last fm Popular Artists API server will listen on


# Building 

Run `gradle build` to build the project. This will compile the project, run checkstyle 
using the [Google Style](config/checkstyle/checkstyle.xml) and run unit tests. 

# Running

Gradle builds an executable jar and places it in `build\libs\`.  
The jar can be run with `java -jar lastfm-popular-artists-1.0-SNAPSHOT`

# Notes

Several design choices were inspired by the Twelve Factor App approach to building cloud native services. 

- Logs are written to Standard out, see [Twelve-Factor App logging principle](https://12factor.net/logs)
- All configuration is done via environment variables, see [Twelve-Factor App config principle](https://12factor.net/config)
- There is no caching in the API server, to keep the server stateless. See [Twelve-Factor App processes principle](https://12factor.net/processes)
- The API server binds to a port and exposes a (minimal) HTTP server. See [Twelve-Factor App port binding principle](https://12factor.net/port-binding) 
