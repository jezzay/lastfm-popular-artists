import React, {Component} from "react";
import Api from "./Api";
import ArtistResult from "./ArtistResult";

class ArtistGeoLookup extends Component {
    constructor() {
        super();
        this.state = {country: '', pageNumber: 1, results: [], statusMsg: ''};
        this.handleCountryUpdate = this.handleCountryUpdate.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.fetchArtists = this.fetchArtists.bind(this);
        this.nextButton =
            <button type="button" onClick={this.handleSubmit} name="next_page" className="geo-next-page">
                Next Page
            </button>;
        this.previousButton =
            <button type="button" onClick={this.handleSubmit} name="previous_page" className="geo-next-page">
                Previous Page
            </button>
    }

    handleCountryUpdate(event) {
        const name = event.target.name;
        this.setState({
            [name]: event.target.value,
            pageNumber: 1 // Reset to first page number when country changes
        });
    }

    handleSubmit(event) {
        console.log('submitted: ', this.state);
        switch (event.target.name) {
            case "geoLookup":
                this.fetchArtists();
                break;
            case "next_page":
                this.setState({pageNumber: this.state.pageNumber + 1}, this.fetchArtists);
                break;
            case "previous_page":
                this.setState({pageNumber: this.state.pageNumber - 1}, this.fetchArtists);
                break;
            default:
                break;
        }
        event.preventDefault();
    }

    fetchArtists() {
        // Start search after 3 characters
        if (this.state.country.length > 3) {
            this.setState({statusMsg: 'Loading...'});
            Api.topArtistByCountry(this.state.country, this.state.pageNumber).then((res) => {
                this.setState({results: res.data, statusMsg: ''});
            }, (err) => {
                console.error(err.response.then((reason) => {
                    console.log(`Failed because of ${reason.error}`);
                    this.setState({results: [], statusMsg: reason.error})
                }));
            });
        } else {
            this.setState({statusMsg: 'Please enter a full country name'});
        }

    }

    render() {
        let resultDom;
        let paginationBody;
        if (this.state.results.length > 0) {
            resultDom =
                <div>
                    {this.state.results.map(function (res) {
                        return <ArtistResult name={res.name} imageURL={res.imageURL} mbid={res.mbid}/>;
                    })}
                </div>;
            paginationBody = this.state.pageNumber > 1 ?
                <div>{this.previousButton}{this.nextButton}</div> :
                <div>{this.nextButton}</div>
        } else {
            resultDom = <div>{this.state.statusMsg}</div>
        }

        return (
            <div className="ArtistGeoLookup">
                <form name="geoLookup" onSubmit={this.handleSubmit}>
                    <p>Please enter a ISO 3166-1 country name</p>
                    <label form="country">
                        Country
                        <input name="country" type="text" value={this.state.country}
                               onChange={this.handleCountryUpdate}/>
                    </label>
                    <p>
                        <button className="geo-submit">
                            Submit
                        </button>
                    </p>
                </form>

                <div>
                    Results:
                    {resultDom}
                    {paginationBody}
                </div>
            </div>
        );
    }
}

export default ArtistGeoLookup;
