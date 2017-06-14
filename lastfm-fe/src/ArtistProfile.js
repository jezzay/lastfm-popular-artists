import React, {Component} from "react";
import Api from "./Api";
import PaginationButtons from "./PaginationButtons";

class ArtistProfile extends Component {

    constructor(props) {
        super(props);
        this.state = {results: [], statusMsg: '', pageNumber: 1};
        this.fetchArtistTopTracks = this.fetchArtistTopTracks.bind(this);
        this.onNext = this.onNext.bind(this);
        this.onPrevious = this.onPrevious.bind(this);
    }

    fetchArtistTopTracks() {
        Api.artistTopTracks(this.props.mbid, this.state.pageNumber).then((res) => {
            this.setState({results: res.data, statusMsg: ''});
        }, (err) => {
            err.response.then((reason) => {
                console.log(`Failed because of ${reason.error}`);
                this.setState({results: [], statusMsg: reason.error})
            });
        });
    }

    onNext() {
        this.setState({pageNumber: this.state.pageNumber + 1}, this.fetchArtistTopTracks);
    }

    onPrevious() {
        this.setState({pageNumber: this.state.pageNumber - 1}, this.fetchArtistTopTracks);
    }

    render() {
        const containerStyle = {
            padding: 10
        };
        let topTracks;
        if (this.state.results.length > 1) {
            topTracks = <div>
                <ul>
                    {this.state.results.map(function (res) {
                        return <li>{res.name}</li>;
                    })}
                </ul>
            </div>;
        }
        return (
            <div className="container" style={containerStyle}>
                <div className="col-xs-1">
                    <img onClick={this.fetchArtistTopTracks} src={this.props.imageURL} alt=""/>
                </div>
                <div className="col-xs-3">
                    {this.props.name}
                </div>
                <div className="col-xs-8">
                    {topTracks}
                    <PaginationButtons
                        includeNextButton={this.state.results.length > 0}
                        includePreviousButton={this.state.pageNumber > 1}
                        onNext={this.onNext}
                        onPrevious={this.onPrevious}/>
                </div>
            </div>
        )
    }
}

export default ArtistProfile;