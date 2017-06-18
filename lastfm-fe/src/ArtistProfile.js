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
            this.setState({results: res.data, statusMsg: ''}, () => {
                const $ = window.$;
                const id = "#artistProfileModal" + this.props.mbid;
                $(id).modal()
            });
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
                <div className="col-xs-3 col-sm-1">
                    <img onClick={this.fetchArtistTopTracks} src={this.props.imageURL} alt=""/>
                </div>
                <div className="col-xs-3 col-sm-1">
                    {this.props.name}
                </div>
                <div className="modal fade"
                     id={"artistProfileModal" + this.props.mbid}
                     tabIndex="-1"
                     role="dialog"
                     aria-labelledby={"artistProfile" + this.props.mbid}>
                    <div className="modal-dialog" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <h4 className="modal-title"
                                    id={"artistProfileLabel" + this.props.mbid}>{this.props.name} Top Tracks</h4>
                            </div>
                            <div className="modal-body">
                                <div className="col-xs-12">
                                    {topTracks}
                                    <PaginationButtons
                                        includeNextButton={this.state.results.length > 0}
                                        includePreviousButton={this.state.pageNumber > 1}
                                        onNext={this.onNext}
                                        onPrevious={this.onPrevious}/>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-default" data-dismiss="modal">Close
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ArtistProfile;