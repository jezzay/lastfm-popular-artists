import Api from "./Api";

function AppState() {
    let _topArtistByCountry = {};
    let _topArtistTracks = {};

    function topArtistByCountry(country, pageNumber) {
        const pageIndexes = getPageIndexes(pageNumber);
        if (hasResultsAtPageNumber(_topArtistByCountry, country, pageNumber)) {
            return Promise.resolve(_topArtistByCountry[country].slice(pageIndexes.minIndex, pageIndexes.maxIndex));
        }
        return Api.topArtistByCountry(country, pageNumber).then((res) => {
            updateState(_topArtistByCountry, country, pageIndexes, res);
            return _topArtistByCountry[country].slice(pageIndexes.minIndex, pageIndexes.maxIndex)
        });
    }

    function artistTopTracks(mbid, pageNumber) {
        const pageIndexes = getPageIndexes(pageNumber);
        if (hasResultsAtPageNumber(_topArtistTracks, mbid, pageNumber)) {
            return Promise.resolve(_topArtistTracks[mbid].slice(pageIndexes.minIndex, pageIndexes.maxIndex));
        }
        return Api.artistTopTracks(mbid, pageNumber).then((res) => {
            updateState(_topArtistTracks, mbid, pageIndexes, res);
            return _topArtistTracks[mbid].slice(pageIndexes.minIndex, pageIndexes.maxIndex)
        });
    }

    function updateState(state, index, pageIndexes, res) {
        let data = res.data;
        if (state[index]) {
            // The last.fm remote API sometimes incorrectly returns too many results for a page. correct
            // this behaviour here, so only five results are shown at a time.
            if (data.length > 5) {
                state[index] = state[index].concat(data.slice(pageIndexes.minIndex, pageIndexes.maxIndex));
            } else {
                state[index] = state[index].concat(data);
            }
        } else {
            state[index] = data;
        }
    }

    function hasResultsAtPageNumber(state, index, pageNumber) {
        const pageIndexes = getPageIndexes(pageNumber);
        return state[index] && state[index][pageIndexes.minIndex] && state[index][pageIndexes.maxIndex];
    }

    function getPageIndexes(pageNumber) {
        const maxIndex = (pageNumber * 5);
        const minIndex = (pageNumber - 1) * 5;
        return {maxIndex, minIndex};
    }

    return {
        topArtistByCountry: topArtistByCountry,
        artistTopTracks: artistTopTracks
    }
}

const state = AppState();

export default state;

