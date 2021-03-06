import React, {Component} from "react";
import ArtistProfile from "./ArtistProfile";
import PaginationButtons from "./PaginationButtons";
import state from './AppState'

class ArtistGeoLookup extends Component {
    constructor() {
        super();
        this.state = {country: '', pageNumber: 1, results: [], statusMsg: ''};
        this.handleCountryUpdate = this.handleCountryUpdate.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.fetchArtists = this.fetchArtists.bind(this);
        this.onNext = this.onNext.bind(this);
        this.onPrevious = this.onPrevious.bind(this);
    }

    handleCountryUpdate(event) {
        const name = event.target.name;
        this.setState({
            [name]: event.target.value,
            pageNumber: 1 // Reset to first page number when country changes
        });
    }

    onNext() {
        this.setState({pageNumber: this.state.pageNumber + 1}, this.fetchArtists);
    }

    onPrevious() {
        this.setState({pageNumber: this.state.pageNumber - 1}, this.fetchArtists);
    }

    handleSubmit(event) {
        this.fetchArtists();
        event.preventDefault();
    }

    fetchArtists() {
        // Start search after 3 characters
        if (this.state.country.length > 3) {
            this.setState({statusMsg: 'Loading...'});
            state.topArtistByCountry(this.state.country, this.state.pageNumber).then((res) => {
                this.setState({results: res, statusMsg: ''});
            }, (err) => {
                err.response.then((reason) => {
                    console.log(`Failed because of ${reason.error}`);
                    this.setState({results: [], statusMsg: reason.error})
                });
            });
        } else {
            this.setState({statusMsg: 'Please enter a full country name'});
        }

    }

    render() {
        let resultDom;
        if (this.state.results.length > 0) {
            resultDom =
                <div>
                    {this.state.results.map(function (res, i) {
                        return <ArtistProfile key={res.mbid} name={res.name} imageURL={res.imageURL} mbid={res.mbid}/>;
                    })}
                </div>;
        } else {
            resultDom = <div>{this.state.statusMsg}</div>
        }

        return (
            <div className="ArtistGeoLookup col-xs-12">
                <form name="geoLookup" onSubmit={this.handleSubmit}
                      className="col-xs-12 col-sm-6 col-md-offset-2 col-md-5 col-lg-offset-2 col-lg-3">
                    <p>Please enter a ISO 3166-1 country name</p>
                    <div className="form-group col-xs-8 col-sm-12">
                        <label htmlFor="country">Country</label>
                        <input name="country" type="text"
                               value={this.state.country}
                               className="form-control"
                               onChange={this.handleCountryUpdate}/>
                    </div>
                    <p className="col-xs-6 col-sm-12">
                        <button className="geo-submit btn btn-default">
                            Submit
                        </button>
                    </p>
                </form>

                <div className="col-xs-12 col-sm-6 col-md-2 col-lg-3">
                    Results:
                    {resultDom}
                    <PaginationButtons
                        includeNextButton={this.state.results.length > 0}
                        includePreviousButton={this.state.pageNumber > 1}
                        onNext={this.onNext}
                        onPrevious={this.onPrevious}/>
                </div>
            </div>
        );
    }
}

export default ArtistGeoLookup;
