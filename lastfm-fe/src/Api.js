function topArtistByCountry(country, pageNumber = 1) {
    return fetch(`api/geo/top-artist/${country}/${pageNumber}/`, {accept: 'application/json'})
        .then(mapHttpStatus)
        .then(toJSON)
}

function artistTopTracks(mbid, pageNumber = 1) {
    return fetch(`/api/artist/${mbid}/top-tracks/${pageNumber}/`, {accept: 'application/json'})
        .then(mapHttpStatus)
        .then(toJSON)
}

function mapHttpStatus(response) {
    if (response.status >= 200 && response.status < 300) {
        return response;
    }
    const error = new Error(`HTTP Error ${response.statusText}`);
    error.status = response.statusText;
    error.response = toJSON(response);
    throw error;
}

function toJSON(response) {
    return response.json();
}

const API = {topArtistByCountry, artistTopTracks};
export default API;